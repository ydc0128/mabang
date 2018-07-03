package com.example.administrator.ydwlxcpt.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.OptionsPickerView;
import com.example.administrator.ydwlxcpt.Adapter.DialogListViewAdapter;
import com.example.administrator.ydwlxcpt.Bean.Car;
import com.example.administrator.ydwlxcpt.Bean.Chongyongdizhi;
import com.example.administrator.ydwlxcpt.Bean.DayBean;
import com.example.administrator.ydwlxcpt.Bean.EntityWashCarTypes;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.MinBean;
import com.example.administrator.ydwlxcpt.Bean.User;
import com.example.administrator.ydwlxcpt.Bean.Youhuiquan;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.example.administrator.ydwlxcpt.Utils.NoDoubleClickListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class XicheActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "XicheActivity";
    private String url_car = Contast.Domain + "api/CarList.ashx?";
    private String url_youhuiquan = Contast.Domain + "api/TicketList.ashx?";
    private String url_dingdan = Contast.Domain + "api/OrderAdd.ashx?";
    private String washTime;

    private ImageView iv_back;
    private RelativeLayout rl_aiche;
    private RelativeLayout rl_weizhi;
    private RelativeLayout rl_shijian;
    private ImageView iv_tianjiaaiche;
    private ImageView iv_changyongdizhi;
    private Dialog mDialog;
    private TextView tv_aiche;
    private TextView tv_weizhi;
    private TextView tv_shijian;
    private TextView tv_chewai_jiage;
    private TextView tv_cheneiwai_jiage;
    private TextView tv_mc_cheneiwai;
    private TextView tv_lungu_jiage;
    private TextView tv_dala_jiage;
    private TextView tv_shinei_jiage, tv_fuwu, tv_zongjine;
    private TextView tv_yinqing_jiage;

    private EditText et_weizhi_buchong;

    private CheckBox cb_chewai;
    private CheckBox cb_cheneiwei;
    private CheckBox cb_lungu;
    private CheckBox cb_dala;
    private CheckBox cb_shinei;
    private CheckBox cb_yinqing;
    private TextView quxiao;
    private TextView queding;
    private TextView tv_mc_chewai;
    private TextView tv_heji;
    private TextView tv_youhuijuan,tv_mc_fadongji,tv_mc_dala,tv_mc_neishi,tv_mc_yinqing;
    private RelativeLayout rl_youhuijuan;
    private Button btn_tijiao;
    private int result = 0;
    private String weizhi;
    private Car car;
    private double finallat;
    private double finallon;

    private List<DayBean> days = new ArrayList<>();
    private List<ArrayList<String>> hours = new ArrayList<>();
    private List<ArrayList<ArrayList<MinBean>>> mins = new ArrayList<>();

    private List<Car> carList = new ArrayList<>();
    private List<Youhuiquan> youhuiquanList = new ArrayList<>();
    private int num_youhuiquan = 0;
    private int youhuiquan_ID = -1;
    private int chewai;
    private int cheneiwai;
    private int fadongji;
    private int dala;
    private int shinei;
    private int kongtiao;
    private int mYear, mMonth, mDay;
    private LinearLayout fuwufei;
//    private TextView tv_dala,tv_shineijingxi,tv_cheweiqingxi,tv_biaozhunqingxi,tv_fadongjiqingxi;
    private boolean isCheck = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiche);
        Intent intent = getIntent();
        weizhi = intent.getStringExtra("dizhi");
        car = (Car) intent.getSerializableExtra("car");
        finallat = intent.getDoubleExtra("lat", 0.0);
        finallon = intent.getDoubleExtra("lon", 0.0);
        //获取控件
        initViews();
        tv_weizhi.setText(weizhi);
        tv_aiche.setText(car.getC_PlateNumber());
        setViews();
        initYouhuiquan();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setViews() {
        if (Contast.titleInfo.getEntityWashCarTypes() != null) {
            for (int i = 0; i < Contast.titleInfo.getEntityWashCarTypes().size(); i++) {
                final EntityWashCarTypes wash = Contast.titleInfo.getEntityWashCarTypes().get(i);
                if (car.getC_CTID() == 3) {
                    switch (wash.getW_ID()) {
                        case 1:
                            tv_mc_chewai.setText(wash.getW_Name());
                            chewai = Integer.parseInt(wash.getW_Price());
                            tv_chewai_jiage.setText(wash.getW_Price());
                            break;
                        case 2:
                            tv_mc_cheneiwai.setText(wash.getW_Name());
                            cheneiwai = Integer.parseInt(wash.getW_Price());
                            tv_cheneiwai_jiage.setText(wash.getW_Price());
                            break;
                        case 18:
                            tv_mc_fadongji.setText(wash.getW_Name());
                            fadongji = Integer.parseInt(wash.getW_Price());
                          tv_lungu_jiage.setText(wash.getW_Price());
                            break;
                        case 4:
                            tv_mc_dala.setText(wash.getW_Name());
                            dala = Integer.parseInt(wash.getW_Price());
                            tv_dala_jiage.setText(wash.getW_Price());
                            break;
                        case 5:
                            tv_mc_neishi.setText(wash.getW_Name());
                            shinei = Integer.parseInt(wash.getW_Price());
                            tv_shinei_jiage.setText(wash.getW_Price());
                            break;
                        case 16:
                            tv_mc_yinqing.setText(wash.getW_Name());
                            kongtiao = Integer.parseInt(wash.getW_Price());
                            tv_yinqing_jiage.setText(wash.getW_Price());
                            break;
                    }
                } else if (car.getC_CTID() == 4) {
                    switch (wash.getW_ID()) {
                        case 7:
                            tv_mc_chewai.setText(wash.getW_Name());
                            chewai = Integer.parseInt(wash.getW_Price());
                            tv_chewai_jiage.setText(wash.getW_Price());
                            break;
                        case 9:
                            tv_mc_cheneiwai.setText(wash.getW_Name());
                            cheneiwai = Integer.parseInt(wash.getW_Price());
                            tv_cheneiwai_jiage.setText(wash.getW_Price());
                            break;
                        case 19:
                            tv_mc_fadongji.setText(wash.getW_Name());
                            fadongji = Integer.parseInt(wash.getW_Price());
                            tv_lungu_jiage.setText(wash.getW_Price());
                            break;
                        case 11:
                            tv_mc_dala.setText(wash.getW_Name());
                            dala = Integer.parseInt(wash.getW_Price());
                            tv_dala_jiage.setText(wash.getW_Price());
                            break;
                        case 12:
                            tv_mc_neishi.setText(wash.getW_Name());
                            shinei = Integer.parseInt(wash.getW_Price());
                            tv_shinei_jiage.setText(wash.getW_Price());
                            break;
                        case 17:
                            tv_mc_yinqing.setText(wash.getW_Name());
                            kongtiao = Integer.parseInt(wash.getW_Price());
                            tv_yinqing_jiage.setText(wash.getW_Price());
                            break;
                    }
                }
            }
        }
    }

    private void initYouhuiquan() {
        FormBody.Builder params = new FormBody.Builder();
        params.add("keys", Contast.KEYS);
        params.add("T_UTel", Contast.user.getU_Tel());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_youhuiquan)
                .post(params.build())
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(XicheActivity.this, "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                final String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    if (string.contains("ErrorStr")) {
                        final Error error = JSON.parseObject(string, Error.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(XicheActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                youhuiquanList = JSON.parseArray(string, Youhuiquan.class);
                                checkYouhuiquan();
                            }
                        });
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(XicheActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                            return;
                       }
                    });
                }
            }
        });
    }

    private void initViews() {
//        tv_dala=(TextView)findViewById(R.id.tv_dala);
//        tv_shineijingxi=(TextView)findViewById(R.id.tv_shineijingxi);
//        tv_cheweiqingxi=(TextView)findViewById(R.id.tv_chewaiqingxi);
//        tv_biaozhunqingxi=(TextView)findViewById(R.id.tv_biaozhunqingxi);
//        tv_fadongjiqingxi=(TextView)findViewById(R.id.tv_fagongjiqingxi);
        iv_back = (ImageView) findViewById(R.id.iv_xiche_back);
        rl_aiche = (RelativeLayout) findViewById(R.id.rl_xiche_aiche);
        rl_weizhi = (RelativeLayout) findViewById(R.id.rl_xiche_weizhi);
        rl_shijian = (RelativeLayout) findViewById(R.id.rl_xiche_shijian);
        iv_tianjiaaiche = (ImageView) findViewById(R.id.iv_xiche_aiche);
        iv_changyongdizhi = (ImageView) findViewById(R.id.iv_xiche_weizhi);
        tv_youhuijuan = (TextView) findViewById(R.id.tv_xiche_daijinquan);
        rl_youhuijuan = (RelativeLayout) findViewById(R.id.rl_xiche_daijinjuan);
        tv_chewai_jiage = (TextView) findViewById(R.id.tv_xiche_xiche_chewai_jiage);
        tv_mc_chewai=(TextView)findViewById(R.id.tv_xiche_xiche_chewai);
        tv_mc_fadongji=(TextView)findViewById(R.id.tv_xiche_xiche_fadongji) ;
        tv_mc_dala=(TextView)findViewById(R.id.tv_xiche_meirong_dala);
        tv_mc_neishi=(TextView)findViewById(R.id.tv_xiche_meirong_shinei);
        tv_mc_yinqing=(TextView)findViewById(R.id.tv_xiche_meirong_yinqing);
        tv_cheneiwai_jiage = (TextView) findViewById(R.id.tv_xiche_xiche_cheneiwai_jiage);
        tv_mc_cheneiwai=(TextView)findViewById(R.id.tv_xiche_xiche_cheneiwai);
        tv_lungu_jiage = (TextView) findViewById(R.id.tv_xiche_xiche_fadongji_jiage);
        tv_dala_jiage = (TextView) findViewById(R.id.tv_xiche_meirong_dala_jiage);
        tv_shinei_jiage = (TextView) findViewById(R.id.tv_xiche_meirong_shinei_jiage);
        tv_fuwu = (TextView) findViewById(R.id.tv_xiche_fuwu);
        tv_zongjine = (TextView) findViewById(R.id.tv_xiche_zongjine);
        tv_yinqing_jiage = (TextView) findViewById(R.id.tv_xiche_meirong_yinqing_jiage);
        et_weizhi_buchong = (EditText) findViewById(R.id.et_xiche_weizhi_buchong);
        tv_aiche = (TextView) findViewById(R.id.tv_xiche_aiche_name);
        tv_weizhi = (TextView) findViewById(R.id.tv_xiche_weizhi_name);
        tv_shijian = (TextView) findViewById(R.id.tv_xiche_shijian_name);
        fuwufei=(LinearLayout)findViewById(R.id.ll_fuwufei);
        cb_chewai = (CheckBox) findViewById(R.id.cb_xiche_xiche_chewai);
        cb_cheneiwei = (CheckBox) findViewById(R.id.cb_xiche_xiche_cheneiwai);
        cb_lungu = (CheckBox) findViewById(R.id.cb_xiche_xiche_fadongji);
        cb_dala = (CheckBox) findViewById(R.id.cb_xiche_meirong_dala);
        cb_shinei = (CheckBox) findViewById(R.id.cb_xiche_meirong_shinei);
        cb_yinqing = (CheckBox) findViewById(R.id.cb_xiche_meirong_yinqing);
        tv_heji = (TextView) findViewById(R.id.tv_xiche_hejijiage);
        btn_tijiao = (Button) findViewById(R.id.btn_xiche_tijiao);
        iv_back.setOnClickListener(this);
        rl_aiche.setOnClickListener(this);
        rl_weizhi.setOnClickListener(this);
        rl_shijian.setOnClickListener(this);
        btn_tijiao.setOnClickListener(this);
        iv_tianjiaaiche.setOnClickListener(this);
        iv_changyongdizhi.setOnClickListener(this);
        rl_youhuijuan.setOnClickListener(this);
        cb_chewai.setOnCheckedChangeListener(this);
        cb_cheneiwei.setOnCheckedChangeListener(this);
        cb_lungu.setOnCheckedChangeListener(this);
        cb_dala.setOnCheckedChangeListener(this);
        cb_shinei.setOnCheckedChangeListener(this);
        cb_yinqing.setOnCheckedChangeListener(this);

        btn_tijiao.setOnClickListener(new NoDoubleClickListener() {
            protected void onNoDoubleClick(View view) {
              showDialog();
            }
        });

    }

    public void showDialog() {
            //1.创建一个Dialog对象，如果是AlertDialog对象的话，弹出的自定义布局四周会有一些阴影，效果不好
            mDialog = new Dialog(this);
            //去除标题栏
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //2.填充布局
            LayoutInflater inflater = LayoutInflater.from(this);
            View           dialogView     = inflater.inflate(R.layout.tanchuang, null);
            //将自定义布局设置进去
            mDialog.setContentView(dialogView);
            //3.设置指定的宽高,如果不设置的话，弹出的对话框可能不会显示全整个布局，当然在布局中写死宽高也可以
            WindowManager.LayoutParams lp     = new WindowManager.LayoutParams();
            Window                     window = mDialog.getWindow();
            lp.copyFrom(window.getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            //注意要在Dialog show之后，再将宽高属性设置进去，才有效果
            mDialog.show();
            window.setAttributes(lp);
            //设置点击其它地方不让消失弹窗
            mDialog.setCancelable(false);
            initDialogView(dialogView);
            initDialogListener();
        }
        private void initDialogView(View view) {
            queding = (TextView) view.findViewById(R.id.bt_tongzhi_queding);
            quxiao = (TextView) view.findViewById(R.id.bt_tongzhi_quxiao);
        }
        private void initDialogListener() {
            quxiao.setOnClickListener(new NoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    mDialog.dismiss();
                }
            });

            queding.setOnClickListener(new NoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    tijiaodingdan();
                }
            });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_xiche_back:
                finish();
                break;
            case R.id.rl_xiche_aiche:
                //TODO
                //如果用户添加了不止一辆，则弹出提示框，让用户选择
                final ProgressDialog pd = new ProgressDialog(XicheActivity.this);
                pd.setMessage("拼命加载中...");
                pd.show();
                FormBody.Builder params = new FormBody.Builder();
                params.add("keys", Contast.KEYS);
                params.add("C_UTel", Contast.user.getU_Tel());
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url_car)
                        .post(params.build())
                        .build();
                okhttp3.Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        pd.dismiss();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(XicheActivity.this, "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    }
                    @Override
                    public void onResponse(final Call call, Response response) throws IOException {
                        pd.dismiss();
                        final String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            if (string.contains("ErrorStr")) {
                                final Error error = JSON.parseObject(string, Error.class);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(XicheActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        carList = JSON.parseArray(string, Car.class);
                                        if (carList.size() > 1) {
                                            //如果已经登录，则跳转填写表单界面
                                            View bottomView = View.inflate(XicheActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
                                            ListView listView = (ListView) bottomView.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                                            List<String> cars = new ArrayList<String>();
                                            for (int i = 0; i < carList.size(); i++) {
                                                cars.add(carList.get(i).getC_PlateNumber());
                                            }
                                            listView.setAdapter(new DialogListViewAdapter(XicheActivity.this, cars));//ListView设置适配器

                                            final AlertDialog dialog = new AlertDialog.Builder(XicheActivity.this)
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
                                                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                                                        long arg3) {
                                                    // TODO Auto-generated method stub
                                                    TextView tv = (TextView) arg1.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
                                                    String aiche = tv.getText().toString();
                                                    tv_aiche.setText(aiche);
                                                    car = carList.get(position);
                                                    setViews();
                                                    dialog.dismiss();
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
                                    Toast.makeText(XicheActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.rl_xiche_weizhi:
                Intent intent = new Intent(XicheActivity.this, ChangyongdizhiListActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.rl_xiche_daijinjuan:
                String str = tv_youhuijuan.getText().toString().trim();
                if (str.equals("无可用的代金券")) {
                    Toast.makeText(XicheActivity.this, "无可用的代金券", Toast.LENGTH_SHORT).show();
                } else {
                    //跳转到代金券列表界面
                    Intent intent1 = new Intent(XicheActivity.this, MyYouhuiquanActivity.class);
                    intent1.putExtra("tt",2);
                    startActivityForResult(intent1, 2);
                }
                break;
            case R.id.rl_xiche_shijian:
                //点击弹出时间选择器，让用户选择预约洗车的时间
                initTimeOptions();
                showOptionsPickerView();

                break;
            case R.id.iv_xiche_aiche:
                //TODO
                //添加车辆按钮，点击跳转到添加车辆的页面
                Intent intent2 = new Intent(XicheActivity.this, AddCarActivity.class);
                startActivity(intent2);
                break;
            case R.id.iv_xiche_weizhi:
                //TODO
                //添加地址按钮，点击跳转到常用地址页面
                break;
            case R.id.btn_xiche_tijiao:
                //TODO
                //提交订单，点击跳转支付结算页面
                    tijiaodingdan();
                break;
            default:
                break;
        }
    }

    private void tijiaodingdan() {
        StringBuffer sb = new StringBuffer();
        if (car.getC_CTID() == 3) {
            if (cb_chewai.isChecked()) {
                sb.append("1," + tv_chewai_jiage.getText().toString()).append("|");
            }
            if (cb_cheneiwei.isChecked()) {
                sb.append("2," + tv_cheneiwai_jiage.getText().toString()).append("|");
            }
            if (cb_lungu.isChecked()) {
                sb.append("18," + tv_lungu_jiage.getText().toString()).append("|");
            }
            if (cb_dala.isChecked()) {
                sb.append("4," + tv_dala_jiage.getText().toString()).append("|");
            }
            if (cb_shinei.isChecked()) {
                sb.append("5," + tv_shinei_jiage.getText().toString()).append("|");
            }
            if (cb_yinqing.isChecked()) {
                sb.append("16," + tv_yinqing_jiage.getText().toString()).append("|");
            }
        } else if (car.getC_CTID() == 4) {
            if (cb_chewai.isChecked()) {
                sb.append("7," + tv_chewai_jiage.getText().toString()).append("|");
            }
            if (cb_cheneiwei.isChecked()) {
                sb.append("9," + tv_cheneiwai_jiage.getText().toString()).append("|");

            }
            if (cb_lungu.isChecked()) {
                sb.append("19," + tv_lungu_jiage.getText().toString()).append("|");

            }
            if (cb_dala.isChecked()) {
                sb.append("11," + tv_dala_jiage.getText().toString()).append("|");

            }
            if (cb_shinei.isChecked()) {
                sb.append("12," + tv_shinei_jiage.getText().toString()).append("|");

            }
            if (cb_yinqing.isChecked()) {
                sb.append("17," + tv_yinqing_jiage.getText().toString()).append("|");
            }
        }
        if (sb.length() > 0 && sb != null) {
            sb.deleteCharAt(sb.length() - 1);
        }
        Log.i(TAG, "tijiaodingdan: " + sb.toString());

        SharedPreferences sp = getSharedPreferences("shezhi", MODE_PRIVATE);
        boolean iscall = sp.getBoolean("iscall", true);
        int calling;
        if (iscall) {
            calling = 1;
        } else {
            calling = 0;
        }

        String chepaihao = tv_aiche.getText().toString();
        String dizhi = tv_weizhi.getText().toString();
        String buchong = et_weizhi_buchong.getText().toString();
        String shijian = tv_shijian.getText().toString();
        String fuwu = sb.toString();
        String daijinquan = tv_youhuijuan.getText().toString();
        String zhifujine = tv_heji.getText().toString();
//      Long yytime = getDateFromStr(shijian);
//      long time = System.currentTimeMillis();
//      long minutes = yytime - time;
//      long shijiancha = minutes / 1000 / 60;
//      Log.w("nima", shijiancha + "时间差");
        if (TextUtils.isEmpty(chepaihao)) {
            Toast.makeText(XicheActivity.this, "车牌号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(dizhi)) {
            Toast.makeText(XicheActivity.this, "车辆位置不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(shijian)) {
            Toast.makeText(XicheActivity.this, "服务时间不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(fuwu)) {
            Toast.makeText(XicheActivity.this, "服务项目不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(zhifujine) > Contast.user.getU_Money()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(XicheActivity.this);
            builder.setTitle("提示");
            builder.setMessage("您当前的余额不足，请先充值");
            builder.setNegativeButton("取消", null);
            builder.setPositiveButton("去充值", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(XicheActivity.this, ChongzhiActivity.class);
                    startActivity(intent);
                }
            });
            builder.create().show();
            return;
        }
        final ProgressDialog pd = new ProgressDialog(XicheActivity.this);
        pd.setMessage("拼命加载中...");
        pd.show();
        FormBody.Builder params = new FormBody.Builder();
        params.add("keys", Contast.KEYS);
        params.add("O_UTel", Contast.user.getU_Tel());
        params.add("O_CarTel", car.getC_CarTel());
        params.add("O_CarName", car.getC_CarName());
        params.add("O_PlateNumber", chepaihao);
        params.add("O_WPart", fuwu);
        params.add("O_CTID", "" + car.getC_CTID());
        int fw= Integer.parseInt(tv_fuwu.getText().toString());
        int yyfwf=fw+result;
        params.add("O_Price", "" +yyfwf);
        if (TextUtils.isEmpty(buchong)) {
            params.add("O_WriteAdress", "");
        } else {
            params.add("O_WriteAdress", buchong);
        }
        params.add("O_Adress", weizhi);
        params.add("O_Lng", "" + finallon);
        params.add("O_Lat", "" + finallat);
        params.add("O_IsPhone", "" + calling);
        params.add("O_Money",   tv_zongjine.getText().toString());
        if (shijian.contains("明天")){
            String yy = shijian.replace("明天", "");
            final Calendar c = Calendar.getInstance();
            c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            mYear = c.get(Calendar.YEAR); // 获取当前年份
            mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
            mDay = c.get(Calendar.DAY_OF_MONTH)+1;// 获取
            if (mMonth<10) {
                String sj = mYear + "-" + "0"+mMonth + "-" + mDay;
                String washTime=sj+yy;
                tv_shijian.setText(washTime.toString());
            }else {
                String sj = mYear + "-"+mMonth + "-" + mDay;
                String washTime=sj+yy;
                tv_shijian.setText(washTime.toString());

            }
        }else if (shijian.contains("今天")){
            String yy = shijian.replace("今天", "");
            final Calendar c = Calendar.getInstance();
            c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            mYear = c.get(Calendar.YEAR); // 获取当前年份
            mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
            mDay = c.get(Calendar.DAY_OF_MONTH);// 获取
            String sj=mYear + "-" + mMonth + "-" + mDay;
            String washTime=sj+yy;
            tv_shijian.setText(washTime.toString());
        }else if (shijian.contains("立即服务")){
            Date day=new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            System.out.println(df.format(day));
        }
        if (daijinquan.contains("代金券") || youhuiquan_ID == -1) {
            params.add("O_TID", "");
        } else {
            params.add("O_TID", "" + youhuiquan_ID);
        }
        params.add("W_ID", "-1");
        params.add("O_WashTime", tv_shijian.getText().toString());
        for (int i = 0; i < params.build().size(); i++) {
            String value = params.build().value(i);
            Log.i(TAG, "tijiaodingdan: value = " + value);
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_dingdan)
                .post(params.build())
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        Toast.makeText(XicheActivity.this, "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
                        return;
                        }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                final String string = response.body().string();
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(XicheActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                   return;
                        }
                    });
                } else {
                    Log.i(TAG, "onResponse: string = " + string);
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    Toast.makeText(XicheActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(XicheActivity.this, "抱歉，附近洗车工在忙，请稍后下单", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });
                        } else {
                            Contast.user = JSON.parseObject(string, User.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(XicheActivity.this, "下单成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(XicheActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
                            return;
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Chongyongdizhi dizhi = (Chongyongdizhi) data.getSerializableExtra("dizhi");
                    tv_weizhi.setText(dizhi.getAW_Adress());
                    et_weizhi_buchong.setText(dizhi.getAW_AdressWrite());
                    finallat = dizhi.getAW_Lat();
                    finallon = dizhi.getAW_Lng();
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    Youhuiquan youhuiquan = (Youhuiquan) data.getSerializableExtra("youhuiquan");
                    tv_youhuijuan.setText("-" + youhuiquan.getT_Ticket());
                    tv_youhuijuan.setTextColor(Color.RED);
                    youhuiquan_ID = youhuiquan.getT_ID();
                    isCheck = true;
                    tv_heji.setText("" + (result - youhuiquan.getT_Ticket()));
                    int zongjine4 = Integer.parseInt(tv_heji.getText().toString()) + Integer.parseInt(tv_fuwu.getText().toString());
                    tv_zongjine.setText(zongjine4 + "");
                }
                break;
        }
    }

    private void showOptionsPickerView() {

        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = days.get(options1).getPickerViewText() + "  "
                        + hours.get(options1).get(option2) + ":"
                        + mins.get(options1).get(option2).get(options3).getPickerViewText();
                if (options1 == 0 && option2 == 0) {
                    tv_shijian.setText("立即服务");
                    tv_fuwu.setText("0");
                    int zongjine = Integer.parseInt(tv_heji.getText().toString()) + Integer.parseInt(tv_fuwu.getText().toString());
                    tv_zongjine.setText(zongjine + "");
                    fuwufei.setVisibility(View.GONE);
                } else {
                    tv_shijian.setText(tx);
                    String shijian= tv_shijian.getText().toString();
                    if (shijian.contains("明天")){
                        String yy = shijian.replace("明天", "");
                        final Calendar c = Calendar.getInstance();
                        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                        mYear = c.get(Calendar.YEAR); // 获取当前年份
                        mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
                        mDay = c.get(Calendar.DAY_OF_MONTH)+1;// 获取
                        if (mMonth<10) {
                            String sj = mYear + "-" + "0"+mMonth + "-" + mDay;
                            washTime=sj+yy;
                        }else {
                            String sj = mYear + "-"+mMonth + "-" + mDay;
                            washTime=sj+yy;
                        }
                    }else if (shijian.contains("今天")){
                        String yy = shijian.replace("今天", "");
                        final Calendar c = Calendar.getInstance();
                        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                        mYear = c.get(Calendar.YEAR); // 获取当前年份
                        mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
                        mDay = c.get(Calendar.DAY_OF_MONTH);// 获取
                        String sj=mYear + "-" + mMonth + "-" + mDay;
                        washTime=sj+yy;
                    }
                    Long yytime = getDateFromStr(washTime);
                    long time = System.currentTimeMillis();
                    long minutes = yytime - time;
                    long shijiancha = minutes / 1000 / 60;
                    if (shijiancha > 30&&shijiancha<60*6) {
                        tv_fuwu.setText("10");
                        int zongjine = Integer.parseInt(tv_heji.getText().toString()) + Integer.parseInt(tv_fuwu.getText().toString());
                        tv_zongjine.setText(zongjine + "");
                        fuwufei.setVisibility(View.VISIBLE);
                        Toast.makeText(XicheActivity.this, "离预约时间小于6小时，加收10元服务费", Toast.LENGTH_SHORT).show();
                    }else if  (shijiancha>60*6){
                        tv_fuwu.setText("20");
                        fuwufei.setVisibility(View.VISIBLE);
                        Toast.makeText(XicheActivity.this, "离预约时间大于6小时，加收20元服务费", Toast.LENGTH_SHORT).show();
                        int zongjine = Integer.parseInt(tv_heji.getText().toString()) + Integer.parseInt(tv_fuwu.getText().toString());
                        tv_zongjine.setText(zongjine + "");
                    }
                }
            }
        }).setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("时间选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setContentTextSize(20)//滚轮文字大小
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .build();
        pvOptions.setPicker(days, hours, mins);
        pvOptions.show();


    }

    private void initTimeOptions() {
        days.clear();
        hours.clear();
        mins.clear();

        days.add(new DayBean("今天"));
        days.add(new DayBean("明天"));


        Calendar cal = Calendar.getInstance();
        int m = cal.get(Calendar.MINUTE);
        int h = cal.get(Calendar.HOUR_OF_DAY);
        int h1 = 0;
        int m1 = 0;
        if (m >= 0 && m <= 25) {
            m1 = m + 30;
            h1 = h;
        } else if (m > 25 && m <= 30) {
            m1 = 0;
            h1 = h + 1;
        } else if (m > 30 && m <= 60) {
            m1 = m - 30;
            h1 = h + 1;
        }
        ArrayList<String> hour_01 = new ArrayList<>();
        hour_01.add("立即服务");
        for (int i = h1; i <= 23; i++) {
            hour_01.add("" + i);

        }
        ArrayList<String> hour_02 = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            hour_02.add("" + i);
        }
        hours.add(hour_01);
        hours.add(hour_02);

        ArrayList<MinBean> mins_01 = new ArrayList<>();

        mins_01.add(new MinBean("  "));

        ArrayList mins_02 = new ArrayList<>();
        for (int i = m1; i <= 55; i++) {
            if (i == 5) {
                mins_02.add(new MinBean("0" + i));
            } else if (i % 5 == 0 && (i / 5) % 2 == 1) {
                mins_02.add(new MinBean("" + i));
            }
        }
        ArrayList mins_03 = new ArrayList<>();

        for (int i = 5; i <= 55; i += 10) {
            if (i == 5) {
                mins_03.add(new MinBean("0" + i));
            } else {
                mins_03.add(new MinBean("" + i));
            }
        }

        ArrayList<ArrayList<MinBean>> mins_04 = new ArrayList<>();
        for (int i = 1; i <= hour_01.size(); i++) {
            if (i == 1) {
                mins_04.add(mins_01);
            } else if (i == 2) {
                mins_04.add(mins_02);
            } else {
                mins_04.add(mins_03);
            }
        }
        ArrayList<ArrayList<MinBean>> mins_05 = new ArrayList<>();
        for (int i = 1; i <= hour_02.size(); i++) {
            mins_05.add(mins_03);
        }
        mins.add(mins_04);
        mins.add(mins_05);
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//        Long yytime = getDateFromStr(washTime);
//        long time = System.currentTimeMillis();
//        long minutes = yytime - time;
//        long shijiancha = minutes / 1000 / 60;
//        if (shijiancha > 30&&shijiancha<60*6) {
//            tv_fuwu.setText(10+"");
//            Toast.makeText(XicheActivity.this, "离预约时间小于6小时，加收10元服务费", Toast.LENGTH_SHORT).show();
//        }else if  (shijiancha>60*6){
//            tv_fuwu.setText(20+"");
//            Toast.makeText(XicheActivity.this, "离预约时间大于6小时，加收20元服务费", Toast.LENGTH_SHORT).show();
//        }else if (shijiancha<30) {
//            tv_fuwu.setText(0+"");
//        }
        switch (buttonView.getId()) {
            case R.id.cb_xiche_xiche_chewai:
                if (isChecked) {
                    result += chewai;
//                    tv_cheweiqingxi.setText("服务内容：外观清洗，前挡玻璃镀膜，轮毂清洗，轮胎打蜡，服务时间：20-30分钟");
//                    tv_cheweiqingxi.setVisibility(View.VISIBLE);
//                    tv_biaozhunqingxi.setVisibility(View.GONE);
//                    tv_fadongjiqingxi.setVisibility(View.GONE);
//                    tv_dala.setVisibility(View.GONE);
//                    tv_shineijingxi.setVisibility(View.GONE);
                } else {
                    result -= chewai;
//                    tv_cheweiqingxi.setVisibility(View.GONE);
//                    tv_biaozhunqingxi.setVisibility(View.GONE);
//                    tv_fadongjiqingxi.setVisibility(View.GONE);
//                    tv_dala.setVisibility(View.GONE);
//                    tv_shineijingxi.setVisibility(View.GONE);
                }
                tv_heji.setText(result + "");
                int zongjine = Integer.parseInt(tv_heji.getText().toString()) + Integer.parseInt(tv_fuwu.getText().toString());
                tv_zongjine.setText(zongjine + "");
                checkYouhuiquan();
                break;
            case R.id.cb_xiche_xiche_cheneiwai:
                if (isChecked) {
                    cb_chewai.setChecked(false);
                    cb_chewai.setClickable(false);
                    result += cheneiwai;
//                    tv_biaozhunqingxi.setVisibility(View.VISIBLE);
//                    tv_fadongjiqingxi.setVisibility(View.GONE);
//                    tv_dala.setVisibility(View.GONE);
//                    tv_shineijingxi.setVisibility(View.GONE);
//                    tv_cheweiqingxi.setVisibility(View.GONE);
//                    tv_biaozhunqingxi.setText("服务内容：车外清洗，空调口出臭，前挡玻璃除雾，服务时间：20-40分钟");
                } else {
                    cb_chewai.setClickable(true);
                    result -= cheneiwai;
//                    tv_biaozhunqingxi.setVisibility(View.GONE);

                }
                tv_heji.setText(result + "");
                int zongjine1 = Integer.parseInt(tv_heji.getText().toString()) + Integer.parseInt(tv_fuwu.getText().toString());
                tv_zongjine.setText(zongjine1 + "");
                checkYouhuiquan();
                break;
            case R.id.cb_xiche_xiche_fadongji:
                if (isChecked) {
                    result += fadongji;
//                    tv_fadongjiqingxi.setText("服务内容：标准清洗，发动机清洗，保养，服务时间:70分钟");
//                    tv_fadongjiqingxi.setVisibility(View.VISIBLE);
//                    tv_biaozhunqingxi.setVisibility(View.GONE);
//                    tv_dala.setVisibility(View.GONE);
//                    tv_shineijingxi.setVisibility(View.GONE);
//                    tv_cheweiqingxi.setVisibility(View.GONE);
                } else {
                    result -= fadongji;
//                    tv_fadongjiqingxi.setVisibility(View.GONE);
//                    tv_biaozhunqingxi.setVisibility(View.GONE);
//                    tv_dala.setVisibility(View.GONE);
//                    tv_shineijingxi.setVisibility(View.GONE);
//                    tv_cheweiqingxi.setVisibility(View.GONE);
                }
                tv_heji.setText(result + "");
                int zongjine2 = Integer.parseInt(tv_heji.getText().toString()) + Integer.parseInt(tv_fuwu.getText().toString());
                tv_zongjine.setText(zongjine2 + "");
                checkYouhuiquan();
                break;
            case R.id.cb_xiche_meirong_dala:
                if (isChecked) {
                    cb_chewai.setChecked(false);
                    cb_chewai.setClickable(false);
                    cb_cheneiwei.setChecked(false);
                    cb_cheneiwei.setClickable(false);
                    result += dala;
//                    tv_dala.setVisibility(View.VISIBLE);
//                    tv_biaozhunqingxi.setVisibility(View.GONE);
//                    tv_cheweiqingxi.setVisibility(View.GONE);
//                    tv_biaozhunqingxi.setVisibility(View.GONE);
//                    tv_fadongjiqingxi.setVisibility(View.GONE);
//                    tv_shineijingxi.setVisibility(View.GONE);
//                    tv_cheweiqingxi.setVisibility(View.GONE);
//                    tv_dala.setText("服务内容：车外清洗，打蜡，服务时间：120分钟");
                } else {
                    cb_chewai.setClickable(true);
                    cb_cheneiwei.setClickable(true);
                    result -= dala;
//                    tv_biaozhunqingxi.setVisibility(View.GONE);
//                    tv_cheweiqingxi.setVisibility(View.GONE);
//                    tv_dala.setVisibility(View.GONE);
                }
                tv_heji.setText(result + "");
                int zongjine3 = Integer.parseInt(tv_heji.getText().toString()) + Integer.parseInt(tv_fuwu.getText().toString());
                tv_zongjine.setText(zongjine3 + "");
                checkYouhuiquan();

                break;
            case R.id.cb_xiche_meirong_shinei:
                if (isChecked) {
                    cb_chewai.setChecked(false);
                    cb_chewai.setClickable(false);
                    cb_cheneiwei.setChecked(false);
                    cb_cheneiwei.setClickable(false);
                    result += shinei;
//                    tv_shineijingxi.setText("服务内容:车外清洗，内饰深度清洁，服务时间：90-180分钟");
//                    tv_shineijingxi.setVisibility(View.VISIBLE);
//                    tv_biaozhunqingxi.setVisibility(View.GONE);
//                    tv_cheweiqingxi.setVisibility(View.GONE);
//                    tv_biaozhunqingxi.setVisibility(View.GONE);
//                    tv_fadongjiqingxi.setVisibility(View.GONE);
//                    tv_dala.setVisibility(View.GONE);
//                    tv_cheweiqingxi.setVisibility(View.GONE);
                } else {
                    cb_chewai.setClickable(true);
                    cb_cheneiwei.setClickable(true);
                    result -= shinei;
//                    tv_biaozhunqingxi.setVisibility(View.GONE);
//                    tv_cheweiqingxi.setVisibility(View.GONE);
//                    tv_shineijingxi.setVisibility(View.GONE);
                }
                tv_heji.setText(result + "");
                int zongjine4 = Integer.parseInt(tv_heji.getText().toString()) + Integer.parseInt(tv_fuwu.getText().toString());
                tv_zongjine.setText(zongjine4 + "");
                checkYouhuiquan();

                break;
            case R.id.cb_xiche_meirong_yinqing:
                tv_heji.setText("");
                if (isChecked) {
                    result += kongtiao;
                } else {
                    result -= kongtiao;
                }
                tv_heji.setText(result + "");
                int zongjine5 = Integer.parseInt(tv_heji.getText().toString()) + Integer.parseInt(tv_fuwu.getText().toString());
                tv_zongjine.setText(zongjine5+ "");
                checkYouhuiquan();
                break;
        }

    }

    private void checkYouhuiquan() {
        num_youhuiquan = 0;
        if (youhuiquanList.size() == 0) {
            tv_youhuijuan.setText("无可用的代金券");
            tv_youhuijuan.setTextColor(Color.GRAY);
        } else {
            for (Youhuiquan youhuiquan : youhuiquanList) {
                if (youhuiquan.getT_Money() <= result) {
                    if (youhuiquan.getT_IsLock() == 0) {
                        num_youhuiquan++;
                        SharedPreferences sh = getSharedPreferences("yhjsl", MODE_PRIVATE);
                        SharedPreferences.Editor edit1 = sh.edit();
                        edit1.putString("yhjzs", result + "");
                        edit1.commit();
                    }
                }
            }
            if (num_youhuiquan == 0) {
                tv_youhuijuan.setText("无可用的代金券");
                tv_youhuijuan.setTextColor(Color.GRAY);
            } else {
                tv_youhuijuan.setText("有" + num_youhuiquan + "张代金券可用");
                tv_youhuijuan.setTextColor(Color.RED);
            }
        }
    }

    public long getDateFromStr(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Long temp = 0L;
        try {
            Date date = (Date) df.parse(dateStr);
            temp = date.getTime();
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
            return temp;
        }
    }
}
