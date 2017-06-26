package com.lpf.quickandroid.gif.mvp;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liupengfei on 2017/6/23 16:27.
 */

public class GifPresenter extends GifContract.Presenter{

    @Override
    public void request(final String url, final String channelId,final String feedId,final int requestNum) {
        mView.showLoading();
//        mModel.requestDatas(url, channelId,feedId,requestNum)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<List<Feed>>(){
//                    @Override
//                    public void onCompleted() {
//                        mView.hideLoading();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                        mView.hideLoading();
//                    }
//
//                    @Override
//                    public void onNext(List<Feed> feedList) {
//                        mView.hideLoading();
//                        if(feedList != null){
//                            mView.success(feedList);
//                        }else{
//                            mView.failed();
//                        }
//                    }
//                });
    }
}
