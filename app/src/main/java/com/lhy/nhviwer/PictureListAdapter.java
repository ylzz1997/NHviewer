package com.lhy.nhviwer;

import android.graphics.Picture;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class PictureListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<PictureContent> pictureList;
    private OnItemClickListener onItemClickListener;
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;

    public PictureListAdapter(List<PictureContent> pictureList) {
        this.pictureList = pictureList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == TYPE_ITEM){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bookpattern,viewGroup,false);
            return new PLVH(view);
        }
        else if (i == TYPE_FOOTER){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bookpatternfoot,viewGroup,false);
            return new PLVHF(view);
        }
        return null;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if(viewHolder instanceof PLVH){
            PLVH plvh= (PLVH)viewHolder;
            plvh.textView.setText(pictureList.get(i).getName());
            if(pictureList.get(i).getImageId()!=0)
                plvh.imageView.setImageResource(pictureList.get(i).getImageId());
            if(pictureList.get(i).getImageBitmap()!=null)
                plvh.imageView.setImageBitmap(pictureList.get(i).getImageBitmap());
            if(onItemClickListener != null){
                plvh.itemView.setBackgroundResource(R.drawable.wave_bg);
                plvh.itemView.setOnClickListener((v)->{
                    onItemClickListener.OnItemClickListener(plvh.itemView,plvh.getLayoutPosition());
                });
            }
        }else if(viewHolder instanceof  PLVHF){
            PLVHF plvhf= (PLVHF)viewHolder;
            switch (loadState) {
                case LOADING: // 正在加载
                    plvhf.progressBar.setVisibility(View.VISIBLE);
                    plvhf.textView.setVisibility(View.VISIBLE);
                    break;

                case LOADING_COMPLETE: // 加载完成
                    plvhf.progressBar.setVisibility(View.INVISIBLE);
                    plvhf.textView.setVisibility(View.INVISIBLE);
                    break;

                default:
                    break;
            }
        }


    }

    interface OnItemClickListener {
        void OnItemClickListener(View view, int position);
    }

    public void claerAllData(){
        pictureList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(holder.getLayoutPosition() == pictureList.size()-1);
        }
    }

    public static class PLVH extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public PLVH(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_book_picture);
            textView = itemView.findViewById(R.id.item_book_text);
        }
    }

    public static class PLVHF extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
                TextView textView;

        public PLVHF(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.PLVF_progressBar);
            textView = itemView.findViewById(R.id.PLVF_textview);
        }
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }
}
