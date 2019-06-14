package com.example.musicplayer1.api;


import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class ApiBuilder {

    private static ApiInfo api;
    public static ApiInfo getApi(Context context){
        if (api == null){
            api = new Retrofit.Builder()
                    .addConverterFactory( GsonConverterFactory.create())
                    .baseUrl("http://192.168.61.106/mp3music/")
                    .build()
                    .create(ApiInfo.class);
        }
        return api;
    }

}
