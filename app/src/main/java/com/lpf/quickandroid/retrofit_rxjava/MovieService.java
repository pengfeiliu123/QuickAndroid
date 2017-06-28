package com.lpf.quickandroid.retrofit_rxjava;

//import io.reactivex.Observable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by liupengfei on 2017/6/28 16:13.
 */

public interface MovieService {

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
