package com.example.administrator.ydwlxcpt.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Adapter.ChongzhiMingxiAdapter;
import com.example.administrator.ydwlxcpt.Bean.ChongzhiMingxi;
import com.example.administrator.ydwlxcpt.Bean.Dingdan;
import com.example.administrator.ydwlxcpt.Bean.Error;
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

public class ChongzhimingxiActivity extends AppCompatActivity {

    private static final String TAG = "ChongzhimingxiActivity";

    private String url = Contast.Domain + "api/PayToList.ashx?";

    private ImageView iv_back;
    private ListView listView;
    private List<ChongzhiMingxi> chongzhiMingxiList;
    private ChongzhiMingxiAdapter adapter;
    private RelativeLayout rlMyyhjNull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chongzhimingxi);
        initViews();
        initListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refelash();
    }

    private void refelash() {
        final ProgressDialog pd = new ProgressDialog(ChongzhimingxiActivity.this);
        pd.setMessage("拼命加载中...");
        pd.show();
        FormBody.Builder params = new FormBody.Builder();
        params.add("P_PayUTel", Contast.user.getU_Tel());
        params.add("keys", Contast.KEYS);
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
                        Toast.makeText(ChongzhimingxiActivity.this, "网络连接异常，请稍后再试", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                pd.dismiss();
                final String string = response.body().string();
                if(response.code() != HttpURLConnection.HTTP_OK){
                    Toast.makeText(ChongzhimingxiActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Log.i(TAG, "onResponse: " + string);
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChongzhimingxiActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    chongzhiMingxiList = JSON.parseArray(string, ChongzhiMingxi.class);
                                    adapter.setData(chongzhiMingxiList);
                                    if (chongzhiMingxiList.size() > 0) {
                                        rlMyyhjNull.setVisibility(View.GONE);
                                    } else {
                                        rlMyyhjNull.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChongzhimingxiActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    }
                }
            }
        });
    }

    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_chongzhimingxi_back);
        rlMyyhjNull=(RelativeLayout)findViewById(R.id.rl_czmx_null) ;
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.lv_chongzhimingxi_listview);
        chongzhiMingxiList = new ArrayList<>();
        if (chongzhiMingxiList != null) {
            adapter = new ChongzhiMingxiAdapter(this, chongzhiMingxiList);
            listView.setAdapter(adapter);
        }else {
            listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.meiyoushuju));
        }
    }
}
