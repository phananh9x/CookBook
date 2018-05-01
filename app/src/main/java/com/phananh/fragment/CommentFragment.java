package com.phananh.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.phananh.adapter.CommentAdapter;
import com.phananh.api.APIServices;
import com.phananh.api.ApiUtils;
import com.phananh.api.response.CommentResponse;
import com.phananh.cookbook.MonAnChiTietActivity;
import com.phananh.cookbook.R;
import com.phananh.model.Comment;
import com.phananh.model.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFragment extends Fragment {
    ProgressDialog progressDialog;
    private APIServices mAPIService;
    EditText edtComment;
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
        final MonAnChiTietActivity activity = (MonAnChiTietActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        // Replace 'android.R.id.list' with the 'id' of your RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.rvComment);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        commentAdapter = new CommentAdapter(this.getActivity(),activity.getListComment());
        recyclerView.setAdapter(commentAdapter);
        edtComment = (EditText) view.findViewById(R.id.txtComment);
        view.findViewById(R.id.btnGui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                progressDialog= new ProgressDialog(view.getContext());
                progressDialog.setMessage("Loading...");
                progressDialog.setIndeterminate(false);
                progressDialog.show();

                mAPIService = ApiUtils.getAPIService();
                mAPIService.postCommentOfFood(activity.getToken(), activity.getMyData().id, new Comment(activity.getUsername(), edtComment.getText().toString())).enqueue(new Callback<CommentResponse>() {
                    @Override
                    public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                        Toast.makeText(view.getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        activity.getListComment().add(response.body().comments);
                        commentAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<CommentResponse> call, Throwable t) {
                        Toast.makeText(view.getContext(), "Post comment failed", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });
        return view;

    }
}
