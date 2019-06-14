package com.example.musicplayer1.api;

import com.example.musicplayer1.api.response.MusicResponse;
import com.example.musicplayer1.api.response.UserResponse;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

//Api để kết nối với server
public interface ApiInfo {

    @FormUrlEncoded
    @POST("register.php")
    Call<Response> register(@Field("user_name" )String userName,
                            @Field("password" )String password,
                            @Field("name" )String name);

    @FormUrlEncoded
    @POST("login.php")
    Call<UserResponse> login(@Field("user_name" )String userName,
                             @Field("password" )String password);

    @GET("music.php")
    Call<MusicResponse> getMusic();         //tạo một luồng mới để reset api, trả về một call back
}
