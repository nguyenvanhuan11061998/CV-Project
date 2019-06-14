package com.example.newminiproject.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity                                                                                             //thể hiện thực thể = một bảng
public class News {                                                                                 //Đối tượng tin tức
    @PrimaryKey(autoGenerate = true)                                                                //Khóa chính
    private int id;

    @ColumnInfo                                                                                     //thể hiện thuộc tính = một cột
    private String image;

    @ColumnInfo
    private String title;

    @ColumnInfo
    private String desc;

    @ColumnInfo
    private String pubDate;

    @ColumnInfo
    private String pathFile;

    @ColumnInfo
    private String link;


//các phương thức get set nhập xuất dữ liệu
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }
}
