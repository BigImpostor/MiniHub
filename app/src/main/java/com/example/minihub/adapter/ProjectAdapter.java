package com.example.minihub.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.minihub.R;
import com.example.minihub.bean.Project;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter {

    private List<Project.Datas> datas;
    private Context mContext;

    private OnItemClickListener mListener;

    public ProjectAdapter(List<Project.Datas> datas, Context context){
        this.datas = datas;
        this.mContext = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_project_fragment,parent,false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ProjectViewHolder projectViewHolder = (ProjectViewHolder) holder;
        Glide.with(mContext).load(datas.get(position).getEnvelopePic()).into(projectViewHolder.projectImageView);
        projectViewHolder.projectTitle.setText(datas.get(position).getTitle());
        projectViewHolder.projectDesc.setText(datas.get(position).getDesc());
        projectViewHolder.projectDate.setText(datas.get(position).getNiceDate());
        projectViewHolder.projectAuthor.setText(datas.get(position).getAuthor());
        projectViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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

    private class ProjectViewHolder extends RecyclerView.ViewHolder{

        private ImageView projectImageView;
        private TextView projectTitle;
        private TextView projectDesc;
        private TextView projectAuthor;
        private TextView projectDate;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            projectImageView = itemView.findViewById(R.id.project_img);
            projectTitle = itemView.findViewById(R.id.project_title);
            projectDesc = itemView.findViewById(R.id.project_desc);
            projectAuthor = itemView.findViewById(R.id.project_author);
            projectDate = itemView.findViewById(R.id.project_date);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
}
