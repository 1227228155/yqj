package com.erze.yqj.moudle.follow;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.erze.yqj.R;
import com.erze.yqj.entity.CodeBean;
import com.erze.yqj.entity.UserDataBean;
import com.erze.yqj.moudle.chat.EaseChatActivity;
import com.erze.yqj.moudle.comment.listview.PullUpLoadMoreListView;
import com.erze.yqj.moudle.follow.adapter.OtherFollowAdapter;
import com.erze.yqj.moudle.follow.add.AddFriendActivity;
import com.erze.yqj.rxframe.base.BaseFrameActivity;
import com.erze.yqj.utils.AutoUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class OtherFollowActivity extends BaseFrameActivity<OtherFollowPresenter, OtherFollowModel> implements OtherFollowContract.View {
    @BindView(R.id.user_username)
    TextView userUsername;
    @BindView(R.id.user_id_text)
    TextView userIdText;
    @BindView(R.id.user_id)
    TextView userId;
    @BindView(R.id.user_vip)
    ImageView userVip;
    @BindView(R.id.other_see_follow_text)
    TextView otherSeeFollowText;
    @BindView(R.id.other_see_follow)
    ImageView otherSeeFollow;
    String user_nickname;
    @BindView(R.id.label_ll)
    LinearLayout labelLl;
    @BindView(R.id.listView)
    PullUpLoadMoreListView listView;
    @BindView(R.id.user_avatar)
    CircleImageView userAvatar;
    @BindView(R.id.other_see_image)
    ImageView otherSeeImage;
    private String userID;
    private Map<String, String> map = new ArrayMap<>();//获取用户信息的集合
    private Map<String, String> map1 = new ArrayMap<>();//获取关注和取消关注的集合
    private SPUtils spUtils;
    private boolean isFollow = false; //判读是否已经关注过
    private String hx_number;//环信聊天的ID
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_see);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        AutoUtils.auto(this);
        ButterKnife.bind(this);
        //获取用户信息
        spUtils = SPUtils.getInstance("user");
        userID = getIntent().getAction();
        map.put("user_id", spUtils.getString("userid"));
        map.put("id", userID);
        map.put("type", "p");
        map.put("page", "1");
        mPresenter.getUserData(map);
        listView.setFocusable(false);
    }


    @Override
    public void showErrorLog() {
        Toast.makeText(this, "获取信息失败，请检查网络连接", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Glide.with(this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502855864057&di=7767be51b1346df24eba506fdf9290d4&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F14%2F15%2F35%2F87V58PICsXS_1024.jpg")
                .into(otherSeeImage);
    }

    @Override
    public void getUserData(UserDataBean bean) {
        user_nickname = bean.getResult().getUser_info().getUser_nickname();
        hx_number = bean.getResult().getUser_info().getUser_id_number();
        userId.setText(userID);
        userUsername.setText(user_nickname);
        Glide.with(this).load(bean.getResult().getUser_info().getUser_pic()).into(userAvatar);
        if (bean.getResult().getAtten_status().equals("y")) {
            otherSeeFollowText.setText("已关注");
            otherSeeFollow.setImageResource(R.mipmap.gz);
            isFollow = true;
        } else {
            otherSeeFollowText.setText("关注");
            otherSeeFollow.setImageResource(R.drawable.other_see_follow);
            isFollow = false;
        }
        LogUtils.e("label", bean.getResult().getUser_info().getUser_label());
        //动态添加标签
        String[] split = bean.getResult().getUser_info().getUser_label().split(",");
        for (int i = 0; i < split.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(split[i]);
            textView.setTextSize(16);
            Resources resource = getBaseContext().getResources();
            ColorStateList csl = resource.getColorStateList(R.color.dark2_gray);
            if (csl != null) {
                textView.setTextColor(csl);
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(30, 0, 0, 0);
            textView.setLayoutParams(lp);
            labelLl.addView(textView);
        }
        //获取作品列表、
        initVideo(bean);

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
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
        listView.setLayoutParams(params);
    }

    private void initVideo(UserDataBean bean) {
        if (bean.getResult().getResource_list() != null) {
            OtherFollowAdapter adapter = new OtherFollowAdapter(this);
            listView.setAdapter(adapter);
            setListViewHeightBasedOnChildren(listView);
        } else {
            OtherFollowAdapter adapter = new OtherFollowAdapter(this);
            listView.setAdapter(adapter);
            setListViewHeightBasedOnChildren(listView);
        }
    }

    @Override
    public void getFollowStatus(CodeBean bean) {
        if (isFollow) {
            otherSeeFollowText.setText("关注");
            isFollow = false;
            otherSeeFollow.setImageResource(R.drawable.other_see_follow);
            Toast.makeText(this, "取消关注", Toast.LENGTH_SHORT).show();
        } else {
            otherSeeFollowText.setText("已关注");
            isFollow = true;
            otherSeeFollow.setImageResource(R.mipmap.gz);
            Toast.makeText(this, "关注成功", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.rl_follow)
    public void onViewClicked() {
        if (isFollow) {
            map1.clear();
            map1.put("user_id", spUtils.getString("userid"));
            map1.put("atten_user_id", userID);
            map1.put("type", "n");
            mPresenter.getFollowStatus(map1);
        } else {
            map1.clear();
            map1.put("user_id", spUtils.getString("userid"));
            map1.put("atten_user_id", userID);
            map1.put("type", "y");
            mPresenter.getFollowStatus(map1);
        }
    }

    @OnClick({R.id.other_see_back, R.id.rl_send_msg,R.id.rl_add_friend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.other_see_back:
                break;
            case R.id.rl_add_friend:
                Intent intent = new Intent(this,  AddFriendActivity.class);
                intent.putExtra("username", user_nickname);
                intent.putExtra("hxid", hx_number);
                startActivity(intent);
                break;
            case R.id.rl_send_msg:
                Intent intent1 = new Intent(this, EaseChatActivity.class);
                intent1.putExtra("username", user_nickname);
                intent1.putExtra("hxid", hx_number);
                startActivity(intent1);
                break;
        }
    }
}
