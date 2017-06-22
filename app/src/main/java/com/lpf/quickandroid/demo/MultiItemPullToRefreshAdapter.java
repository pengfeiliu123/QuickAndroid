package com.lpf.quickandroid.demo;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lpf.quickandroid.R;

import java.util.List;

/**
 * Created by liupengfei on 2017/6/18 15:43.
 */

public class MultiItemPullToRefreshAdapter extends BaseMultiItemQuickAdapter<ItemDemoBean, BaseViewHolder>{
    public MultiItemPullToRefreshAdapter(List<ItemDemoBean> data) {
        super(data);
        addItemType(0, R.layout.second_item_1);
        addItemType(1, R.layout.second_item_2);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemDemoBean item) {
        switch (helper.getItemViewType()) {
            case 0:
                bindItemType1(helper, item);
                break;
            case 1:
                bindItemType2(helper, item);
                break;
        }
    }

    private void bindItemType1(BaseViewHolder helper, ItemDemoBean item) {
        helper.addOnClickListener(R.id.second_item1_img);
        helper.getView(R.id.second_item1_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "haha", Toast.LENGTH_SHORT).show();
            }
        });
        helper.getView(R.id.second_item1_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "click text", Toast.LENGTH_SHORT).show();
            }
        });
        ((TextView)helper.getView(R.id.second_item1_tv)).setText(item.getTitle());
    }

    private void bindItemType2(BaseViewHolder helper, ItemDemoBean item) {
        helper.addOnClickListener(R.id.second_item2_tv);
        ((TextView)helper.getView(R.id.second_item2_tv)).setText(item.getTitle());
    }

}
