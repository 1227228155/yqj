package com.erze.yqj.rxframe.base;

import android.os.Bundle;

import com.erze.yqj.common.BaseFragment;
import com.erze.yqj.rxframe.util.TUtil;


/**
 * Created by Administrator on 2017/4/2 0002.
 */

public abstract class BaseFrameFragment<P extends BasePresenter, M extends BaseModel> extends BaseFragment implements BaseView{
    public P mPresenter;

    public M mModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) {
            mPresenter.attachVM(this, mModel);
        }
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachVM();
        }
        super.onDestroy();
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestError(String msg) {

    }

    @Override
    public void onInternetError() {

    }

    @Override
    public void onRequestEnd() {

    }
}

