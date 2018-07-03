package com.example.administrator.ydwlxcpt.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Adapter.DialogListViewAdapter;
import com.example.administrator.ydwlxcpt.Adapter.FuwuxiangmuAdapter;
import com.example.administrator.ydwlxcpt.Bean.Dingdan;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.Fuwuxiangmu;
import com.example.administrator.ydwlxcpt.Bean.User;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.example.administrator.ydwlxcpt.Utils.ActivityUtils;
import com.example.administrator.ydwlxcpt.View.MyListView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import static com.example.administrator.ydwlxcpt.R.id.pv_dingdanxiangqing_max;

//订单详情
public class DingdanXiangqingActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "DingdanXiangqingActivit";
    private String url = Contast.Domain + "api/OrderDelete.ashx?";
    private ImageView iv_back;
    private Button btn_quxiao;
    private TextView tv_dingdanhao;
    private TextView tv_shijian;
    private TextView tv_chepaihao;
    private TextView tv_lianxiren;
    private TextView tv_shoujihao;
    private TextView tv_dizhi;
    private TextView tv_hejijine;
    private TextView tv_zhuangtai;
    private TextView tv_jibie;
    private TextView tv_daijinqan;
    private MyListView lv_fuwuxiangmu;
    private List<Fuwuxiangmu> fuwuxiangmuList;
    private FuwuxiangmuAdapter adapter;

    private ImageView iv_touxiang;
    private TextView tv_xichegong_name;
    private TextView tv_xichegong_gonghao;
    private TextView tv_xichegong_lianxifangshi;
    private TextView tv_kefudianhua;
    private ImageView iv_xicheqian;
    private ImageView iv_xichehou;
    private ImageView iv_xichegong_lianxifangshi;
    private ImageView iv_kefu_lianxifangshi;
    private PhotoView pv_max;
    private Dingdan dingdan;
    private LinearLayout ll_xichegongxinxi;
    private List<String> xichegongdianhua;
    private List<String> quxiaoyuanyin;
    private List<String> kefudianhua;

    private Button btn_zailaiyidan;
    private Button btn_qupingjia;
    private TextView tv_time_end;
    private TextView tv_fuwufei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingdan_xiangqing);
        Intent intent = getIntent();
        //接收参数
        dingdan = (Dingdan) intent.getSerializableExtra("dingdan");
        Log.e("dingdan", dingdan.toString());
        initViews();
        initListView();
        setDatas();
    }

    private void initListView() {
        lv_fuwuxiangmu = (MyListView) findViewById(R.id.lv_dingdanxiangqing_fiwuxiangmu);
        fuwuxiangmuList = new ArrayList<>();
        String fuwu = dingdan.getO_WPart();
        if (fuwu.contains("|")) {
            String[] split = fuwu.split("\\|");
            for (int i = 0; i < split.length; i++) {
                Fuwuxiangmu fuwuxiangmu = new Fuwuxiangmu();
                String[] strings = split[i].split(",");
                int num = Integer.parseInt(strings[0]);
                String price = strings[1];
                switch (num) {
                    case 1:
                    case 7:
                        fuwuxiangmu.setName("车外清洗");
                        break;
                    case 2:
                    case 9:
                        fuwuxiangmu.setName("车内外清洗");
                        break;
                    case 16:
                    case 17:
                        fuwuxiangmu.setName("清洗轮毂");
                        break;
                    case 4:
                    case 11:
                        fuwuxiangmu.setName("打蜡");
                        break;
                    case 5:
                    case 12:
                        fuwuxiangmu.setName("室内精洗");
                        break;
                    case 18:
                    case 19:
                        fuwuxiangmu.setName("引擎清洗");
                        break;
                }
                fuwuxiangmu.setPrice(price);
                fuwuxiangmuList.add(fuwuxiangmu);
            }
        } else {
            Fuwuxiangmu fuwuxiangmu = new Fuwuxiangmu();
            String[] split = fuwu.split(",");
            int num = Integer.parseInt(split[0]);
            String price = split[1];
            switch (num) {
                case 1:
                case 7:
                    fuwuxiangmu.setName("车外清洗");
                    break;
                case 2:
                case 9:
                    fuwuxiangmu.setName("车内外清洗");
                    break;
                case 16:
                case 17:
                    fuwuxiangmu.setName("清洗轮毂");
                    break;
                case 4:
                case 11:
                    fuwuxiangmu.setName("打蜡");
                    break;
                case 5:
                case 12:
                    fuwuxiangmu.setName("室内精洗");
                    break;
                case 18:
                case 19:
                    fuwuxiangmu.setName("引擎清洗");
                    break;
            }
            fuwuxiangmu.setPrice(price);
            fuwuxiangmuList.add(fuwuxiangmu);
        }
        FuwuxiangmuAdapter adapter = new FuwuxiangmuAdapter(DingdanXiangqingActivity.this, fuwuxiangmuList);
        lv_fuwuxiangmu.setAdapter(adapter);
        setListViewHeightBasedOnChildren(lv_fuwuxiangmu);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        setDatas();
//    }

    private void setDatas() {
        if (dingdan.getO_TypeID() == 1) {
            if (dingdan.getO_ISCancel() == 0) {
                ll_xichegongxinxi.setVisibility(View.GONE);
                tv_zhuangtai.setText("待接单");
                btn_quxiao.setVisibility(View.VISIBLE);
            } else if (dingdan.getO_ISCancel() == 1) {
                ll_xichegongxinxi.setVisibility(View.GONE);
                tv_zhuangtai.setText("已取消");
                btn_quxiao.setVisibility(View.GONE);
            }
        } else if (dingdan.getO_TypeID() == 2) {
            ll_xichegongxinxi.setVisibility(View.VISIBLE);
            btn_quxiao.setVisibility(View.INVISIBLE);
            tv_zhuangtai.setText("已接单");
        } else if (dingdan.getO_TypeID() == 3) {
            ll_xichegongxinxi.setVisibility(View.VISIBLE);
            btn_quxiao.setVisibility(View.INVISIBLE);
            tv_zhuangtai.setText("洗车中");
        } else if (dingdan.getO_TypeID() == 4) {
            ll_xichegongxinxi.setVisibility(View.VISIBLE);
            btn_quxiao.setVisibility(View.INVISIBLE);
            tv_zhuangtai.setText("已完成");
            btn_qupingjia.setVisibility(View.VISIBLE);
        } else if (dingdan.getO_TypeID() == 5) {
            ll_xichegongxinxi.setVisibility(View.VISIBLE);
            btn_quxiao.setVisibility(View.INVISIBLE);
            btn_qupingjia.setVisibility(View.VISIBLE);
            tv_zhuangtai.setText("申请退款");
        } else if (dingdan.getO_TypeID() == 6) {
            ll_xichegongxinxi.setVisibility(View.VISIBLE);
            btn_quxiao.setVisibility(View.INVISIBLE);
            btn_qupingjia.setVisibility(View.VISIBLE);
            tv_zhuangtai.setText("已退款");
        } else if (dingdan.getO_TypeID() == 7) {
            ll_xichegongxinxi.setVisibility(View.VISIBLE);
            btn_quxiao.setVisibility(View.INVISIBLE);
            tv_zhuangtai.setText("检查车辆状况");
        } else if (dingdan.getO_TypeID() == 8) {
            ll_xichegongxinxi.setVisibility(View.VISIBLE);
            btn_quxiao.setVisibility(View.INVISIBLE);
            tv_zhuangtai.setText("已到达");
        } else if (dingdan.getO_TypeID() == 9) {
            ll_xichegongxinxi.setVisibility(View.VISIBLE);
            btn_quxiao.setVisibility(View.INVISIBLE);
            tv_zhuangtai.setText("赶往途中");
        }
        xichegongdianhua = new ArrayList<>();
        xichegongdianhua.add(dingdan.getO_WorkerTel());
        kefudianhua = new ArrayList<>();
        kefudianhua.add(tv_kefudianhua.getText().toString());
//        String str = dingdan.getO_Time();
        if (dingdan.getO_WashType() == 0) {
            String b = dingdan.getO_Time();
            String shijian = b.replace("T", "");
            tv_shijian.setText(shijian);
        } else {
            String shijian = dingdan.getO_WashTime();
            tv_shijian.setText(shijian);
        }
        String sb = dingdan.getO_TimeEnd();
        String timeend = sb.replace("T", " ");
        tv_time_end.setText(timeend);
        tv_dingdanhao.setText(dingdan.getO_ID());
        tv_chepaihao.setText(dingdan.getO_PlateNumber());
        tv_lianxiren.setText(dingdan.getO_CarName());
        tv_shoujihao.setText(dingdan.getO_UTel());
        tv_dizhi.setText(dingdan.getO_Adress());
        tv_daijinqan.setText("-" + "¥" + dingdan.getO_Ticket());
        String yuyue = dingdan.getO_WashTime();
        long yuyueshijian = getDateFromStr(yuyue);
        String xiadan = dingdan.getO_Time();
        String shijian1 = xiadan.replace("T", " ");
        String shijian2 = shijian1.substring(0, shijian1.length() - 3);
        Log.e("shijian", shijian2);
        long xiadanshijiajian = getDateFromStr(shijian2);
        long minutes = yuyueshijian - xiadanshijiajian;
        long shijiancha1 = minutes / 1000 / 60;
        Log.e("ddddddddddd", shijiancha1 + "");
        if (shijiancha1 > 30 && shijiancha1 < 60 * 6) {
            tv_fuwufei.setText("(含服务费10元)");
        } else if (shijiancha1 > 60 * 6) {
            tv_fuwufei.setText("(含服务费20元)");
        } else if (shijiancha1 < 30) {
            tv_fuwufei.setVisibility(View.GONE);
        }
        tv_hejijine.setText("" + dingdan.getO_Money());
        tv_xichegong_name.setText(dingdan.getO_WorkerName());
        tv_xichegong_gonghao.setText("" + dingdan.getO_WorkerID());
        tv_xichegong_lianxifangshi.setText(dingdan.getO_WorkerTel());
        if (dingdan.getO_CTID() == 3) {
            tv_jibie.setText("小轿车");
        } else if (dingdan.getO_CTID() == 4) {
            tv_jibie.setText("SUV/MPV");
        }
        if (TextUtils.isEmpty(dingdan.getO_WorkerImage())) {
            Picasso.with(this).load(R.drawable.morentouxiang).into(iv_touxiang);
        } else {
            Uri image = Uri.parse(Contast.Domain + dingdan.getO_WorkerImage());
            Picasso.with(this).load(image)
                    .placeholder(R.drawable.morentouxiang)
                    .error(R.drawable.morentouxiang)
                    .into(iv_touxiang);
        }
        if (TextUtils.isEmpty(dingdan.getO_BeforePic())) {
            Picasso.with(this).load(R.drawable.carmoren).into(iv_xicheqian);
        } else {
            Uri image = Uri.parse(Contast.Domain + dingdan.getO_BeforePic());
            Picasso.with(this).load(image)
                    .placeholder(R.drawable.carmoren)
                    .error(R.drawable.carmoren)
                    .into(iv_xicheqian);
        }
        if (TextUtils.isEmpty(dingdan.getO_AfterPic())) {
            Picasso.with(this).load(R.drawable.carmoren).into(iv_xichehou);
        } else {
            Uri image = Uri.parse(Contast.Domain + dingdan.getO_AfterPic());
            Picasso.with(this).load(image)
                    .placeholder(R.drawable.carmoren)
                    .error(R.drawable.carmoren)
                    .into(iv_xichehou);
        }
        pv_max.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                pv_max.setVisibility(View.GONE);
            }

            @Override
            public void onOutsidePhotoTap() {
            }
        });
        if (dingdan.getO_IsEvaluate() == 0) {
            btn_qupingjia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DingdanXiangqingActivity.this, PingjiaActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dingdan", dingdan);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        } else {
            btn_qupingjia.setVisibility(View.GONE);
        }
    }
    private void initViews() {
        quxiaoyuanyin = new ArrayList<>();
        quxiaoyuanyin.add("行程改变");
        quxiaoyuanyin.add("我想重新下单");
        quxiaoyuanyin.add("车辆信息不符");
        quxiaoyuanyin.add("洗车工要求取消订单");
        quxiaoyuanyin.add("其他原因");
        iv_back = (ImageView) findViewById(R.id.iv_dingdanxiangqing_back);
        btn_quxiao = (Button) findViewById(R.id.btn_dingdanxiangqing_more);
        ll_xichegongxinxi = (LinearLayout) findViewById(R.id.ll_dingdanxiangqing_xichegongxinxi);
        tv_dingdanhao = (TextView) findViewById(R.id.tv_dingdanxiangqing_dingdanhao);
        tv_zhuangtai = (TextView) findViewById(R.id.tv_dingdanxiangqing_zhuangtai);
        tv_shijian = (TextView) findViewById(R.id.tv_dingdanxiangqing_xiadanshijian);
        tv_chepaihao = (TextView) findViewById(R.id.tv_dingdanxiangqing_chepaihao);
        tv_lianxiren = (TextView) findViewById(R.id.tv_dingdanxiangqing_lianxiren);
        tv_shoujihao = (TextView) findViewById(R.id.tv_dingdanxiangqing_lianxifangshi);
        tv_dizhi = (TextView) findViewById(R.id.tv_dingdanxiangqing_dizhi);
        tv_jibie = (TextView) findViewById(R.id.tv_dingdanxiangqing_jibie);
        tv_hejijine = (TextView) findViewById(R.id.tv_dingdanxiangqing_hejijine);
        tv_xichegong_name = (TextView) findViewById(R.id.tv_dingdanxiangqing_xichegong_name);
        tv_xichegong_gonghao = (TextView) findViewById(R.id.tv_dingdanxiangqing_xichegong_gonghao);
        tv_xichegong_lianxifangshi = (TextView) findViewById(R.id.tv_dingdanxiangqing_xichegong_lianxifangshi);
        tv_kefudianhua = (TextView) findViewById(R.id.tv_dingdanxiangqing_kefudianhua);
        iv_touxiang = (ImageView) findViewById(R.id.iv_dingdanxiangqing_xichegong_touxiang);
        iv_xichegong_lianxifangshi = (ImageView) findViewById(R.id.iv_dingdanxiangqing_xichegong_lianxita);
        iv_kefu_lianxifangshi = (ImageView) findViewById(R.id.iv_dingdanxiangqing_kefudianhua);
        pv_max = (PhotoView) findViewById(pv_dingdanxiangqing_max);
        tv_daijinqan = (TextView) findViewById(R.id.tv_dingdanxiangqing_daijinquan);
        iv_xicheqian = (ImageView) findViewById(R.id.iv_dingdanxiangqing_xicheqian);
        iv_xichehou = (ImageView) findViewById(R.id.iv_dingdanxiangqing_xichehou);
        tv_time_end = (TextView) findViewById(R.id.tv_dingdanxiangqing_wanchengshijian);
        btn_qupingjia = (Button) findViewById(R.id.btn_dingdanxiangqing_qupingjia);
        btn_zailaiyidan = (Button) findViewById(R.id.btn_dingdanxiangqing_zailaiyidan);
        tv_fuwufei = (TextView) findViewById(R.id.tv_fuwufei_dingdanxiangqing);
        iv_back.setOnClickListener(this);
        btn_quxiao.setOnClickListener(this);
        iv_xichegong_lianxifangshi.setOnClickListener(this);
        iv_kefu_lianxifangshi.setOnClickListener(this);
        iv_xicheqian.setOnClickListener(this);
        iv_xichehou.setOnClickListener(this);
        btn_qupingjia.setOnClickListener(this);
        btn_zailaiyidan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_dingdanxiangqing_back:
                finish();
                break;
            case R.id.btn_dingdanxiangqing_more:
                quxiao();
                break;
            case R.id.iv_dingdanxiangqing_xichegong_lianxita:
                View photoView = View.inflate(DingdanXiangqingActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
                ListView listView = (ListView) photoView.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                listView.setAdapter(new DialogListViewAdapter(DingdanXiangqingActivity.this, xichegongdianhua));//ListView设置适配器

                final AlertDialog dialog = new AlertDialog.Builder(DingdanXiangqingActivity.this)
                        .setView(photoView)//在这里把写好的这个listview的布局加载dialog中
                        .create();
                dialog.show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView tv = (TextView) view.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
                        String dianhua = tv.getText().toString();
                        // 拨号：激活系统的拨号组件
                        Intent intent = new Intent(); // 意图对象：动作 + 数据
                        intent.setAction(Intent.ACTION_DIAL); // 设置动作
                        Uri data = Uri.parse("tel:" + dianhua); // 设置数据
                        intent.setData(data);
                        startActivity(intent); // 激活Activity组件
                    }
                });
                break;
            case R.id.iv_dingdanxiangqing_kefudianhua:
                View photoView1 = View.inflate(DingdanXiangqingActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
                ListView listView1 = (ListView) photoView1.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                listView1.setAdapter(new DialogListViewAdapter(DingdanXiangqingActivity.this, kefudianhua));//ListView设置适配器
                final AlertDialog dialog1 = new AlertDialog.Builder(DingdanXiangqingActivity.this)
                        .setView(photoView1)//在这里把写好的这个listview的布局加载dialog中
                        .create();
                dialog1.show();
                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView tv = (TextView) view.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
                        String dianhua = tv.getText().toString();
                        // 拨号：激活系统的拨号组件
                        Intent intent = new Intent(); // 意图对象：动作 + 数据
                        intent.setAction(Intent.ACTION_DIAL); // 设置动作
                        Uri data = Uri.parse("tel:" + dianhua); // 设置数据
                        intent.setData(data);
                        startActivity(intent); // 激活Activity组件
                    }
                });
                break;
            case R.id.iv_dingdanxiangqing_xicheqian:
                if (TextUtils.isEmpty(dingdan.getO_BeforePic())) {
                    Picasso.with(this).load(R.drawable.carmoren)
                            .placeholder(R.drawable.morentouxiang)
                            .error(R.drawable.morentouxiang)
                            .into(pv_max);
                    pv_max.setVisibility(View.VISIBLE);
                } else {
                    Uri image = Uri.parse(Contast.Domain + dingdan.getO_BeforePic());
                    Picasso.with(this).load(image)
                            .placeholder(R.drawable.morentouxiang)
                            .error(R.drawable.morentouxiang)
                            .into(pv_max);
                    pv_max.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_dingdanxiangqing_xichehou:
                if (TextUtils.isEmpty(dingdan.getO_AfterPic())) {
                    Picasso.with(this).load(R.drawable.carmoren)
                            .placeholder(R.drawable.morentouxiang)
                            .error(R.drawable.morentouxiang)
                            .into(pv_max);
                    pv_max.setVisibility(View.VISIBLE);
                    pv_max.setVisibility(View.VISIBLE);
                } else {
                    Uri image = Uri.parse(Contast.Domain + dingdan.getO_AfterPic());
                    Picasso.with(this).load(image)
                            .placeholder(R.drawable.morentouxiang)
                            .error(R.drawable.morentouxiang)
                            .into(pv_max);
                    pv_max.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_dingdanxiangqing_zailaiyidan:
                ActivityUtils.finishAll();
                Intent intent = new Intent(DingdanXiangqingActivity.this, MainActivity.class);
                intent.putExtra("from", 1);
                startActivity(intent);
                break;
            case R.id.btn_dingdanxiangqing_qupingjia:
                Intent intent1 = new Intent(DingdanXiangqingActivity.this, PingjiaActivity.class);
                intent1.putExtra("dingdan", dingdan);
                startActivity(intent1);
                break;
        }
    }

    private void quxiaodingdan(final String yuanyin) {
        Log.i(TAG, "quxiaodingdan: " + yuanyin);
        final ProgressDialog pd = new ProgressDialog(DingdanXiangqingActivity.this);
        pd.setMessage("拼命加载中...");
        pd.show();
        FormBody.Builder params = new FormBody.Builder();
        params.add("O_UTel", Contast.user.getU_Tel());
        params.add("O_PlateNumber", dingdan.getO_PlateNumber());
        params.add("O_ID", "" + dingdan.getO_IDS());
        params.add("keys", Contast.KEYS);
        if (!TextUtils.isEmpty(yuanyin)) {
            params.add("O_ISCancelValue", yuanyin);
        } else {
            params.add("O_ISCancelValue", "");
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
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
                        Toast.makeText(DingdanXiangqingActivity.this, "网络连接异常，请稍后再试", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                pd.dismiss();
                String string = response.body().string();
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(DingdanXiangqingActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Log.i(TAG, "onResponse: " + string);
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DingdanXiangqingActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Contast.user = JSON.parseObject(string, User.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DingdanXiangqingActivity.this, "订单已取消", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(DingdanXiangqingActivity.this,MyDingdanActivity.class);
//                                startActivity(intent);
                                    Log.w("tttttt", yuanyin);
                                    finish();
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(DingdanXiangqingActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    }
                }
            }
        });
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public void quxiao() {
        View photoView2 = View.inflate(DingdanXiangqingActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
        ListView listView2 = (ListView) photoView2.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
        listView2.setAdapter(new DialogListViewAdapter(DingdanXiangqingActivity.this, quxiaoyuanyin));//ListView设置适配器
        final AlertDialog dialog2 = new AlertDialog.Builder(DingdanXiangqingActivity.this)
                .setTitle("取消原因")
                .setNegativeButton("取消", null)
                .setView(photoView2)//在这里把写好的这个listview的布局加载dialog中
                .create();
        dialog2.show();
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
                String yuanyin = tv.getText().toString();
                quxiaodingdan(yuanyin);
                dialog2.dismiss();
            }
        });
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
