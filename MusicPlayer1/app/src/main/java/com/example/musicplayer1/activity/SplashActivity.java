package com.example.musicplayer1.activity;

import android.content.Intent;
import android.os.Handler;

import com.example.musicplayer1.base.BaseActivity;
import com.example.musicplayer1.R;
import com.example.musicplayer1.databinding.ActivitySplashBinding;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    @Override
    protected void initAct() {
        Handler handler = new Handler();                                                                  //tạo một handler để chạy SplashActivity
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }
}
