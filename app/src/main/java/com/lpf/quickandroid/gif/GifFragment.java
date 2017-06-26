package com.lpf.quickandroid.gif;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lpf.quickandroid.R;
import com.lpf.quickandroid.base.BaseFragment;
import com.lpf.quickandroid.gif.mvp.Feed;
import com.lpf.quickandroid.gif.mvp.GifContract;
import com.lpf.quickandroid.gif.mvp.GifModel;
import com.lpf.quickandroid.gif.mvp.GifPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class GifFragment extends BaseFragment<GifPresenter, GifModel> implements GifContract.View {


    @BindView(R.id.gif_rv)
    RecyclerView gifRv;
    @BindView(R.id.gif_sw)
    SwipeRefreshLayout gifSw;
    @BindView(R.id.gifFragmentContainer)
    FrameLayout gifFragmentContainer;

    public GifFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initPresenter() {
        mPresenter.setViewAndModel(this, mModel);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_gif, container, false);
        return rootView;
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void success(List<Feed> feedList) {

    }

    @Override
    public void failed() {

    }

}
