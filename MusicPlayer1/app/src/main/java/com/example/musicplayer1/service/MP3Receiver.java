package com.example.musicplayer1.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

//BrodcastReceiver:lắng nghe các sự kiện trong hệ thống để sử lý cho app( xử lý sự kiện click vào các nút trên notification)
//Trong hệ thống , có những action phải đăng kí bằng context, không đăng ký được bằng action trên manifrest
//Từ android 28 trở đi , bắt buộc phải đăng ký trên context mới chạy được.
public class MP3Receiver  extends BroadcastReceiver {


    //inten: chứa dữ liệu lấy vào
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,intent.getAction(),Toast.LENGTH_LONG).show();
    }
}
