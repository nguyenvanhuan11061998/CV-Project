package com.example.musicplayer1;

import android.app.Application;

import com.example.musicplayer1.api.response.UserResponse;
import com.example.musicplayer1.service.Mp3Service;

//khởi tạo ApplicationContext, đưa service vào đồng bộ với app
public class App extends Application {

    private Mp3Service service;                                                                     //khởi tạo service Mp3Service
    private UserResponse user;                                                                      //lấy user để sử dụng


    //khởi tạo tài nguyên khi bắt đầu mở ứng dụng
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //lấy ra service
    public Mp3Service getService() {
        return service;
    }

    //đưa service vào app
    public void setService(Mp3Service service) {
        this.service = service;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
