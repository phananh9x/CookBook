package com.phananh.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.phananh.model.Food;
import com.phananh.cookbook.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Minamino on 1/15/2016.
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder> {


    private Activity activity;
    private List<Food> dsMonAn;


    public FoodAdapter(Activity activity, List<Food> dsMonAn) {
        this.activity = activity;
        this.dsMonAn = dsMonAn;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(activity,R.layout.custom_layout_danhsachmonan,null);
       // View view=View.inflate(activity,R.layout.danh_sach_mon_an,null);

        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        Food monAn=dsMonAn.get(position);
        if(position%2==0)
        {
            holder.linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.light_green1));
            Picasso.with(activity).load(monAn.image).placeholder(R.drawable.none).error(R.drawable.none).into(holder.imgHinhMonAn);
            holder.txtMoTa.setText(monAn.decriptions);
            holder.txtTenMonAn.setText(monAn.name);
        }
        else {
            holder.linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.light_green2));
            Picasso.with(activity).load(monAn.image).placeholder(R.drawable.none).error(R.drawable.none).into(holder.imgHinhMonAn);
            holder.txtMoTa.setText(monAn.decriptions);
            holder.txtTenMonAn.setText(monAn.name);
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
