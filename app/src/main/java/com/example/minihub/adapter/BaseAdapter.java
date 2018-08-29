package com.example.minihub.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.minihub.R;


public abstract class BaseAdapter extends RecyclerView.Adapter {

    protected Context mContext;

    private static int TYPE_ITEM = 0101010101;
    private static int TYPE_FOOT = 1111111111;

    private int loading_state = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_COMPLETE = 2;

    public BaseAdapter(Context context) {
        super();
        this.mContext = context;
    }

    protected abstract RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent, int viewType);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_footer, parent, false);
            return new FootViewHolder(view);
        }
        return createItemViewHolder(parent,viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() == position + 1)
            return TYPE_FOOT;
        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FootViewHolder){
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (loading_state){
                case STATE_LOADING:
                    footViewHolder.loadingTips.setVisibility(View.VISIBLE);
                    footViewHolder.progressBar.setVisibility(View.VISIBLE);
                    footViewHolder.rightLine.setVisibility(View.GONE);
                    footViewHolder.leftLine.setVisibility(View.GONE);
                    footViewHolder.loadEndTips.setVisibility(View.GONE);
                    break;
                case STATE_COMPLETE:
                    footViewHolder.loadingTips.setVisibility(View.GONE);
                    footViewHolder.progressBar.setVisibility(View.GONE);
                    footViewHolder.leftLine.setVisibility(View.VISIBLE);
                    footViewHolder.loadEndTips.setVisibility(View.VISIBLE);
                    footViewHolder.rightLine.setVisibility(View.VISIBLE);
                    break;
            }
        }
        bindItemHolder(holder,position);

    }

    protected abstract void bindItemHolder(@NonNull RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        if (getDataSize() != 0)
            return getDataSize() + 1;
        return 0;
    }

    protected abstract int getDataSize();

    public void loadingState(int state){
        this.loading_state = state;
    }

    protected class FootViewHolder extends RecyclerView.ViewHolder{

        private ProgressBar progressBar;
        private TextView loadingTips;
        private TextView loadEndTips;
        private View leftLine, rightLine;
        public FootViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.loading_progressBar);
            loadingTips = itemView.findViewById(R.id.loading_tips);
            loadEndTips = itemView.findViewById(R.id.load_end_tips);
            leftLine = itemView.findViewById(R.id.left_line);
            rightLine = itemView.findViewById(R.id.right_line);
        }
    }
}
