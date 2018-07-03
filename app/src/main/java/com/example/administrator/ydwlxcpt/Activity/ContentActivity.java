package com.example.administrator.ydwlxcpt.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ydwlxcpt.R;

public class ContentActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_title;
    private TextView tv_biaoti;
    private TextView tv_neirong;
    private String from;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        initViews();
    }

    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_content_back);
        tv_title = (TextView) findViewById(R.id.tv_content_title);
        tv_biaoti = (TextView) findViewById(R.id.tv_content_biaoti);
        tv_neirong = (TextView) findViewById(R.id.tv_content_neirong);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(from.equals("gongsijieshao")){
            tv_title.setText("公司介绍");
            tv_biaoti.setText(R.string.tv_content_gongsijieshao);
            tv_neirong.setText(R.string.tv_content_gongsijieshao_neirong);
        }

        if(from.equals("fuwutiaokuan")){
            tv_title.setText("服务条款");
            tv_biaoti.setText(R.string.tv_content_fuwutiaokuan);
            tv_neirong.setText(R.string.tv_content_fuwutiaokuan_content);
        }

        if(from.equals("chongzhixieyi")){
            tv_title.setText("充值协议");
            tv_biaoti.setText(R.string.tv_content_chongzhixieyi);
            tv_neirong.setText(R.string.tv_content_chongzhixieyi_content);
        }
    }
}
