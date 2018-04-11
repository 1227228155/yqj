package com.erze.yqj.moudle.world.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by 1227228155@qq.com on 2017/6/28.
 */

public class TabAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> views;
    private List<String> Strings;
    public TabAdapter(FragmentManager fm,List<Fragment> views,List<String> Strings) {
        super(fm);
        this.views=views;
        this.Strings=Strings;
    }


    @Override
    public Fragment getItem(int position) {
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }
    //配置标题的方法
    @Override
    public CharSequence getPageTitle(int position) {
        return Strings.get(position);
    }
}
