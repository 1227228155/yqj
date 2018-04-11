package com.erze.yqj.moudle.user.rank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.erze.yqj.R;
import com.erze.yqj.entity.HotBean;
import com.erze.yqj.utils.AutoUtils;

import java.util.List;

/**
 * Created by 1227228155@qq.com on 2017/7/21.
 */

public class RankAdapter extends BaseAdapter {
    Context context;
    List<HotBean.ResultBean> list;

    public RankAdapter(Context context,  List<HotBean.ResultBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size()-3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position+3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_rank, null);
            holder.avatar = (ImageView) convertView.findViewById(R.id.item_rank_avatar);
            holder.name = (TextView) convertView.findViewById(R.id.item_rank_name);
            holder.id = (TextView) convertView.findViewById(R.id.item_rank_id);
            AutoUtils.auto(convertView);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        Glide.with(context).load(list.get(position + 3).getUser_pic()).into(holder.avatar);
        holder.name.setText(list.get(position+3).getUser_nickname());
        holder.id.setText(list.get(position+3).getUser_id());
        return convertView;
    }



    class ViewHolder{
        ImageView avatar;
        TextView name;
        TextView id;
        TextView count;
    }
}
