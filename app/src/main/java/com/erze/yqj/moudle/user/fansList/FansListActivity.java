package com.erze.yqj.moudle.user.fansList;

import android.os.Bundle;
import android.util.ArrayMap;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.R;
import com.erze.yqj.entity.FansListBean;
import com.erze.yqj.moudle.user.followlist.adapter.FollowListAdapter;
import com.erze.yqj.rxframe.base.BaseFrameActivity;
import com.erze.yqj.utils.AutoUtils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FansListActivity extends BaseFrameActivity<FansListPresenter, FansListModel> implements FansListContract.View {
    @BindView(R.id.fansListView)
    ListView fansListView;
    @BindView(R.id.common_title)
    TextView commonTitle;
    private SPUtils spUtils;
    private Map<String, String> map = new ArrayMap<>();
    private int mCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans_list);
        AutoUtils.auto(this);
        ButterKnife.bind(this);
        commonTitle.setText("我的粉丝");
        spUtils = SPUtils.getInstance("user");
        map.put("user_id", spUtils.getString("userid"));
        map.put("type", "o");
        map.put("page", String.valueOf(mCount));
        mPresenter.getFansBean(map);
    }

    @Override
    public void getFansBean(FansListBean bean) {
        List<FansListBean.ResultBean.ListBean> list = bean.getResult().getList();
        FollowListAdapter adapter = new FollowListAdapter(this, list, "fans");
        fansListView.setAdapter(adapter);

    }

    @OnClick(R.id.common_back)
    public void onViewClicked() {
        finish();
    }
}
