package com.lpf.quickandroid;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by liupengfei on 2017/6/11 16:02.
 */

public class App extends MultiDexApplication {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        KLog.init(BuildConfig.LOG_DEBUG);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    public static Context getContext(){
        return context;
    }
}
