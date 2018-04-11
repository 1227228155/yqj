package com.erze.yqj.moudle.student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erze.yqj.R;
import com.erze.yqj.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1227228155@qq.com on 2017/8/9.
 */

public class StudentAdapter extends BaseAdapter {
    Context mContext;
    List<String> strings = new ArrayList<>();
    List<String> strings2 = new ArrayList<>();
    List<Integer> bitmaps = new ArrayList<>();
    public StudentAdapter(Context mContext, List<String> strings, List<String> strings2, List<Integer> bitmaps) {
        this.mContext = mContext;
        this.strings = strings;
        this.strings2 = strings2;
        this.bitmaps = bitmaps;
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
        final ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_student, null);
            AutoUtils.auto(convertView);
            holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            holder.iv1 = (ImageView) convertView.findViewById(R.id.iv1);
            holder.iv2 = (ImageView) convertView.findViewById(R.id.iv2);
            holder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
            holder.tv2 = (TextView) convertView.findViewById(R.id.tv2);
            holder.rl = (RelativeLayout) convertView.findViewById(R.id.rl);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.iv.setImageResource(bitmaps.get(position));
        holder.tv1.setText(strings.get(position));
        holder.tv2.setText(strings2.get(position));
        if (position==2){
            holder.iv2.setVisibility(View.VISIBLE);
        }
        if (position%2==0){
            holder.iv1.setImageResource(R.mipmap.ss1);
        }else {
            holder.iv1.setImageResource(R.mipmap.ss2);
        }
        return convertView;
    }

    class ViewHolder{
        TextView tv1;
        TextView tv2;
        ImageView iv;
        ImageView iv1;
        ImageView iv2;
        RelativeLayout rl;
    }
}
