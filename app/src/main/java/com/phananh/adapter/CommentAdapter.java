package com.phananh.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.phananh.cookbook.R;
import com.phananh.model.Comment;
import com.phananh.model.Content;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    public LinearLayout linearLayout;
    private LayoutInflater mLayoutInflater;
    private Activity activity;
    private List<Comment> dsComment;


    public CommentAdapter(Activity activity, List<Comment> dsComment) {
        this.activity = activity;
        this.dsComment = dsComment;
        mLayoutInflater =  LayoutInflater.from(activity);
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= mLayoutInflater.inflate(R.layout.custom_layout_rv_comment, null);
        // View view=View.inflate(activity,R.layout.danh_sach_mon_an,null);

        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment comment=dsComment.get(position);
        holder.username.setText(comment.getName().toString());
        holder.content.setText(comment.getContent().toString());
        if (comment.getImage() != null) {
            Picasso.with(activity).load(comment.getImage()).placeholder(R.drawable.none).error(R.drawable.none).into(holder.imgUser);
        }
    }
    @Override
    public int getItemCount() {
        return dsComment == null ? 0 : dsComment.size();

    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}