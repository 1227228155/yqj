package com.erze.yqj.moudle.main;

import android.util.ArrayMap;

import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.KSYSDKBean;
import com.erze.yqj.rxframe.rx.Rxschedulers;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/6.
 */

public class MainModel implements MainContract.Model {
    Map<String, String> map = new ArrayMap<>();
    @Override
    public Observable<KSYSDKBean> getSDK(String pkg) {
        return NetDao.getInstance().getKSVSApi().getSDK(pkg).compose(Rxschedulers.<KSYSDKBean>io_main());
    }

}
