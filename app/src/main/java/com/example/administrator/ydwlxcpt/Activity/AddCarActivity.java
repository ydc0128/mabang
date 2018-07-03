package com.example.administrator.ydwlxcpt.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.administrator.ydwlxcpt.Adapter.MyFragmentPagerAdapter;
import com.example.administrator.ydwlxcpt.Bean.Car;
import com.example.administrator.ydwlxcpt.Fragment.BaoxianxinxiFragment;
import com.example.administrator.ydwlxcpt.Fragment.JibenxinxiFragment;
import com.example.administrator.ydwlxcpt.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.ydwlxcpt.R.id.line_addcar_baoxianxinxi;
import static com.example.administrator.ydwlxcpt.R.id.line_addcar_jibenxinxi;

public class AddCarActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private ImageView iv_back;
    private RadioButton rb_jibenxinxi;
    private RadioButton rb_baoxianxinxi;
    private TextView tv_title;

    private View line_jibenxinxi;
    private View line_baoxianxinxi;
    private Button btn_bianji;
    public static Car car;
    public static int from ;
    private Handler mHandler;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        Intent intent = getIntent();
        from = intent.getIntExtra("from",-1);
        car = (Car) intent.getSerializableExtra("car");
        initViews();
        initViewPage();
        setViews();
    }
    public void setHandler(Handler handler) {
        mHandler = handler;
    }

    private void setViews() {
        if(from == 0){
            tv_title.setText("车辆信息");
            btn_bianji.setVisibility(View.VISIBLE);
        }else if(from == 1){
            tv_title.setText("添加车辆");
            btn_bianji.setVisibility(View.INVISIBLE);
        }
    }

    private void initViewPage() {   
        viewPager = (ViewPager) findViewById(R.id.vp_addcar_viewpager);
        List<Fragment> fragmentList = new ArrayList<>();
        JibenxinxiFragment jibenxinxiFragment = new JibenxinxiFragment();
        BaoxianxinxiFragment baoxianxinxiFragment = new BaoxianxinxiFragment();
        fragmentList.add(jibenxinxiFragment);
        fragmentList.add(baoxianxinxiFragment);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    rb_jibenxinxi.setChecked(true);
                    rb_baoxianxinxi.setChecked(false);
                }else if(position==1){
                    rb_jibenxinxi.setChecked(false);
                    rb_baoxianxinxi.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_addcar_back);
        rb_jibenxinxi = (RadioButton) findViewById(R.id.rb_addcar_jibenxinxi);
        rb_baoxianxinxi = (RadioButton) findViewById(R.id.rb_addcar_baoxianxinxi);
        line_jibenxinxi = findViewById(line_addcar_jibenxinxi);
        btn_bianji = (Button) findViewById(R.id.btn_addcar_more);
        line_baoxianxinxi = findViewById(line_addcar_baoxianxinxi);
        tv_title = (TextView) findViewById(R.id.tv_addcar_title);


        iv_back.setOnClickListener(this);
        btn_bianji.setOnClickListener(this);
        rb_jibenxinxi.setOnCheckedChangeListener(this);
        rb_baoxianxinxi.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_addcar_back:
                finish();
                break;
            case R.id.btn_addcar_more:
                Message msg1 = new Message();
                Message msg2 = new Message();
                msg1.what = 1;
                msg2.what = 2;
                mHandler.sendMessage(msg1);
                mHandler.sendMessage(msg2);
                btn_bianji.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.rb_addcar_jibenxinxi:
                if(isChecked){
                    line_jibenxinxi.setVisibility(View.VISIBLE);
                    rb_baoxianxinxi.setChecked(false);
                    line_baoxianxinxi.setVisibility(View.INVISIBLE);
                    viewPager.setCurrentItem(0);
                }else{
                    line_jibenxinxi.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.rb_addcar_baoxianxinxi:
                if(isChecked){
                    line_baoxianxinxi.setVisibility(View.VISIBLE);
                    rb_jibenxinxi.setChecked(false);
                    line_jibenxinxi.setVisibility(View.INVISIBLE);
                    viewPager.setCurrentItem(1);
                }else{
                    line_baoxianxinxi.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }
}
