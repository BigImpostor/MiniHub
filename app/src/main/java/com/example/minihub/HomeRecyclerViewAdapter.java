package com.example.minihub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.minihub.adapter.BaseAdapter;
import com.example.minihub.bean.Article;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

public class HomeRecyclerViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<Article.Datas> datas;
    private List<String> imagesList;

    private OnItemClickListener mListener;

    private static final int TYPE_BANNER = 0;

    public HomeRecyclerViewAdapter(Context context, List<Article.Datas> datas,List<String> imagesList ){
        super(context);
        this.mContext = context;
        this.datas = datas;
        this.imagesList = imagesList;
    }

    public void addImagesList(List<String> images){
        this.imagesList.clear();
        this.imagesList.addAll(images);
        notifyDataSetChanged();
    }

    public void addArticleDatas(List<Article.Datas> datas){
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_BANNER;
        return super.getItemViewType(position);
    }

    @Override
    protected RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BANNER){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_fragment_banner, parent, false);
            return new BannerHolder(view);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_fragment,parent,false);
        return new HomeItemHolder(view);
    }


    @Override
    protected void bindItemHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BannerHolder){
            BannerHolder bannerHolder = (BannerHolder) holder;
            bannerHolder.banner.setImages(imagesList).setImageLoader(new GlideImageLoader()).start();
        }

        if (holder instanceof  HomeItemHolder){
            final int itemPosition = position - 1;
            HomeItemHolder itemHolder = (HomeItemHolder) holder;
            itemHolder.chapterTV.setText(datas.get(itemPosition).getChapterName());
            itemHolder.descTV.setText(datas.get(itemPosition).getTitle());
            itemHolder.authorTV.setText(datas.get(itemPosition).getAuthor());
            itemHolder.dateTV.setText(datas.get(itemPosition).getNiceDate());
            itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
                        mListener.onItemClick(itemPosition);
                    }
                }
            });
        }
    }

    @Override
    protected int getDataSize() {
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

    public class BannerHolder extends RecyclerView.ViewHolder{
        Banner banner;
        public BannerHolder(View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner);
        }
    }


    private class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            Glide.with(context).load(path).into(imageView);

        }
    }


    public interface OnItemClickListener{
        void onItemClick(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
}
