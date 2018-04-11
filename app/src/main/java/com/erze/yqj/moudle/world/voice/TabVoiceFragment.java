package com.erze.yqj.moudle.world.voice;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erze.yqj.R;
import com.erze.yqj.entity.Videobean;
import com.erze.yqj.moudle.world.voice.adapter.TabVoiceAdapter;
import com.erze.yqj.rxframe.base.BaseFrameFragment;
import com.erze.yqj.utils.SpaceItemDecoration;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 1227228155@qq.com on 2017/6/28.
 */

public class TabVoiceFragment extends BaseFrameFragment<TabVoicePresenter, TabVoiceModel> implements TabVoiceContract.View {
    @BindView(R.id.recyclerView)
    PullLoadMoreRecyclerView recyclerView;
    Unbinder unbinder;
    private int page = 1;
    private Map<String, String> map = new ArrayMap<>();
    private boolean update = false;
    TabVoiceAdapter adapter;
    public TabVoiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_voice, container, false);
        unbinder = ButterKnife.bind(this, view);
        getData(page);
        initListener1();
        return view;
    }


    private void initListener1() {
        recyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                update = true;
                page=1;
                getData(1);
            }

            @Override
            public void onLoadMore() {
                  page++;
                 getData(page);
            }
        });
    }

    private void getData(int page) {
        map.clear();
        map.put("param", "voice");
        map.put("page", String.valueOf(page));
        mPresenter.getVideo(map);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showErrorLog() {
        recyclerView.setPullLoadMoreCompleted();
    }

    @Override
    public void getVideo(Videobean videobean) {
        List<Videobean.ResultBean> beanList = videobean.getResult();
        if (page>1){
            adapter.addAdapter(beanList);
        }else {
            if (update){
                adapter.initAdapter(beanList);
                update = false;
            }else {
                adapter = new TabVoiceAdapter(getActivity(), beanList,getActivity().getWindow(),recyclerView);
                //3.为recyclerView设置布局管理器
                recyclerView.setStaggeredGridLayout(2);
                recyclerView.addItemDecoration(new SpaceItemDecoration(20));
                recyclerView.setAdapter(adapter);
            }
        }
        recyclerView.setPullLoadMoreCompleted();
    }
}
