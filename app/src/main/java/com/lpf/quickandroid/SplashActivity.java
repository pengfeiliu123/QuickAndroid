package com.lpf.quickandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lpf.quickandroid.retrofit_rxjava.RetrofitForMovieActivity;
import com.lpf.quickandroid.splash.ItemSplash;
import com.lpf.quickandroid.splash.SplashRvAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_rv)
    RecyclerView splashRv;
    SplashRvAdapter mAdapter;
    private List<ItemSplash> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        datas.add(new ItemSplash("ButtomNavigation",MainActivity.class));
        datas.add(new ItemSplash("RetrofitOnly",RetrofitForMovieActivity.class));
        splashRv.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new SplashRvAdapter(R.layout.item_splash, datas);
        splashRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SplashActivity.this,datas.get(position).getActivity());
                startActivity(intent);
            }
        });
    }
}
