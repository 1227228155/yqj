package com.erze.yqj.moudle.follow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.erze.yqj.R;
import com.erze.yqj.utils.AutoUtils;

/**
 * Created by 1227228155@qq.com on 2017/8/14.
 */

public class OtherFollowAdapter extends BaseAdapter {
    private Context context;

    public OtherFollowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final OtherFollowAdapter.ViewHolder holder;
        if(convertView == null)
        {
            holder = new OtherFollowAdapter.ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_other_follow, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            AutoUtils.auto(convertView);
            convertView.setTag(holder);
        }else
        {
            holder = (OtherFollowAdapter.ViewHolder)convertView.getTag();
        }
        Glide.with(context)
                .load("http://pic.58pic.com/58pic/13/89/81/98m58PICHv7_1024.jpg")
                .into( holder.iv);
        return convertView;
    }

    class ViewHolder{
        ImageView iv;

    }
}
