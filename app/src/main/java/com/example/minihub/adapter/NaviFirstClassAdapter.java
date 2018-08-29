package com.example.minihub.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.minihub.R;

import java.util.List;

public class NaviFirstClassAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> firstClassItemNames;
    private OnItemClickListener itemClickListener;

    private boolean firstShow = true;

    private int currentPos = 0;

    public void addItems(List<String> items){
        this.firstClassItemNames.addAll(items);
        notifyDataSetChanged();
    }

    public void selectedItemPosition(int pos){
        this.currentPos = pos;
    }

    public int getSelectedItemPosition(){
        return currentPos;
    }

    public NaviFirstClassAdapter(Context context, List<String> list) {
        super();
        this.mContext = context;
        this.firstClassItemNames = list;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_first_class_navifragment, parent, false);
        return new FirstClassHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final FirstClassHolder firstClassHolder = (FirstClassHolder) holder;
        firstClassHolder.firstClassItem.setText(firstClassItemNames.get(position));
        firstClassHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null){
                    itemClickListener.onItemClick(position);
                }
            }
        });
        if (position == getSelectedItemPosition()){
            firstClassHolder.selectedBackground();
        }else {
            firstClassHolder.unselectedBackground();
        }
//        if (position == 0 && firstShow){
//            firstClassHolder.selectedBackground();
//            firstShow = false;
//        }
    }

    @Override
    public int getItemCount() {
        return firstClassItemNames.size();
    }

    public class FirstClassHolder extends RecyclerView.ViewHolder{
        RelativeLayout rootView;
        TextView firstClassItem;
        public FirstClassHolder(View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.item_root_view);
            firstClassItem = itemView.findViewById(R.id.first_class_item);
        }

        public void selectedBackground(){
            rootView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            firstClassItem.setTextColor(mContext.getResources().getColor(R.color.material_teal_accent_700));
        }

        public void unselectedBackground(){
            rootView.setBackgroundColor(mContext.getResources().getColor(R.color.material_blue_grey_50));
            firstClassItem.setTextColor(mContext.getResources().getColor(R.color.material_blue_grey_300));
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

}
