package com.lpf.quickandroid.demo;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by liupengfei on 2017/6/20 16:41.
 */

public class DemoViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<DemoFragment> fragments = new ArrayList<>();
    private DemoFragment currentFragment;

    public DemoViewPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments.clear();
        fragments.add(DemoFragment.newInstance(0));
        fragments.add(DemoFragment.newInstance(1));
        fragments.add(DemoFragment.newInstance(2));
        fragments.add(DemoFragment.newInstance(3));
    }

    @Override
    public DemoFragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if(getCurrentFragment()!=object){
            currentFragment = ((DemoFragment)object);
        }
        super.setPrimaryItem(container, position, object);
    }

    public DemoFragment getCurrentFragment() {
        return currentFragment;
    }

}
