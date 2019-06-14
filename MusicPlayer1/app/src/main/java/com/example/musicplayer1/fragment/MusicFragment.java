package com.example.musicplayer1.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.musicplayer1.base.BaseAdapter;
import com.example.musicplayer1.base.BaseFragment;
import com.example.musicplayer1.models.Music;
import com.example.musicplayer1.R;
import com.example.musicplayer1.databinding.FragmentMusicBinding;

import java.util.ArrayList;

public class MusicFragment extends BaseFragment<FragmentMusicBinding> implements MediaListener<Music>{
    private BaseAdapter<Music> adapter;
    private ArrayList<Music> arrMusic;
    private ArrayList<Music> arrMusic1;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)  {
        super.onActivityCreated(savedInstanceState);
        arrMusic = new ArrayList<>();
        arrMusic = systemData.getSongs();

        adapter = new BaseAdapter<>(getContext(),R.layout.item_music);
        binding.lvSong.setAdapter(adapter);
        adapter.setListener(this);
        adapter.setData(arrMusic);

    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_music;
    }

    @Override
    public int getTitle() {
        return R.string.music;
    }

    public void chageMusic(String s){
        arrMusic1 = new ArrayList<>();
        for (Music music:arrMusic){
            if (music.getTitle().contains(s)){
                arrMusic1.add(music);
            }
        }
        adapter.setData(arrMusic1);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemMediaClick(Music music) {
        app.getService().setArrMusic(adapter.getData());
        int index = adapter.getData().indexOf(music);
        app.getService().create(index);

    }
}
