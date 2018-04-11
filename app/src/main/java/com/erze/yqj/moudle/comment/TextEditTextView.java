package com.erze.yqj.moudle.comment;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

/**
 *
 * 重写EditText 用来监听Edittext收起时的监听时间
 * Created by 1227228155@qq.com on 2017/7/31.
 */

public class TextEditTextView extends AppCompatEditText {
    private View view,view2;//view 需要显示的View  view2需要隐藏的View
    public TextEditTextView(Context context) {
        super(context);
    }

    public TextEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setView(View view,View view2){
        this.view =view;
        this.view2 =view2;
    }
    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == 1) {
            Log.i("main_activity", "键盘向下 ");
            if (view!=null){
                view.setVisibility(VISIBLE);
            }
            view2.setVisibility(GONE);
            super.onKeyPreIme(keyCode, event);
            return false;
        }
        return super.onKeyPreIme(keyCode, event);
    }
}
