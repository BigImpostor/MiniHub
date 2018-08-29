package com.example.minihub.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class BaseRecyclerOnScrollerListener extends RecyclerView.OnScrollListener {


    private boolean isSlidingUpward = false;


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (newState == RecyclerView.SCROLL_STATE_IDLE){
            int itemCount = manager.getItemCount();
            int lastItemPosition = manager.findLastVisibleItemPosition();
            if (lastItemPosition == itemCount - 1)
                loadMore();
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        isSlidingUpward = dy > 0;
    }

    public abstract void loadMore();
}
