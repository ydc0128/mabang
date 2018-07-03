package com.example.administrator.ydwlxcpt.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Adapter.PingpaiAdapter;
import com.example.administrator.ydwlxcpt.Adapter.XinghaoAdapter;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.Pingpai;
import com.example.administrator.ydwlxcpt.Bean.Xinghao;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;

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
//限号
public class XinghaoActivity extends AppCompatActivity {

    private static final String TAG = "PingpaiActivity";
    private String url = Contast.Domain+"api/CarGetXingHao.ashx?";

    private ImageView iv_back;
    private List<Xinghao> xinghaoList;
    private ListView listView;
    private XinghaoAdapter adapter;

    private Pingpai pingpai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinghao);
        Intent intent = getIntent();
        pingpai = (Pingpai) intent.getSerializableExtra("pingpai");
        initViews();
        initListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refelash();
    }

    private void refelash() {
        final ProgressDialog pd = new ProgressDialog(XinghaoActivity.this);
        pd.setMessage("拼命加载中...");
        pd.show();
        FormBody.Builder params = new FormBody.Builder();
        params.add("keys", Contast.KEYS);
        params.add("CBT_BrandName", pingpai.getCB_BrandName());
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
                        pd.dismiss();
                        Toast.makeText(XinghaoActivity.this, "网络连接异常，请稍后再试", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                pd.dismiss();
                final String string = response.body().string();
                if(response.code() != HttpURLConnection.HTTP_OK){
                    Toast.makeText(XinghaoActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
               return;
                }else {
                    Log.i(TAG, "onResponse: " + string);
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(XinghaoActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    xinghaoList = JSON.parseArray(string, Xinghao.class);
                                    adapter.setData(xinghaoList);
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(XinghaoActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    }
                }
            }
        });
    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.lv_xinghao_listview);
        xinghaoList = new ArrayList<>();
        adapter = new XinghaoAdapter(this,xinghaoList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Xinghao xinghao = (Xinghao) adapter.getItem(position);
                Contast.Pingpai = pingpai.getCB_BrandName();
                Contast.Xinghao = xinghao.getCBT_BrandTypeName();
                finish();
                finish();
            }
        });
    }

    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_xinghao_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
