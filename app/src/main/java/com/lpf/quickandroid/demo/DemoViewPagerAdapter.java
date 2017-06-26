package com.lpf.quickandroid.demo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.lpf.quickandroid.gif.GifFragment;

import java.util.ArrayList;

/**
 * Created by liupengfei on 2017/6/20 16:41.
 */

public class DemoViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private Fragment currentFragment;

    public DemoViewPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments.clear();
        fragments.add(DemoFragment.newInstance(0));
        fragments.add(DemoFragment.newInstance(0));
        fragments.add(DemoFragment.newInstance(0));
        fragments.add(DemoFragment.newInstance(0));
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if(getCurrentFragment()!=object){
            currentFragment = ((Fragment)object);
        }
        super.setPrimaryItem(container, position, object);
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

}
