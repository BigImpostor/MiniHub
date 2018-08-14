package com.example.minihub;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.minihub.bean.Article;

import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Article.Datas> datas;

    private OnItemClickListener mListener;

    public HomeRecyclerViewAdapter(Context context, List<Article.Datas> datas){
        this.mContext = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_fragment,parent,false);
        return new HomeItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        HomeItemHolder itemHolder = (HomeItemHolder) holder;
        itemHolder.chapterTV.setText(datas.get(position).getChapterName());
        itemHolder.descTV.setText(datas.get(position).getDesc());
        itemHolder.authorTV.setText(datas.get(position).getAuthor());
        itemHolder.dateTV.setText((CharSequence) datas.get(position).getNiceDate());
        itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class HomeItemHolder extends RecyclerView.ViewHolder{
        public TextView chapterTV;
        public TextView descTV;
        public TextView authorTV;
        public TextView dateTV;
        public HomeItemHolder(View itemView) {
            super(itemView);
            chapterTV = itemView.findViewById(R.id.chapter_tv);
            descTV = itemView.findViewById(R.id.desc_tv);
            authorTV = itemView.findViewById(R.id.author_tv);
            dateTV = itemView.findViewById(R.id.date_tv);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
}
