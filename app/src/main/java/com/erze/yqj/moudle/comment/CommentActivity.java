package com.erze.yqj.moudle.comment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.ArrayMap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.erze.yqj.R;
import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.Code;
import com.erze.yqj.entity.CodeBean;
import com.erze.yqj.entity.CommentBean;
import com.erze.yqj.entity.SendComment;
import com.erze.yqj.entity.SendReply;
import com.erze.yqj.entity.Videobean;
import com.erze.yqj.entity.WorldBean;
import com.erze.yqj.moudle.comment.adapter.CommentAdapter;
import com.erze.yqj.moudle.report.ReportActivity;
import com.erze.yqj.rxframe.base.BaseFrameActivity;
import com.erze.yqj.rxframe.rx.Rxschedulers;
import com.erze.yqj.utils.AutoUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class CommentActivity extends BaseFrameActivity<CommentPresneter, CommentModel> implements CommentContract.View,PopupMenu.OnMenuItemClickListener {
    @BindView(R.id.common_back)
    ImageView commonBack;
    @BindView(R.id.common_title)
    TextView commonTitle;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.username)
    TextView username1;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.comment_dz)
    ImageView commentDz;
    @BindView(R.id.comment_zf)
    ImageView commentZf;
    @BindView(R.id.comment_pl)
    ImageView commentPl;
    @BindView(R.id.comment_listView)
    ListView commentListView;
    @BindView(R.id.send_msg_rl)
    RelativeLayout sendMsgRl;
    @BindView(R.id.editText)
    TextEditTextView editText;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.bottom_rl)
    RelativeLayout bottomRl;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.more)
    ImageView more;
    private String video_url, pic_url, title, video_id,username,userid;
    @BindView(R.id.comment_video)
    JCVideoPlayerStandard commentVideo;
    Videobean.ResultBean video_list;//推荐页面跳转过来的数据
    WorldBean.ResultBean.VideoListBean videoList;//世界页面跳转过来的数据
    Map<String, String> commentMap = new ArrayMap<>();
    Map<String, String> sendCommentMap = new ArrayMap<>();
    String comment;//评论的内容
    SPUtils spUtils = SPUtils.getInstance("user");
    private View popuwindowView;
    private PopupWindow popupWindow;
    private Button pp_btn1, pp_btn2, pp_btn3;
    private boolean isCommentOrReply = true;//判断用户发送的是评论还是回复  true为评论
    private CommentBean.ResultBean result;//获取评论列表的数据
    private int selector;//用户选择回复的position
    private CommentAdapter adapter;
    private int count;//消息列表总的数量，用来判断是否请求更多的数据
    private int page = 1;//当前请求的数据是第一页
    private Map<String, String> map = new ArrayMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        AutoUtils.auto(this);
        ButterKnife.bind(this);
        initPopupWindow();
        getIntent1();
        setUpVideoPlayer();
        setUpTitle();
        initListener1();
        addWatch();//添加观看次数
    }

    private void setUpTitle() {
        commonTitle.setText("动态详情");
        more.setVisibility(View.VISIBLE);
    }

    private void setUpVideoPlayer() {
        commentVideo.setUp(video_url, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, title);
        commentVideo.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this).load(pic_url).into(commentVideo.thumbImageView);
    }

    private void getIntent1() {
        video_url = getIntent().getStringExtra("video_url");
        pic_url = getIntent().getStringExtra("pic_url");
        title = getIntent().getStringExtra("title");
        video_id = getIntent().getStringExtra("video_id");
        username = getIntent().getStringExtra("username");
        userid = getIntent().getStringExtra("userid");
    }

    private void addWatch() {
        Map<String, String> map = new ArrayMap<>();
        map.put("video_id", video_id);
        map.put("type", "w");
        NetDao.getInstance().getCommonApi().getVideoTimes(map).compose(Rxschedulers.<Code>io_main());
    }

    private void initPopupWindow() {
        username1.setText(username);
        popuwindowView = LayoutInflater.from(this).inflate(R.layout.popuwindow, null);
        pp_btn1 = (Button) popuwindowView.findViewById(R.id.pp_btn1);
        pp_btn2 = (Button) popuwindowView.findViewById(R.id.pp_btn2);
        pp_btn3 = (Button) popuwindowView.findViewById(R.id.pp_btn3);
        pp_btn1.setText("回复");
    }

    private void initListener1() {
        //editText监听事件
        editText.setView(bottomRl, sendMsgRl);
        scrollView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //手指按下调用一次
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //手指按下移动式会一直调用
                        //如果在这里判断是否到达底部，，会一直加载数据，这样就会卡死
                        break;
                    case MotionEvent.ACTION_UP:
                        //手指离开，表示上拉一次，就调用一次
                        // 这样能保证每次手指点击只能调用一次
                        int scrollY = v.getScrollY();
                        int height = v.getHeight();
                        int scrollViewMeasuredHeight = scrollView.getChildAt(0).getMeasuredHeight();//获得ScrollView的真实高度
                        if (scrollY == 0) {

                        }
                        if ((scrollY + height) == scrollViewMeasuredHeight) {
                            // 到达底部，刷新数据
                            refreshData();
                        }
                        break;

                }
                return false;
            }
        });
        commentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //popupWindow 监听事件
                selector = position;
                setOnPopupWindowListener();
                return false;
            }
        });

    }

    /**
     * 下拉刷新数据
     */
    private void refreshData() {
        if (count > page * 10) {
            page++;
            Toast.makeText(this, "刷新数据", Toast.LENGTH_SHORT).show();
            initComment(page);
        }
    }

    //测量ListView的高度
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        listView.setLayoutParams(params);
    }


    private void setOnPopupWindowListener() {
        final Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;
        window.setAttributes(lp);
        popupWindow = new PopupWindow(popuwindowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(commentListView, Gravity.BOTTOM, 0, 0);
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
            }
        });
        pp_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCommentOrReply = false;//当前是回复评论状态
                ejectKeyboard();  //回复评论，弹起键盘
                popupWindow.dismiss();

            }
        });
        pp_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        commentVideo.startButton.performClick();
        initComment(1);//加载第一页数据

    }

    /**
     * 请求评论列表
     */
    private void initComment(int count) {
        commentMap.clear();
        commentMap.put("page", String.valueOf(count));
        commentMap.put("type", "c");
        commentMap.put("video_id", video_id);
        mPresenter.getComment(commentMap);

    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @OnClick({R.id.comment_bottom_dz, R.id.comment_bottom_zf, R.id.comment_bottom_pl, R.id.send_comment, R.id.common_back,
            R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.more:
                initMenu(view);
                break;
            case R.id.common_back:
                finish();
                break;
            case R.id.comment_bottom_dz:
                break;
            case R.id.comment_bottom_zf:
                initListener2();
                break;
            case R.id.comment_bottom_pl:
                ejectKeyboard();
                break;
            case R.id.send_comment:
                if (isCommentOrReply) {
                    bottomRl.setVisibility(View.VISIBLE);
                    sendComment();
                } else {
                    bottomRl.setVisibility(View.VISIBLE);
                    sendReply();
                }
                break;
        }
    }

    private void initMenu(View view) {
        PopupMenu popup = new PopupMenu(this,view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.more, popup.getMenu());
      //  setIconEnable(popup.getMenu(),true);
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

 /*   *//**
     * 利用反射
     * 设置PopupMenu菜单的图标显示
     * @param menu
     * @param enable
     *//*
    private void setIconEnable(Menu menu, boolean enable)
    {
        try
        {
            Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            m.setAccessible(true);
            //传入参数
            m.invoke(menu, enable);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }*/
    /**
     * 弹出键盘
     */
    private void ejectKeyboard() {
        sendMsgRl.setVisibility(View.VISIBLE);
        bottomRl.setVisibility(View.GONE);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    /**
     * 发送评论
     */
    private void sendComment() {
        comment = editText.getText().toString().trim();
        sendCommentMap.clear();
        sendCommentMap.put("type", "c");
        sendCommentMap.put("rid", video_id);
        sendCommentMap.put("uid", spUtils.getString("userid"));
        sendCommentMap.put("content", comment);
        if (EmptyUtils.isNotEmpty(comment)) {
            mPresenter.sendComment(sendCommentMap);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            sendMsgRl.setVisibility(View.GONE);
        }
    }

    /**
     * 发送回复
     */
    private void sendReply() {
        comment = editText.getText().toString().trim();
        sendCommentMap.clear();
        sendCommentMap.put("type", "r");
        sendCommentMap.put("uid", spUtils.getString("userid"));
        sendCommentMap.put("user_id", result.getList().get(selector).getUser_id());
        LogUtils.e("user_id", result.getList().get(selector).getUser_id());
        sendCommentMap.put("comm_id", result.getList().get(selector).getComments_id());
        LogUtils.e("comm_id", result.getList().get(selector).getComments_id());
        sendCommentMap.put("content", comment);
        if (EmptyUtils.isNotEmpty(comment)) {
            mPresenter.sendReply(sendCommentMap);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            sendMsgRl.setVisibility(View.GONE);
            isCommentOrReply = true;
        }
    }

    @Override
    public void sendComment(SendComment sendComment) {
        if (sendComment.getCode().equals("200")) {
            Toast.makeText(this, "评论成功", Toast.LENGTH_SHORT).show();
            initComment(1);
            editText.setText("");
        }
    }

    @Override
    public void sendReply(SendReply sendComment) {
        if (sendComment.getCode().equals("200")) {
            Toast.makeText(this, "评论成功", Toast.LENGTH_SHORT).show();
            initComment(1);
            editText.setText("");
        }
    }

    private void initListener2() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
        UMWeb web = new UMWeb("http://www.yiqijun.com/");
        web.setTitle("艺气君");//标题
        //   web.setThumb(thumb);  //缩略图
        web.setDescription("哈哈哈哈哈");//描述
        new ShareAction(this).withMedia(web).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(shareListener).open();
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
            Toast.makeText(CommentActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(CommentActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(CommentActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    public void getCommentBean(CommentBean bean) {
        result = bean.getResult();
        count = Integer.parseInt(result.getComm_count());
        if (page == 1) {
            adapter = new CommentAdapter(this, result);
            commentListView.setAdapter(adapter);
            setListViewHeightBasedOnChildren(commentListView);
        } else {
            adapter.addAllData(result);
            commentListView.setAdapter(adapter);
            setListViewHeightBasedOnChildren(commentListView);
        }
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                sendCollection();
                return true;
            case R.id.report_item:
                  sendReport();
                return true;
            case R.id.follow_item:
                sendFollow();
                return true;
            default:
                return false;
        }
    }

    private void sendCollection() {
        map.clear();
        map.put("user_id", spUtils.getString("userid"));
        map.put("video_id",video_id);
        map.put("type", "y");
        NetDao.getInstance().getCommonApi().getCollection(map).compose(Rxschedulers.<Code>io_main())
                .subscribe(new Consumer<Code>() {
                    @Override
                    public void accept(@NonNull Code code) throws Exception {
                        LogUtils.e("code",code.getCode());
                        if (code.getCode().equals("2014")) {
                            Toast.makeText(CommentActivity.this, "已经收藏过了", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CommentActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }

    private void sendReport() {
        Intent intent = new Intent( CommentActivity.this, ReportActivity.class);
        intent.putExtra("video_url",  video_url);
        intent.putExtra("pic_url",  pic_url);
        intent.putExtra("title",  title);
        intent.putExtra("video_id", video_id);
        intent.putExtra("username", username);
        intent.putExtra("userid", userid);
        startActivity(intent);
    }

    private void sendFollow() {
        map.clear();
        map.put("user_id", spUtils.getString("userid"));
        map.put("atten_user_id",userid);
        map.put("type", "y");
        NetDao.getInstance().getCommonApi().getFollowStatus(map).compose(Rxschedulers.<CodeBean>io_main())
                .subscribe(new Consumer<CodeBean>() {
                    @Override
                    public void accept(@NonNull CodeBean bean) throws Exception {
                        if (bean.getCode().equals("200")){
                            Toast.makeText(CommentActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(CommentActivity.this, "已经关注过啦", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }
}
