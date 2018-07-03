package com.example.administrator.ydwlxcpt.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.ydwlxcpt.Adapter.WeizhangAdapter;
import com.example.administrator.ydwlxcpt.Bean.Car;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.User;
import com.example.administrator.ydwlxcpt.Bean.Weizhang;
import com.example.administrator.ydwlxcpt.Bean.WeizhangMsg;
import com.example.administrator.ydwlxcpt.Bean.WeizhangResult;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//违章
public class WeizhangActivity extends BaseActivity {

    private static final String TAG = "WeizhangActivity";
    private String url = "http://api.jisuapi.com/illegal/query?";
    private static final String APPKEY = "358027940be2843e";

    private ImageView iv_back;
    private TextView tv_title;
    private TextView tv_cishu;
    private TextView tv_fenshu;
    private TextView tv_fakuan;
    private TextView tv_content;


    private RelativeLayout rl_null;
    private ListView listView;

    private List<Weizhang> weizhangList;
    private WeizhangAdapter adapter;
    private Car car;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weizhang);
        Intent intent = getIntent();
        car = (Car) intent.getSerializableExtra("car");
        initViews();
        initListView();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.lv_weizhang);
        weizhangList = new ArrayList<>();
        adapter = new WeizhangAdapter(WeizhangActivity.this, weizhangList);
        listView.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        refalash();
    }
    private void refalash() {
        String chepaihao = car.getC_PlateNumber();
        String zi = chepaihao.substring(0, 1);
        String wei = chepaihao.substring(1, chepaihao.length());
        String chejiahao = car.getC_VIN();
        String fadongjihao = car.getC_MoterNumber();
        Log.i(TAG, "refalash: zi="+zi);
        Log.i(TAG, "refalash: wei="+wei);
        tv_title.setText(car.getC_PlateNumber());
        final ProgressDialog pd = new ProgressDialog(WeizhangActivity.this);
        pd.setMessage("拼命加载中...");
        pd.show();
        FormBody.Builder params = new FormBody.Builder();
        params.add("lsprefix", zi);
        params.add("lsnum", wei);
        params.add("appkey", APPKEY);
        params.add("frameno", chejiahao);
        params.add("engineno", fadongjihao);
        params.add("lstype", "02");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(params.build())
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                pd.dismiss();
                //响应失败
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeizhangActivity.this, "网络连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                        return;

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                pd.dismiss();
                //响应成功  response.body().string() 获取字符串数据，当然还可以获取其他
                final String string = response.body().string();
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(WeizhangActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    if (TextUtils.isEmpty(string)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(WeizhangActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        });
                    } else {
                        Log.i(TAG, "onResponse: json=" + string);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject jsonObject = JSONObject.parseObject(string);
                                if (jsonObject.getInteger("status") != 0) {
                                    tv_content.setText(jsonObject.getString("msg"));
                                } else {
                                    String msg = jsonObject.getString("msg");
                                    if("恭喜您，没有违章！".equals(msg)){
                                        rl_null.setVisibility(View.VISIBLE);
                                    }else {
                                        JSONObject resultarr = jsonObject.getJSONObject("result");
                                        if (resultarr != null) {
                                            tv_cishu.setText(resultarr.getInteger("count") + "次");
                                            tv_fenshu.setText(resultarr.getInteger("totalscore") + "分");
                                            tv_fakuan.setText(resultarr.getInteger("totalprice") + "元");
                                            if (resultarr.getJSONArray("list") != null) {
                                                JSONArray list = resultarr.getJSONArray("list");
                                                List<Weizhang> weizhangs = JSONArray.parseArray(list.toString(), Weizhang.class);
                                                adapter.setData(weizhangs);
                                                rl_null.setVisibility(View.INVISIBLE);
//                                                /**
//                                                 * 通知
//                                                 */
//                                                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                                                Notification.Builder builder = new Notification.Builder(WeizhangActivity.this);
//                                                builder.setSmallIcon(R.drawable.appicon);        //设置图标
//                                                builder.setContentText("您有"+weizhangs.size()+"条违章");                 //消息内容
//                                                builder.setWhen(System.currentTimeMillis());         //发送时间
//                                                builder.setDefaults(Notification.DEFAULT_ALL);      //设置默认的提示音，振动方式，灯光
//                                                builder.setAutoCancel(true);                         //打开程序后图标消失
//                                                //跳转活动
//                                                Intent intent =new Intent (WeizhangActivity.this,NotificationIntent.class);
//                                                PendingIntent pi = PendingIntent.getActivities(WeizhangActivity.this, 0, new Intent[]{intent}, PendingIntent.FLAG_CANCEL_CURRENT);
//                                                builder.setContentIntent(pi);
//                                                //创建通知栏对象，显示通知信息
//                                                Notification notification = builder.build();
//                                                manager.notify(1, notification);
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }
    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_weizhang_back);
        tv_title = (TextView) findViewById(R.id.tv_weizhang_title);
        tv_cishu = (TextView) findViewById(R.id.tv_weizhang_cishu);
        tv_fenshu = (TextView) findViewById(R.id.tv_weizhang_fenshu);
        tv_fakuan = (TextView) findViewById(R.id.tv_weizhang_fakuan);
        rl_null = (RelativeLayout) findViewById(R.id.rl_weizhang_null);
//        tv_content = (TextView) findViewById(R.id.tv_weizhang_content);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Log.i(TAG, "onClick: 111");
            }
        });
    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Weizhang Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        AppIndex.AppIndexApi.start(client2, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client2, getIndexApiAction());
        client2.disconnect();
    }
}
