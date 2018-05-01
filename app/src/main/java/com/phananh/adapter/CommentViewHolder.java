package com.phananh.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phananh.cookbook.R;

public class CommentViewHolder extends RecyclerView.ViewHolder {
    public TextView username;
    public TextView content;
    public ImageView imgUser;



    public CommentViewHolder(View itemView) {
        super(itemView);
        username = (TextView) itemView.findViewById(R.id.txtUserName);
        content = (TextView) itemView.findViewById(R.id.txtDanhGia);
        imgUser= (ImageView) itemView.findViewById(R.id.imgUser);
    }
}