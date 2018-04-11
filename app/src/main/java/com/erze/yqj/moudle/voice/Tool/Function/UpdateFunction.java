package com.erze.yqj.moudle.voice.Tool.Function;

import android.os.Handler;
import android.os.Looper;


/**
 * Created by zhengtongyu on 16/5/29.
 */
public class UpdateFunction {
    public static void ShowToastFromThread(String toastString) {
        ShowToastFromThread(toastString, false);
    }

    public static void ShowToastFromThread(final String toastString, final boolean isLong) {
        if (CommonFunction.isEmpty(toastString)) {
            return;
        }

        if (CommonFunction.IsInMainThread()) {
       //     MyApplication.getInstance().showToast(toastString, "UpdateFunction", isLong);
        } else {
            Handler handler = new Handler(Looper.getMainLooper());

            handler.post(new Runnable() {
                @Override
                public void run() {
                  //  MyApplication.getInstance().showToast(toastString, "UpdateFunction", isLong);
                }
            });
        }
    }
}
