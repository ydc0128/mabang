package com.example.administrator.ydwlxcpt.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//设置密码
public class ShezhimimaActivity extends AppCompatActivity {

    private String url = Contast.Domain+"api/UserUpdatePasswordByIdentifyCode.ashx?";
    private static final String TAG = "ShezhimimaActivity";

    private ImageView iv_back;
    private EditText et_pwd;
    private Button btn_queding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shezhimima);
        initViews();
    }

    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_shezhimima_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_pwd = (EditText) findViewById(R.id.et_shezhimima_pwd);
        btn_queding = (Button) findViewById(R.id.btn_shezhimima_next);

        btn_queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = et_pwd.getText().toString();
                if(TextUtils.isEmpty(pwd)){
                    Toast.makeText(ShezhimimaActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pwd.length()<6||pwd.length()>16){
                    Toast.makeText(ShezhimimaActivity.this,"密码长度不正确，请重新输入",Toast.LENGTH_SHORT).show();
                    return;
                }
                final ProgressDialog pd = new ProgressDialog(ShezhimimaActivity.this);
                pd.setMessage("拼命加载中...");
                pd.show();
                FormBody.Builder params = new FormBody.Builder();
                params.add("U_Tel", Contast.user.getU_Tel());
                params.add("U_PwdNew", pwd);
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
                                Toast.makeText(ShezhimimaActivity.this, "网络连接异常，请稍后再试", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        pd.dismiss();
                        String string = response.body().string();
                        if(response.code() != HttpURLConnection.HTTP_OK){
                            Toast.makeText(ShezhimimaActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                            return;

                        }else {
                            Log.i(TAG, "onResponse: " + string);
                            if (!TextUtils.isEmpty(string)) {
                                if (string.contains("ErrorStr")) {
                                    final Error error = JSON.parseObject(string, Error.class);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ShezhimimaActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                            return;

                                        }
                                    });
                                } else if (string.contains("OkStr")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ShezhimimaActivity.this, "密码设置完成", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                    finish();
                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ShezhimimaActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                        return;

                                    }
                                });
                            }
                        }
                    }
                });
            }
        });
    }
}
