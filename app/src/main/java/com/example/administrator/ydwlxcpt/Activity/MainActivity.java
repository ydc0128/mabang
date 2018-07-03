package com.example.administrator.ydwlxcpt.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import com.example.administrator.ydwlxcpt.Adapter.DialogListViewAdapter;
import com.example.administrator.ydwlxcpt.Bean.Car;
import com.example.administrator.ydwlxcpt.Bean.Dingdan;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.Massage;
import com.example.administrator.ydwlxcpt.Bean.UpdateBean;
import com.example.administrator.ydwlxcpt.Bean.Worker;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.example.administrator.ydwlxcpt.Utils.NoDoubleClickListener;
import com.example.administrator.ydwlxcpt.Utils.NoFastClickUtils;
//import com.example.administrator.ydwlxcpt.Utils.UpdateConfig;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//客户端
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private List<String> xichegongdianhua;
    private String url = Contast.Domain + "api/CarList.ashx?";
    private String url_worker = Contast.Domain + "api/WorkerListUser.ashx?";
    private String url_dingdan = Contast.Domain + "api/OrderList.ashx?";
    private String url_gengxin = Contast.Domain + "api/VersionList.ashx";
    private String url_xiaoxi = Contast.Domain + "api/MessageList.ashx";
    private List<UpdateBean> gengxinList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private List<Worker> workers;
    private List<String> quxiaoyuanyin;
    private CircleImageView iv_cehua_touxiang;
    private TextView tv_cehua_yonghuming;
    private TextView tv_cehua_gengxin;
//    private TextView tv_cehua_huiyuan;
    private TextView tv_cehua_qianbao;
    private TextView tv_cehua_dingdan;
    private TextView tv_cehua_aiche;
    private TextView tv_cehua_shezhi;
    private TextView tv_cehua_bangzhu;
    private TextView tv_cehua_fuwujieshao;
    private MapView mMapView;
    private TextView cheliangweizhi;
    private Button xiche;
    private Button baoxian;
    private Button weizhang;
    private ImageButton ib_shuaxin;
    private CircleImageView iv_touxiang;
    private ImageView iv_logout;
    private Activity activity;
    private ImageView iv_main_msg;
    private boolean isFirstLocation = true;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private BaiduMap mBaiduMap;
    private String dangqianweizhi;
    private RelativeLayout rl_xiaoxi_tongzhi;
    private double lat;
    private double lon;
    private double finallat;
    private double finallon;
    private GeoCoder mSearch;
    private List<String> initData = new ArrayList<>();
    private List<Car> carList = new ArrayList<>();
    private int from;
    private List<String> Dingdan_car = new ArrayList<>();
    private String phone = "02987365853";
    //private Intent intent10 = new Intent("com.angel.Android.MUSIC");
    private List<Dingdan> dingdanList;
    private TextView tv_new_msg;

    private TextView tv_dingdanshuliang;
    private RelativeLayout rl_dingdan;
    private List<Dingdan> jinxingzhongList;
    private List<UpdateBean> banbens;
    private int shuliang=0;
    private List<Massage> massageList,massages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        //使用百度地图的任何功能都需要先初始化这段代码  最好放在全局中进行初始化
        //百度地图+定位+marker比较简单 我就不放到全局去了
        SDKInitializer.initialize(getApplicationContext());
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=5a77c28d");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        activity = this;
//        UpdateConfig.initGet(this);
//        Intent intentdd = new Intent(MainActivity.this, ChuangKou.class);
//         startActivity(intentdd);
//        new Thread(new GameThread()).start();
//        autoUpdate();
//        if (worker.getW_Isonline() == 0) {
//        } else {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(MainActivity.this);
//       }
        Intent intent = getIntent();
        from = intent.getIntExtra("from", -1);
        //获取控件
        initViews();
        //获取侧滑对象内容
        initCehuaViews();
        //侧滑菜单监听设置
        setDraweLayout();
//      setDraweyout();
        //获取BaiduMap对象
        mBaiduMap = mMapView.getMap();
        //获取POI搜索对象
        mSearch = GeoCoder.newInstance();
        //创建地理编码检索监听者
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(MainActivity.this, "抱歉，未能找到结果",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(MainActivity.this, "抱歉，未能找到结果",
                            Toast.LENGTH_LONG).show();
                }
                cheliangweizhi.setText(result.getSematicDescription());
                initWorker();
            }
        };
        //设置地理编码检索监听者
        mSearch.setOnGetGeoCodeResultListener(listener);
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);
        //配置定位参数
        initLocation();
        //开始定位
        mLocationClient.start();
        //监听地图中心点位置
        initListener();
//        xiaoxi();
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);

        }
//        // 不显示地图上比例尺
//        mMapView.showScaleControl(false);
//
//        // 不显示地图缩放控件（按钮控制栏）
//        mMapView.showZoomControls(false);
//        for (Dingdan item : dingdanList) {
//            int type = item.getO_TypeID();
//                if (type == 4) {
//                startService(intent10);
//                }
//        }
//        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
//        } else {
//            doBspatch();
//        }
//        gengxin();
//         String requestStri = "{\"code\":0,\"data\":{\"download_url\":\"http://www.mabangxiche.com/download/mabangxiche.apk\",\"force\":false,\"update_content\":\"1.新增加自动更新接口2.修复马帮洗车客户端BUG3.修改订单详情。\",\"v_code\":\"10\",\"v_name\":\"v1.0.20180424\",\"v_sha1\":\"7db76e18ac92bb29ff0ef012abfe178a78477534\",\"v_size\":32.45MB}}";
//        networkAutoUpdate(requestStri);
    }

    private void setDraweLayout() {
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (checkLogin()) {
                    if (TextUtils.isEmpty(Contast.user.getU_Image())) {
                        Picasso.with(MainActivity.this).load(R.drawable.cehuamorentouxiang).into(iv_cehua_touxiang);
                    } else {
                        Uri image = Uri.parse(Contast.Domain + Contast.user.getU_Image());
                        Picasso.with(MainActivity.this).load(image)
                                .placeholder(R.drawable.cehuamorentouxiang)
                                .error(R.drawable.cehuamorentouxiang)
                                .into(iv_cehua_touxiang);
                    }
                    if (TextUtils.isEmpty(Contast.user.getU_Name())) {
                        tv_cehua_yonghuming.setText(Contast.user.getU_Tel());
                    } else {
                        tv_cehua_yonghuming.setText(Contast.user.getU_Name());
                    }
//                    if (Contast.user.getU_Grade() == 1) {
//                        tv_cehua_huiyuan.setText("普通会员");
//                    } else if (Contast.user.getU_Grade() == 2) {
//                        tv_cehua_huiyuan.setText("白金会员");
//                    } else if (Contast.user.getU_Grade() == 3) {
//                        tv_cehua_huiyuan.setText("钻石会员");
//                    }
                    tv_cehua_yonghuming.setVisibility(View.VISIBLE);
//                    tv_cehua_huiyuan.setVisibility(View.VISIBLE);
                } else {
                    //如果没有登录，则提示用户登录
                    Picasso.with(MainActivity.this).load(R.drawable.cehuamorentouxiang).into(iv_cehua_touxiang);
                    tv_cehua_yonghuming.setText("未登录");
                    tv_cehua_yonghuming.setVisibility(View.VISIBLE);
//                    tv_cehua_huiyuan.setVisibility(View.INVISIBLE);
                }
            }
        };
        drawerLayout.setDrawerListener(toggle);
    }

    private void initCehuaViews() {
//      bt_chaxun = (Button) findViewById(R.id.bt_chaxun_zhuangtai);
        drawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        iv_cehua_touxiang = (CircleImageView) findViewById(R.id.iv_cehua_touxiang);
        tv_cehua_yonghuming = (TextView) findViewById(R.id.tv_cehua_phone);
//        tv_cehua_huiyuan = (TextView) findViewById(R.id.tv_cehua_huiyuan);
        tv_cehua_fuwujieshao=(TextView)findViewById(R.id.tv_cehua_fuwujiesao);
        tv_cehua_qianbao = (TextView) findViewById(R.id.tv_cehua_qianbao);
        tv_cehua_dingdan = (TextView) findViewById(R.id.tv_cehua_dingdan);
        tv_cehua_aiche = (TextView) findViewById(R.id.tv_cehua_aiche);
        tv_cehua_shezhi = (TextView) findViewById(R.id.tv_cehua_shezhi);
        tv_cehua_bangzhu = (TextView) findViewById(R.id.tv_cehua_bangzhu);

//      tv_cehua_gengxin = (TextView) findViewById(R.id.tv_cehua_gengxin);
        iv_cehua_touxiang.setOnClickListener(this);
        tv_cehua_yonghuming.setOnClickListener(this);
//        tv_cehua_huiyuan.setOnClickListener(this);
        tv_cehua_qianbao.setOnClickListener(this);
        tv_cehua_dingdan.setOnClickListener(this);
        tv_cehua_aiche.setOnClickListener(this);
        tv_cehua_shezhi.setOnClickListener(this);
        tv_cehua_bangzhu.setOnClickListener(this);
        tv_cehua_fuwujieshao.setOnClickListener(this);
//        bt_chaxun.setOnClickListener(this);
//        tv_cehua_gengxin.setOnClickListener(this);
//        tv_cehua_gengxin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String requestStri = "{\"code\":0,\"data\":{\"download_url\":\"http://wap.apk.anzhi.com/data3/apk/201512/20/55089e385f6e9f350e6455f995ca3452_26503500.apk\",\"force\":false,\"update_content\":\"测试更新接口\",\"v_code\":\"10\",\"v_name\":\"v1.0.0.16070810\",\"v_sha1\":\"7db76e18ac92bb29ff0ef012abfe178a78477534\",\"v_size\":12365909}}";
//                networkAutoUpdate(requestStri);
//            }
//        });
        xiche.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if (checkLogin()) {
                    checkCar(0);
                } else {
                    startLogin();
                }
            }
        });
        tv_cehua_bangzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkLogin()) {
                    new AlertDialog.Builder(activity).setTitle("您确认拨打电话吗？")
//                        .setIcon(android.R.drawable.ic_dialog_info)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("返回", null)
                            .show();
                } else {
                    startLogin();
                }
            }
        });
        showDrawerLayout();
    }

    private void showDrawerLayout() {
        if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.openDrawer(Gravity.LEFT);
        } else {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    private void initViews() {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bdmapView);
        workers = new ArrayList<>();
        //记录当前Maker标记位置的地理信息
        cheliangweizhi = (TextView) findViewById(R.id.tv_main_cheliangweizhi);
        //获取控件
        iv_touxiang = (CircleImageView) findViewById(R.id.iv_main_touxiang);
        xiche = (Button) findViewById(R.id.btn_main_xiche);
        baoxian = (Button) findViewById(R.id.btn_main_baoxian);
        weizhang = (Button) findViewById(R.id.btn_main_weizhang);
        ib_shuaxin = (ImageButton) findViewById(R.id.ib_main_shuaxin);
        rl_xiaoxi_tongzhi = (RelativeLayout) findViewById(R.id.rl_xiaoxi_tongzhi);
        tv_new_msg = (TextView) findViewById(R.id.tv_new_msg);
        rl_dingdan = (RelativeLayout) findViewById(R.id.rl_dingdan_xinxi);
        ImageView iv_dingdan = (ImageView) findViewById(R.id.iv_main_dingdan);
        tv_dingdanshuliang = (TextView) findViewById(R.id.tv_dingdan_shuliang);

        rl_dingdan.setOnClickListener(this);
        xiche.setOnClickListener(this);
        baoxian.setOnClickListener(this);
        weizhang.setOnClickListener(this);
        ib_shuaxin.setOnClickListener(this);
        iv_touxiang.setOnClickListener(this);
        rl_xiaoxi_tongzhi.setOnClickListener(this);
        rl_dingdan.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if (checkLogin()) {
                    Intent intentdd = new Intent(MainActivity.this, CehuaDiaLayout.class);
                    startActivity(intentdd);

                } else {
                    startLogin();
                }
            }
        });
    }
/**
 * 弹窗
 * <p>
 * 地图状态改变
 */
//    private void show1() {
//        refelash();
//        Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
//        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_normal, null);
//        bottomDialog.setContentView(contentView);
//        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
//        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
//        contentView.setLayoutParams(layoutParams);
//        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
//        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
//        bottomDialog.show();
//    }

    /**
     * 地图状态改变
     */
    private void initListener() {
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus status) {
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus status) {
                updateMapState(status);
            }

            @Override
            public void onMapStatusChange(MapStatus status) {
            }
        });
    }

    private void updateMapState(MapStatus status) {
        LatLng mCenterLatLng = status.target;
        /**获取经纬度*/
        finallat = mCenterLatLng.latitude;
        finallon = mCenterLatLng.longitude;
        LatLng point = new LatLng(finallat, finallon);
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(point));
    }

    /**
     * 设置中心点
     */
    private void setUserMapCenter() {
        Log.v("pcw", "setUserMapCenter : lat : " + lat + " lon : " + lon);
        LatLng cenpt = new LatLng(lat, lon);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
//        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(13).build()));
    }

    /**
     * 配置定位参数
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 5000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_main_xiche:
//                if (checkLogin()) {
//                    checkCar(0);
//                } else {
//                    //如果没有登录，直接跳转登录界面
//                    startLogin();
//                }
//                break;
//            case R.id.bt_chaxun_zhuangtai:
//                if (dingdanhao == "") {
//                    Intent intent = new Intent(MainActivity.this, DialogActivity.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(MainActivity.this, "您没有新的订单", Toast.LENGTH_SHORT).show();
//                }
//                break;
            case R.id.rl_xiaoxi_tongzhi:
                if (checkLogin()) {
                    Intent intent4 = new Intent(MainActivity.this, MassageActivity.class);
                    startActivity(intent4);
                } else {
                    startLogin();
                }
                break;
            case R.id.btn_main_baoxian:
                if (checkLogin()) {
                    //如果已经登录，则跳转填写表单界面
                    checkCar(2);
//                    View bottomView = View.inflate(MainActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
//                    ListView listView = (ListView) bottomView.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
//                    listView.setAdapter(new DialogListViewAdapter(MainActivity.this, initData));//ListView设置适配器
//                    final AlertDialog dialog = new AlertDialog.Builder(this)
//                            .setTitle("选择爱车").setView(bottomView)//在这里把写好的这个listview的布局加载dialog中
//                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            }).create();
//                    dialog.show();
//                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//响应listview中的item的点击事件
//
//                        @Override
//                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                                long arg3) {
//                            // TODO Auto-generated method stub
//
//                            TextView tv = (TextView) arg1.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
//                            String aiche = tv.getText().toString();
//                            Intent intent = new Intent(MainActivity.this, AddBaoxianActivity.class);
//                            intent.putExtra("aiche", aiche);
//                            startActivity(intent);
//                            dialog.dismiss();
//                        }
//                    });
                } else {
                    //如果没有登录，直接跳转登录界面
                    startLogin();
                }
                break;
            case R.id.btn_main_weizhang:
                if (checkLogin()) {
                    //如果已经登录，则跳转填写表单界面
                    checkCar(1);
                } else {
                    //如果没有登录，直接跳转登录界面
                    startLogin();
                }
                break;
            case R.id.ib_main_shuaxin:
                cheliangweizhi.setText(dangqianweizhi);
                setUserMapCenter();
                initWorker();
                initLocation();
                mLocationClient.start();
                initListener();
                break;
            case R.id.iv_main_touxiang:
                drawerLayout.openDrawer(Gravity.LEFT);//侧滑打开
                break;
//            case R.id.rl_dingdan_xinxi:
//                if (checkLogin()) {
//                    if (rl_dingdan.isClickable()) {
//                        Intent intentdd = new Intent(MainActivity.this, CehuaDiaLayout.class);
//                        startActivity(intentdd);
//                    }
//                } else {
//                    startLogin();
//                }
//                break;
            case R.id.iv_cehua_touxiang:
                if (checkLogin()) {
                    //如果已经登录，则跳转到我的相册进行头像选取
                    Intent intent = new Intent(MainActivity.this, GerenziliaoActivity.class);
                    startActivity(intent);
                } else {
                    //如果没有登录，直接跳转登录界面
                    drawerLayout.closeDrawer(Gravity.LEFT);
                    startLogin();
                }
                break;
            case R.id.tv_cehua_fuwujiesao:
                if (checkLogin()) {
                    //如果已经登录，则跳转到我的相册进行头像选取
                    Intent intent = new Intent(MainActivity.this, Fuwujieshao.class);
                    startActivity(intent);
                } else {
                    //如果没有登录，直接跳转登录界面
                    drawerLayout.closeDrawer(Gravity.LEFT);
                    startLogin();
                }
                break;
            case R.id.tv_cehua_phone:
                if (checkLogin()) {
                    //TODO
                    //如果已经登录，则查看个人详情
                } else {
                    //如果没有登录，直接跳转登录界面
                    drawerLayout.closeDrawer(Gravity.LEFT);
                    startLogin();
                }
                break;
//            case R.id.tv_cehua_huiyuan:
//                if (checkLogin()) {
//                    //TODO
//                    //如果已经登录,则查看会员详情
//                } else {
//                    //如果没有登录，直接跳转登录界面
//                    drawerLayout.closeDrawer(Gravity.LEFT);
//                    startLogin();
//                }
//                break;
            case R.id.tv_cehua_qianbao:
                if (checkLogin()) {
                    //TODO
                    //如果已经登录,则查看钱包详情
                    Intent intent = new Intent(MainActivity.this, MyMoneyActivity.class);
                    startActivity(intent);
                } else {
                    //如果没有登录，直接跳转登录界面
                    drawerLayout.closeDrawer(Gravity.LEFT);
                    startLogin();
                }
                break;
            case R.id.tv_cehua_dingdan:
                if (checkLogin()) {
                    //TODO
                    //如果已经登录,则查看订单详情
                    Intent intent = new Intent(MainActivity.this, MyDingdanActivity.class);
//                    String dizhi = cheliangweizhi.getText().toString();
//                    intent.putExtra("dizhi", dizhi);
                    startActivity(intent);
                } else {
                    //如果没有登录，直接跳转登录界面
                    drawerLayout.closeDrawer(Gravity.LEFT);
                    startLogin();
                }
                break;
            case R.id.tv_cehua_aiche:
                if (checkLogin()) {
                    //TODO
                    //如果已经登录,则查看爱车详情
                    Intent intent = new Intent(MainActivity.this, MyCarActivity.class);
                    startActivity(intent);
                } else {
                    //如果没有登录，直接跳转登录界面
                    drawerLayout.closeDrawer(Gravity.LEFT);
                    startLogin();
                }
                break;
            case R.id.tv_cehua_shezhi:
                if (checkLogin()) {
                    //TODO
                    //如果已经登录,则查看设置详情
                    Intent intent = new Intent(MainActivity.this, MyShezhiActivity.class);
                    startActivity(intent);
                } else {
                    //如果没有登录，直接跳转登录界面
                    drawerLayout.closeDrawer(Gravity.LEFT);
                    startLogin();
                }
                break;
            case R.id.tv_cehua_bangzhu:
                if (checkLogin()) {
                    //TODO
                    //如果已经登录,则查看帮助详情
                } else {
                    //如果没有登录，直接跳转登录界面
                    drawerLayout.closeDrawer(Gravity.LEFT);
                    startLogin();
                }
                break;
            default:
                break;
        }
    }

    private void checkDingdan(final Car car, final Dialog dialog) {
        FormBody.Builder params = new FormBody.Builder();
        params.add("keys", Contast.KEYS);
        params.add("O_UTel", Contast.user.getU_Tel());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_dingdan)
                .post(params.build())
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                pd.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "网络连接异常，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
//                pd.dismiss();
                final String string = response.body().string();
                Log.i(TAG, "onResponse: string=" + string);
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(MainActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                } else {
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Dingdan_car.clear();
                                    List<Dingdan> dingdans = JSON.parseArray(string, Dingdan.class);
                                    if (dingdans.size() > 0) {
                                        for (Dingdan dingdan : dingdans) {
                                            if (dingdan.getO_ISCancel() == 0) {
                                                if (dingdan.getO_TypeID() == 1 || dingdan.getO_TypeID() == 2 || dingdan.getO_TypeID() == 3 ||
                                                        dingdan.getO_TypeID() == 7 || dingdan.getO_TypeID() == 8) {
                                                    Dingdan_car.add(dingdan.getO_PlateNumber());
                                                }
                                            }
                                        }
                                        if (Dingdan_car.size() > 0) {
                                            boolean flag = false;
                                            Log.i(TAG, "run: Dingdan_car=" + Dingdan_car.toString());
                                            Log.i(TAG, "run: car.getC_PlateNumber()=" + car.getC_PlateNumber());
                                            for (String str : Dingdan_car) {
                                                if (str.equals(car.getC_PlateNumber())) {
                                                    flag = true;
                                                }
                                            }
                                            Log.i(TAG, "run: flag= " + flag);
                                            if (flag) {
                                                if (dialog != null) {
                                                    dialog.dismiss();
                                                }
                                                Toast.makeText(MainActivity.this, "请不要重复下单", Toast.LENGTH_SHORT).show();
                                            } else {
                                                String dizhi = cheliangweizhi.getText().toString();
                                                Log.i(TAG, "run: lat = " + finallat);
                                                Log.i(TAG, "run: lon = " + finallon);
                                                if (finallat == 0.0) {
                                                    finallat = lat;
                                                }
                                                if (finallon == 0.0) {
                                                    finallon = lon;
                                                }
                                                Intent intent = new Intent(MainActivity.this, XicheActivity.class);
                                                intent.putExtra("dizhi", dizhi);
                                                intent.putExtra("car", car);
                                                intent.putExtra("lat", finallat);
                                                intent.putExtra("lon", finallon);
                                                startActivity(intent);
                                                if (dialog != null) {
                                                    dialog.dismiss();
                                                }
                                            }
                                        } else {
                                            String dizhi = cheliangweizhi.getText().toString();
                                            Log.i(TAG, "run: lat = " + finallat);
                                            Log.i(TAG, "run: lon = " + finallon);
                                            if (finallat == 0.0) {
                                                finallat = lat;
                                            }
                                            if (finallon == 0.0) {
                                                finallon = lon;
                                            }
                                            Intent intent = new Intent(MainActivity.this, XicheActivity.class);
                                            intent.putExtra("dizhi", dizhi);
                                            intent.putExtra("car", car);
                                            intent.putExtra("lat", finallat);
                                            intent.putExtra("lon", finallon);
                                            startActivity(intent);
                                            if (dialog != null) {
                                                dialog.dismiss();
                                            }
                                        }
                                    } else {
                                        String dizhi = cheliangweizhi.getText().toString();
                                        Log.i(TAG, "run: lat = " + finallat);
                                        Log.i(TAG, "run: lon = " + finallon);
                                        if (finallat == 0.0) {
                                            finallat = lat;
                                        }
                                        if (finallon == 0.0) {
                                            finallon = lon;
                                        }
                                        Intent intent = new Intent(MainActivity.this, XicheActivity.class);
                                        intent.putExtra("dizhi", dizhi);
                                        intent.putExtra("car", car);
                                        intent.putExtra("lat", finallat);
                                        intent.putExtra("lon", finallon);
                                        startActivity(intent);
                                        if (dialog != null) {
                                            dialog.dismiss();
                                        }
                                    }
                                }

                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    private void startLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private boolean checkLogin() {
        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("isLogin", false);
        return isLogin;
    }

    private void checkCar(final int i) {
//        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
//        pd.setMessage("拼命加载中...");
//        pd.show();
        FormBody.Builder params = new FormBody.Builder();
        params.add("keys", Contast.KEYS);
        params.add("C_UTel", Contast.user.getU_Tel());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(params.build())
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                pd.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "网络连接异常，请稍后再试", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
//                pd.dismiss();
                final String string = response.body().string();
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(MainActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    carList = JSON.parseArray(string, Car.class);
                                    if (carList.size() == 0) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        builder.setTitle("提示");
                                        builder.setMessage("您目前没有任何爱车，请先添加爱车");
                                        builder.setNegativeButton("暂不添加", null);
                                        builder.setPositiveButton("添加爱车", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(MainActivity.this, AddCarActivity.class);
                                                startActivity(intent);
                                            }
                                        });
                                        builder.create().show();
                                    } else if (carList.size() == 1) {
                                        if (NoFastClickUtils.isFastClick()) {

                                        } else {
                                            if (i == 0) {
                                                checkDingdan(carList.get(0), null);
                                            } else if (i == 1) {
                                                Car car = carList.get(0);
                                                if (!TextUtils.isEmpty(car.getC_PlateNumber()) && !TextUtils.isEmpty(car.getC_MoterNumber())
                                                        && !TextUtils.isEmpty(car.getC_VIN())) {
                                                    Intent intent = new Intent(MainActivity.this, WeizhangActivity.class);
                                                    intent.putExtra("car", car);
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(MainActivity.this, "请完善您爱车的详细信息再进行查询", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    } else if (carList.size() > 1) {
                                        //如果已经登录，则跳转填写表单界面
                                        View bottomView = View.inflate(MainActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
                                        ListView listView = (ListView) bottomView.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                                        List<String> cars = new ArrayList<String>();
                                        for (int i = 0; i < carList.size(); i++) {
                                            cars.add(carList.get(i).getC_PlateNumber());
                                        }
                                        listView.setAdapter(new DialogListViewAdapter(MainActivity.this, cars));//ListView设置适配器

                                        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                                                .setTitle("选择爱车").setView(bottomView)//在这里把写好的这个listview的布局加载dialog中
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                }).create();
                                        dialog.show();
                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//响应listview中的item的点击事件
                                            @Override
                                            public void onItemClick(AdapterView<?> arg0, final View arg1, int posotion,
                                                                    long arg3) {
                                                // TODO Auto-generated method stub
                                                if (NoFastClickUtils.isFastClick()) {

                                                } else {
                                                    if (i == 0) {
                                                        checkDingdan(carList.get(posotion), dialog);
                                                    } else if (i == 1) {
                                                        Car car = carList.get(posotion);
                                                        if (!TextUtils.isEmpty(car.getC_PlateNumber()) && !TextUtils.isEmpty(car.getC_MoterNumber())
                                                                && !TextUtils.isEmpty(car.getC_VIN())) {
                                                            Intent intent = new Intent(MainActivity.this, WeizhangActivity.class);
                                                            intent.putExtra("car", car);
                                                            startActivity(intent);
                                                            dialog.dismiss();
                                                            arg1.setEnabled(false);
                                                            arg1.postDelayed(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    arg1.setEnabled(true);
                                                                }
                                                            }, 500);
                                                        } else {
                                                            Toast.makeText(MainActivity.this, "请完善您爱车的详细信息再进行查询", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }else if (i==2){
                                                        TextView tv = (TextView) arg1.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
                                                        String aiche = tv.getText().toString();
                                                        Intent intent = new Intent(MainActivity.this, AddBaoxianActivity.class);
                                                        intent.putExtra("aiche", aiche);
                                                        startActivity(intent);
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * 实现定位监听 位置一旦有所改变就会调用这个方法
     * 可以在这个方法里面获取到定位之后获取到的一系列数据
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            lat = location.getLatitude();
            lon = location.getLongitude();
            //这个判断是为了防止每次定位都重新设置中心点和marker
            if (isFirstLocation) {
                isFirstLocation = false;
                setUserMapCenter();
                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        return location.getLocationDescribe();
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        dangqianweizhi = s;
                        cheliangweizhi.setText(dangqianweizhi);
                    }
                }.execute();
            }
//            Log.i("BaiduLocationApiDem", sb.toString());
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
        }
    }

    /**
     * 捕捉back
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                drawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                finish();

            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 必须要实现
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mSearch.destroy();
    }

    /**
     * 必须要实现
     */
    @Override
    protected void onResume() {
        super.onResume();
//        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
//        mMapView.onResume();
//        UpdateHelper.getInstance().setUpdateListener(null);
        if (from == 1) {
            xiche.performClick();
            from = 0;
        } else {
            ib_shuaxin.performClick();
        }
        if (checkLogin()) {
            if (TextUtils.isEmpty(Contast.user.getU_Image())) {
                Picasso.with(MainActivity.this).load(R.drawable.cehuamorentouxiang).into(iv_touxiang);
            } else {
                Uri image = Uri.parse(Contast.Domain + Contast.user.getU_Image());
                Picasso.with(MainActivity.this).load(image)
                        .placeholder(R.drawable.touxiang)
                        .error(R.drawable.touxiang)
                        .into(iv_touxiang);
            }
        } else {
            Picasso.with(MainActivity.this).load(R.drawable.cehuamorentouxiang).into(iv_touxiang);
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                initWorker();
            }
        }, 3000);
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
        SharedPreferences sPreferences = getSharedPreferences("weidu", MODE_PRIVATE);
        String shuliang1 = sPreferences.getString("wdsl", "");
//        Log.e("weidu", shuliang1);
        if (shuliang1.equals("0")) {
            tv_new_msg.setVisibility(View.GONE);
        } else {
            tv_new_msg.setVisibility(View.VISIBLE);
        }
        dingdanshuliang();
    }

    private void initWorker() {
//        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
//        pd.setMessage("拼命加载中...");
//        pd.show();
        FormBody.Builder params = new FormBody.Builder();
        params.add("keys", Contast.KEYS);
        params.add("Lng", "" + finallon);
        params.add("Lat", "" + finallat);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_worker)
                .post(params.build())
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                pd.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(MainActivity.this, "3网络连接异常，请稍后再试", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
//                pd.dismiss();
                final String string = response.body().string();
                Log.i(TAG, "onResponse: string =" + string);
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Toast.makeText(MainActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });
                } else {
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });
                        } else {
                            workers.clear();
                            mBaiduMap.clear();
                            workers = JSON.parseArray(string, Worker.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (workers.size() != 0) {
                                        for (Worker worker : workers) {
                                            if (worker.getW_WSID() == 2 && worker.getW_LSID() == 0) {
                                                double w_lat = worker.getW_Lat();
                                                double w_log = worker.getW_Lng();
                                                Log.i(TAG, "run: worker=" + worker.toString());
                                                setMarker(new LatLng(w_lat, w_log));
//                                                Toast.makeText(MainActivity.this,"附近有 "+workers.size()+" 个洗车工",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }else {
//                                        Toast.makeText(MainActivity.this,"附近没有洗车工",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
//                            if (workers.size()!=0){
//                                for (Worker worker : workers){
//                                    double w_lat = worker.getW_Lat();
//                                    double w_log = worker.getW_Lng();
//                                    setMarker(new LatLng(w_lat, w_log));
//                                }
//                            }
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * 添加marker
     */
    private void setMarker(LatLng point) {
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.worker);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions optionses = new MarkerOptions()
                .position(point)
                .icon(bitmap);

        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(optionses);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                return true;
            }
        });
//        mBaiduMap.addOverlays();
    }

    /**
     * 必须要实现
     */
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

//    public void gengxin() {
//        FormBody.Builder params = new FormBody.Builder();
//        params.add("keys", Contast.KEYS);
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(url_gengxin)
//                .post(params.build())
//                .build();
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
////                pd.dismiss();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(MainActivity.this, "网络连接异常，请稍后再试", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(final Call call, Response response) throws IOException {
////                pd.dismiss();
//                final String string = response.body().string();
//                if (response.code() != HttpURLConnection.HTTP_OK) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(MainActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } else {
//                    if (!TextUtils.isEmpty(string)) {
//                        if (string.contains("ErrorStr")) {
//                            final Error error = JSON.parseObject(string, Error.class);
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(MainActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        } else {
//
//                            gengxinList = JSON.parseArray(string, UpdateBean.class);
////                            String requestStri ="[{\"V_ID\":1,\"V_Name\":\"版本一\",\"V_Code\":\"v1.0\",\"V_DownloadUrl\":\"http://www.mabangxiche.com/download/mabangxiche.apk\",\"V_UpdateContent\":\"第一个版本，欢迎下载\",\"V_Size\":\"32.45MB\",\"V_Sha\":\"abc\",\"V_Fore\":\"是\",\"V_Remark\":\"\"}]";
////                            String requestStri = "{\"code\":0,\"data\":{\"download_url\":\"http://www.mabangxiche.com/download/mabangxiche.apk\",\"force\":false,\"update_content\":\"第一个版本，欢迎下载\",\"v_code\":\"10\",\"v_name\":\"v1.0.0.16070810\",\"v_sha1\":\"7db76e18ac92bb29ff0ef012abfe178a78477534\",\"v_size\":32.45MB}}";
////                            Log.e("1111111111111111",gengxinList.toString());
////                            networkAutoUpdate(requestStri);
//                            for (UpdateBean updateBean:gengxinList){
////                                String banben=updateBean.getV_Code();
//                                String localVersion = null;
//                                try {
//                                    PackageInfo packageInfo = context.getApplicationContext()
//                                            .getPackageManager()
//                                            .getPackageInfo(context.getPackageName(), 0);
//                                    localVersion = String.valueOf(packageInfo.versionCode);
////                                  if (banben.equals(localVersion)){
////
//                                  }else {
//                                      Intent intentdd = new Intent(MainActivity.this, ChuangKou.class);
//                                      startActivity(intentdd);
//                                  }
//                                } catch (PackageManager.NameNotFoundException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        }
//                    } else {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(MainActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }
//            }
//        });
//    }

    public void dingdanshuliang() {
        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        String tel = sp.getString("U_Tel", "");
        FormBody.Builder params = new FormBody.Builder();
        params.add("keys", Contast.KEYS);
        params.add("O_UTel", tel);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_dingdan)
                .post(params.build())
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                pd.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
                        return;

                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
//                pd.dismiss();
                final String string = response.body().string();
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(MainActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                    return;

                                }
                            });
                        } else runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dingdanList = JSON.parseArray(string, Dingdan.class);
                                shuliang();
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        });
                    }
                }
            }
        });
    }
    private void shuliang(){
        shuliang = 0;
        if (dingdanList.size() == 0) {
            tv_dingdanshuliang.setVisibility(View.GONE);
            rl_dingdan.setVisibility(View.GONE);
        } else {
        for (Dingdan item : dingdanList) {
            int type = item.getO_TypeID();
            if (item.getO_ISCancel() == 0)
                if (type == 1 || type == 2 || type == 3 || type == 8 || type == 9) {
                    shuliang++;
                    }
                }
            if (shuliang==0){
                tv_dingdanshuliang.setVisibility(View.GONE);
                rl_dingdan.setVisibility(View.GONE);
            }else {
                tv_dingdanshuliang.setText(shuliang+"");
                tv_dingdanshuliang.setVisibility(View.VISIBLE);
                rl_dingdan.setVisibility(View.VISIBLE);

            }
        }
    }
}