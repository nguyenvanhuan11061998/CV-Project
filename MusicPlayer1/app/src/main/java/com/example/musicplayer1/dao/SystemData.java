package com.example.musicplayer1.dao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.musicplayer1.models.Album;
import com.example.musicplayer1.models.Artist;
import com.example.musicplayer1.models.FieldInfo;
import com.example.musicplayer1.models.MP3Media;
import com.example.musicplayer1.models.Music;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class SystemData {                                                       //lấy dữ liệu từ hệ thống ra, ở đây là lấy các bài hát
    private ContentResolver resolver;                                           //đọc dữ liệu được chia sẻ từ ứng dụng khác

    public SystemData(Context context) {
        resolver = context.getContentResolver();
    }

    public ArrayList<Music> getSongs(){                                                             //lấy ra mảng bài hát từ hệ thống
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        ArrayList<Music> arr = getData(cursor, Music.class);

        ArrayList<Album> arrAlbum = getAlbum();
        for (Music m:arr) {
            for (Album a:arrAlbum) {
                if (m.getAlbumId() == a.getId()){
                    m.setImg(a.getImage());
                }
            }
        }

        return arr;
    }

    public ArrayList<Music> getSongsByArtist(int id){                                               //lấy ra danh sách bài hát có id artist truyền vào
        String selection = MediaStore.Audio.AudioColumns.ARTIST_ID + "=?";
        String[] selectionAgs = new String[]{
                String.valueOf(id)
        };
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, selection, selectionAgs, null);
        ArrayList<Music> arr = getData(cursor, Music.class);
        return arr;
    }

    public ArrayList<Album> getAlbum(){                                                             //lấy ra mảng album từ hệ thống
        Cursor cursor = resolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,null,null,null,null);
        return getData(cursor,Album.class);
    }

    public ArrayList<Artist> getArtist(){
        Cursor cursor = resolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,null,null,null,null);
        return getData(cursor,Artist.class);
    }

    private  <T extends MP3Media> ArrayList<T> getData(Cursor cursor, Class<T> clz) {                           //
        ArrayList<T> arr = new ArrayList<>();
        try {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                // tạo ra đối tượng bằng cách gọi constructor rỗng
                T t = clz.newInstance();                                                                    //hàm gọi contructor rỗng của một class bất kỳ ( clz)
                // lấy toàn bộ thuộc tính của đối tượng
                Field[] fields = clz.getDeclaredFields();                                                   //lấy các trường của đối tượng clz

                for (Field f : fields) {
                    // lấy annotation của trường
                    FieldInfo info = f.getAnnotation(FieldInfo.class);
                    // nếu k có annotation thì đọc sang field tiếp theo
                    if (info == null) continue;
                    // lấy dữ liệu từ database
                    int index = cursor.getColumnIndex(info.fieldName());
                    String value = cursor.getString(index);
                    // set dữ liệu vào cho object
                    setData(f, t, value);
                }
                arr.add(t);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return arr;
    }



    private <T extends MP3Media> void setData(Field f, T t, String value) throws IllegalAccessException {
        f.setAccessible(true);
        // lấy ra kiểu dữ liệu của thuộc tính
        String typeName = f.getType().getSimpleName();

        if (typeName.equalsIgnoreCase("int")){
            f.setInt(t, Integer.parseInt(value));
            return;
        }
        if (typeName.equalsIgnoreCase("long")){
            f.setLong(t, Long.parseLong(value));
            return;
        }
        if (typeName.equalsIgnoreCase("float")){
            f.setFloat(t, Float.parseFloat(value));
            return;
        }
        if (typeName.equalsIgnoreCase("double")){
            f.setDouble(t, Double.parseDouble(value));
            return;
        }
        if (typeName.equalsIgnoreCase("boolean")){
            f.setBoolean(t, Boolean.parseBoolean(value));
            return;
        }
        // set cho kiểu dữ liệu đối tượng
        f.set(t, value);
    }
}
