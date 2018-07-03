package com.example.administrator.ydwlxcpt.Utils;

import android.view.View;
import android.widget.AdapterView;

import java.util.Calendar;

import static com.baidu.location.h.j.v;

/**
 * Created by Administrator on 2017/12/27.
 */

public abstract class NoDoubleClickListener  implements View.OnClickListener {

    public static final int MIN_CLICK_DELAY_TIME = 5000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View view) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(view);
        }else {

        }
    }


    protected abstract void onNoDoubleClick(View view);

}
