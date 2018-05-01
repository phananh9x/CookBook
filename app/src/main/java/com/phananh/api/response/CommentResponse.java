package com.phananh.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.phananh.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentResponse extends BaseResponse{
    @SerializedName("results")
    @Expose
    public Comment comments;

    public CommentResponse(Comment comments) {
        this.comments = comments;
    }

    public Comment getComments() {
        return comments;
    }
}
