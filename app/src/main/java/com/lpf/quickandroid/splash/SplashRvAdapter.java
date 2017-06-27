package com.lpf.quickandroid.splash;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lpf.quickandroid.R;

import java.util.List;

/**
 * Created by liupengfei on 2017/6/27 12:11.
 */

public class SplashRvAdapter extends BaseQuickAdapter<ItemSplash, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public SplashRvAdapter(@LayoutRes int layoutResId, @Nullable List<ItemSplash> data) {
        super(layoutResId, data);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, ItemSplash item) {
        ((TextView) helper.getView(R.id.splash_item_name)).setText(item.getName());
    }
}
