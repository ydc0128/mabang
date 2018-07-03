package com.example.administrator.ydwlxcpt.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ydwlxcpt.Bean.Dingdan;
import com.example.administrator.ydwlxcpt.Bean.Massage;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChongZhiTongZhi extends AppCompatActivity {


    @BindView(R.id.iv_cztz_back)
    ImageView ivCztzBack;
    @BindView(R.id.tv_cztz_title)
    TextView tvCztzTitle;
    @BindView(R.id.btn_cztz_more)
    Button btnCztzMore;
    @BindView(R.id.tv_kaishi_riqi)
    TextView tvKaishiRiqi;
    @BindView(R.id.tv_jieshu_riqi)
    TextView tvJieshuRiqi;
    @BindView(R.id.tv_chongzhi_jine)
    TextView tvChongzhiJine;
    @BindView(R.id.tv_youhui)
    TextView tvYouhui;
    @BindView(R.id.tv_chongzhi_tiaozhuan)
    TextView tvChongzhiTiaozhuan;
    @BindView(R.id.rl_chongzhi_shuoming)
    LinearLayout rlChongzhiShuoming;
    private Massage massage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chong_zhi_tong_zhi);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        massage = (Massage) intent.getSerializableExtra("chongzhi");
        tvChongzhiJine.setText(massage.getM_Title());
    }

    @OnClick({R.id.iv_cztz_back, R.id.tv_chongzhi_tiaozhuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_cztz_back:
                finish();
                break;
            case R.id.tv_chongzhi_tiaozhuan:
//                Intent intent=new Intent(ChongZhiTongZhi.this,HuoDongChongzhiActivity.class);
//                startActivity(intent);
                break;
        }
    }
}
