package com.lpf.quickandroid.splash;

import android.app.Activity;

/**
 * Created by liupengfei on 2017/6/27 12:53.
 */

public class ItemSplash {

    private String name;
    private Class activity;

    public ItemSplash(String name, Class activity) {
        this.name = name;
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }
}
