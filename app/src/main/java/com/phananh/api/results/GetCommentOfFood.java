package com.phananh.api.results;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.phananh.api.response.BaseResponse;
import com.phananh.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class GetCommentOfFood extends BaseResponse {
    @SerializedName("results")
    @Expose
    public List<Comment> comments;

    public List<com.phananh.model.Comment> getCommentOfFood() {
        if (comments == null){
            comments = new ArrayList<>();
        }
        return comments;
    }
}