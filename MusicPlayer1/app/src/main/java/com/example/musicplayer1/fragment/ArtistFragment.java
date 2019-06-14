package com.example.musicplayer1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.musicplayer1.activity.ArtistActivity;
import com.example.musicplayer1.base.BaseAdapter;
import com.example.musicplayer1.base.BaseFragment;
import com.example.musicplayer1.R;
import com.example.musicplayer1.databinding.FragmentArtistBinding;
import com.example.musicplayer1.models.Artist;

import java.util.ArrayList;

public class ArtistFragment extends BaseFragment<FragmentArtistBinding> implements MediaListener<Artist> {
    public static final String EXTER_ARTIST_NAME = "extra_artist_name";
    private BaseAdapter<Artist> adapter;
    private ArrayList<Artist> arrArtist = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new BaseAdapter<>(getContext(),R.layout.item_artist);
        binding.lvArtist.setAdapter(adapter);
        adapter.setData(systemData.getArtist());
        adapter.setListener(this);

        arrArtist = systemData.getArtist();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_artist;
    }

    @Override
    public int getTitle() {
        return R.string.artist;
    }

    @Override
    public void onItemMediaClick(Artist artist) {

        String nameArtist = artist.getArtist();
        Intent intent = new Intent(getContext(), ArtistActivity.class);
        intent.putExtra(EXTER_ARTIST_NAME,nameArtist);

        startActivity(intent);
    }
}
