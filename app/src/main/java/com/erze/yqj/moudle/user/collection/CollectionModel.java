package com.erze.yqj.moudle.user.collection;

import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.FollowBean;
import com.erze.yqj.rxframe.rx.Rxschedulers;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/28.
 */

public class CollectionModel implements CollectionContract.Model {
    @Override
    public Observable<FollowBean> getCollectionInfo(Map<String, String> map) {
        return NetDao.getInstance().getCommonApi().getCollectionInfo(map).compose(Rxschedulers.<FollowBean>io_main());
    }
}
