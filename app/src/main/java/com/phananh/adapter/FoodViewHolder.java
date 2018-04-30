package com.phananh.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phananh.cookbook.R;

/**
 * Created by Minamino on 1/15/2016.
 */
public class FoodViewHolder extends RecyclerView.ViewHolder {
    public ImageView imgHinhMonAn;
    public TextView txtTenMonAn;
    public TextView txtMoTa;
    public Bitmap hinhMonAn;
    public LinearLayout linearLayout;


    public FoodViewHolder(View itemView) {
        super(itemView);
        imgHinhMonAn= (ImageView) itemView.findViewById(R.id.imgHinhMonAn);
        txtTenMonAn= (TextView) itemView.findViewById(R.id.txtTenMonAn);
        txtMoTa= (TextView) itemView.findViewById(R.id.txtMoTa);
        linearLayout= (LinearLayout) itemView.findViewById(R.id.LinearLayout);

    }
}
