package com.example.musicplayer1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.musicplayer1.activity.ArtistActivity;
import com.example.musicplayer1.base.BaseAdapter;
import com.example.musicplayer1.base.BaseFragment;
import com.example.musicplayer1.R;
import com.example.musicplayer1.databinding.FragmentAlbumBinding;
import com.example.musicplayer1.models.Album;

public class AlbumFragment extends BaseFragment<FragmentAlbumBinding> implements MediaListener<Album> {
    public static final String EXTRA_ALBUM = "extra_AlbumId";
    private BaseAdapter<Album> adapter;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_album;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new BaseAdapter<>(getContext(),R.layout.item_album);
        binding.lvAlbum.setAdapter(adapter);
        adapter.setData(systemData.getAlbum());
        adapter.setListener(this);

    }

    @Override
    public int getTitle() {
        return R.string.album;
    }

    @Override
    public void onItemMediaClick(Album album) {
        String albumName = album.getName();
        Intent intent = new Intent(getContext(),ArtistActivity.class);
        intent.putExtra(EXTRA_ALBUM,albumName);
        startActivity(intent);
    }
}
