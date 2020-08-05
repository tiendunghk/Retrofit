package com.example.notificationfcm;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MyService {
    @POST("user/register")
    Call<Response> registerUser(@Body RequestBody requestBody);

    @POST("device/register")
    Call<Response> registerDevice(@Body RequestBody requestBody);
}
