package com.example.musicplayer1.activity;

import android.content.Intent;
import android.view.View;

import com.example.musicplayer1.App;
import com.example.musicplayer1.R;
import com.example.musicplayer1.base.BaseActivity;
import com.example.musicplayer1.base.BaseAdapter;
import com.example.musicplayer1.dao.SystemData;
import com.example.musicplayer1.databinding.ActivityArtistBinding;
import com.example.musicplayer1.fragment.AlbumFragment;
import com.example.musicplayer1.fragment.ArtistFragment;
import com.example.musicplayer1.fragment.MediaListener;
import com.example.musicplayer1.models.Music;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class ArtistActivity extends BaseActivity<ActivityArtistBinding> implements View.OnClickListener, MediaListener<Music> {

    private String title = "";
    private long sumTime = 0 ;

    private BaseAdapter<Music> adapter;
    private SystemData systemData;
    private ArrayList<Music> arrMusic;
    private App app;


    @Override
    protected void initAct() {
        systemData = new SystemData(this);
        app = (App) this.getApplicationContext();

        getMusicWithArtistName();
        String time = getTime(sumTime);

        arrMusic = new ArrayList<>();
        arrMusic = getMusicWithArtistName();

        adapter = new BaseAdapter<>(this,R.layout.item_music_of_artist);
        binding.lvSongOfArtist.setAdapter(adapter);
        adapter.setData(arrMusic);

        binding.tvArtist1.setText(title);
        binding.sumTime.setText(time);


        adapter.setListener(this);

        binding.btnPhatNhac.setOnClickListener(this);
    }

    private ArrayList<Music> getMusicWithArtistName() {                                             //lấy danh sách bài hát có tên ca sĩ được truyền vào bằng intent
        ArrayList<Music> arr = new ArrayList<>();

        Intent intent = getIntent();
        sumTime = 0;

        if (intent.getStringExtra(ArtistFragment.EXTER_ARTIST_NAME)!= null){
            String nameArtist  = intent.getStringExtra(ArtistFragment.EXTER_ARTIST_NAME);
            title = nameArtist;
            for (Music music:systemData.getSongs()) {
                if (nameArtist.equals(music.getArtist())){
                    arr.add(music);
                    sumTime = sumTime + music.getDuration();
                }
            }
        }else{
            String albumName = intent.getStringExtra(AlbumFragment.EXTRA_ALBUM);
            title = albumName;

            for (Music music : systemData.getSongs()){
                if (albumName.equals(music.getAlbum())){
                    arr.add(music);
                    sumTime = sumTime + music.getDuration();
                }
            }

        }

        return arr;
    }

    private String getTime(long time){
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        String stringTime = format.format(new Date(time));
        return stringTime;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_artist;
    }

    @Override
    public void onClick(View v) {
//        Random random = new Random();
//        int index = random.nextInt(arrMusic.size());
//        app.getService().setArrMusic(arrMusic);
//        app.getService().create(index);
//        finish();
    }

    @Override
    public void onItemMediaClick(Music music) {
        app.getService().setArrMusic(arrMusic);
        int index = adapter.getData().indexOf(music);
        app.getService().create(index);
        finish();
    }
}
