package com.example.digitalmuseum.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HttpRequestService {

    String URL = "http://10.20.170.240:8080"; //

    @POST("v1/get/medium/{code}/list")
    Call<JsonObject> getSmallList(@Path("code") String code);

    @POST("v1/get/small/{code}/list")
    Call<JsonObject> getContentsList(@Path("code") String code);

    @POST("v1/get/data/{code}/")
    Call<JsonObject> getData(@Path("code") String code);
}
