package com.erze.yqj.rxframe.rx;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 *
 */

public class RxManager {
    private Map<String, Observable<?>> mObservables = new HashMap<>();// 管理观察源
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();// 管理订阅者


    public void add(Disposable m) {
        mCompositeDisposable.add(m);
    }

    public void clear() {
        mCompositeDisposable.dispose();// 取消订阅
    }
}
