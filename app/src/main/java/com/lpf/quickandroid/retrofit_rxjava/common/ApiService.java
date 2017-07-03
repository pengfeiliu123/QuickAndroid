package com.lpf.quickandroid.retrofit_rxjava.common;

import com.lpf.quickandroid.retrofit_rxjava.MovieEntity;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by liupengfei on 2017/6/29 15:36.
 */

public interface ApiService {
    @GET("top250")
    Call<MovieEntity> getTopMovie(@Query("start") int start,
                                  @Query("count") int count);

    @GET("top250")
    Observable<MovieEntity> getTopMovie2(@Query("start") int start,
                                         @Query("count") int count);

    @GET("top250")
    Flowable<MovieEntity> getTopMovie3(@Query("start") int start,
                                       @Query("count") int count);
}
