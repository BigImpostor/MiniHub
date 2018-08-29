package com.example.minihub.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.minihub.R;
import com.example.minihub.bean.Article;

import java.util.List;

public class ArticleAdatper extends BaseAdapter{

    private List<Article.Datas> articleList;


    public ArticleAdatper(Context context,List<Article.Datas> articles) {
        super(context);
        this.articleList = articles;
    }

    public void addItem(List<Article.Datas> articleList){
        this.articleList.addAll(articleList);
        notifyDataSetChanged();
    }

    @Override
    protected RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_fragment, parent, false);
        return new ArticleHolder(view);
    }

    @Override
    protected void bindItemHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ArticleHolder){
            ArticleHolder articleHolder = (ArticleHolder) holder;
            articleHolder.author.setText(articleList.get(position).getAuthor());
            articleHolder.desc.setText(articleList.get(position).getTitle());
            articleHolder.date.setText(articleList.get(position).getNiceDate());
            articleHolder.chapter.setText(articleList.get(position).getChapterName());
        }

    }

    @Override
    protected int getDataSize() {
        return articleList.size();
    }

    class ArticleHolder extends RecyclerView.ViewHolder{

        private TextView chapter;
        private TextView author;
        private TextView date;
        private TextView desc;
        public ArticleHolder(View itemView) {
            super(itemView);
            chapter = itemView.findViewById(R.id.chapter_tv);
            author = itemView.findViewById(R.id.author_tv);
            date = itemView.findViewById(R.id.date_tv);
            desc = itemView.findViewById(R.id.desc_tv);
        }
    }
}
