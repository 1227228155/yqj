package com.erze.yqj.rxframe.base;

/**
 * Created by Administrator on 2017/4/2 0002.
 */

public interface BaseView {
    void onRequestStart();
    void onRequestError(String msg);
    void onRequestEnd();
    void onInternetError();
}
