package com.erze.yqj.moudle.voice.bgm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erze.yqj.R;
import com.erze.yqj.entity.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1227228155@qq.com on 2017/8/2.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder>{

    private Context mContext;
    private List<Music.ResultBean.ListBean>  dataList = new ArrayList<>();
    private OnItemSelectListener mListener;
    public interface OnItemSelectListener{
        void onItemSelect(int position);
    }
    public void setOnItemSelectListener(OnItemSelectListener listener){
        mListener = listener;
    }

    public MusicAdapter(Context context,List<Music.ResultBean.ListBean>  dataList) {
        mContext = context;
        this.dataList = dataList;
    }
    public void addAllData(List<Music.ResultBean.ListBean> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_bgm_listview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Music.ResultBean.ListBean listBean = dataList.get(position);
        holder.name.setText(listBean.getMp3_name());
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemSelect(position);
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        RelativeLayout rl;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
        }
    }

}
