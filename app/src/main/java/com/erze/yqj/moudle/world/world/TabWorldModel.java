package com.erze.yqj.moudle.world.world;

import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.Videobean;
import com.erze.yqj.rxframe.rx.Rxschedulers;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 1227228155@qq.com on 2017/7/26.
 */

public class TabWorldModel implements TabWorldContract.Model {
    @Override
    public Observable<Videobean> getVideo(Map<String, String> map) {
        return NetDao.getInstance().getCommonApi().getWorld2(map).compose(Rxschedulers.<Videobean>io_main());
    }
}
