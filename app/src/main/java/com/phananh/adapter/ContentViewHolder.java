package com.phananh.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phananh.cookbook.R;

public class ContentViewHolder extends   RecyclerView.ViewHolder {
    public TextView buoc;
    public TextView vitri;
    public ImageView img1;
    public ImageView img2;
    public ImageView img3;
    public ImageView img4;


    public ContentViewHolder(View itemView) {
        super(itemView);
        vitri = (TextView) itemView.findViewById(R.id.txtPosition);
        buoc = (TextView) itemView.findViewById(R.id.txtStep);
        img1= (ImageView) itemView.findViewById(R.id.imgHinh1);
        img2= (ImageView) itemView.findViewById(R.id.imgHinh2);
        img3= (ImageView) itemView.findViewById(R.id.imgHinh3);
        img4= (ImageView) itemView.findViewById(R.id.imgHinh4);

    }
}
