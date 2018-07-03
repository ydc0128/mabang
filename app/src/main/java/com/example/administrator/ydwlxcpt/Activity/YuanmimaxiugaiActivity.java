package com.example.administrator.ydwlxcpt.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
//原密码
public class YuanmimaxiugaiActivity extends AppCompatActivity {

    private static final String TAG = "YuanmimaxiugaiActivity";
    private String url = Contast.Domain+"api/UserUpdateByPassword.ashx?";

    private ImageView iv_back,iv_yuan,iv_xin,iv_chongfu;
    private EditText et_pwd;
    private EditText et_Newpwd,et_chongfu;
    private Button btn_queding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuanmimaxiugai);
        initViews();
    }

    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_yuanmimaxiugai_back);
        et_pwd = (EditText) findViewById(R.id.et_yuanmimaxiugai_pwd);
        et_Newpwd = (EditText) findViewById(R.id.et_yuanmimaxiugai_newpwd);
        btn_queding = (Button) findViewById(R.id.btn_yuanmimaxiugai_next);
        et_chongfu=(EditText)findViewById(R.id.et_yuanmimaxiugai_chongfu) ;
        iv_yuan=(ImageView)findViewById(R.id.iv_yuan_mima);
        iv_xin=(ImageView)findViewById(R.id.iv_xin_chakan);
        iv_chongfu=(ImageView)findViewById(R.id.iv_xin_chongfu);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_xin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iv_xin == null || et_Newpwd == null) return;

                if (iv_xin.isSelected()) {
                    iv_xin.setSelected(false);
                    et_Newpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_Newpwd.setSelection(et_Newpwd.getText().length());
                } else {
                    iv_xin.setSelected(true);
                    et_Newpwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_Newpwd.setSelection(et_Newpwd.getText().length());
//                        iv_xin_chakan.setBackgroundResource(R.drawable.chakanmima);
                }
            }
        });
        iv_chongfu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iv_chongfu == null || et_chongfu == null) return;

                if (iv_chongfu.isSelected()) {
                    iv_chongfu.setSelected(false);
                    et_chongfu.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_chongfu.setSelection(et_chongfu.getText().length());
                } else {
                    iv_chongfu.setSelected(true);
                    et_chongfu.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_chongfu.setSelection(et_chongfu.getText().length());
//                    iv_chongfu_chakan.setBackgroundResource(R.drawable.chakanmima);
                }
            }
        });
        iv_yuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iv_yuan==null||et_pwd==null)return;
                if (iv_yuan.isSelected()){
                    iv_yuan.setSelected(false);
                    et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_pwd.setSelection(et_pwd.getText().length());
                }else {
                    iv_yuan.setSelected(true);
                    et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_pwd.setSelection(et_pwd.getText().length());
//                    iv_yuan_chakan.setBackgroundResource(R.drawable.chakanmima);
                }
            }
        });
        btn_queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = et_pwd.getText().toString();
                String newPwd = et_Newpwd.getText().toString();
                String Chongfu=et_chongfu.getText().toString();
                if(TextUtils.isEmpty(pwd)){
                    Toast.makeText(YuanmimaxiugaiActivity.this,"原密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pwd.length()<6||pwd.length()>16){
                    Toast.makeText(YuanmimaxiugaiActivity.this,"原密码长度不正确，请重新输入",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(newPwd)){
                    Toast.makeText(YuanmimaxiugaiActivity.this,"新密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(newPwd.length()<6||newPwd.length()>16){
                    Toast.makeText(YuanmimaxiugaiActivity.this,"新密码长度不正确，请重新输入",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newPwd.equals(pwd)){
                    Toast.makeText(YuanmimaxiugaiActivity.this,"原密码和新密码不能重复",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Chongfu)){
                    Toast.makeText(YuanmimaxiugaiActivity.this,"确认密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!newPwd.equals(Chongfu)){
                    Toast.makeText(YuanmimaxiugaiActivity.this,"密码输入不一致",Toast.LENGTH_SHORT).show();
                    return;
                }
                final ProgressDialog pd = new ProgressDialog(YuanmimaxiugaiActivity.this);
                pd.setMessage("拼命加载中...");
                pd.show();
                FormBody.Builder params = new FormBody.Builder();
                params.add("U_Tel", Contast.user.getU_Tel());
                params.add("U_PwdNew", newPwd);
                params.add("U_Pwd", pwd);
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
                                pd.dismiss();
                                Toast.makeText(YuanmimaxiugaiActivity.this, "网络连接异常，请稍后再试", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        pd.dismiss();
                        String string = response.body().string();
                        if(response.code() != HttpURLConnection.HTTP_OK){
                            Toast.makeText(YuanmimaxiugaiActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            Log.i(TAG, "onResponse: " + string);
                            if (!TextUtils.isEmpty(string)) {
                                if (string.contains("ErrorStr")) {
                                    final Error error = JSON.parseObject(string, Error.class);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(YuanmimaxiugaiActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    });
                                } else if (string.contains("OkStr")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(YuanmimaxiugaiActivity.this, "密码修改完成", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    finish();
                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(YuanmimaxiugaiActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
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
