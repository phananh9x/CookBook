package com.phananh.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phananh.adapter.MaterialsAdapter;
import com.phananh.cookbook.MonAnChiTietActivity;
import com.phananh.cookbook.R;
import com.phananh.model.Food;
import com.phananh.model.Material;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class MaterialsFragment extends Fragment {
    private RecyclerView recyclerView;
    private MaterialsAdapter materialAdapter;
    private LinearLayoutManager mLayoutManager;
    public MaterialsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MonAnChiTietActivity activity = (MonAnChiTietActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_materials, container, false);

        // Replace 'android.R.id.list' with the 'id' of your RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.rvMeterials);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        materialAdapter = new MaterialsAdapter(this.getActivity(),activity.getMyData().materials);
        recyclerView.setAdapter(materialAdapter);
        return view;
    }
}
