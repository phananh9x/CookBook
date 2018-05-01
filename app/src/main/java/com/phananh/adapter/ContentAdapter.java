package com.phananh.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.phananh.cookbook.R;
import com.phananh.model.Content;
import com.phananh.model.Image;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContentAdapter  extends RecyclerView.Adapter<ContentViewHolder> {
    public LinearLayout linearLayout;
    private LayoutInflater mLayoutInflater;
    private Activity activity;
    private List<Content> dsContent;


    public ContentAdapter(Activity activity, List<Content> dsMaterial) {
        this.activity = activity;
        this.dsContent = dsMaterial;
        mLayoutInflater =  LayoutInflater.from(activity);
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= mLayoutInflater.inflate(R.layout.custom_layout_rv_content, null);
        // View view=View.inflate(activity,R.layout.danh_sach_mon_an,null);

        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        Content content=dsContent.get(position);

        holder.vitri.setText(String.valueOf(position+1));
        holder.buoc.setText(content.getStep().toString());
        if(content.getArrImage().size() >=1 && !content.getArrImage().get(0).image.isEmpty()) {
            Picasso.with(activity).load(content.getArrImage().get(0).image).placeholder(R.drawable.none).error(R.drawable.none).into(holder.img1);
        }
        if(content.getArrImage().size() >=2 && !content.getArrImage().get(1).image.isEmpty()) {
            Picasso.with(activity).load(content.getArrImage().get(1).image).placeholder(R.drawable.none).error(R.drawable.none).into(holder.img2);
        }
        if(content.getArrImage().size() >=3 && !content.getArrImage().get(2).image.isEmpty()) {

            Picasso.with(activity).load(content.getArrImage().get(2).image).placeholder(R.drawable.none).error(R.drawable.none).into(holder.img3);
        }
        if(content.getArrImage().size() >=4 && !content.getArrImage().get(3).image.isEmpty()) {
            Picasso.with(activity).load(content.getArrImage().get(3).image).placeholder(R.drawable.none).error(R.drawable.none).into(holder.img4);
        }
    }
    @Override
    public int getItemCount() {
        return dsContent == null ? 0 : dsContent.size();

    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
