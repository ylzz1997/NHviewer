package com.lhy.nhviwer;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.Arrays;
import java.util.Collections;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    //用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
        // 当不滑动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            //获取最后一个完全显示的itemPosition
            int[] lastPositions = new int[(manager).getSpanCount()];
            int[] lastItemPositions = manager.findLastVisibleItemPositions(lastPositions);
            Integer[] lastItemPositionsI = Arrays.stream(lastItemPositions).boxed().toArray(Integer[]::new);
            int lastItemPosition = (int)Collections.max(Arrays.asList(lastItemPositionsI));
            int itemCount = manager.getItemCount();

            // 判断是否滑动到了最后一个item，并且是向上滑动
            if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
                //加载更多
                onLoadMore();
            }else{
                onCancel();
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
        isSlidingUpward = dy > 0;
    }

    /**
     * 加载更多回调
     */
    public abstract void onLoadMore();
    public abstract void onCancel();
}