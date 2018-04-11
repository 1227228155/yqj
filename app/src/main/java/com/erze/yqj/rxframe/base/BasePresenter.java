package com.erze.yqj.rxframe.base;


import com.erze.yqj.rxframe.rx.RxManager;

/**
 * Created by Administrator on 2017/4/2 0002.
 */


public abstract class BasePresenter<M, V> {
    public M mModel;
    public V mView;
    public RxManager mRxmanager = new RxManager();
    public void attachVM(V v, M m) {
        this.mModel = m;
        this.mView = v;

    }

    public void detachVM() {
        mView = null;
        mModel = null;
        mRxmanager.clear();
    }

}
