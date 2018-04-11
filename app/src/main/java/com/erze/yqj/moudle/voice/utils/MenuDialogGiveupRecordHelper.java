package com.erze.yqj.moudle.voice.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.erze.yqj.moudle.voice.ui.MenuDialog;

/**
 * Created by 1227228155@qq.com on 2017/9/5.
 */

public class MenuDialogGiveupRecordHelper {
    protected static final int REQUEST_CODE_INITIAL_PIC_FROM_CAMERA = 0;
    protected static final int REQUEST_CODE_INITIAL_PIC_FROM_GALLERY = 1;
    protected static final int REQUEST_CODE_INITIAL_PIC_FROM_CROP = 2;
    private static final int PIC_PIXLS = 300;

    private MenuDialog mMenuDialog;
    private ImageView photo;
    private MenuDialogListerner menuDialogListerner;

    public MenuDialogGiveupRecordHelper(Context context, final MenuDialogListerner menuDialogListerner){

        String[] items = new String[]{"重新录制","放弃录制"};
        mMenuDialog = new MenuDialog(context, "确定要放弃录制吗",items , new MenuDialog.OnMenuListener() {
            @Override
            public void onMenuClick(MenuDialog dialog, int which, String item) {
                switch(which){
                    case 0:
                        menuDialogListerner.onSelected(0);
                        break;
                    case 1:
                        menuDialogListerner.onSelected(1);
                        break;
                }
            }
            @Override
            public void onMenuCanle(MenuDialog dialog) {

            }
        });
        this.menuDialogListerner = menuDialogListerner;

    }

    public void show(View v) {

        mMenuDialog.show(v);
    }
}
