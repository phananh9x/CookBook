package com.phananh.api;

import com.phananh.api.response.LoginResponse;
<<<<<<< HEAD
import com.phananh.api.response.SignUpResponse;
=======
import com.phananh.model.Category;
import com.phananh.model.Food;
>>>>>>> 8b3890948dc163b497ff29b1c3bb2e13ac72a05f
import com.phananh.model.LogIn;
import com.phananh.model.SignUp;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
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
    Call<LoginResponse> login(
            @Body LogIn logIn
    );

    @POST("users/register")
    Call<SignUpResponse> SignUp(
            @Body SignUp signUp
    );

    @GET("category/")
    Call<List<Category>> getCategories(
            @Header("token") String token
    );

    @GET("food/{categoryId}")
    Call<List<Food>> getFood(
            @Header("token") String token,
            @Path("categoryId") String categoryId
    );


}