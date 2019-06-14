package com.example.musicplayer1.api.response;

import com.example.musicplayer1.models.Music;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MusicResponse implements Serializable{

    @SerializedName("result")
    private ArrayList<Music> arr;

    public ArrayList<Music> getArr() {
        return arr;
    }

}

