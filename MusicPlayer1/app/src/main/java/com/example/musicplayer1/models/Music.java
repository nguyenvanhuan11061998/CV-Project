package com.example.musicplayer1.models;

import android.provider.MediaStore;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Music extends MP3Media {

    @SerializedName("title")
    @FieldInfo(fieldName = MediaStore.Audio.AudioColumns.TITLE)
    private String title;

    @SerializedName("url")
    @FieldInfo(fieldName = MediaStore.Audio.AudioColumns.DATA)
    private String data;                                                                            //link đến bài hát


    @FieldInfo(fieldName =  MediaStore.Audio.AudioColumns.DURATION)
    private int duration;                                                                          //thời lượng bài hát

    @SerializedName("artist")
    @FieldInfo(fieldName = MediaStore.Audio.AudioColumns.ARTIST)
    private String artist;

    @FieldInfo(fieldName = MediaStore.Audio.AudioColumns.ALBUM)
    private String album;


    @FieldInfo(fieldName = MediaStore.Audio.AlbumColumns.ALBUM_ID)
    private long AlbumId;

    private String img;




    public long getAlbumId() {
        return AlbumId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public String getData() {
        return data;
    }

    public int getDuration() {
        return duration;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getStringDuration(){
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        String time = format.format(new Date(getDuration()));
        return time;
    }
}
