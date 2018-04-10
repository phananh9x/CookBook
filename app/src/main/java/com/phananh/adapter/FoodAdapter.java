package com.phananh.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.phananh.model.MonAn;
import com.phananh.cookbook.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Minamino on 1/15/2016.
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodHolderView> {


    private Activity activity;
    private ArrayList<MonAn> dsMonAn;


    public FoodAdapter(Activity activity, ArrayList<MonAn> dsMonAn) {
        this.activity = activity;
        this.dsMonAn = dsMonAn;
    }

    @Override
    public FoodHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(activity,R.layout.custom_layout_danhsachmonan,null);
       // View view=View.inflate(activity,R.layout.danh_sach_mon_an,null);

        return new FoodHolderView(view);
    }

    @Override
    public void onBindViewHolder(FoodHolderView holder, int position) {
        MonAn monAn=dsMonAn.get(position);
        if(position%2==0)
        {
            holder.linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.light_green1));
            Picasso.with(activity).load(monAn.getUrl().toString()).placeholder(R.drawable.none).error(R.drawable.none).into(holder.imgHinhMonAn);
            holder.txtMoTa.setText(monAn.getMoTa().toString());
            holder.txtTenMonAn.setText(monAn.getTenMonAn().toString());
        }
        else {
            holder.linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.light_green2));
            Picasso.with(activity).load(monAn.getUrl().toString()).placeholder(R.drawable.none).error(R.drawable.none).into(holder.imgHinhMonAn);
            holder.txtMoTa.setText(monAn.getMoTa().toString());
            holder.txtTenMonAn.setText(monAn.getTenMonAn().toString());
        }




    }
    @Override
    public int getItemCount() {
        return dsMonAn == null ? 0 : dsMonAn.size();

    }



    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
