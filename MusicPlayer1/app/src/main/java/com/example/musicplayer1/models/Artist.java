package com.example.musicplayer1.models;

import android.provider.MediaStore;

public class Artist extends MP3Media{
    @FieldInfo(fieldName = MediaStore.Audio.Artists.ARTIST)
    private String artist;
    @FieldInfo(fieldName = MediaStore.Audio.Artists.NUMBER_OF_ALBUMS)
    private String numberAlbum;
    @FieldInfo(fieldName = MediaStore.Audio.Artists.NUMBER_OF_TRACKS)
    private String numberMusic;

    public String getArtist() {
        return artist;
    }

    public String getNumberAlbum() {
        return numberAlbum;
    }

    public String getNumberMusic() {
        return numberMusic;
    }


}
