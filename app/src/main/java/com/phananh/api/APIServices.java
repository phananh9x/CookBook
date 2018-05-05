package com.phananh.api;

import com.phananh.api.response.CommentResponse;
import com.phananh.api.response.UserProfileResponse;
import com.phananh.api.results.GetCategoryResults;
import com.phananh.api.results.GetCommentOfFood;
import com.phananh.api.results.GetFoodDetailResults;
import com.phananh.api.results.GetFoodsResults;
import com.phananh.api.results.LoginResults;
import com.phananh.api.results.SignUpResults;
import com.phananh.api.results.UserProfilesResults;
import com.phananh.model.Comment;
import com.phananh.model.Food;
import com.phananh.model.LogIn;
import com.phananh.model.SignUp;
import com.phananh.model.UserProfiles;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by LeNghi on 4/9/2018.
 */

public interface APIServices {

    @POST("users/sign_in")
    Call<LoginResults> login(
            @Body LogIn logIn
    );

    @POST("users/register")
    Call<SignUpResults> SignUp(
            @Body SignUp signUp
    );



    @GET("category/")
    Call<GetCategoryResults> getCategories(
            @Header("token") String token
    );

    @GET("food/{categoryId}")
    Call<GetFoodsResults> getFood(
            @Header("token") String token,
            @Path("categoryId") String categoryId
    );

    @GET("food/{foodId}/detail")
    Call<GetFoodDetailResults> getFoodDetail(
            @Header("token") String token,
            @Path("foodId") String foodId
    );

    @POST("food/{foodId}/update")
    Call<GetFoodDetailResults> getFoodUpdate(
            @Header("token") String token,
            @Path("foodId") String foodId,
            @Body Food food
    );

    @GET("comment/{foodId}/")
    Call<GetCommentOfFood> getCommentOfFood(
            @Header("token") String token,
            @Path("foodId") String foodId
    );

    @POST("comment/{foodId}/create")
    Call<CommentResponse> postCommentOfFood(
            @Header("token") String token,
            @Path("foodId") String foodId,
            @Body Comment comment
    );

    @GET("users/{userId}/info")
    Call<UserProfilesResults> getUserProfiles(
            @Header("token") String token,
            @Path("userId") String userId
    );

    @POST("users/{userId}/update")
    Call<UserProfileResponse> getUserProfiles(
            @Header("token") String token,
            @Path("userId") String userId,
            @Body UserProfiles userProfiles
            );


}