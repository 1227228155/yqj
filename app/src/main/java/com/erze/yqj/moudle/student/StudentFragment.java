package com.erze.yqj.moudle.student;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.erze.yqj.GlideImageLoader;
import com.erze.yqj.R;
import com.erze.yqj.utils.AutoUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFragment extends Fragment {
    List<String> strings = new ArrayList<>();
    List<String> strings2 = new ArrayList<>();
    List<Integer> bitmaps = new ArrayList<>();
    List<Integer> bitmapList = new ArrayList<>();
    @BindView(R.id.student_gridView)
    GridView studentGridView;
    Unbinder unbinder;
    @BindView(R.id.student_banner)
    Banner banner;

    public StudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmet_student, container, false);
        AutoUtils.auto(banner);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
        initBanner();
    }

    private void initBanner() {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(bitmapList);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.ForegroundToBackground);
        //设置标题集合（当banner样式有显示title时）
        //   banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        //  banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    private void initView() {
        StudentAdapter adapter = new StudentAdapter(getActivity(), strings, strings2, bitmaps);
        studentGridView.setAdapter(adapter);
    }

    private void initData() {
        strings.add("高考真题");
        strings.add("院校查询");
        strings.add("艺考待查");
        strings.add("艺考真题");
        strings.add("手动查询");
        strings.add("艺考咨询");
        strings2.add("高考历年真题通道");
        strings2.add("700所学校任你选择");
        strings2.add("快速查询艺考成绩");
        strings2.add("艺考历年真题通道");
        strings2.add("手动查询艺考成绩");
        strings2.add("不明白的来点我");
        bitmaps.add(R.mipmap.s1);
        bitmaps.add(R.mipmap.s2);
        bitmaps.add(R.mipmap.s3);
        bitmaps.add(R.mipmap.s4);
        bitmaps.add(R.mipmap.s5);
        bitmaps.add(R.mipmap.s6);
        bitmapList.add(R.drawable.student_bg);
        bitmapList.add(R.drawable.student_bg2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
