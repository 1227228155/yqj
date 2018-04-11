package com.erze.yqj.moudle.world;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erze.yqj.R;
import com.erze.yqj.entity.WorldBean;
import com.erze.yqj.moudle.world.adapter.TabAdapter;
import com.erze.yqj.moudle.world.recommend.TabRecommendFragment;
import com.erze.yqj.moudle.world.voice.TabVoiceFragment;
import com.erze.yqj.moudle.world.world.TabWorldFragment;
import com.erze.yqj.rxframe.base.BaseFrameFragment;
import com.weavey.loading.lib.LoadingLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorldFragment extends BaseFrameFragment<WorldPresenter, WorldModel> implements WorldContract.View {
    @BindView(R.id.loading_layout)
    LoadingLayout loading;
    TabLayout tabLayout;
    ViewPager viewPager;
    Unbinder unbinder;
    private List<Fragment> mFragment = new ArrayList<>();
    private List<String> mTitle = new ArrayList<>();
    private Map<String, String> map = new ArrayMap<>();
    private WorldBean worldBean;
    TabAdapter adapter;

    public WorldFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_world, container, false);
        tabLayout = (TabLayout) inflate.findViewById(R.id.world_tab);
        viewPager = (ViewPager) inflate.findViewById(R.id.world_viewPager);

        //   LogUtils.e("SHA1",  AppUtils.getAppSignatureSHA1()); 获取SHA1签名
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initLoading();
        initData1();
    }

    private void initData1() {
        map.clear();
        map.put("param", "recommend");
        map.put("page", "1");
        mPresenter.getRecommendData(map);
    }

    private void initLoading() {
        loading.setLoadingPage(R.layout.define_loading_page)
                .setReloadButtonText("点我重新加载哦")
                .setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                initData1();
            }
        });
        loading.setStatus(LoadingLayout.Loading);
    }
    private void initView1() {
        if (worldBean != null) {
            mFragment.add(new TabRecommendFragment(worldBean));
        } else {
            mFragment.add(new TabRecommendFragment());
        }
        mFragment.add(new TabWorldFragment());
        mFragment.add(new TabVoiceFragment());
        mTitle.add("推荐");
        mTitle.add("世界");
        mTitle.add("Voice");
        adapter = new TabAdapter(getActivity().getSupportFragmentManager(), mFragment, mTitle);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        //为TabLayout设置ViewPager
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void showErrorLog() {
        loading.setStatus(LoadingLayout.Error);
    }

    @Override
    public void showErrorNet() {
        loading.setStatus(LoadingLayout.No_Network);
    }

    @Override
    public void getRecommendData(WorldBean worldBean1) {
        this.worldBean = worldBean1;
        loading.setStatus(LoadingLayout.Success);
    }

    @Override
    public void showData() {
        initView1();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
