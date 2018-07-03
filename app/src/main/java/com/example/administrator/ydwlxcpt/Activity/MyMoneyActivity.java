package com.example.administrator.ydwlxcpt.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Adapter.YouhuiquanAdapter;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.User;
import com.example.administrator.ydwlxcpt.Bean.Youhuiquan;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.example.administrator.ydwlxcpt.wxapi.WXPayEntryActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//我的钱包
public class MyMoneyActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = "MyMoneyActivity";
    private String url_login = Contast.Domain + "api/UserLoginDefault.ashx?";
    private String url = Contast.Domain + "api/TicketList.ashx?";
    private ImageView iv_back;
    private Button btn_yuemingxi;
    private TextView tv_yue,tv_shuliang;
    private RelativeLayout rl_chongzhi;
    private RelativeLayout rl_daijinjuan;
    private ListView listView;
    private List<Youhuiquan> youhuiquanList,wolist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_money);
        //获取控件
        initViews();
        //获取ListView
        initListView();

    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.lv_mymoney_listview);
    }

    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_mymoney_back);
        btn_yuemingxi = (Button) findViewById(R.id.btn_mymoney_more);
        tv_yue = (TextView) findViewById(R.id.tv_mymoney_yue);
        rl_chongzhi = (RelativeLayout) findViewById(R.id.rl_mymoney_chongzhi);
        rl_daijinjuan = (RelativeLayout) findViewById(R.id.rl_mymoney_daijinquan);
        tv_shuliang=(TextView)findViewById(R.id.tv_yhj_shuliang);
        iv_back.setOnClickListener(this);
        rl_chongzhi.setOnClickListener(this);
        rl_daijinjuan.setOnClickListener(this);
        btn_yuemingxi.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initUser();
        refelash();
    }

    private void initUser() {
        FormBody.Builder params = new FormBody.Builder();
        params.add("U_Tel", Contast.user.getU_Tel());
        params.add("U_IMEI", Contast.user.getU_IMEI());
        params.add("keys", Contast.KEYS);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_login)
                .post(params.build())
                .build();

        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                //响应失败
                Toast.makeText(MyMoneyActivity.this, "网络连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                return;

            }
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                //响应成功  response.body().string() 获取字符串数据，当然还可以获取其他
                String string = response.body().string();
                Log.i(TAG, "onResponse: string=" + string);
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(MyMoneyActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    Log.i(TAG, "onResponse: json=" + string);
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MyMoneyActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                    return;

                                }
                            });
                        } else {
                            User user = JSON.parseObject(string, User.class);
                            Contast.user = user;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_yue.setText(""+Contast.user.getU_Money());
                                    Intent intent1 = new Intent();
                                    intent1.putExtra("U_Mone",Contast.user.getU_Money()); // 这里用来传值
                                    intent1.setClass(MyMoneyActivity.this, WXPayEntryActivity.class);

                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MyMoneyActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        });
                    }
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_mymoney_back:
                finish();
                break;
            case R.id.btn_mymoney_more:
                //TODO
                //跳转到余额明细界面
                Intent intent = new Intent(MyMoneyActivity.this,YuemingxianActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_mymoney_chongzhi:
                //跳转到充值界面
                Intent intent2 = new Intent(MyMoneyActivity.this,ChongzhiActivity.class);
                startActivity(intent2);
                break;
            case R.id.rl_mymoney_daijinquan:
                //跳转到代金券界面
                Intent intent1 = new Intent(MyMoneyActivity.this,MyYouhuiquanActivity.class);
                intent1.putExtra("tt",1);
                startActivity(intent1);
                break;
        }
    }
    //网络请求
    private void refelash() {
        final ProgressDialog pd = new ProgressDialog(MyMoneyActivity.this);
        pd.setMessage("拼命加载中...");
        pd.show();
        FormBody.Builder params = new FormBody.Builder();
        params.add("T_UTel", Contast.user.getU_Tel());
        params.add("keys", Contast.KEYS);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(params.build())
                .build();

        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                pd.dismiss();
                //响应失败
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyMoneyActivity.this, "网络连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                        return;

                    }
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                pd.dismiss();
                //响应成功  response.body().string() 获取字符串数据，当然还可以获取其他
                final String string = response.body().string();
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(MyMoneyActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    Log.i(TAG, "onResponse: json=" + string);
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MyMoneyActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                    return;

                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    youhuiquanList = JSON.parseArray(string, Youhuiquan.class);
                                    wolist = new ArrayList<>();
                                    for (Youhuiquan item : youhuiquanList) {
                                        if (item.getT_IsLock()==0) {
                                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                                            String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
                                            long date2 = getDateFromStr(date);
                                            String zhuce = item.getT_Time();
                                            String zhuce1 = zhuce.replace("T", " ");
                                            long zhuce3 = getDateFromStr(zhuce1);
                                            long minutes = date2 - zhuce3;
                                            if(minutes<=0) {
                                                wolist.add(item);
                                            }else{

                                            }
                                        }
                                    }
                                    tv_shuliang.setText(wolist.size()+"张");
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MyMoneyActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        });
                    }
                }
            }
        });
    }
    //转换时间格式
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
