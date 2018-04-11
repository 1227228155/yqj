package com.erze.yqj.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.erze.yqj.R;

/**
 * Created by 1227228155@qq.com on 2017/7/29.
 */

public class MFGT {
    public static void startActivity(Context context, Intent intent){
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
}
