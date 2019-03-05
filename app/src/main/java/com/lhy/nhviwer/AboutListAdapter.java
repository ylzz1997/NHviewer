package com.lhy.nhviwer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AboutListAdapter extends RecyclerView.Adapter<AboutListAdapter.ALVH>{

    private List<StandarSecondItemContent> list = new ArrayList<StandarSecondItemContent>();
    private OnItemClickListener onItemClickListener;

    public AboutListAdapter(List<StandarSecondItemContent> list) {
        this.list = list;
    }

    public AboutListAdapter() {
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ALVH onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_standarseconditem,viewGroup,false);
        return new ALVH(view);
    }

    interface OnItemClickListener {
        void OnItemClickListener(View view, int position);
    }

    @Override
    public void onBindViewHolder(ALVH alvh, int i) {
        alvh.title.setText(list.get(i).getTitle());
        alvh.content.setText(list.get(i).getContent());
        if(onItemClickListener != null){
            alvh.itemView.setBackgroundResource(R.drawable.wave_bg);
            alvh.itemView.setOnClickListener((v)->{
                onItemClickListener.OnItemClickListener(alvh.itemView,alvh.getLayoutPosition());
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ALVH extends RecyclerView.ViewHolder{
        TextView title;
        TextView content;
        public ALVH(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.SDItem_title);
            content = itemView.findViewById(R.id.SDItem_content);
        }
    }
}
