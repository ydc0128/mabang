package com.example.administrator.ydwlxcpt.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.administrator.ydwlxcpt.Bean.RegisterPhone;
import com.example.administrator.ydwlxcpt.Bean.User;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.example.administrator.ydwlxcpt.Utils.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;

import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CodeActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "CodeActivity";

    private String url_register = Contast.Domain+"api/UserRegister.ashx?";
    private String url_login = Contast.Domain+"api/UserLoginPhone.ashx?";

    private ImageView iv_back;
    private ImageView iv_title;
    private EditText et_code;
    private Button btn_next;
    private int from;
    private RegisterPhone registerPhone;
    private String code;
    private String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        initViews();
        Intent intent = getIntent();
        from = intent.getIntExtra("from", -1);
        phone = intent.getStringExtra("phone");
        registerPhone = (RegisterPhone) intent.getSerializableExtra("registerPhone");
        if (from == 0) {
            btn_next.setText("完成");
        } else if (from == 1) {
            btn_next.setText("下一步");
        }

        Log.i(TAG, "onClick: phone="+phone);
        Log.i(TAG, "onClick: from="+from);
        Log.i(TAG, "onClick: U_IdentityID="+code);
    }

    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_code_back);
        iv_title = (ImageView) findViewById(R.id.iv_code_title);
        et_code = (EditText) findViewById(R.id.et_code_code);
        btn_next = (Button) findViewById(R.id.btn_code_submit);
        iv_back.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_code_back:
                finish();
                break;
            case R.id.btn_code_submit:
                String code = et_code.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(CodeActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (code.length() != 6) {
                    Toast.makeText(CodeActivity.this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (from == 1) {
                    SMSSDK.getInstance().checkSmsCodeAsyn(phone, code, new SmscheckListener() {
                        @Override
                        public void checkCodeSuccess(final String code) {
                            // 验证码验证成功，code 为验证码信息。
                            Intent intent = new Intent(CodeActivity.this, AddPwdActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void checkCodeFail(int errCode, final String errMsg) {
                            // 验证码验证失败, errCode 为错误码，详情请见文档后面的错误码表；errMsg 为错误描述。
                        }
                    });
//                    //TODO
//                    //发送网络请求，对验证码进行确认
//                    final ProgressDialog pd = new ProgressDialog(CodeActivity.this);
//                    pd.setMessage("拼命加载中...");
//                    pd.show();
//                    FormBody.Builder params = new FormBody.Builder();
//                    params.add("U_LSID", "" + registerPhone.getU_LSID());
//                    params.add("U_Tel", registerPhone.getU_Tel());
//                    params.add("U_IdentityID", code);
//                    params.add("keys", Contast.KEYS);
//                    OkHttpClient client = new OkHttpClient();
//                    Request request = new Request.Builder()
//                            .url(url_register)
//                            .post(params.build())
//                            .build();
//                    okhttp3.Call call = client.newCall(request);
//                    call.enqueue(new Callback() {
//                        @Override
//                        public void onFailure(Call call, IOException e) {
//                            pd.dismiss();
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(CodeActivity.this, "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onResponse(Call call, Response response) throws IOException {
//                            pd.dismiss();
//                            String string = response.body().string();
//                            if (!TextUtils.isEmpty(string)) {
//                                if (string.contains("ErrorStr")) {
//                                    final Error error = JSON.parseObject(string, Error.class);
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Toast.makeText(CodeActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                } else {
//                                    Contast.user = JSON.parseObject(string, User.class);
//                                    //如果验证成功
//                                    SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
//                                    SharedPreferences.Editor edit = sp.edit();
//                                    edit.putBoolean("isLogin", true);
//                                    edit.putString("U_Tel",Contast.user.getU_Tel());
//                                    edit.putString("U_IMEI",Contast.user.getU_IMEI());
//                                    edit.commit();
//                                    Intent intent = new Intent(CodeActivity.this, AddPwdActivity.class);
//                                    intent.putExtra("registerPhone", registerPhone);
//                                    startActivity(intent);
//                                }
//                            }else{
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Toast.makeText(CodeActivity.this,"服务器繁忙，请稍后重试...",Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            }
//                        }
//                    });
                } else if (from == 0) {
                    //TODO
                    //发送网络请求，对验证码进行确认
                    FormBody.Builder params = new FormBody.Builder();
                    params.add("keys", Contast.KEYS);
                    params.add("U_Tel", phone);
                    params.add("U_IdentityID", code);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url_login)
                            .post(params.build())
                            .build();
                    okhttp3.Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CodeActivity.this, "网络连接异常，请稍后再试", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String string = response.body().string();
                            if (response.code() != HttpURLConnection.HTTP_OK) {
                                Toast.makeText(CodeActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                            } else {
                                if (!TextUtils.isEmpty(string)) {
                                    if (string.contains("ErrorStr")) {
                                        final Error error = JSON.parseObject(string, Error.class);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(CodeActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                       return;
                                            }
                                        });
                                    } else {
                                        Contast.user = JSON.parseObject(string, User.class);
                                        //如果验证成功
                                        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                                        SharedPreferences.Editor edit = sp.edit();
                                        edit.putBoolean("isLogin", true);
                                        edit.putString("U_Tel", Contast.user.getU_Tel());
                                        edit.putString("U_IMEI", Contast.user.getU_IMEI());
                                        edit.commit();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(CodeActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        Intent intent = new Intent(CodeActivity.this, MainActivity.class);
                                        intent.putExtra("registerPhone", registerPhone);
                                        startActivity(intent);
                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(CodeActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
                break;
        }
    }
}
