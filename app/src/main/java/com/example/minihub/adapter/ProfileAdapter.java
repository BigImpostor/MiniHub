package com.example.minihub.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minihub.R;

public class ProfileAdapter extends RecyclerView.Adapter {

    private Context mContext;

    public ProfileAdapter(Context context) {
        super();
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_profile, parent,false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProfileViewHolder viewHolder = (ProfileViewHolder) holder;
        switch (position){
            case 0:
                viewHolder.icon.setImageResource(R.drawable.ic_favor);
                viewHolder.choice.setText("我的收藏");
                break;
            case 1:
                viewHolder.icon.setImageResource(R.drawable.ic_exit);
                viewHolder.choice.setText("退出登录");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    private class ProfileViewHolder extends RecyclerView.ViewHolder{
        private ImageView icon;
        private TextView choice;

        public ProfileViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.profile_item_icon);
            choice = itemView.findViewById(R.id.profile_item_choice);
        }
    }

}
