package com.erze.yqj.moudle.user.followlist;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.ListView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.R;
import com.erze.yqj.entity.FansListBean;
import com.erze.yqj.moudle.user.fansList.FansListActivity;
import com.erze.yqj.moudle.user.followlist.adapter.FollowListAdapter;
import com.erze.yqj.rxframe.base.BaseFrameActivity;
import com.erze.yqj.utils.AutoUtils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FollowListActivity extends BaseFrameActivity<FollowListPresenter, FollowListModel> implements FollowListContract.View {


    @BindView(R.id.follow_listView)
    ListView followListView;
    SPUtils spUtils;
    Map<String, String> map = new ArrayMap<>();
    List<FansListBean.ResultBean.ListBean> list;
    FollowListAdapter adapter;
    private int count = 1;
    private int position;//要删除item的id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_list);
        AutoUtils.auto(this);
        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        spUtils = SPUtils.getInstance("user");
        map.put("user_id", spUtils.getString("userid"));
        map.put("type", "m");
        map.put("page", String.valueOf(count));
        mPresenter.getFollowList(map);
    }

    @Override
    public void getFollowList(FansListBean bean) {
        list = bean.getResult().getList();
        LogUtils.e("list", list.toString());
        adapter = new FollowListAdapter(this, list, "follow");
        followListView.setAdapter(adapter);
    }

    @Override
    public void showErrorLog() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.back, R.id.go_fansList})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.go_fansList:
                ActivityUtils.startActivity(this, FansListActivity.class);
                break;
        }
    }
}
