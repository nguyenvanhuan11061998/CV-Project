package com.example.musicplayer1.fragment;

import com.example.musicplayer1.base.BaseAdapter;
import com.example.musicplayer1.models.MP3Media;

public interface MediaListener <T extends MP3Media> extends BaseAdapter.ListItemListener {
    void onItemMediaClick(T t);
}
