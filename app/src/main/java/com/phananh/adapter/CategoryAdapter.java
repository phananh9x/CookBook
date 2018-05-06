package com.phananh.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.phananh.model.Category;
import com.phananh.model.DanhMuc;
import com.phananh.cookbook.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Minamino on 1/15/2016.
 */
public class CategoryAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Category> danhsachanh;

    public CategoryAdapter(Context context, int layout, List<Category> danhsachanh) {
        this.context = context;
        this.layout = layout;
        this.danhsachanh = danhsachanh;
    }

    private class ViewHolder {
        ImageView hinhanh;
        TextView txtText;
    }

    @Override
    public int getCount() {
        return danhsachanh == null ? 0 : danhsachanh.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewrow = convertView;
        if (viewrow == null) {
            viewrow = View.inflate(context, R.layout.custom_layout_gridview, null);

            ViewHolder holder = new ViewHolder();
            holder.hinhanh = (ImageView) viewrow.findViewById(R.id.imHinhAnh);
            holder.txtText = (TextView) viewrow.findViewById(R.id.txtText);

            viewrow.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) viewrow.getTag();
        Glide.with(context).load(danhsachanh.get(position).getImage()).into(holder.hinhanh);
        holder.txtText.setText(danhsachanh.get(position).getName());

        return viewrow;
    }
}
