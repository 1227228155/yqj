package com.erze.yqj.rxframe.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/9 0009.
 */

public class Rxschedulers {
    public static <T> ObservableTransformer<T,T> io_main(){
        return new ObservableTransformer<T,T>(){
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return
                        //生产线程
                        upstream.subscribeOn(Schedulers.io())
                        //消费线程
                        .observeOn(AndroidSchedulers.mainThread());

            }

        };
    }
}
