package com.example.administrator.ydwlxcpt.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.administrator.ydwlxcpt.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/11.
 */
//消息列表
public class MassageListActivity extends BaseActivity {
    @BindView(R.id.iv_msglist_back)
    ImageView ivMsglistBack;
    @BindView(R.id.tv_msg_title)
    TextView tvMsgTitle;
    @BindView(R.id.btn_msglist_more)
    Button btnMsglistMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_list);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_msglist_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_msglist_back:
                finish();
                break;

        }
    }
}
