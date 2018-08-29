package com.example.minihub.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.minihub.R;
import com.example.minihub.bean.Navigation;

import java.util.List;

public class NaviSecondClassAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Navigation.Children> childrenList;

    private OnClickItemListener itemListener;

    public void addItems(List<Navigation.Children> childrenList){
        this.childrenList.addAll(childrenList);
        notifyDataSetChanged();
    }

    public List<Navigation.Children> getChildrenList(){
        return this.childrenList;
    }

    public interface OnClickItemListener{
        void onClickItem(int pos);
    }

    public void setOnClickItemListener(OnClickItemListener itemListener){
        this.itemListener = itemListener;
    }

    public void clearItems(){
        if (childrenList.size() != 0)
            childrenList.clear();
        notifyDataSetChanged();
    }

    public NaviSecondClassAdapter(Context context, List<Navigation.Children> childrenList) {
        super();
        this.mContext = context;
        this.childrenList = childrenList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_navi_second_item, parent, false);
        return new SecondClassHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        SecondClassHolder secondClassHolder = (SecondClassHolder) holder;
        String name = childrenList.get(position).getName();
        secondClassHolder.textView.setText(name);
        secondClassHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemListener != null){
                    itemListener.onClickItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return childrenList.size();
    }
    class SecondClassHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public SecondClassHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.navi_second_textview);
        }
    }
}
