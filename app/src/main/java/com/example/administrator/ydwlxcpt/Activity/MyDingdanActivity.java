package com.example.administrator.ydwlxcpt.Activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Adapter.DingdanAdapter;
import com.example.administrator.ydwlxcpt.Bean.Dingdan;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.User;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.example.administrator.ydwlxcpt.Utils.ActivityUtils;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyDingdanActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private String url = Contast.Domain + "api/OrderList.ashx?";

    private static final String TAG = "MyDingdanActivity";
    private ImageView iv_back;
    private RadioButton rb_all;
    private RadioButton rb_yiwancheng;
    private RadioButton rb_danjiedan;
    private RadioButton rb_jinxingzhong;
    private RadioButton rb_yiquxiao;
    private View line_all;
    private View line_yiwancheng;
    private View line_danjiedan;
    private View line_jinxingzhong;
    private View line_yiquxiao;
    private Button btn_xiadan;
    private String dizhi;
    private RelativeLayout rl_null;
    private PullToRefreshListView listView;
    private List<Dingdan> dingdanList;
    private DingdanAdapter adapter;
    private CompoundButton buttonView;
   public int IT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dingdan);
        Intent intent = getIntent();
        dizhi = intent.getStringExtra("dizhi");
        //获取控件
        initViews();
        initListView();
    }


    private void initListView() {
        listView = (PullToRefreshListView) findViewById(R.id.ptrlv_mydingdan_listview);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        dingdanList = new ArrayList<>();
        adapter = new DingdanAdapter(this, dingdanList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("123", "onItemClick: position=" + position);
                Dingdan dingdan = (Dingdan) adapter.getItem(position - 1);
                Intent intent = new Intent(MyDingdanActivity.this, DingdanXiangqingActivity.class);
                intent.putExtra("dingdan", dingdan);
                startActivity(intent);
            }
        });
    }

       private void checkList(List<Dingdan> list) {
        if (list.size() > 0) {
            rl_null.setVisibility(View.INVISIBLE);
        } else {
            rl_null.setVisibility(View.VISIBLE);
        }
    }


    private void refelash() {
//        final ProgressDialog pd = new ProgressDialog(MyDingdanActivity.this);
//        pd.setMessage("拼命加载中...");
//        pd.show();
        FormBody.Builder params = new FormBody.Builder();
        params.add("keys", Contast.KEYS);
        params.add("O_UTel", Contast.user.getU_Tel());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
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
                        Toast.makeText(MyDingdanActivity.this, "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
                        return;

                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
//                pd.dismiss();
                final String string = response.body().string();
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(MyDingdanActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                } else {
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MyDingdanActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                    return;

                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dingdanList.clear();
                                    List<Dingdan> dingdans = JSON.parseArray(string, Dingdan.class);
                                    if (dingdans.size() > 0) {
                                        for (Dingdan dingdan : dingdans) {
//                                           if (dingdan.getO_ISCancel() == 0) {
                                            dingdanList.add(dingdan);
                                            Log.w("dingdan",dingdanList.toString());
//                                            }
                                        }
                                    }
                                    checkList(dingdanList);
                                    adapter.setData(dingdanList);
                                }

                            });

                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MyDingdanActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        });
                    }
                }
            }

        });
    }
    public void dianji(){
     if (rb_all.isChecked()){
         IT=1;
     }else if (rb_yiwancheng.isChecked()){
         IT=2;
     }else if (rb_danjiedan.isChecked()){
         IT=3;
     }else if (rb_jinxingzhong.isChecked()){
         IT=4;
     }else if (rb_yiquxiao.isChecked()){
         IT=5;
     }
    }

//    private void quxiaodingdan(final Dingdan dingdan) {
//        Log.i(TAG, "quxiaodingdan: " + "服务时间超时");
//        FormBody.Builder params = new FormBody.Builder();
//        params.add("O_UTel", Contast.user.getU_Tel());
//        params.add("O_PlateNumber", dingdan.getO_PlateNumber());
//        params.add("O_ID", "" + dingdan.getO_IDS());
//        params.add("keys", Contast.KEYS);
//        params.add("O_ISCancelValue", "服务时间超时");
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(url_delete)
//                .post(params.build())
//                .build();
//        okhttp3.Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(MyDingdanActivity.this, "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
//                    }
//                });
//         }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String string = response.body().string();
//                if (response.code() != HttpURLConnection.HTTP_OK) {
//                    Toast.makeText(MyDingdanActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
//                } else {
//                    Log.i(TAG, "onResponse: " + string);
//                    if (!TextUtils.isEmpty(string)) {
//                        if (string.contains("ErrorStr")) {
//                            final Error error = JSON.parseObject(string, Error.class);
//                            Toast.makeText(MyDingdanActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
//                        } else {
//                            Contast.user = JSON.parseObject(string, User.class);
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    dingdanList.remove(dingdan);
//                                    checkList(dingdanList);
//                                    adapter.setData(dingdanList);
//                                    Toast.makeText(MyDingdanActivity.this, "订单已取消", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                    } else {
//                        Toast.makeText(MyDingdanActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//    }

    @Override
    protected void onResume() {
        super.onResume();
        refelash();
        rb_all.setChecked(true);
        line_all.setVisibility(View.VISIBLE);
    }

    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_mydingdan_back);
        rb_all = (RadioButton) findViewById(R.id.rb_mydingdan_all);
        rb_yiwancheng = (RadioButton) findViewById(R.id.rb_mydingdan_yiwancheng);
        rb_danjiedan = (RadioButton) findViewById(R.id.rb_mydingdan_danjiedan);
        rb_jinxingzhong = (RadioButton) findViewById(R.id.rb_mydingdan_jinxingzhong);
        rl_null = (RelativeLayout) findViewById(R.id.rl_mydingdan_null);
//        btn_xiadan = (Button) findViewById(R.id.btn_mydingdan_xiadan);
        line_all = findViewById(R.id.line_mydingdan_all);
        line_yiwancheng = findViewById(R.id.line_mydingdan_yiwancheng);
        line_danjiedan = findViewById(R.id.line_mydingdan_danjiedan);
        line_jinxingzhong = findViewById(R.id.line_mydingdan_jinxingzhong);
        line_yiquxiao=findViewById(R.id.line_mydingdan_yiquxiao);
        rb_yiquxiao=(RadioButton) findViewById(R.id.rb_mydingdan_yiquxiao);
        iv_back.setOnClickListener(this);
//        btn_xiadan.setOnClickListener(this);
        rb_all.setOnCheckedChangeListener(this);
        rb_yiwancheng.setOnCheckedChangeListener(this);
        rb_danjiedan.setOnCheckedChangeListener(this);
        rb_jinxingzhong.setOnCheckedChangeListener(this);
        rb_yiquxiao.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_mydingdan_back:
                finish();
                break;
//            case R.id.btn_mydingdan_xiadan:
//                ActivityUtils.finishAll();
//                Intent intent = new Intent(MyDingdanActivity.this, MainActivity.class);
//                intent.putExtra("from", 1);
//                startActivity(intent);
//                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rb_mydingdan_all:
                if (isChecked) {
                    line_all.setVisibility(View.VISIBLE);
                    rb_yiwancheng.setChecked(false);
                    rb_danjiedan.setChecked(false);
                    rb_jinxingzhong.setChecked(false);
                    rb_yiquxiao.setChecked(false);
                    line_yiwancheng.setVisibility(View.INVISIBLE);
                    line_danjiedan.setVisibility(View.INVISIBLE);
                    line_jinxingzhong.setVisibility(View.INVISIBLE);
                    line_yiquxiao.setVisibility(View.INVISIBLE);

                    checkList(dingdanList);
                    adapter.setData(dingdanList);
                } else {
                    line_all.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.rb_mydingdan_yiquxiao:
                if (isChecked){
                    line_yiquxiao.setVisibility(View.VISIBLE);
                    rb_all.setChecked(false);
                    rb_yiwancheng.setChecked(false);
                    rb_jinxingzhong.setChecked(false);
                    rb_danjiedan.setChecked(false);
                    rb_yiquxiao.setChecked(true);
                    line_yiwancheng.setVisibility(View.INVISIBLE);
                    line_all.setVisibility(View.INVISIBLE);
                    line_jinxingzhong.setVisibility(View.INVISIBLE);
                    line_danjiedan.setVisibility(View.INVISIBLE);
                    checkList(dingdanList);
                    List<Dingdan> yiquxiao = new ArrayList<>();
                    for (Dingdan item : dingdanList) {
                        if (item.getO_ISCancel()== 1) {
                            yiquxiao.add(item);
                        }
                    }
                    checkList(yiquxiao);
                    adapter.setData(yiquxiao);
                } else {
                    line_yiquxiao.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.rb_mydingdan_yiwancheng:

                if (isChecked) {
                    line_yiwancheng.setVisibility(View.VISIBLE);
                    rb_all.setChecked(false);
                    rb_danjiedan.setChecked(false);
                    rb_jinxingzhong.setChecked(false);
                    rb_yiquxiao.setChecked(false);
                    line_all.setVisibility(View.INVISIBLE);
                    line_danjiedan.setVisibility(View.INVISIBLE);
                    line_jinxingzhong.setVisibility(View.INVISIBLE);
                    line_yiquxiao.setVisibility(View.INVISIBLE);
                    checkList(dingdanList);
                    List<Dingdan> yiwanchengList = new ArrayList<>();
                    for (Dingdan item : dingdanList) {
                        int type = item.getO_TypeID();
                        if (item.getO_ISCancel() == 0)
                            if (type == 4) {
                                yiwanchengList.add(item);
                            }
                    }
                    checkList(yiwanchengList);
                    adapter.setData(yiwanchengList);
                } else {
                    line_yiwancheng.setVisibility(View.INVISIBLE);
                }
//                if (isChecked) {
//                    final ProgressDialog pd = new ProgressDialog(MyDingdanActivity.this);
//                    pd.setMessage("拼命加载中...");
//                    pd.show();
//                    FormBody.Builder params = new FormBody.Builder();
//                    params.add("keys", Contast.KEYS);
//                    params.add("O_UTel", Contast.user.getU_Tel());
//                    OkHttpClient client = new OkHttpClient();
//                    Request request = new Request.Builder()
//                            .url(url)
//                            .post(params.build())
//                            .build();
//                    okhttp3.Call call = client.newCall(request);
//                    call.enqueue(new Callback() {
//
//                        @Override
//                        public void onFailure(Call call, IOException e) {
//                            pd.dismiss();
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(MyDingdanActivity.this, "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                        @Override
//                        public void onResponse(Call call, Response response) throws IOException {
//                            pd.dismiss();
//                            final String string = response.body().string();
//                            if (response.code() != HttpURLConnection.HTTP_OK) {
//                                Toast.makeText(MyDingdanActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
//                            } else {
//                                if (!TextUtils.isEmpty(string)) {
//                                    if (string.contains("ErrorStr")) {
//                                        final Error error = JSON.parseObject(string, Error.class);
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                Toast.makeText(MyDingdanActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                                    } else {
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//
//
//                                                dingdanList = JSON.parseArray(string, Dingdan.class);
//                                                checkList(dingdanList);
//                                                List<Dingdan> yiwanchengList = new ArrayList<>();
//                                                for (Dingdan item : dingdanList) {
//                                                    int type = item.getO_TypeID();
//                                                    if (type == 4) {
//                                                        yiwanchengList.add(item);
//                                                    }
//                                                }
//                                                checkList(yiwanchengList);
//                                                adapter.setData(yiwanchengList);
//                                            }
//                                        });
//
//                                    }
//                                } else {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Toast.makeText(MyDingdanActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
//                            }
//                        }
//
//                    });
//                }

                break;
            case R.id.rb_mydingdan_danjiedan:
                if (isChecked) {
                    line_danjiedan.setVisibility(View.VISIBLE);
                    rb_all.setChecked(false);
                    rb_yiwancheng.setChecked(false);
                    rb_jinxingzhong.setChecked(false);
                    rb_yiquxiao.setChecked(false);
                    line_yiwancheng.setVisibility(View.INVISIBLE);
                    line_all.setVisibility(View.INVISIBLE);
                    line_jinxingzhong.setVisibility(View.INVISIBLE);
                    line_yiquxiao.setVisibility(View.INVISIBLE);
                    List<Dingdan> danjiedanList = new ArrayList<>();
                    for (Dingdan item : dingdanList) {
                        int type = item.getO_TypeID();
                        if (item.getO_ISCancel() == 0)
                            if (type == 1) {
                                danjiedanList.add(item);
                            }
                    }
                    checkList(danjiedanList);
                    adapter.setData(danjiedanList);
                } else {
                    line_danjiedan.setVisibility(View.INVISIBLE);
                }

//                if (isChecked) {
//                final ProgressDialog pd = new ProgressDialog(MyDingdanActivity.this);
//                pd.setMessage("拼命加载中...");
//                pd.show();
//                FormBody.Builder params = new FormBody.Builder();
//                params.add("keys", Contast.KEYS);
//                params.add("O_UTel", Contast.user.getU_Tel());
//                OkHttpClient client = new OkHttpClient();
//                Request request = new Request.Builder()
//                        .url(url)
//                        .post(params.build())
//                        .build();
//                okhttp3.Call call = client.newCall(request);
//                call.enqueue(new Callback() {
//
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        pd.dismiss();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(MyDingdanActivity.this, "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        pd.dismiss();
//                        final String string = response.body().string();
//                        if (response.code() != HttpURLConnection.HTTP_OK) {
//                            Toast.makeText(MyDingdanActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
//                        } else {
//                            if (!TextUtils.isEmpty(string)) {
//                                if (string.contains("ErrorStr")) {
//                                    final Error error = JSON.parseObject(string, Error.class);
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Toast.makeText(MyDingdanActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                } else {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
////                                                line_yiwancheng.setVisibility(View.VISIBLE);
////                                                rb_all.setChecked(false);
////                                                rb_danjiedan.setChecked(false);
////                                                rb_jinxingzhong.setChecked(false);
////                                                line_all.setVisibility(View.INVISIBLE);
////                                                line_danjiedan.setVisibility(View.INVISIBLE);
////                                                line_jinxingzhong.setVisibility(View.INVISIBLE);
////
////                                                dingdanList = JSON.parseArray(string, Dingdan.class);
////                                                checkList(dingdanList);
////                                                List<Dingdan> yiwanchengList = new ArrayList<>();
////                                                for (Dingdan item : dingdanList) {
////                                                    int type = item.getO_TypeID();
////                                                    if (type == 4) {
////                                                        yiwanchengList.add(item);
////                                                    }
////                                                }
////                                                checkList(yiwanchengList);
//                                            line_danjiedan.setVisibility(View.VISIBLE);
//                                            rb_all.setChecked(false);
//                                            rb_yiwancheng.setChecked(false);
//                                            rb_jinxingzhong.setChecked(false);
//                                            line_yiwancheng.setVisibility(View.INVISIBLE);
//                                            line_all.setVisibility(View.INVISIBLE);
//                                            line_jinxingzhong.setVisibility(View.INVISIBLE);
//                                            List<Dingdan> danjiedanList = new ArrayList<>();
//                                            for (Dingdan item : dingdanList) {
//                                                int type = item.getO_TypeID();
//                                                if (type == 1) {
//                                                    danjiedanList.add(item);
//                                                }
//                                            }
//                                            checkList(danjiedanList);
//                                            adapter.setData(danjiedanList);
//                                        }
//
//                                    });
//
//                                }
//                            } else {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Toast.makeText(MyDingdanActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            }
//                        }
//                    }
//                });
//            }
                break;
            case R.id.rb_mydingdan_jinxingzhong:
                if (isChecked) {
                    line_jinxingzhong.setVisibility(View.VISIBLE);
                    rb_all.setChecked(false);
                    rb_yiwancheng.setChecked(false);
                    rb_danjiedan.setChecked(false);
                    rb_yiquxiao.setChecked(false);
                    line_danjiedan.setVisibility(View.INVISIBLE);
                    line_yiwancheng.setVisibility(View.INVISIBLE);
                    line_all.setVisibility(View.INVISIBLE);
                    line_yiquxiao.setVisibility(View.INVISIBLE);
                    List<Dingdan> jinxingzhongList = new ArrayList<>();
                    for (Dingdan item : dingdanList) {
                        int type = item.getO_TypeID();
                        if (item.getO_ISCancel()== 0)
                            if (type == 2 || type == 3 || type == 8 || type == 7) {
                                jinxingzhongList.add(item);
                            }
                    }
                    checkList(jinxingzhongList);
                    adapter.setData(jinxingzhongList);
                } else {
                    line_jinxingzhong.setVisibility(View.INVISIBLE);
                }

//                if (isChecked) {
//                    final ProgressDialog pd = new ProgressDialog(MyDingdanActivity.this);
//                    pd.setMessage("拼命加载中...");
//                    pd.show();
//                    FormBody.Builder params = new FormBody.Builder();
//                    params.add("keys", Contast.KEYS);
//                    params.add("O_UTel", Contast.user.getU_Tel());
//                    OkHttpClient client = new OkHttpClient();
//                    Request request = new Request.Builder()
//                            .url(url)
//                            .post(params.build())
//                            .build();
//                    okhttp3.Call call = client.newCall(request);
//                    call.enqueue(new Callback() {
//
//                        @Override
//                        public void onFailure(Call call, IOException e) {
//                            pd.dismiss();
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(MyDingdanActivity.this, "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onResponse(Call call, Response response) throws IOException {
//                            pd.dismiss();
//                            final String string = response.body().string();
//                            if (response.code() != HttpURLConnection.HTTP_OK) {
//                                Toast.makeText(MyDingdanActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
//                            } else {
//                                if (!TextUtils.isEmpty(string)) {
//                                    if (string.contains("ErrorStr")) {
//                                        final Error error = JSON.parseObject(string, Error.class);
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                Toast.makeText(MyDingdanActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                                    } else {
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
////                                                line_yiwancheng.setVisibility(View.VISIBLE);
////                                                rb_all.setChecked(false);
////                                                rb_danjiedan.setChecked(false);
////                                                rb_jinxingzhong.setChecked(false);
////                                                line_all.setVisibility(View.INVISIBLE);
////                                                line_danjiedan.setVisibility(View.INVISIBLE);
////                                                line_jinxingzhong.setVisibility(View.INVISIBLE);
////
////                                                dingdanList = JSON.parseArray(string, Dingdan.class);
////                                                checkList(dingdanList);
////                                                List<Dingdan> yiwanchengList = new ArrayList<>();
////                                                for (Dingdan item : dingdanList) {
////                                                    int type = item.getO_TypeID();
////                                                    if (type == 4) {
////                                                        yiwanchengList.add(item);
////                                                    }
////                                                }
////                                                checkList(yiwanchengList);
////                                                line_danjiedan.setVisibility(View.VISIBLE);
////                                                rb_all.setChecked(false);
////                                                rb_yiwancheng.setChecked(false);
////                                                rb_jinxingzhong.setChecked(false);
////                                                line_yiwancheng.setVisibility(View.INVISIBLE);
////                                                line_all.setVisibility(View.INVISIBLE);
////                                                line_jinxingzhong.setVisibility(View.INVISIBLE);
////                                                List<Dingdan> danjiedanList = new ArrayList<>();
////                                                for (Dingdan item : dingdanList) {
////                                                    int type = item.getO_TypeID();
////                                                    if (type == 1) {
////                                                        danjiedanList.add(item);
////                                                    }
////                                                }
////                                                checkList(danjiedanList);
//                                                line_jinxingzhong.setVisibility(View.VISIBLE);
//                                                rb_all.setChecked(false);
//                                                rb_yiwancheng.setChecked(false);
//                                                rb_danjiedan.setChecked(false);
//                                                line_danjiedan.setVisibility(View.INVISIBLE);
//                                                line_yiwancheng.setVisibility(View.INVISIBLE);
//                                                line_all.setVisibility(View.INVISIBLE);
//                                                List<Dingdan> jinxingzhongList = new ArrayList<>();
//                                                for (Dingdan item : dingdanList) {
//                                                    int type = item.getO_TypeID();
//                                                    if (type == 2 || type == 3 || type==8 || type == 7) {
//                                                        jinxingzhongList.add(item);
//                                                    }
//                                                }
//                                                checkList(jinxingzhongList);
//                                                adapter.setData(jinxingzhongList);
//                                            }
//
//                                        });
//
//                                    }
//                                } else {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Toast.makeText(MyDingdanActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
//                            }
//                        }
//
//                    });
//                }

                break;
        }
    }
}
