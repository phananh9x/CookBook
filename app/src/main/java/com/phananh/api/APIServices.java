package com.phananh.api;

import com.phananh.api.response.LoginResponse;
import com.phananh.model.LogIn;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by LeNghi on 4/9/2018.
 */

public interface APIServices {

    @POST("users/sign_in")
    Call<LoginResponse> login(
            @Body LogIn logIn
    );
}