package com.erze.yqj.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 各种转换方法的工具类
 *
 */

public class ConvertUtils {
    public static <T> ArrayList<T> array2List(T[] array) {
        List<T> list = Arrays.asList(array);
        ArrayList<T> arrayList = new ArrayList<>(list);
        return arrayList;
    }

    public static int px2dp(Context context, int px){
        int density = (int) context.getResources().getDisplayMetrics().density;
        return px/density;
    }

}
