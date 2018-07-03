package com.example.administrator.ydwlxcpt.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Adapter.QiangyhjAdapter;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.Massage;
import com.example.administrator.ydwlxcpt.Bean.YHJMingxi;
import com.example.administrator.ydwlxcpt.Bean.Youhuiquan;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.example.administrator.ydwlxcpt.Utils.Utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.administrator.ydwlxcpt.Contast.Contast.yhjMingxi;
import static com.example.administrator.ydwlxcpt.Contast.Contast.youhuiquan;

public class YouHuiJuanTongzhi extends BaseActivity {
    private static final String TAG = "YouHuiJuanTongzhi";
    @BindView(R.id.iv_yhjtz_back)
    ImageView ivYhjtzBack;
    @BindView(R.id.tv_yhjtz_title)
    TextView tvYhjtzTitle;
    @BindView(R.id.btn_yhjtz_more)
    ImageView btnYhjtzMore;
    @BindView(R.id.lv_yhjmx_tz)
    ListView lvYhjmxTz;
    @BindView(R.id.rl_myyhj_null)
    RelativeLayout rlMyyhjNull;
    private String url_qiang = Contast.Domain + "api/Ticket.ashx";
    private String url_shuaxin = Contast.Domain + "api/TicketMeList.ashx";
    private List<YHJMingxi> YHJlist;
    private QiangyhjAdapter adapter;
    private ListView listView;
    private List<Youhuiquan> youhuiquanList;
    private String url_liebiao = Contast.Domain + "api/TicketList.ashx?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_hui_juan_tongzhi);
        ButterKnife.bind(this);
//      initViews();
        initListView();
       refelash();
       shuaxin();
    }


    public void shuaxin() {
        final ProgressDialog pd = new ProgressDialog(YouHuiJuanTongzhi.this);
        pd.setMessage("拼命加载中...");
        pd.show();
        FormBody.Builder params = new FormBody.Builder();
        params.add("T_UTel", Contast.user.getU_Tel());
        params.add("TM_ID", yhjMingxi.getTM_ID() + "");
        params.add("keys", Contast.KEYS);
        Log.i("323313213131323", yhjMingxi.getTM_ID() + "");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_shuaxin)
                .post(params.build())
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                pd.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(YouHuiJuanTongzhi.this, "网络连接异常，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                pd.dismiss();
                final String string = response.body().string();
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(YouHuiJuanTongzhi.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.i(TAG, "onResponse: " + string);
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(YouHuiJuanTongzhi.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    YHJlist = JSON.parseArray(string, YHJMingxi.class);
//                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//                                    String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
//                                    long date2 = getDateFromStr(date);
//                                    String zhuce = Contast.user.getU_CreatTime();
//                                    String zhuce1 = zhuce.replace("T", " ");
//                                    long zhuce3 = getDateFromStr(zhuce1);
//                                    long minutes = date2 - zhuce3;
//                                    long shijiancha1 = minutes / 1000 / 60;
//                                    if (shijiancha1 > 60 * 24) {
                                        List<YHJMingxi> yiquxiao = new ArrayList<>();
                                        for (YHJMingxi item : YHJlist) {
                                            if (item.getTM_ID() != 1028) {
                                                yiquxiao.add(item);
                                                Log.e("优惠券",yiquxiao.toString());
                                            }
                                        }
                                        adapter.setData(yiquxiao);
                                        if (yiquxiao.size() > 0) {
                                            rlMyyhjNull.setVisibility(View.GONE);
                                        } else {
                                            rlMyyhjNull.setVisibility(View.VISIBLE);
                                        }
//                                    } else {
//                                        adapter.setData(YHJlist);
//                                        if (YHJlist.size() > 0) {
//                                            rlMyyhjNull.setVisibility(View.GONE);
//                                        } else {
//                                            rlMyyhjNull.setVisibility(View.VISIBLE);
//                                        }
//                                    }

                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(YouHuiJuanTongzhi.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }


    private void initListView() {
        listView = (ListView) findViewById(R.id.lv_yhjmx_tz);
        YHJlist = new ArrayList<>();
        if (YHJlist != null) {
            adapter = new QiangyhjAdapter(this, YHJlist);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    YHJMingxi yhjMingxi=(YHJMingxi) adapter.getItem(position);
                        String b2=yhjMingxi.getTM_ID()+"";
                    SharedPreferences sh1 = getSharedPreferences("YHJBH", MODE_PRIVATE);
                    String b= sh1.getString("yhjbh", "");
                    Log.w("优惠券ID",b);
                        if (Utils.isFastClick()) {
                            if (b.contains(b2)) {
                                Toast.makeText(YouHuiJuanTongzhi.this, "该代金券已经领取", Toast.LENGTH_SHORT).show();
                            } else {
                                final ProgressDialog pd = new ProgressDialog(YouHuiJuanTongzhi.this);
                                pd.setMessage("拼命加载中...");
                                pd.show();
                                FormBody.Builder params = new FormBody.Builder();
                                params.add("T_UTel", Contast.user.getU_Tel());
                                params.add("T_TMID", b2);
                                params.add("keys", Contast.KEYS);
                                Log.i("ninininininniinni", b2);
                                OkHttpClient client = new OkHttpClient();
                                Request request = new Request.Builder()
                                        .url(url_qiang)
                                        .post(params.build())
                                        .build();
                                Call call = client.newCall(request);
                                call.enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                pd.dismiss();
                                                Toast.makeText(YouHuiJuanTongzhi.this, "网络连接异常，请稍后再试", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        final String string = response.body().string();
                                        if (response.code() != HttpURLConnection.HTTP_OK) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(YouHuiJuanTongzhi.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else {
                                            Log.i(TAG, "onResponse: " + string);
                                            if (!TextUtils.isEmpty(string)) {
                                                if (string.contains("ErrorStr")) {
                                                    final Error error = JSON.parseObject(string, Error.class);
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(YouHuiJuanTongzhi.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                } else {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(YouHuiJuanTongzhi.this, "领取成功", Toast.LENGTH_SHORT).show();
//                                                        YHJlist.remove(position);
                                                            refelash();
                                                        }
                                                    });
                                                }
                                            } else {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(YouHuiJuanTongzhi.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });
                            }
                        }
                }
            });
        } else {
            listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.meiyoushuju));
        }

    }

    private void refelash() {
        final ProgressDialog pd = new ProgressDialog(YouHuiJuanTongzhi.this);
        pd.setMessage("拼命加载中...");
        pd.show();
        FormBody.Builder params = new FormBody.Builder();
        params.add("T_UTel", Contast.user.getU_Tel());
        params.add("keys", Contast.KEYS);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_liebiao)
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
                        Toast.makeText(YouHuiJuanTongzhi.this, "网络连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                pd.dismiss();
                //响应成功  response.body().string() 获取字符串数据，当然还可以获取其他
                final String string = response.body().string();
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(YouHuiJuanTongzhi.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i(TAG, "onResponse: json=" + string);
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(YouHuiJuanTongzhi.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    youhuiquanList = JSON.parseArray(string, Youhuiquan.class);
//                                    for (Youhuiquan item : youhuiquanList) {
//                                        int type=item.getT_TMID();
                                        ArrayList mId = new ArrayList<Integer>();
                                        for(int i=0;i<youhuiquanList.size();i++)
                                        {
                                            String id = youhuiquanList.get(i).getT_TMID()+"";
                                            mId.add(id);
                                    }
                                        SharedPreferences sh1 = getSharedPreferences("YHJBH", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sh1.edit();
                                        editor.putString("yhjbh", mId.toString());
                                        editor.commit();
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(YouHuiJuanTongzhi.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }
//    @BindView(R.id.iv_yhjtz_back)
//    ImageView ivYhjtzBack;
//    @BindView(R.id.tv_yhjtz_title)
//    TextView tvYhjtzTitle;
//
//    @BindView(R.id.tv_huodongshijian_start)
//    TextView tvHuodongshijianStart;
//    @BindView(R.id.tv_huodongshijian_end)
//    TextView tvHuodongshijianEnd;
//    @BindView(R.id.tv_yhj_jine_5)
//    TextView tvYhjJine5;
//    @BindView(R.id.tv_yhj_lijishiyong)
//    TextView tvYhjLijishiyong;
//    @BindView(R.id.iv_yhj_5)
//    LinearLayout ivYhj5;
//    @BindView(R.id.btn_yhjtz_more)
//    ImageView btnYhjtzMore;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_you_hui_juan_tongzhi);
//        ButterKnife.bind(this);
//    }
//
//    @OnClick({R.id.iv_yhjtz_back, R.id.iv_yhj_5, R.id.tv_yhj_lijishiyong,R.id.btn_yhjtz_more})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.iv_yhjtz_back:
//                finish();
//                break;
//            case R.id.iv_yhj_5:
//                break;
//            case R.id.tv_yhj_lijishiyong:
//                break;
//            case R.id.btn_yhjtz_more:
//                Intent intent=new Intent(YouHuiJuanTongzhi.this,YHJMX.class);
//                startActivity(intent);
//                break;
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        shuaxin();
    }
    @OnClick(R.id.iv_yhjtz_back)
    public void onViewClicked() {
        finish();
    }

    public long getDateFromStr(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
