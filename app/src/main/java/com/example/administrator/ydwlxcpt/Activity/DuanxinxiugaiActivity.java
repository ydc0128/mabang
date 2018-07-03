package com.example.administrator.ydwlxcpt.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscodeListener;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//短信修改
public class DuanxinxiugaiActivity extends BaseActivity {

    private static final String TAG = "DuanxinxiugaiActivity";

    private ImageView iv_back;
    private TextView tv_phone;
    private EditText et_code;
    private Button btn_code;
    private Button btn_next;
    private int time = 60;
    private boolean flag = true;
    private Timer timer;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (time <= 0) {
                        time = 60;
                        btn_code.setText("重获验证码");
                        btn_code.setClickable(true);
                        flag = false;
                    } else {
                        btn_code.setText(time + "s");
                        btn_code.setClickable(false);
                        time--;
                    }
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duanxinxiugai);
        initViews();
    }

    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_duanxinxiugai_back);
        tv_phone = (TextView) findViewById(R.id.tv_duanxinxiugai_phone);
        et_code = (EditText) findViewById(R.id.et_duanxinxiugai_code);
        btn_code = (Button) findViewById(R.id.btn_duanxinxiugai_code);
        btn_next = (Button) findViewById(R.id.btn_duanxinxiugai_next);
        btn_next.setClickable(false);

        String phone = Contast.user.getU_Tel();
        String qian = phone.substring(0,3);
        String hou = phone.substring(7,phone.length());
        tv_phone.setText(qian+"****"+hou);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCode();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DuanxinxiugaiActivity.this,ShezhimimaActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initCode() {
        SMSSDK.getInstance().getSmsCodeAsyn(Contast.user.getU_Tel(), "1", new SmscodeListener() {
            @Override
            public void getCodeSuccess(final String uuid) {
                // 获取验证码成功，uuid 为此次获取的唯一标识码。
                Toast.makeText(DuanxinxiugaiActivity.this,"验证码发送成功",Toast.LENGTH_SHORT).show();
                btn_next.setClickable(true);
            }
            @Override
            public void getCodeFail(int errCode, final String errMsg) {
                // 获取验证码失败 errCode 为错误码，详情请见文档后面的错误码表；errMsg 为错误描述。
                Toast.makeText(DuanxinxiugaiActivity.this,errMsg,Toast.LENGTH_SHORT).show();
            }
        });
        timer = new Timer();
        flag = true;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(flag){
                    Message msg = Message.obtain();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            }
        },0,1000);
    }
}
