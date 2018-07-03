package com.example.administrator.ydwlxcpt.Activity;

import android.app.Application;

//import com.example.administrator.ydwlxcpt.Utils.UpdateConfig;

/**
 * @author Administrator
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /**默认的请求方式，使用get请求*/
//        UpdateConfig.initGet(this);
        /**post的请求方式*/
//        UpdateConfig.initPost(this);
        /**分离网络使用的初始化*/
//        UpdateConfig.initNoUrl(this);
    }
}
