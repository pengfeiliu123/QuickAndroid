package com.lpf.quickandroid.gif.mvp;

import com.google.gson.Gson;
import com.lpf.quickandroid.gif.Util;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

import static com.lpf.quickandroid.gif.Util.ignoreTrust;

/**
 * Created by liupengfei on 2017/6/23 16:27.
 */

public class GifModel implements GifContract.Model{

    @Override
    public List<Feed> requestDatas(final String url, final String channelId,final String feedId,final int requestNum) {

//        return Observable.create(new Observable.OnSubscribe<List<Feed>>() {
//            @Override
//            public void call(Subscriber<? super List<Feed>> subscriber) {
//
//                NewsListRequest newsListRequest = new NewsListRequest();
//                newsListRequest.initWithNum(channelId, NewsListRequest.PullAction.Enter, 1, feedId, requestNum);
//                RequestBody postBody = RequestBody.create(MediaType.parse("application/json"), Util.toJsonPretty(newsListRequest));
//                Request.Builder requestBuilder = new Request.Builder().url(url).post(postBody);
//                requestBuilder = Util.addHeader(requestBuilder);
//                Request request = requestBuilder.build();
//                try {
//                    OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
//                            .readTimeout(NGCommonConfiguration.COMMON_READ_TIME_OUT, TimeUnit.SECONDS)
//                            .connectTimeout(NGCommonConfiguration.COMMON_CONNECT_TIME_OUT, TimeUnit.SECONDS)
//                            .writeTimeout(NGCommonConfiguration.COMMON_WRITE_TIME_OUT, TimeUnit.SECONDS);
//                    builder = ignoreTrust(builder);
//                    //Log.d("test", Util.toJsonPretty(newsListRequest));
//                    OkHttpClient okHttpClient = builder.build();
//                    Call call = okHttpClient.newCall(request);
//                    Response response = call.execute();
//                    if (response.code() == 200) {
//                        String responseStr = response.body().string();
//                        Gson gson = new Gson();
//                        Feed[] feeds = gson.fromJson(responseStr, Feed[].class);
//                        List<Feed> newFeedList = Arrays.asList(feeds);
//                        subscriber.onNext(newFeedList);
//                    }
//                    //Log.d("test", "code:" + response.code());
//                } catch (java.net.UnknownHostException e) {
//                    subscriber.onNext(null);
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                    subscriber.onNext(null);
//                }
//
//            }
//        });
return null;

    }
}
