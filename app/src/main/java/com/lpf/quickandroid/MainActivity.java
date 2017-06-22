package com.lpf.quickandroid;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.lpf.quickandroid.demo.DemoFragment;
import com.lpf.quickandroid.demo.DemoViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private DemoFragment mCurrentFragment;
    private DemoViewPagerAdapter mAdapter;
    private AHBottomNavigationAdapter mNavigationAdapter;
    private int[] mTabColors;
    private Handler mHandler = new Handler();

    @BindView(R.id.view_pager)
    AHBottomNavigationViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void initViews() {
        mTabColors = getApplicationContext().getResources().getIntArray(R.array.tab_colors);
        mNavigationAdapter = new AHBottomNavigationAdapter(this,R.menu.bottom_navigation_menu_3);
        mNavigationAdapter.setupWithBottomNavigation(bottomNavigation,mTabColors);

        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                if(mCurrentFragment == null){
                    mCurrentFragment = mAdapter.getCurrentFragment();
                }

                if(wasSelected){
                    mCurrentFragment.refresh();
                    return true;
                }

                if(mCurrentFragment != null){
                    mCurrentFragment.willBeHidden();
                }

                viewPager.setCurrentItem(position,false);
                mCurrentFragment = mAdapter.getCurrentFragment();
                mCurrentFragment.willBeDisplayed();

                bottomNavigation.setNotification("",position);          // dimiss notification num

                return true;
            }
        });

        viewPager.setOffscreenPageLimit(4);
        mAdapter = new DemoViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        mCurrentFragment = mAdapter.getCurrentFragment();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AHNotification notification = new AHNotification.Builder()
                        .setText(":)")
                        .setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_notification_back))
                        .setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_notification_text))
                        .build();
                bottomNavigation.setNotification(notification, 1);
            }
        },3000);

        advancedSetting();
    }

    private void advancedSetting() {
        bottomNavigation.setAccentColor(ContextCompat.getColor(this, R.color.color_navigation_item));
        bottomNavigation.setSelectedBackgroundVisible(true);
//        bottomNavigation.setColored(true);
//        bottomNavigation.restoreBottomNavigation(true);
//        bottomNavigation.hideBottomNavigation(true);

//        AHBottomNavigation.TitleState.ALWAYS_SHOW);
//        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
    }
}
