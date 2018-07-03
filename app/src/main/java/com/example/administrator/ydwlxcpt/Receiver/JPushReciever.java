package com.example.administrator.ydwlxcpt.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.example.administrator.ydwlxcpt.Utils.AudioUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/1/18.
 */

public class JPushReciever extends BroadcastReceiver {
    private boolean flag = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            final String content = (String) bundle.get(JPushInterface.EXTRA_ALERT);
            AudioUtils.getInstance().init(context);
            AudioUtils.getInstance().speakText(content);
            Log.e("sanshao", content);
        } catch (Exception e) {
            Log.e("", e.toString());
        }
    }
}
