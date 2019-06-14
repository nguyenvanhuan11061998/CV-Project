package com.example.musicplayer1.activity;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.widget.SeekBar;

import com.example.musicplayer1.App;
import com.example.musicplayer1.R;
import com.example.musicplayer1.base.BaseActivity;
import com.example.musicplayer1.databinding.ActivityPlayBinding;
import com.example.musicplayer1.models.Music;
import com.example.musicplayer1.service.Mp3Service;
import com.example.musicplayer1.view.MP3PlayViewListener;


public class PlayActivity extends BaseActivity<ActivityPlayBinding> implements MP3PlayViewListener, SeekBar.OnSeekBarChangeListener {

    private App app;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_play;
    }

    @Override
    protected void initAct() {
        app = (App) getApplicationContext();                                                            //đưa activity vào service
        app.getService().getIsPlaying().observe(this, new Observer<Boolean>() {                 //lấy trạng thái của bài hát trong service ra
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                binding.setIsPlaying(aBoolean);
            }
        });

        app.getService().getMusic().observe(this, new Observer<Music>() {                       //đưa bài hát đang phát trong service vào binding để hiển thị ra mang hình
            @Override
            public void onChanged(@Nullable Music music) {
                binding.setMusic(music);
            }
        });

        app.getService().getCurrent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                binding.setCurrent(integer);
            }
        });
        app.getService().getIsLife().observe(this, new Observer<Boolean>() {                        //khi isLife tắt tự động đóng activity này lại
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean == false){
                    finish();
                }
            }
        });

        binding.setListener(this);                                                                  //đưa sự kiện vào cho activity
        binding.sbTime.setOnSeekBarChangeListener(this);                                                //bộ sự kiện của seekBar để thay đổi seekBar
    }


    @Override
    public void next() {
        app.getService().change(Mp3Service.NEXT);
    }

    @Override
    public void prev() {
        app.getService().change(Mp3Service.PREV);
    }

    @Override
    public void play() {
        if (app.getService().getIsPlaying().getValue()==true){
            app.getService().pause();
        }else {
            app.getService().start();
        }
    }

    //seekBar, vị trí cần thay đổi tới, tua nhạc
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser){                                                                              //kiểm tra seekbar tua nhạc phải từ người dùng kéo mới sử lý
            app.getService().seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
