package com.example.administrator.ydwlxcpt.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.TitleInfo;
import com.example.administrator.ydwlxcpt.Bean.User;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//欢迎
public class WelcomeActivity extends BaseActivity {

    private String url = Contast.Domain + "api/UserLoginDefault.ashx?";
    private String url_titleInfo = Contast.Domain + "api/GetTitleInfoHandler.ashx?";
    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initTitleInfo();
        initPermission();
    }

    private void initTitleInfo() {
//        final ProgressDialog pd = new ProgressDialog(WelcomeActivity.this);
//        pd.setMessage("拼命加载中...");
//        pd.show();
        FormBody.Builder params = new FormBody.Builder();
        params.add("keys", Contast.KEYS);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_titleInfo)
                .post(params.build())
                .build();

        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
//                pd.dismiss();
                //响应失败
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WelcomeActivity.this, "基本信息获取失败...", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
//                pd.dismiss();
                //响应成功  response.body().string() 获取字符串数据，当然还可以获取其他
                String string = response.body().string();
                Log.i(TAG, "onResponse: json=" + string);
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WelcomeActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(WelcomeActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if (string.contains("无法找到资源") && string.contains("404")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(WelcomeActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Contast.titleInfo = JSON.parseObject(string, TitleInfo.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i(TAG, "run: Contast.titleInfo=" + Contast.titleInfo.toString());
//                                 Toast.makeText(WelcomeActivity.this, "平台信息获取成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(WelcomeActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    private void initLogin() {
        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        boolean isFirst = sp.getBoolean("isFirst", true);
        if (isFirst) {
            Intent intent = new Intent(WelcomeActivity.this, Yindaoye.class);
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean("isFirst", false);
            edit.commit();
            startActivity(intent);
            finish();
        } else {
            boolean isLogin = sp.getBoolean("isLogin", false);
            String U_Tel = sp.getString("U_Tel", "");
            String U_IMEI = sp.getString("U_IMEI", "");
            if (isLogin) {
                final ProgressDialog pd = new ProgressDialog(WelcomeActivity.this);
                pd.setMessage("拼命加载中...");
                pd.show();
                FormBody.Builder params = new FormBody.Builder();
                params.add("U_Tel", U_Tel);
                params.add("U_IMEI", U_IMEI);
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
                                Toast.makeText(WelcomeActivity.this, "网络连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, Response response) throws IOException {
                        pd.dismiss();
                        //响应成功  response.body().string() 获取字符串数据，当然还可以获取其他
                        String string = response.body().string();
                        if (response.code() != HttpURLConnection.HTTP_OK) {
                            Toast.makeText(WelcomeActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Log.i(TAG, "onResponse: json=" + string);
                            if (!TextUtils.isEmpty(string)) {
                                if (string.contains("ErrorStr")) {
                                    final Error error = JSON.parseObject(string, Error.class);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(WelcomeActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    });
                                } else {
                                    User user = JSON.parseObject(string, User.class);
                                    Contast.user = user;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
//                                            Toast.makeText(WelcomeActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(WelcomeActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                });
                            }
                        }
                    }
                });
            }
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }
    }


    private void initPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(WelcomeActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, 1);
            } else {
                initLogin();
            }
        } else {
            initLogin();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED) {
                    initLogin();
                } else {
                    Toast.makeText(WelcomeActivity.this, "由于您没有赋予权限，可能导致部分功能无法使用", Toast.LENGTH_SHORT).show();
                    initLogin();
                }
        }
    }
}
