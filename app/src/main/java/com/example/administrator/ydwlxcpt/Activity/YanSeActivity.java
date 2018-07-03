package com.example.administrator.ydwlxcpt.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ydwlxcpt.R;
import com.example.administrator.ydwlxcpt.Utils.FlowGroupView;
import com.example.administrator.ydwlxcpt.View.FirstEvent;
import com.example.administrator.ydwlxcpt.View.TwoEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/8.
 */

public class YanSeActivity extends Activity {
    private String mNames[] = {
            "白", "灰", "红", "蓝", "黑", "银", "棕","绿","黄","青", "紫","金"
    };

    private FlowGroupView mFlowLayout,mFlowLayout_shuzi;
    private EditText chepai;
    private List<TextView> tvs = null;
    private TextView quxiao,queding;
    private ImageView shanchu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop);
        initChildViews();
        setFinishOnTouchOutside(true);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = (int) (d.getHeight() * 0.5); // 高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 1); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
        p.gravity = Gravity.BOTTOM;//设置对话框置顶显示
        intview();
    }

    private void intview() {
        shanchu=(ImageView)findViewById(R.id.iv_yanse_shanchu);
        quxiao=(TextView)findViewById(R.id.tv_yanse_quxiao);
        queding=(TextView)findViewById(R.id.tv_yanse_queding);
        chepai=(EditText)findViewById(R.id.et_yanse_chepai);
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
               String yanse= chepai.getText().toString();
                EventBus.getDefault().post(
                        new TwoEvent(yanse));
                finish();

            }
        });
    }

    private void initChildViews() {
        tvs = new LinkedList<TextView>();

        mFlowLayout = (FlowGroupView) findViewById(R.id.flowlayout_yanse);
        chepai = (EditText) findViewById(R.id.et_yanse_chepai);
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
                }
            });
        }
    }
}
