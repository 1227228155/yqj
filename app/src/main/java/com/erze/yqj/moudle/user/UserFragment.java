package com.erze.yqj.moudle.user;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.erze.yqj.R;
import com.erze.yqj.entity.ObjKey;
import com.erze.yqj.entity.UserBean;
import com.erze.yqj.moudle.chat.ChatActivity;
import com.erze.yqj.moudle.chat.ui.EaseConversationListFragment;
import com.erze.yqj.moudle.user.collection.CollectionActivity;
import com.erze.yqj.moudle.user.followlist.FollowListActivity;
import com.erze.yqj.moudle.user.rank.RankActivity;
import com.erze.yqj.moudle.user.rank.RankVideoActivity;
import com.erze.yqj.moudle.user.setting.SettingActivity;
import com.erze.yqj.moudle.user.works.WorksActivity;
import com.erze.yqj.moudle.voice.ui.LoadingView;
import com.erze.yqj.moudle.voice.utils.PermissiUtils;
import com.erze.yqj.rxframe.base.BaseFrameFragment;
import com.erze.yqj.widget.ReboundScrollView;
import com.hyphenate.chat.EMConversation;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

import static com.erze.yqj.R.id.user_background_iv;
import static com.erze.yqj.R.id.user_video_count;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends BaseFrameFragment<UserFragmentPresenter, UserFragmentModel> implements UserFragmentContract.View {


    Unbinder unbinder;
    @BindView(user_background_iv)
    ImageView userBackgroundIv;
    @BindView(R.id.user_hot)
    ImageView userHot;
    @BindView(R.id.user_hot_text)
    TextView userHotText;
    @BindView(R.id.hot_rl)
    RelativeLayout hotRl;
    @BindView(R.id.fg_user_title_rl)
    RelativeLayout fgUserTitleRl;
    @BindView(R.id.user_ranking_text)
    TextView userRankingText;
    @BindView(R.id.user_rank_rl)
    RelativeLayout userRankRl;
    @BindView(R.id.user_follow)
    TextView userFollow;
    @BindView(R.id.user_msg)
    TextView userMsg;
    @BindView(R.id.user_msg_count)
    LinearLayout userMsgCount;
    @BindView(R.id.user_avatar)
    ImageView userAvatar;
    @BindView(R.id.user_username)
    TextView userUsername;
    @BindView(R.id.user_id_text)
    TextView userIdText;
    @BindView(R.id.user_id)
    TextView userId;
    @BindView(R.id.user_vip)
    ImageView userVip;
    @BindView(R.id.user_age)
    TextView userAge;
    @BindView(R.id.user_qm_text)
    TextView userQmText;

    SPUtils spUtils;
    @BindView(R.id.user_sex_img)
    ImageView userSexImg;
    @BindView(user_video_count)
    TextView userVideoCount;
    @BindView(R.id.user_fans)
    TextView userFans;
    @BindView(R.id.user_qm_context)
    TextView userQmContext;
    @BindView(R.id.sl)
    ReboundScrollView sl;
    private Map<String, String> map = new ArrayMap<>();
    private String username;
    private String mUnreadMsgCount;
    private PopupWindow popupWindow;
    private View popuwindowView;
    private Button pp_btn1, pp_btn2, pp_btn3;
    private Window window;
    private LoadingView loading;//加载View
    private Bitmap photo;//用户选择的头像
    private  List<EMConversation> mList = new ArrayList<>();
    private boolean share;
    EaseConversationListFragment conversationListFragment;
    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        unbinder = ButterKnife.bind(this, view);
        spUtils = SPUtils.getInstance("user");
        initPopupWindow();
        EventBus.getDefault().register(this);
        return view;
    }

    private void initPopupWindow() {
        window = getActivity().getWindow();
        loading = new LoadingView(getActivity(), R.style.CustomDialog);
        popuwindowView = LayoutInflater.from(getActivity()).inflate(R.layout.popuwindow, null);
        pp_btn1 = (Button) popuwindowView.findViewById(R.id.pp_btn1);
        pp_btn2 = (Button) popuwindowView.findViewById(R.id.pp_btn2);
        pp_btn3 = (Button) popuwindowView.findViewById(R.id.pp_btn3);
        pp_btn1.setText("修改背景封面");
        pp_btn2.setText("取消");
    }


    @Subscriber(tag = "msg", mode = ThreadMode.MAIN)
    private void updateUserAsync(ObjKey user) {
        userMsg.setText(user.getKey());
    }
    @Subscriber(tag = "initMsg", mode = ThreadMode.ASYNC)
    private void updateMsg(ObjKey user) {
        getMsgCount();
    }

    /**
     * 获取所有会话列表的未读消息
     */
    private void getMsgCount() {
        if (conversationListFragment==null){
            conversationListFragment = new EaseConversationListFragment();
        }
        conversationListFragment.refreshMsg();

    }

    private void getData() {
        map.put("user_id", spUtils.getString("userid"));
        map.put("type", "o");
        mPresenter.getUserData(map);
    }



    @Override
    public void onStart() {
        super.onStart();
        initData1();
        getData();
        getMsgCount();
    }

    private void initData1() {
        if (EmptyUtils.isNotEmpty(spUtils.getString("userbg"))){
            Glide.with(getContext()).load(spUtils.getString("userbg")).into(userBackgroundIv);

        }
        username = spUtils.getString("username");
        userUsername.setText(username);
        userId.setText(spUtils.getString("userid"));
        if (EmptyUtils.isNotEmpty(spUtils.getString("useravatar"))) {
            Glide.with(this).load(spUtils.getString("useravatar"))
                    .into(userAvatar);
        } else {
            userAvatar.setImageResource(R.drawable.null_avatar);
        }
        if (spUtils.getString("usersex").equals("男")) {
            userSexImg.setImageResource(R.drawable.sex_nan);
        } else {
            userSexImg.setImageResource(R.drawable.sex_nv);
        }
        if (spUtils.getString("uservip").equals("n")) {
            userVip.setVisibility(View.GONE);
        } else {
            userVip.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.user_msg, R.id.gotoShare, R.id.user_setting_rl, R.id.hot_rl, R.id.user_rank_rl, R.id.user_data_arrow, R.id.user_follow
            , R.id.go_collectionInfo, R.id.user_video_count, user_background_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_msg:
                ActivityUtils.startActivity(getActivity(), ChatActivity.class);
                break;
            case R.id.gotoShare:
                initShare();
                break;
            case R.id.user_setting_rl:
                ActivityUtils.startActivity(getActivity(), SettingActivity.class);
                break;
            case R.id.user_data_arrow:
                ActivityUtils.startActivity(getActivity(), UpdateUserDataActivity.class);
                break;
            case R.id.user_follow:
                ActivityUtils.startActivity(getActivity(), FollowListActivity.class);
                break;
            case R.id.go_collectionInfo:
                ActivityUtils.startActivity(getActivity(), CollectionActivity.class);
                break;
            case R.id.user_video_count:
                ActivityUtils.startActivity(getActivity(), WorksActivity.class);
                break;
            case R.id.hot_rl:
                ActivityUtils.startActivity(getActivity(), RankVideoActivity.class);
                break;
            case R.id.user_rank_rl:
                ActivityUtils.startActivity(getActivity(), RankActivity.class);
                break;
            case R.id.user_background_iv:
                selectBackgroundImage();
                break;
        }
    }



    @PermissionSuccess(requestCode = 123)
    public void openContact() {
        //  initData();

    }

    @PermissionFail(requestCode = 123)
    public void failContact() {
        PermissiUtils.openSettingActivity(getActivity());
    }

    private void initShare() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(getActivity(), mPermissionList, 123);
        }
        UMWeb web = new UMWeb("http://www.yiqijun.com/");
        web.setTitle("艺气君");//标题
        //   web.setThumb(thumb);  //缩略图
        web.setDescription("哈哈哈哈哈");//描述
        new ShareAction(getActivity()).withMedia(web).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(shareListener).open();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(), "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), "取消了", Toast.LENGTH_LONG).show();

        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(getActivity()).release();
    }

    @Override
    public void showErrorLog() {
    }

    @Override
    public void getUserDataBean(UserBean bean) {
        String user_signature = bean.getResult().getUser_info().getUser_signature();
        byte[] bytes = EncodeUtils.base64Decode(user_signature);
        userQmContext.setText(new String(bytes));
        userFollow.setText(bean.getResult().getAtten_count());
        userVideoCount.setText(bean.getResult().getVideo_count());
        userFans.setText(bean.getResult().getAtten_user_count());
        userAge.setText(bean.getResult().getUser_info().getUser_age());
        if (EmptyUtils.isNotEmpty(spUtils.getString("userbg"))){
            Glide.with(this).load(spUtils.getString("userbg")).into(userBackgroundIv);
        }else {
            Glide.with(this).load(bean.getResult().getUser_info().getUser_back_wall()).into(userBackgroundIv);
            spUtils.put("userbg",bean.getResult().getUser_info().getUser_back_wall());
        }
    }

    private void selectBackgroundImage() {
        pp_btn2.setVisibility(View.GONE);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;
        window.setAttributes(lp);
        popupWindow = new PopupWindow(popuwindowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(sl, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //popupwindow消失的时候恢复成原来的透明度
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = 1f;
                window.setAttributes(lp);
            }
        });
        pp_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                //popupwindow消失的时候恢复成原来的透明度
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = 1f;
                window.setAttributes(lp);
            }
        });
        pp_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                ActivityUtils.startActivity(getActivity(),SelectBgPhotoActivity.class);
            }
        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }


}
