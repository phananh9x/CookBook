package com.phananh.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phananh.adapter.ContentAdapter;
import com.phananh.cookbook.MonAnChiTietActivity;
import com.phananh.cookbook.R;
import com.phananh.model.Content;
import com.phananh.model.Food;

import java.util.List;


public class ContentFragment extends Fragment {
    private List<Content> dsContent;
    private RecyclerView recyclerView;
    private ContentAdapter materialAdapter;
    private LinearLayoutManager mLayoutManager;
    public ContentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MonAnChiTietActivity activity = (MonAnChiTietActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        // Replace 'android.R.id.list' with the 'id' of your RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.rvContent);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        materialAdapter = new ContentAdapter(this.getActivity(),activity.getMyData().content);
        recyclerView.setAdapter(materialAdapter);
        return view;
    }
}
