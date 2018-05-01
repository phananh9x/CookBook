package com.phananh.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phananh.adapter.CommentAdapter;
import com.phananh.cookbook.MonAnChiTietActivity;
import com.phananh.cookbook.R;
import com.phananh.model.Comment;
import com.phananh.model.Food;

import java.util.List;

public class CommentFragment extends Fragment {
    private List<Comment> dsComment;
    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private LinearLayoutManager mLayoutManager;
    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MonAnChiTietActivity activity = (MonAnChiTietActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        // Replace 'android.R.id.list' with the 'id' of your RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.rvComment);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        commentAdapter = new CommentAdapter(this.getActivity(),activity.getListComment());
        recyclerView.setAdapter(commentAdapter);
        return view;

    }
}
