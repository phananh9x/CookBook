package com.phananh.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phananh.cookbook.MonAnChiTietActivity;
import com.phananh.cookbook.R;
import com.phananh.model.Food;

public class CommentFragment extends Fragment {
    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MonAnChiTietActivity activity = (MonAnChiTietActivity) getActivity();
        Food food = activity.getMyData();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comment, container, false);

    }
}
