package com.erze.yqj.moudle.comment.adapter;

import android.content.Context;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.R;
import com.erze.yqj.entity.ReplyBean;
import com.erze.yqj.utils.AutoUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by 1227228155@qq.com on 2017/8/1.
 */

public class ReplyAdapter extends BaseAdapter{
    Context context;
    List<ReplyBean.ResultBean> bean;
    Map<String, String> map = new ArrayMap<>();
    SPUtils spUtils = SPUtils.getInstance("user");
    public ReplyAdapter(Context context,    List<ReplyBean.ResultBean> bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return bean.size();
    }

    @Override
    public Object getItem(int position) {
        return bean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        ReplyBean.ResultBean resultBean = bean.get(position);
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment_list, null);
            AutoUtils.auto(convertView);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.context = (TextView) convertView.findViewById(R.id.context);
            holder.dz_count = (TextView) convertView.findViewById(R.id.dz_count);
            holder.other_name = (TextView) convertView.findViewById(R.id.other_id);
            holder.a = (TextView) convertView.findViewById(R.id.a);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.name.setText(resultBean.getReply_user_nickname());
        holder.context.setText(new String(EncodeUtils.base64Decode(resultBean.getReply_contents())));
        if (resultBean.getReply_type().equals("2")){
            holder.a.setVisibility(View.VISIBLE);
            holder.name.setText(resultBean.getUser_nickname());
            holder.other_name.setVisibility(View.VISIBLE);
            holder.other_name.setText(resultBean.getB_user_nickname());
        }
        return convertView;
    }



    class ViewHolder{
        ImageView avatar;
        TextView name,context,other_name,msg_count,dz_count,a;
    }
}
