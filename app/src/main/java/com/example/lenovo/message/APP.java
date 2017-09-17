package com.example.lenovo.message;

import android.app.Application;
import android.content.Context;

import com.example.lenovo.message.util.Constants;
import com.mob.MobSDK;

/**
 * Created by lenovo on 2017/9/1.
 */
public class APP extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        MobSDK.init(this, Constants.KEY,Constants.SECRET);
    }
}
