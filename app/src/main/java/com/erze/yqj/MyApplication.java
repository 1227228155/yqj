package com.erze.yqj;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.blankj.utilcode.util.Utils;
import com.erze.yqj.moudle.voice.Tool.Function.CommonFunction;
import com.erze.yqj.moudle.voice.Tool.Function.InitFunction;
import com.github.moduth.blockcanary.BlockCanary;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.mob.MobApplication;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.utils.Log;
import com.weavey.loading.lib.LoadingLayout;

import java.util.Iterator;
import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by 1227228155@qq.com on 2017/6/23.
 */

public class MyApplication extends MobApplication {
    private boolean initialised;
    private boolean initialisedInUiThread;

    private Toast messageToast;
    private Toast longMessageToast;

    private static MyApplication myApplication = null;
    //private List<Activity> mList = new LinkedList<>();
    public static MyApplication getInstance() {
        if (myApplication == null) {
            synchronized (MyApplication.class) {
                if (myApplication == null) {
                    myApplication = new MyApplication();
                }
            }
        }
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
     //   initRefreshLayout();
        LeakCanary.install(this);
        // Normal app init code...
        //工具类初始化
        Utils.init(this);
        //环信EaseUI初始化
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        options.setAutoLogin(false);//设置为不能自动登录
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(this.getPackageName())) {
            Log.e(TAG, "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EaseUI.getInstance().init(this, options);
        //配置友盟分享 微信 qq 微博
        Config.DEBUG = true;
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wx97d469ca92fc550d","c2f13287145d8dafd80f4bb83319c978");
        PlatformConfig.setQQZone("1105864751","JdsE0P65mWu4VW2h");
        PlatformConfig.setSinaWeibo("4134614834", "2424210dc2454ea4069cc60475ca1763","http://sns.whalecloud.com/sina2/callback");

        if (CommonFunction.isEmpty(CommonFunction.GetPackageName())) {
            // 百度定位sdk定位服务或者类似启动remote service的第三方库运行在一个单独的进程
            // 每次定位服务启动的时候，都会调用application::onCreate,创建新的进程
            // 这个特殊处理是，如果从pid找不到对应的processInfo,processName，
            // 则此application::onCreate是被service调用的，直接返回
            return;
        }

        initialised = false;
        initialisedInUiThread = false;
        initRefreshLayout();
        initLoadingLayout();
    }

    private void initLoadingLayout() {
        LoadingLayout.getConfig()
                .setErrorText("出错啦~请稍后重试！")
                .setEmptyText("暂无数据")
                .setNoNetworkText("无网络连接，请检查您的网络···")
                .setErrorImage(R.mipmap.error)
                .setEmptyImage(R.mipmap.empty)
                .setNoNetworkImage(R.mipmap.no_network)
                .setAllTipTextColor(R.color.gray)
                .setAllTipTextSize(14)
                .setReloadButtonText("点我重试哦")
                .setReloadButtonTextSize(14)
                .setReloadButtonTextColor(R.color.gray)
                .setReloadButtonWidthAndHeight(150,40);
    }

    public void initRefreshLayout() {
      //static 代码段可以防止内存泄露
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
                @Override
                public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                    layout.setPrimaryColorsId(R.color.transparent2, android.R.color.white);//全局设置主题颜色
                    return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
                }
            });
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
                @Override
                public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                    //指定为经典Footer，默认是 BallPulseFooter
                    return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
                }
            });

    }
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    public synchronized void initialise() {
        if (!initialised) {
            initialised = true;
            InitFunction.Initialise(this);
        }
    }

}
