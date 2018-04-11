package com.erze.yqj.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/4/2 0002.
 */

public class BaseFragment extends Fragment implements BaseMethod {


    private View mContentView;
     private ViewGroup container;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView();
        initData();
        initListener();
        initLoad();
        this.container=container;
        return mContentView;
    }
    public View getContentView() {
        return mContentView;
    }

    public void setContentView(int viewId){
        this.mContentView =getActivity().getLayoutInflater().inflate(viewId, container, false);
    }
    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initLoad() {

    }
}
