package com.phananh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.phananh.cookbook.R;
import com.phananh.model.Content;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CreateFoodAdapter extends BaseAdapter{
    private Context context;
    private List<Content> list;

    public CreateFoodAdapter(Context context, List<Content> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_step_test, null);
            holder = new ViewHolder();
            holder.tv = (TextView) view.findViewById(R.id.tv_test);
            holder.tv.setText(list.get(i).getStep());
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        return view;
    }

    static class ViewHolder {
       TextView tv;
    }
}