package com.phananh.api;

import com.phananh.api.response.LoginResponse;
import com.phananh.api.response.SignUpResponse;
import com.phananh.api.results.GetCategoryResults;
import com.phananh.api.results.GetFoodDetailResults;
import com.phananh.api.results.GetFoodsResults;
import com.phananh.api.results.LoginResults;
import com.phananh.api.results.SignUpResults;
import com.phananh.model.Category;
import com.phananh.model.Food;
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


}