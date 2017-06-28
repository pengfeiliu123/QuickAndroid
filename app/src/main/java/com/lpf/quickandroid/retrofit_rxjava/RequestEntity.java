package com.lpf.quickandroid.retrofit_rxjava;

/**
 * Created by liupengfei on 2017/6/28 20:07.
 */

public class RequestEntity {
    private int start;
    private int count;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public RequestEntity(int start, int count) {
        this.start = start;
        this.count = count;
    }
}
