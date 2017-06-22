package com.lpf.quickandroid.demo;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by liupengfei on 2017/6/18 15:24.
 */

public class ItemDemoBean implements MultiItemEntity{

    private String title;
    private Class<?> activity;
    private int imageResource;
    private int type;

    @Override
    public int getItemType() {
        return type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<?> getActivity() {
        return activity;
    }

    public void setActivity(Class<?> activity) {
        this.activity = activity;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
