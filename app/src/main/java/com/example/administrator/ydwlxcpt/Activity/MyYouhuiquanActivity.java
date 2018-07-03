package com.example.administrator.ydwlxcpt.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Adapter.YouhuiquanAdapter;
import com.example.administrator.ydwlxcpt.Bean.Dingdan;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.Massage;
import com.example.administrator.ydwlxcpt.Bean.Youhuiquan;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.administrator.ydwlxcpt.Contast.Contast.youhuiquan;

//优惠券
public class MyYouhuiquanActivity extends AppCompatActivity {

    private static final String TAG = "MyYouhuiquanActivity";
    private String url = Contast.Domain + "api/TicketList.ashx?";
    private ImageView iv_back;
    private ListView listView;
    private List<Youhuiquan> youhuiquanList;
    private YouhuiquanAdapter adapter;
    private RelativeLayout rl_null;
    private List<Youhuiquan> jinxingzhongList,wolist;
    private int leixing;
    private int xianshi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_youhuiquan);
        //获取控件
        initViews();
        //listview连接适配器
        initListView();
        //获取参数
        Intent intent = getIntent();
        leixing = intent.getIntExtra("tt",-1);
    }

    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_myyouhuiquan_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_null=(RelativeLayout)findViewById(R.id.rl_myyhj_null);
    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.lv_myyouhuiquan_listview);
        youhuiquanList = new ArrayList<>();
        if (youhuiquanList != null) {
            adapter = new YouhuiquanAdapter(MyYouhuiquanActivity.this, youhuiquanList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Youhuiquan youhuiquan = (Youhuiquan) adapter.getItem(position);
                    Intent intent = new Intent();
                    intent.putExtra("youhuiquan", youhuiquan);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        } else {
            listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.meiyoushuju));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        refelash();
    }
//    public void dianji(int position){
//        Youhuiquan youhuiquan = (Youhuiquan) adapter.getItem(position-1);
//        Intent intent = new Intent();
//        intent.putExtra("youhuiquan", youhuiquan);
//        setResult(RESULT_OK, intent);
//        finish();
//    }

    private void refelash() {
        final ProgressDialog pd = new ProgressDialog(MyYouhuiquanActivity.this);
        pd.setMessage("拼命加载中...");
        pd.show();
        FormBody.Builder params = new FormBody.Builder();
        params.add("T_UTel",Contast.user.getU_Tel());
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
                        Toast.makeText(MyYouhuiquanActivity.this, "网络连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(MyYouhuiquanActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    Log.i(TAG, "onResponse: json=" + string);
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MyYouhuiquanActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                    return;

                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (leixing==1) {

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
                                        adapter.setData(wolist);
                                        if (wolist.size()>0){
                                            rl_null.setVisibility(View.GONE);
                                        }else {
                                            rl_null.setVisibility(View.VISIBLE);
                                        }
//                                        String id = youhuiquanList.toString();
//                                        SharedPreferences sh1 = getSharedPreferences("YHJBH", MODE_PRIVATE);
//                                        SharedPreferences.Editor editor = sh1.edit();
//                                        editor.putString("yhjbh",id);
//                                        editor.commit();
                                        Log.e("b", youhuiquanList.toString());
                                    }else if (leixing==2){
                                        youhuiquanList = JSON.parseArray(string, Youhuiquan.class);
                                        jinxingzhongList = new ArrayList<>();
                                        for (Youhuiquan item : youhuiquanList) {
                                            SharedPreferences sh=getSharedPreferences("yhjsl",MODE_PRIVATE);
                                            String yhjsl= sh.getString("yhjzs","");
                                            int type = item.getT_Money();
                                            int ss= Integer.parseInt(yhjsl);
                                                if (type <ss||type==ss) {
                                                    if (item.getT_IsLock()==0)
                                                    jinxingzhongList.add(item);
                                                }
                                        }
                                        adapter.setData(jinxingzhongList);
//                                        checkList(jinxingzhongList);
                                    }
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MyYouhuiquanActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        });
                    }

                }
            }
        });
    }
    private void checkList(List<Youhuiquan> list) {
        if (list.size() > 0) {
            rl_null.setVisibility(View.INVISIBLE);
        } else {
            rl_null.setVisibility(View.VISIBLE);
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
