package com.erze.yqj.moudle.voice.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.erze.yqj.R;

/**
 * Created by 1227228155@qq.com on 2017/9/5.
 */

public class MenuDialog implements Animation.AnimationListener {
    private View mMenuView;
    private OnMenuListener mOnMenuListener;
    private PopupWindow mPopupWindow;
    //菜单动画 进入
    private Animation mAnimationIn;
    //菜单动画 出去
    private Animation mAnimationOut;
    //遮罩层动画 进入
    private Animation mAnimationShadeIn;
    //遮罩层动画 出去
    private Animation mAnimationShadeOut;
    private View content_layout;
    private View bg_layout;
    private String[] items;

    public MenuDialog(Context context, String title, String[] items, OnMenuListener l) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.menu_dialog, null);
        Button btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
        Button bt_title = (Button) mMenuView.findViewById(R.id.bt_title);
        //取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                cancel();
            }
        });

        if (!TextUtils.isEmpty(title)){
            bt_title.setText(title);
        }

        bg_layout = mMenuView.findViewById(R.id.bg_layout);
        content_layout = mMenuView.findViewById(R.id.content_layout);
        LinearLayout item_layout = (LinearLayout)mMenuView.findViewById(R.id.item_layout);
        this.items = items;
        for(int i = 0; i < items.length; i++){
            String item = items[i];
            Button view = (Button) inflater.inflate(R.layout.menu_dialog_item, item_layout, false);
            item_layout.addView(view);
            view.setOnClickListener(creatOnClickListener(i));
            view.setText(item);
            view.setTextColor(context.getResources().getColor(R.color.black));
        }
        mOnMenuListener = l;
        mPopupWindow = new PopupWindow(context);
        //设置SelectPicPopupWindow的View
        mPopupWindow.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        mPopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        mPopupWindow.setFocusable(true);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        mPopupWindow.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.content_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        cancel();
                    }
                }
                return true;
            }
        });
        mAnimationIn = AnimationUtils.loadAnimation(context,
                R.anim.anim_menu1_enter);
        mAnimationOut = AnimationUtils.loadAnimation(context,
                R.anim.anim_menu1_exit);
        mAnimationShadeIn = AnimationUtils.loadAnimation(context,
                R.anim.anim_menu1_bg_enter);
        mAnimationShadeOut = AnimationUtils.loadAnimation(context,
                R.anim.anim_menu1_bg_exit);
        mAnimationOut.setAnimationListener(this);
    }

    private View.OnClickListener creatOnClickListener(final int i){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                if(mOnMenuListener != null){
                    mOnMenuListener.onMenuClick(MenuDialog.this, i, items[i]);
                }
            }
        };
    }

    private void cancel(){
        bg_layout.startAnimation(mAnimationShadeOut);
        content_layout.startAnimation(mAnimationOut);
    }

    public interface OnMenuListener {
        public void onMenuClick(MenuDialog dialog, int which, String item);
        public void onMenuCanle(MenuDialog dialog);
    }

    public void show(View v){
        mPopupWindow.showAtLocation(v
                , Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        bg_layout.startAnimation(mAnimationShadeIn);
        content_layout.startAnimation(mAnimationIn);
    }

    public void dismiss(){
        cancel();
    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // TODO Auto-generated method stub
        if(mAnimationOut.equals(animation)){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPopupWindow.dismiss();
                    if(mOnMenuListener != null){
                        mOnMenuListener.onMenuCanle(MenuDialog.this);
                    }
                }
            }, 10);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }
    Handler handler = new Handler();
}
