package com.lpf.quickandroid.retrofit_rxjava.common;

import android.content.Context;

import com.lpf.quickandroid.retrofit_rxjava.MovieEntity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liupengfei on 2017/6/29 15:34.
 */

public class HttpMethods {

    public static final String baseUrl = "https://api.douban.com/v2/movie/";

    public static final int default_timeout = 5;

    private Retrofit retrofit;
    private ApiService apiService;

    /*** singleton *****/

    private HttpMethods(){
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(default_timeout, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    private static class SingletonHolder{
        private static final HttpMethods instance = new HttpMethods();
    }

    public static HttpMethods getInstance(){
        return SingletonHolder.instance;
    }

    /*** deal with request api ****/
    public Observable<MovieEntity> getTopMovie2(int start, int count){
        return apiService.getTopMovie2(start,count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<MovieEntity> getTopMovie3(int start,int count){
        return apiService.getTopMovie3(start,count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
