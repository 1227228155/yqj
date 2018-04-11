package com.erze.yqj.moudle.voice.bgm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erze.yqj.R;
import com.erze.yqj.entity.MusicBean;

import java.util.List;

/**
 * Created by 1227228155@qq.com on 2017/7/17.
 */

public class NetMusicAdapter extends RecyclerView.Adapter<NetMusicAdapter.ViewHolder> {
    private Context mContext;
      private   List<MusicBean.DataBean.SongBean.ListBean> dataList;
    private OnItemSelectListener mListener;
    public interface OnItemSelectListener{
        void onItemSelect2(int position);
    }
    public void setOnItemSelectListener(OnItemSelectListener listener){
        mListener = listener;
    }
    public NetMusicAdapter(Context context, List<MusicBean.DataBean.SongBean.ListBean> dataList) {
        mContext = context;
        this.dataList = dataList;
    }
    public void addAllData( List<MusicBean.DataBean.SongBean.ListBean>dataList) {
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
        holder.name.setText(dataList.get(position).getFsong()+"-"+dataList.get(position).getFsinger()+" "+dataList.get(position).getFsinger2());
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemSelect2(position);
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
