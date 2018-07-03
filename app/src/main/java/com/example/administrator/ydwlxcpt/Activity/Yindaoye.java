package com.example.administrator.ydwlxcpt.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.ydwlxcpt.Adapter.MyFragmentPagerAdapter;
import com.example.administrator.ydwlxcpt.Fragment.BaoxianxinxiFragment;
import com.example.administrator.ydwlxcpt.Fragment.JibenxinxiFragment;
import com.example.administrator.ydwlxcpt.Fragment.Yindaoye1Fragment;
import com.example.administrator.ydwlxcpt.Fragment.Yindaoye2Fragment;
import com.example.administrator.ydwlxcpt.Fragment.Yindaoye3Fragment;
import com.example.administrator.ydwlxcpt.R;

import java.util.ArrayList;
import java.util.List;
//引导页
public class Yindaoye extends BaseActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_yindaoye);
        initViewPager();
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.vp_yindaoye_viewpager);
        List<Fragment> fragmentList = new ArrayList<>();
        Yindaoye1Fragment yindaoye1Fragment = new Yindaoye1Fragment();
        Yindaoye2Fragment yindaoye2Fragment = new Yindaoye2Fragment();
        Yindaoye3Fragment yindaoye3Fragment = new Yindaoye3Fragment();
        fragmentList.add(yindaoye1Fragment);
        fragmentList.add(yindaoye2Fragment);
        fragmentList.add(yindaoye3Fragment);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.setCurrentItem(0);
    }
}
