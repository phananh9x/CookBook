package com.phananh.api;

/**
 * Created by LeNghi on 4/9/2018.
 */

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://cookbook-server.herokuapp.com/";

    public static APIServices getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIServices.class);
    }
}
