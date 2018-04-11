package com.erze.yqj.moudle.follow;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.R;
import com.erze.yqj.entity.FollowBean;
import com.erze.yqj.entity.ObjKey;
import com.erze.yqj.moudle.comment.listview.PullUpLoadMoreListView;
import com.erze.yqj.moudle.follow.adapter.FollowAdapter;
import com.erze.yqj.rxframe.base.BaseFrameFragment;
import com.erze.yqj.utils.AutoUtils;
import com.umeng.socialize.UMShareAPI;
import com.weavey.loading.lib.LoadingLayout;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowFragment extends BaseFrameFragment<FollowPresenter, FollowModel> implements FollowContract.View {
    @BindView(R.id.follow_listView)
    PullUpLoadMoreListView followListView;
    Unbinder unbinder;
    List<FollowBean.ResultBean> resultBeanList;
    SPUtils spUtils;
    Map<String, String> map = new ArrayMap<>();
    @BindView(R.id.loading_layout)
    LoadingLayout loading;
    private int page = 1;
    FollowAdapter adapter;
    private boolean update = false;//取消关注，添加关注之后 刷新列表数据
    private boolean initAdapter = false;//切换账号之后 刷新用户数据
    public FollowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_follow, container, false);
        AutoUtils.auto(getActivity());
        unbinder = ButterKnife.bind(this, inflate);
        spUtils = SPUtils.getInstance("user");
        initListener1();
        EventBus.getDefault().register(this);
        initData1(1);
        return inflate;
    }
    @Subscriber(tag = "initData", mode = ThreadMode.MAIN)
    private void updateUserAsync(ObjKey user) {
        LogUtils.e("initData","--------");
        if (user.getKey().equals("initAdapter")){
            initAdapter = true;
        }
        initData1(1);
        update = true;
        page = 1;
    }

    private void initData1(int page) {
        spUtils = SPUtils.getInstance("user");
        map.clear();
        map.put("id", spUtils.getString("userid"));
        map.put("page", String.valueOf(page));
        LogUtils.e("initData",spUtils.getString("userid"));
        mPresenter.getFollow(map);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initListener1() {
        followListView.setOnLoadMoreListener(new PullUpLoadMoreListView.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                page++;
                initData1(page);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        UMShareAPI.get(getActivity()).release();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void getFollowBean(FollowBean bean) {
        loading.setStatus(LoadingLayout.Success);
        if (page > 1) {
            adapter.addAdapter(bean.getResult());
            followListView.setLoadState(false);
        } else {
            if (update) {
                if (initAdapter){
                    initAdapter(bean);
                    initAdapter = false;
                }else {
                    adapter.initAdapter(bean.getResult());
                    update = false;
                }

            } else {
                initAdapter(bean);
            }

        }
    }

    private void initAdapter(FollowBean bean) {
        Window window = getActivity().getWindow();
        resultBeanList = bean.getResult();
        adapter = new FollowAdapter(getActivity(), resultBeanList, window, "关注", followListView);
        followListView.setAdapter(adapter);
    }

    @Override
    public void showErrorLog() {
        loading.setStatus(LoadingLayout.Error);
    }

    @Override
    public void showEmptyLog() {
        if (page==1){
            adapter.deleteAdapter();
            loading.setStatus(LoadingLayout.Empty);
            LogUtils.e("showEmptyLog","showEmptyLog");
        }else {
            followListView.setLoadState(false);
        }
    }
}
