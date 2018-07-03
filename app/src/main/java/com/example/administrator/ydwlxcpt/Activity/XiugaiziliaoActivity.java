package com.example.administrator.ydwlxcpt.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.ydwlxcpt.R;
//修改资料
public class XiugaiziliaoActivity extends BaseActivity {

    private ImageView iv_back;
    private EditText et_name;
    private Button btn_baocun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiugaiziliao);
        initViews();
    }

    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_xiugaiziliao_list_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_name = (EditText) findViewById(R.id.et_xiugaiziliao_content);
        btn_baocun = (Button) findViewById(R.id.btn_xiugaiziliao_baocun);
        btn_baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(XiugaiziliaoActivity.this,"姓名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("name",name);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
