package com.example.administrator.ydwlxcpt.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Adapter.DialogListViewAdapter;
import com.example.administrator.ydwlxcpt.Bean.Dingdan;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.User;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/2/27.
 */

public class DialogActivity extends Activity implements View.OnClickListener{
    private List<String> quxiaoyuanyin;
    private Dingdan dingdan;
    private String url_quxiao = Contast.Domain + "api/OrderDelete.ashx?";
    private String url_dingdan = Contast.Domain + "api/OrderList2.ashx?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //////设置为true点击区域外消失
        setFinishOnTouchOutside(true);
        setContentView(R.layout.dialog_content_normal);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = (int) (d.getHeight() * 0.3); // 高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 1); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
        ButterKnife.bind(this);
        getWindow().setWindowAnimations(R.style.mystyle);//动画
        p.gravity = Gravity.BOTTOM;//设置对话框置顶显示
//        iniver();
    }
//    @OnClick({R.id.bt_dadianhua, R.id.bt_quxiao})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.bt_dadianhua:
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + dingdan.getO_WorkerTel()));
//                startActivity(intent);
//                break;
//            case R.id.bt_quxiao:
//                if (dingdan.getO_ISCancel()!=1) {
//                    if (dingdan.getO_TypeID()==1) {
//                        View photoView2 = View.inflate(DialogActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
//                        ListView listView2 = (ListView) photoView2.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
//                        listView2.setAdapter(new DialogListViewAdapter(DialogActivity.this, quxiaoyuanyin));//ListView设置适配器
//
//                        final AlertDialog dialog2 = new AlertDialog.Builder(DialogActivity.this)
//                                .setTitle("取消原因")
//                                .setNegativeButton("取消", null)
//                                .setView(photoView2)//在这里把写好的这个listview的布局加载dialog中
//                                .create();
//                        dialog2.show();
//                        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                TextView tv = (TextView) view.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
//                                String yuanyin = tv.getText().toString();
//                                quxiaodingdan(yuanyin);
//                                dialog2.dismiss();
//                            }
//                        });
//                    }else {
//                        Toast.makeText(DialogActivity.this, "订单正在进行，请不要取消", Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    Toast.makeText(DialogActivity.this, "订单已经取消", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//    }
    private void refelash() {
//      final ProgressDialog pd = new ProgressDialog(MyDingdanActivity.this);
//      pd.setMessage("拼命加载中...");
//      pd.show();
        FormBody.Builder params = new FormBody.Builder();
        params.add("keys", Contast.KEYS);
        params.add("O_UTel", Contast.user.getU_Tel());
        params.add("pageSize", String.valueOf(1));
        params.add("pageIndex", String.valueOf(0));
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
                        Toast.makeText(DialogActivity.this, "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(final Call call, Response response) throws IOException {
//                pd.dismiss();
                final String string = response.body().string();
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(DialogActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                } else {
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DialogActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    List<Dingdan> dingdans = JSON.parseArray(string, Dingdan.class);
                                    if (dingdans.size() > 0) {
                                        for (final Dingdan dingdan : dingdans) {
                                            quxiaoyuanyin = new ArrayList<>();
                                            quxiaoyuanyin.add("行程改变");
                                            quxiaoyuanyin.add("我想重新下单");
                                            quxiaoyuanyin.add("车辆信息不符");
                                            quxiaoyuanyin.add("洗车工要求取消订单");
                                            quxiaoyuanyin.add("其他原因");
                                            TextView tvDingdanZhuangtai = (TextView) findViewById(R.id.tv_dingdan_zhuangtai);
                                            TextView tvDingdanhao = (TextView) findViewById(R.id.tv_dingdanhao);
                                            TextView xiadanshijian = (TextView) findViewById(R.id.tv_xiadanshijian);
                                            TextView yuangongmane = (TextView) findViewById(R.id.tv_name);
                                            LinearLayout xichegong = (LinearLayout) findViewById(R.id.xichegong_xinxi);
                                            LinearLayout chuangkou=(LinearLayout)findViewById(R.id.chuangkou);
                                            RelativeLayout meiyoushuju=(RelativeLayout)findViewById(R.id.meiyoushuju);
                                            final Button dadianhua = (Button) findViewById(R.id.bt_dadianhua);
                                            Button quxiao = (Button) findViewById(R.id.bt_quxiao);
                                            CircleImageView iv_touxiang = (CircleImageView) findViewById(R.id.iv_yangong_touxiang);
                                            if (TextUtils.isEmpty(dingdan.getO_WorkerImage())) {
                                                Picasso.with(DialogActivity.this).load(R.drawable.cehuamorentouxiang).into(iv_touxiang);
                                            } else {
                                                Uri image = Uri.parse(dingdan.getO_WorkerImage());
                                                Picasso.with(DialogActivity.this).load(image)
                                                        .placeholder(R.drawable.cehuamorentouxiang)
                                                        .error(R.drawable.cehuamorentouxiang)
                                                        .into(iv_touxiang);
                                            }
                                            if (dingdan.getO_TypeID() == 1) {
                                                if (dingdan.getO_ISCancel() == 0) {
                                                    tvDingdanZhuangtai.setText("待接单");
                                                    chuangkou.setVisibility(View.VISIBLE);
                                                    meiyoushuju.setVisibility(View.GONE);
                                                } else if (dingdan.getO_ISCancel() == 1) {
                                                    tvDingdanZhuangtai.setText("已取消");
                                                    chuangkou.setVisibility(View.GONE);
                                                    meiyoushuju.setVisibility(View.VISIBLE);
                                                }
                                            } else if (dingdan.getO_TypeID() == 2) {
                                                tvDingdanZhuangtai.setText("在赶往途中");
                                                chuangkou.setVisibility(View.VISIBLE);
                                                meiyoushuju.setVisibility(View.GONE);
                                            } else if (dingdan.getO_TypeID() == 3) {
                                                tvDingdanZhuangtai.setText("进行中");
                                                chuangkou.setVisibility(View.VISIBLE);
                                                meiyoushuju.setVisibility(View.GONE);
                                            } else if (dingdan.getO_TypeID() == 4) {
                                                tvDingdanZhuangtai.setText("已完成");
                                                chuangkou.setVisibility(View.GONE);
                                                meiyoushuju.setVisibility(View.VISIBLE);
                                            } else if (dingdan.getO_TypeID() == 5) {
                                                tvDingdanZhuangtai.setText("申请退款");
                                            } else if (dingdan.getO_TypeID() == 6) {
                                                tvDingdanZhuangtai.setText("已退款");
                                            } else if (dingdan.getO_TypeID() == 7) {
                                                tvDingdanZhuangtai.setText("检查车辆状况");
                                            } else if (dingdan.getO_TypeID() == 8) {
                                                tvDingdanZhuangtai.setText("已到达");
                                                chuangkou.setVisibility(View.VISIBLE);
                                                meiyoushuju.setVisibility(View.GONE);
                                            }
                                            yuangongmane.setText(dingdan.getO_WorkerName());
                                            tvDingdanhao.setText(dingdan.getO_ID());
                                            String str = dingdan.getO_Time();
                                            String shijian = str.replace("T", "  ");
                                            xiadanshijian.setText(shijian);
                                            quxiao.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if (dingdan.getO_ISCancel()!=1) {
                                                        if (dingdan.getO_TypeID()==1) {
                                                            View photoView2 = View.inflate(DialogActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
                                                            ListView listView2 = (ListView) photoView2.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                                                            listView2.setAdapter(new DialogListViewAdapter(DialogActivity.this, quxiaoyuanyin));//ListView设置适配器

                                                            final AlertDialog dialog2 = new AlertDialog.Builder(DialogActivity.this)
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
                                                        }else {
                                                            Toast.makeText(DialogActivity.this, "订单正在进行，请不要取消", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }else {
                                                        Toast.makeText(DialogActivity.this, "订单已经取消", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                            dadianhua.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if (dingdan.getO_TypeID()!=1){
                                                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + dingdan.getO_WorkerTel()));
                                                        startActivity(intent);
                                                    }else {
                                                        Toast.makeText(DialogActivity.this, "您的订单还没有接单，请稍后", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                            }
                                        }
                                }

                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(DialogActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }
    private void quxiaodingdan(String yuanyin) {
        final ProgressDialog pd = new ProgressDialog(DialogActivity.this);
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
                .url(url_quxiao)
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
                        Toast.makeText(DialogActivity.this, "网络连接异常，请稍后再试", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                pd.dismiss();
                String string = response.body().string();
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(DialogActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DialogActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Contast.user = JSON.parseObject(string, User.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DialogActivity.this, "订单已取消", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(DingdanXiangqingActivity.this,MyDingdanActivity.class);
//                                startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(DialogActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    }
                }
            }
        });
    }
//    private void iniver() {
//
//        quxiaoyuanyin = new ArrayList<>();
//        quxiaoyuanyin.add("行程改变");
//        quxiaoyuanyin.add("我想重新下单");
//        quxiaoyuanyin.add("车辆信息不符");
//        quxiaoyuanyin.add("洗车工要求取消订单");
//        quxiaoyuanyin.add("其他原因");
//        TextView tvDingdanZhuangtai = (TextView) findViewById(R.id.tv_dingdan_zhuangtai);
//        TextView tvDingdanhao = (TextView) findViewById(R.id.tv_dingdanhao);
//        TextView xiadanshijian = (TextView) findViewById(R.id.tv_xiadanshijian);
//        TextView yuangongmane = (TextView) findViewById(R.id.tv_name);
//        LinearLayout xichegong = (LinearLayout) findViewById(R.id.xichegong_xinxi);
//        Button dadianhua = (Button) findViewById(R.id.bt_dadianhua);
//        Button quxiao = (Button) findViewById(R.id.bt_quxiao);
//        CircleImageView iv_touxiang = (CircleImageView) findViewById(R.id.iv_yangong_touxiang);
//        dadianhua.setOnClickListener(this);
//        quxiao.setOnClickListener(this);
//        if (TextUtils.isEmpty(dingdan.getO_WorkerImage())) {
//            Picasso.with(DialogActivity.this).load(R.drawable.cehuamorentouxiang).into(iv_touxiang);
//        } else {
//            Uri image = Uri.parse(dingdan.getO_WorkerImage());
//            Picasso.with(DialogActivity.this).load(image)
//                    .placeholder(R.drawable.cehuamorentouxiang)
//                    .error(R.drawable.cehuamorentouxiang)
//                    .into(iv_touxiang);
//        }
//        if (dingdan.getO_TypeID() == 1) {
//            if (dingdan.getO_ISCancel() == 0) {
//                tvDingdanZhuangtai.setText("待接单");
//                xichegong.setVisibility(View.GONE);
//            } else if (dingdan.getO_ISCancel() == 1) {
//                tvDingdanZhuangtai.setText("已取消");
//            }
//        } else if (dingdan.getO_TypeID() == 2) {
//            tvDingdanZhuangtai.setText("在赶往途中");
//            xichegong.setVisibility(View.VISIBLE);
//        } else if (dingdan.getO_TypeID() == 3) {
//            tvDingdanZhuangtai.setText("进行中");
//            xichegong.setVisibility(View.VISIBLE);
//        } else if (dingdan.getO_TypeID() == 4) {
//            tvDingdanZhuangtai.setText("已完成");
//            xichegong.setVisibility(View.VISIBLE);
//        } else if (dingdan.getO_TypeID() == 5) {
//            tvDingdanZhuangtai.setText("申请退款");
//        } else if (dingdan.getO_TypeID() == 6) {
//            tvDingdanZhuangtai.setText("已退款");
//        } else if (dingdan.getO_TypeID() == 7) {
//            tvDingdanZhuangtai.setText("检查车辆状况");
//        } else if (dingdan.getO_TypeID() == 8) {
//            tvDingdanZhuangtai.setText("已到达");
//            xichegong.setVisibility(View.VISIBLE);
//        }
//        yuangongmane.setText(dingdan.getO_WorkerName());
//        tvDingdanhao.setText(dingdan.getO_ID());
//        xiadanshijian.setText(dingdan.getO_Time());
//    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        refelash();
    }
}
