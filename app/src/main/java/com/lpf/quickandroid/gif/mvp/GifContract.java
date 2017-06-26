package com.lpf.quickandroid.gif.mvp;

import com.lpf.quickandroid.base.BasePresenter;
import com.lpf.quickandroid.base.IBaseModel;
import com.lpf.quickandroid.base.IBaseView;

import java.util.List;

/**
 * Created by liupengfei on 2017/6/23 16:27.
 */

public class GifContract {

    public interface Model extends IBaseModel{
        List<Feed> requestDatas(final String url, final String channelId,final String feedId,final int requestNum);
    }

    public interface View extends IBaseView{
        void success(List<Feed> feedList);
        void failed();
    }

    abstract static class Presenter extends BasePresenter<View,Model>{
        abstract void request(final String url, final String channelId,final String feedId,final int requestNum);
    }
}
