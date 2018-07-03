package com.example.administrator.ydwlxcpt.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.ydwlxcpt.R;

/**
 * Created by Administrator on 2018/5/10.
 */

public class Fuwujieshao extends BaseActivity {
    private ImageView back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuwu_jieshao);
        back=(ImageView)findViewById(R.id.iv_fuwujieshao_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
