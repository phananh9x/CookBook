package com.phananh.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phananh.cookbook.R;

public class MaterialsViewHolder extends   RecyclerView.ViewHolder {
    public TextView tenNguyenLieu;
    public TextView khauPhan;
    public LinearLayout linearLayout;

    public MaterialsViewHolder(View itemView) {
        super(itemView);
        tenNguyenLieu = (TextView) itemView.findViewById(R.id.txtTenNguyenLieu);
        khauPhan = (TextView) itemView.findViewById(R.id.txtKhauPhan);
        linearLayout= (LinearLayout) itemView.findViewById(R.id.ln_material);
    }
}
