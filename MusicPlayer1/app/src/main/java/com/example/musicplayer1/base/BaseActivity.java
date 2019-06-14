package com.example.musicplayer1.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity <BD extends ViewDataBinding> extends AppCompatActivity {

    protected  BD binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {                                  //tạo activity
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,getLayoutId());                       //set thông tin cho activity
        initAct();
    }

    protected abstract void initAct();                                                              //hàm khởi tạo khi tạo mới activity

    protected abstract int getLayoutId();                                                           //lấy ra id của layout activity
}
