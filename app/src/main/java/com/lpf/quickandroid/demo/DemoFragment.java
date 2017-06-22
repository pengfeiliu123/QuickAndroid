package com.lpf.quickandroid.demo;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lpf.quickandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DemoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{

    private FrameLayout mFragmentContainer;

    private MultiItemPullToRefreshAdapter mMultiRefreshAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private List<ItemDemoBean> mDataList;


    public DemoFragment() {
        // Required empty public constructor
    }

    public static DemoFragment newInstance(int index) {
        DemoFragment fragment = new DemoFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments().getInt("index", 0) == 0) {
            View view = inflater.inflate(R.layout.fragment_demo, container, false);
            initDemoFirst(view);
            return view;
        } else {
            View view = inflater.inflate(R.layout.fragment_demo_2, container, false);
            initDatas();
            initDemoRv(view);
            return view;
        }
    }

    // init First Fragment demo
    private void initDemoFirst(View view) {

    }

    // init Second Fragment demo
    private void initDemoRv(View view){
        mFragmentContainer = (FrameLayout) view.findViewById(R.id.fragmentContainer);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.demo_sw);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //设置刷新时动画的颜色，可以设置4个
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        mRecyclerView = (RecyclerView)view.findViewById(R.id.demo_rv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mMultiRefreshAdapter = new MultiItemPullToRefreshAdapter(mDataList);
        mMultiRefreshAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        View headerView = getActivity().getLayoutInflater().inflate(R.layout.item_header,(ViewGroup)mRecyclerView.getParent(),false);
        mMultiRefreshAdapter.addHeaderView(headerView);
        mMultiRefreshAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), "Click all Item Area", Toast.LENGTH_SHORT).show();
            }
        });
        mMultiRefreshAdapter.setOnLoadMoreListener(this,mRecyclerView);
        mMultiRefreshAdapter.setEmptyView(R.layout.empty_view);

        mRecyclerView.setAdapter(mMultiRefreshAdapter);
    }

    private void initDatas() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ItemDemoBean item = new ItemDemoBean();
            if(i%2 == 0){
                item.setType(0);
            }else{
                item.setType(1);
            }
            item.setTitle("Title->"+i);
            item.setImageResource(R.drawable.ic_home_black_24dp);
            mDataList.add(item);
        }
    }

    /**
     * refresh
     */
    public void refresh() {
        if (getArguments().getInt("index", 0) > 0 && mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    /**
     * Called when a fragment will be displayed
     */
    public void willBeDisplayed(){
        if(mFragmentContainer!=null){
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(),R.anim.fade_in);
            mFragmentContainer.startAnimation(fadeIn);
        }
    }

    /**
     * Called when a fragment will be displayed
     */
    public void willBeHidden(){
        if (mFragmentContainer != null) {
            Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
            mFragmentContainer.startAnimation(fadeOut);
        }
    }

    /**
     * swipeLayout refresh
     */
    @Override
    public void onRefresh() {
        mMultiRefreshAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<ItemDemoBean> newDatas = new ArrayList<ItemDemoBean>();
                for (int i = 0; i < 5; i++) {
                    ItemDemoBean item = new ItemDemoBean();
                    item.setTitle("New Title->"+i);
                    item.setImageResource(R.drawable.ic_home_black_24dp);
                    newDatas.add(item);
                }
                mMultiRefreshAdapter.addData(0,newDatas);
                mSwipeRefreshLayout.setRefreshing(false);
                mMultiRefreshAdapter.setEnableLoadMore(true);
            }
        },1000);
    }

    /**
     * request More Data
     */
    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<ItemDemoBean> newDatas = new ArrayList<ItemDemoBean>();
                for (int i = 0; i < 5; i++) {
                    ItemDemoBean item = new ItemDemoBean();
                    item.setTitle("Request More Title->"+i);
                    item.setImageResource(R.drawable.ic_home_black_24dp);
                    newDatas.add(item);
                }
                mMultiRefreshAdapter.addData(newDatas);
                mMultiRefreshAdapter.loadMoreComplete();
                mSwipeRefreshLayout.setEnabled(true);
            }
        },1000);
    }
}
