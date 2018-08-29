package com.example.minihub.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.minihub.R;
import com.example.minihub.bean.Query;
import com.example.minihub.utils.Utils;


import java.util.List;

public class QueryAdapter extends BaseAdapter {

    private Context mContext;
    private List<Query.Datas> queryList;

    public QueryAdapter(Context context, List<Query.Datas> datas) {
        super(context);
        this.mContext = context;
        this.queryList = datas;
    }


    @Override
    public RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_query_activity, parent,false);
        return new QueryHolder(view);
    }

    @Override
    public void bindItemHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof QueryHolder){
            QueryHolder queryHolder = (QueryHolder) holder;
            queryHolder.author.setText(queryList.get(position).getAuthor());
            queryHolder.chapterName.setText(queryList.get(position).getChapterName());
            queryHolder.title.setText(Utils.delHTMLTag(queryList.get(position).getTitle()));
            queryHolder.date.setText(queryList.get(position).getNiceDate());
        }
    }

    @Override
    protected int getDataSize() {
        return queryList.size();
    }


    public void addData(List<Query.Datas> datas){
        this.queryList.addAll(datas);
        notifyDataSetChanged();
    }


    class QueryHolder extends RecyclerView.ViewHolder{

        private TextView author;
        private TextView chapterName;
        private TextView title;
        private TextView date;
        public QueryHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.query_author);
            chapterName = itemView.findViewById(R.id.query_chapter_name);
            title = itemView.findViewById(R.id.query_title);
            date = itemView.findViewById(R.id.query_date);
        }
    }
}
