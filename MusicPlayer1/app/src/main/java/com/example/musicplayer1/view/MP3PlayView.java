package com.example.musicplayer1.view;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.musicplayer1.App;
import com.example.musicplayer1.activity.MainActivity;
import com.example.musicplayer1.activity.PlayActivity;
import com.example.musicplayer1.databinding.UiPlayViewBinding;
import com.example.musicplayer1.service.Mp3Service;

public class MP3PlayView extends FrameLayout implements MP3PlayViewListener, View.OnClickListener {

    private UiPlayViewBinding binding;
    private App app;

    public MP3PlayView(@NonNull Context context) {
        super(context);
        init();
    }

    public MP3PlayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MP3PlayView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MP3PlayView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        binding = UiPlayViewBinding.inflate(LayoutInflater.from(getContext()));
        addView(binding.getRoot());
        app = (App) getContext().getApplicationContext();
        setVisibility(GONE);
        binding.setListener(this);                                                                                      //gọi sự kiện click cho
        setOnClickListener(this);
    }

    public void registerState(){
        MainActivity act = (MainActivity) getContext();
        app.getService().getIsLife().observe(act, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean){
                    setVisibility(VISIBLE);
                }else{
                    setVisibility(GONE);
                }
            }
        });

        app.getService().getName().observe(act, new Observer<String>() {                                    //lấy ra tên bài hát hiện tại đang phát để đưa lên MP3PlayView
            @Override
            public void onChanged(@Nullable String s) {
                binding.setName(s);                                                                         //đưa tên bài hát vào binding đưa sang layout
            }
        });

        app.getService().getIsPlaying().observe(act, new Observer<Boolean>() {                              //lấy ra trạng thái phát nhạc của bài hát để hiển thị lên layout
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                binding.setIsPlaying(aBoolean);
            }
        });
    }


    //xử lý sự kiện khi nhấn next
    @Override
    public void next() {
        app.getService().change(Mp3Service.NEXT);
    }


    //xử lý sự kiện khi nhấn prev
    @Override
    public void prev() {
        app.getService().change(Mp3Service.PREV);
    }


    //xử lý khi nhấn play, pause:
    @Override
    public void play() {
         if (app.getService().getIsPlaying().getValue() == true){                                       //nếu trạng thái phát nhạc đang bằng true thì :
             app.getService().pause();                                                                  //chuyển sang pause
         }else {
             app.getService().start();                                                                  //nếu trạng thái đang bằng false thì tức là đang pause, thì chuyển sang play
         }
    }

    //click vào bài hát sẽ mở activity play ra để chơi nhạc
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), PlayActivity.class);
        getContext().startActivity(intent);
    }
}