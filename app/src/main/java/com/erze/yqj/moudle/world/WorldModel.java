package com.erze.yqj.moudle.world;

import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.WorldBean;
import com.erze.yqj.rxframe.rx.Rxschedulers;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/25.
 */

public class WorldModel implements WorldContract.Model {
    @Override
    public Observable<WorldBean> getRecommendData(Map<String, String> map) {
        return NetDao.getInstance().getCommonApi().getWorld(map).compose(Rxschedulers.<WorldBean>io_main());
    }
}
