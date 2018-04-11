package com.erze.yqj.moudle.release.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.erze.yqj.R;
import com.erze.yqj.utils.AutoUtils;

import java.util.ArrayList;

/**
 * Created by 1227228155@qq.com on 2017/7/8.
 */

public class ReleaseAdapter extends BaseAdapter {
    Context context;
    ArrayList<Bitmap> arrayList;

    public ReleaseAdapter(Context context, ArrayList<Bitmap> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder=null;
        if (viewHolder==null){
            convertView =  LayoutInflater.from(context).inflate(R.layout.item_release_fm, null);
            AutoUtils.auto(convertView);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.item_release_fm);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageBitmap(arrayList.get(position));
        return convertView;
    }

    static class ViewHolder {
       ImageView imageView;
    }
}
