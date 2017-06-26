package com.lpf.quickandroid;

import android.support.multidex.MultiDexApplication;

import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by liupengfei on 2017/6/11 16:02.
 */

public class MainApplication extends MultiDexApplication {

    private static MainApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        KLog.init(BuildConfig.LOG_DEBUG);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    public static MainApplication getInstance(){
        return instance;
    }
}
