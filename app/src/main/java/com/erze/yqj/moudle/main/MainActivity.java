package com.erze.yqj.moudle.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.MyApplication;
import com.erze.yqj.R;
import com.erze.yqj.entity.KSYSDKBean;
import com.erze.yqj.entity.ObjKey;
import com.erze.yqj.moudle.Login.LoginActivity;
import com.erze.yqj.moudle.follow.FollowFragment;
import com.erze.yqj.moudle.main.other.BlankFragment;
import com.erze.yqj.moudle.main.other.OnlyIconItemView;
import com.erze.yqj.moudle.main.popup.PopupMenuUtil;
import com.erze.yqj.moudle.student.StudentFragment;
import com.erze.yqj.moudle.user.UserFragment;
import com.erze.yqj.moudle.world.WorldFragment;
import com.erze.yqj.rxframe.base.BaseFrameActivity;
import com.erze.yqj.utils.AutoUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;
import com.ksyun.media.shortvideo.utils.AuthInfoManager;
import com.weavey.loading.lib.LoadingLayout;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;


public class MainActivity extends BaseFrameActivity<MainPresenter, MainModel> implements MainContract.View {

    Fragment[] fragments = new Fragment[5];
    FollowFragment followFragment;
    WorldFragment worldFragment;
    StudentFragment studentFragment;
    UserFragment userFragment;
    BlankFragment blankFragment;
    NavigationController navigationController;
    ImageView imageView;
    public String authInfo, date;
    @BindView(R.id.tab)
    PageBottomTabLayout tab;
    @BindView(R.id.loading_layout)
    LoadingLayout loading;
    private Handler mMainHandler;
    private String token, serverDate;
    private static int MAX_RETRY_COUNT = 3;  //若AKSK请求失败尝试3次
    private int mRetryCount = 0;
    private long mExitTime;//退出程序的时间
    SPUtils spUtils;
    private boolean exit = false;
    private Toast toast = null;
    private FragmentTransaction ft;
    private MyConnectionListener myConnectionListener;
    private  String hxid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        MIUISetStatusBarLightMode(getWindow(), true);
        FlymeSetStatusBarLightMode(getWindow(), true);
        setContentView(R.layout.activity_main);
        AutoUtils.setSize(this, true, 1080, 1920);
        AutoUtils.auto(this);
        ButterKnife.bind(this);
        AutoUtils.auto(tab);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (mMainHandler == null) {
            mMainHandler = new Handler();
        }
        mPresenter.getSDK(getPackageName());
        initView1();
        addFragment();
        if (spUtils == null) {
            spUtils = SPUtils.getInstance("user");
        }
        setListener();
        navigationController.setSelect(1);
        EventBus.getDefault().register(this);
    }




    /**
     * 登陆环信服务器
     */
    private void loginHX() {
        if (spUtils == null) {
            spUtils = SPUtils.getInstance("user");
        }
         hxid = spUtils.getString("userhxid");
        LogUtils.e("当前登录用户的环信ID：", "----------" + hxid);
        EMClient.getInstance().login(hxid, "yqj", new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                LogUtils.e("chat", "登录聊天服务器成功!");
                MyApplication.getInstance().initialise();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                LogUtils.e("chat", "登录聊天服务器失败!");
            }
        });
        //注册一个监听连接状态的listener
        if (myConnectionListener==null){
            myConnectionListener = new MyConnectionListener();
            EMClient.getInstance().addConnectionListener(myConnectionListener);
        }
    }



    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                        Toast.makeText(MainActivity.this, "账号已被移除", Toast.LENGTH_SHORT).show();
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                        Toast.makeText(MainActivity.this, "账号已在其他设备登陆", Toast.LENGTH_SHORT).show();
                    } else {
                        if (NetUtils.hasNetwork(MainActivity.this)) {
                            //连接不到聊天服务器
                          //  Toast.makeText(MainActivity.this, "连接不到聊天服务器", Toast.LENGTH_SHORT).show();
                        } else {
                            //当前网络不可用，请检查网络设置
                            Toast.makeText(MainActivity.this, "当前连接不可用，请检查网络设置", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        }
    }

    /**
     * 获得authInfo和date参数
     * 初始化录制SDK的鉴权
     *
     * @param ksysdkBean
     */
    @Override
    public void getSDK(KSYSDKBean ksysdkBean) {
        authInfo = ksysdkBean.getData().getAuthorization();
        date = ksysdkBean.getData().getXamzdate();
        LogUtils.e("getSDK", authInfo);
        LogUtils.e("getSDK", date);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (EmptyUtils.isEmpty(spUtils.getString("isLogin"))) {
            navigationController.setSelect(1);
        } else {
            loginHX();
        }

    }

    private AuthInfoManager.CheckAuthResultListener mCheckAuthResultListener = new AuthInfoManager
            .CheckAuthResultListener() {
        @Override
        public void onAuthResult(int result) {
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    AuthInfoManager.getInstance().removeAuthResultListener(mCheckAuthResultListener);
                    if (AuthInfoManager.getInstance().getAuthState()) {
                        LogUtils.e("onAuthResult", "成功");
                    } else {
                        LogUtils.e("onAuthResult", "失败");
                        //如果请求失败尝试三次
                        if (mRetryCount < MAX_RETRY_COUNT) {
                            mPresenter.getSDK(getPackageName());
                            mRetryCount++;
                        }

                    }
                }
            });
        }
    };

    /**
     * 鉴权完毕
     * 跳转到录制视频界面，携带视频配置的参数
     */
    @Override
    public void gotoRecord() {
        AuthInfoManager.getInstance().setAuthInfo(getApplicationContext(), authInfo,
                date);
        //添加鉴权结果回调接口(不是必须)
        AuthInfoManager.getInstance().addAuthResultListener(mCheckAuthResultListener);
        //开始向KSServer申请鉴权
        AuthInfoManager.getInstance().checkAuth();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (mMainHandler != null) {
            mMainHandler.removeCallbacksAndMessages(null);
            mMainHandler = null;
        }
        EMClient instance = EMClient.getInstance();
        instance.removeConnectionListener(myConnectionListener);
        instance = null;
        PopupMenuUtil.getInstance()._close();
        AuthInfoManager.getInstance().removeAuthResultListener(mCheckAuthResultListener);
        EventBus.getDefault().unregister(this);
    }

    private void initView1() {
        AutoUtils.auto(tab);
        View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_only_icon, null);
        AutoUtils.auto(inflate);
        imageView = (ImageView) inflate.findViewById(R.id.icon);
        navigationController = tab.custom()
                .addItem(newItem(R.drawable.m11, R.drawable.m1, "关注"))
                .addItem(newItem(R.drawable.m33, R.drawable.mm2, "世界"))
                .addItem(newOnlyItem(R.drawable.m0, R.drawable.m0))
                .addItem(newItem(R.drawable.m44, R.drawable.m3, "学生"))
                .addItem(newItem(R.drawable.m22, R.drawable.m4, "我的"))
                .build();
        if (toast == null) {
            toast = Toast.makeText(this, "再次点击退出程序", Toast.LENGTH_SHORT);
        }

    }

    public void addFragment() {
        followFragment = new FollowFragment();
        worldFragment = new WorldFragment();
        studentFragment = new StudentFragment();
        userFragment = new UserFragment();
        blankFragment = new BlankFragment();
        fragments[0] = followFragment;
        fragments[1] = worldFragment;
        fragments[2] = blankFragment;
        fragments[3] = studentFragment;
        fragments[4] = userFragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_frameLayout, fragments[0]).add(R.id.main_frameLayout, fragments[1]).add(R.id.main_frameLayout, fragments[3])
                .add(R.id.main_frameLayout, fragments[2])
                .add(R.id.main_frameLayout, fragments[4])
                .hide(blankFragment)
                .hide(worldFragment)
                .hide(studentFragment)
                .hide(userFragment)
                .show(followFragment)
                .commit();
    }

    //底部导航栏的监听事件
    private void setListener() {
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {

                //判断用户是否已经登陆，如果没有登陆跳转到登陆页面
                if (EmptyUtils.isEmpty(spUtils.getString("isLogin"))) {
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.show(fragments[index]).hide(fragments[old]).commit();
                    if (index == 0 || index == 4) {
                        navigationController.setSelect(old);
                        ActivityUtils.startActivity(MainActivity.this, LoginActivity.class);
                    }
                } else {
                    if (index != 2) {
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.show(fragments[index]).hide(fragments[old]).commit();
                        if (index == 0) {
                            EventBus.getDefault().post(new ObjKey("mr.simple"), "initData");
                        }else if (index==4){
                            EventBus.getDefault().post(new ObjKey("mr.simple"), "initMsg");
                        }

                    } else {
                        //判断用户是否已经登陆，如果没有登陆跳转到登陆页面
                        if (EmptyUtils.isNotEmpty(spUtils.getString("isLogin"))) {
                            PopupMenuUtil.getInstance()._show(MainActivity.this, imageView);
                            navigationController.setSelect(old);
                        } else {
                            navigationController.setSelect(old);
                            ActivityUtils.startActivity(MainActivity.this, LoginActivity.class);
                        }

                    }
                }

            }


            @Override
            public void onRepeat(int index) {

            }
        });

    }
    @Subscriber(tag = "isMsg", mode = ThreadMode.MAIN)
    private void isMsg(ObjKey user) {
        String key = user.getKey();
        if (key.equals("true")) {
            navigationController.setHasMessage(4,true);
        }else {
            navigationController.setHasMessage(4,false);
        }
    }
    //创建一个图片加文字的底部菜单Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(Color.BLACK);
        AutoUtils.auto(normalItemView);
        return normalItemView;
    }

    //创建只有一个图片的底部菜单Item
    private OnlyIconItemView newOnlyItem(int drawable, int checkedDrawable) {
        OnlyIconItemView onlyIconItemView = new OnlyIconItemView(this);
        onlyIconItemView.initialize(drawable, checkedDrawable);
        onlyIconItemView.setChecked(false);
        AutoUtils.auto(onlyIconItemView);
        return onlyIconItemView;
    }


    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            toast.show();
            mExitTime = System.currentTimeMillis();
        } else {
            toast.cancel();
            finish();
            //  System.exit(0);
        }
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        userFragment.onActivityResult(requestCode, resultCode, data);
    }
}
