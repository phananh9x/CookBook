package com.phananh.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.phananh.cookbook.R;
import com.phananh.model.Material;

import java.util.List;

public class MaterialsAdapter extends RecyclerView.Adapter<MaterialsViewHolder> {
    public LinearLayout linearLayout;
    private LayoutInflater mLayoutInflater;
    private Activity activity;
    private List<Material> dsMaterial;


    public MaterialsAdapter(Activity activity, List<Material> dsMaterial) {
        this.activity = activity;
        this.dsMaterial = dsMaterial;
        mLayoutInflater =  LayoutInflater.from(activity);
    }

    @Override
    public MaterialsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= mLayoutInflater.inflate(R.layout.custom_layout_rv_meterials, null);
        // View view=View.inflate(activity,R.layout.danh_sach_mon_an,null);

        return new MaterialsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MaterialsViewHolder holder, int position) {
        Material material=dsMaterial.get(position);
        if(position%2==0)
        {
            holder.linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.light_green4));
            holder.tenNguyenLieu.setText(material.material);
            holder.khauPhan.setText(material.amount);
        }
        else {
            holder.linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.light_green3));
            holder.tenNguyenLieu.setText(material.material);
            holder.khauPhan.setText(material.amount);
        }
    }
    @Override
    public int getItemCount() {
        return dsMaterial == null ? 0 : dsMaterial.size();

    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
