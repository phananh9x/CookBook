package com.phananh.dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.phananh.cookbook.R;
import com.phananh.model.Image;

import java.util.List;

/**
 * Created by thanh on 06/05/2018.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Context mContext;
    private List<Image> mItems;

    public ImageAdapter(Context context, List<Image> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder holder, int position) {
        Glide.with(mContext).load(mItems.get(position).image).into(holder.ivStep);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivStep;

        public ViewHolder(View itemView) {
            super(itemView);
            ivStep = (ImageView) itemView.findViewById(R.id.iv_step);
        }
    }
}
