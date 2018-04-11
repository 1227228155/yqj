package com.erze.yqj.moudle.user.followlist;

import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.FansListBean;
import com.erze.yqj.rxframe.rx.Rxschedulers;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/27.
 */

public class FollowListModel implements FollowListContract.Model {
    @Override
    public Observable<FansListBean> getFollowList(Map<String, String> map) {
        return NetDao.getInstance().getCommonApi().getFollowList(map).compose(Rxschedulers.<FansListBean>io_main());
    }


}
