package com.example.administrator.ydwlxcpt.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ydwlxcpt.Fragment.JibenxinxiFragment;
import com.example.administrator.ydwlxcpt.R;
import com.example.administrator.ydwlxcpt.Utils.FlowGroupView;
import com.example.administrator.ydwlxcpt.View.FirstEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/8.
 */
public class FlowLayoutActivity extends Activity {
    private String mNames[] = {
            "京","津","沪","皖","新","蒙","闽","贵","甘","赣",
            "渝","冀","豫","苏","藏","川","宁","琼","贵","青",
            "浙","辽","黑","吉","鄂","粤", "晋","陕","云","湘"
    };
    private String nub[]={
            "A","B","C","D","E","F","G","H","G",
            "K","L","M","N","O","P","Q","R","S","T",
            "U","V","W","X","Y","Z", "0","1","2","3" ,
            "4","5","6","7","8","9"
    };
    private FlowGroupView mFlowLayout,mFlowLayout_shuzi;
    private EditText chepai;
    private List<TextView> tvs = null;
    private TextView quxiao,queding;
    private ImageView shanchu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liushi);
        initChildViews();
        setFinishOnTouchOutside(true);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = (int) (d.getHeight() * 0.5); // `高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 1); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
        p.gravity = Gravity.BOTTOM;//设置对话框置顶显示
        intview();
    }
    private void intview() {
        shanchu=(ImageView)findViewById(R.id.iv_shanchu);
        quxiao=(TextView)findViewById(R.id.tv_quxiao);
        queding=(TextView)findViewById(R.id.tv_queding);
        chepai = (EditText) findViewById(R.id.et_chepai_chepai);
        chepai.setInputType(InputType.TYPE_NULL);
        shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=chepai.getText().toString();
                if(s.length()==0){
//判断字符长度，如果为0就不做任何处理
                }else if(s.length()>0){
                    chepai.setText(s.substring(0,s.length()-1));//将字符取的地方减一位需要减2.“sss”.length()=3,取ss需要取的位置到1,因为subString是左闭右开，所以是需要-2
                }
            }
        });
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String chepaihao= chepai.getText().toString();
                EventBus.getDefault().post(
                        new FirstEvent(chepaihao));
                finish();
            }
        });
    }

    private void initChildViews() {
        tvs = new LinkedList<TextView>();
        mFlowLayout = (FlowGroupView) findViewById(R.id.flowlayout);
        mFlowLayout_shuzi=(FlowGroupView)findViewById(R.id.flowlayout_shuzi);
        mFlowLayout_shuzi.setVisibility(View.GONE);

        final ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.width=100;
        lp.height=100;
        lp.leftMargin = 10;
        lp.rightMargin = 10;
        lp.topMargin = 10;
        lp.bottomMargin = 10;
        for (int i = 0; i < mNames.length; i++) {
            final TextView view = new TextView(this);
            view.setText(mNames[i]);
            view.setTextColor(Color.BLACK);
            view.setTextSize(25);
            view.setPadding(10,0,10,0);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
            tvs.add(view);
            mFlowLayout.addView(view, lp);
            final String str=view.getText().toString();
            tvs.get(i).setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View view) {
                    chepai.setText(str);
                    chepai.setCursorVisible(false);
                    shuzi();
                }
            });
        }
    }

    private void shuzi() {
        tvs = new LinkedList<TextView>();
        mFlowLayout_shuzi = (FlowGroupView) findViewById(R.id.flowlayout_shuzi);
        mFlowLayout.setVisibility(View.GONE);
        mFlowLayout_shuzi.setVisibility(View.VISIBLE);
        final ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.width=100;
        lp.height=100;
        lp.leftMargin = 10;
        lp.rightMargin = 10;
        lp.topMargin = 10;
        lp.bottomMargin = 10;
        for (int i = 0; i < nub.length; i++) {
            final TextView view = new TextView(this);
            view.setText(nub[i]);
            view.setTextColor(Color.BLACK);
            view.setTextSize(25);
            view.setPadding(20,0,10,0);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
            tvs.add(view);
            mFlowLayout_shuzi.addView(view, lp);
            final String shuzi=view.getText().toString();
            tvs.get(i).setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View view) {
                    chepai.setText(chepai.getText()+shuzi);
                    chepai.setCursorVisible(false);
                }
            });
        }
    }
}
