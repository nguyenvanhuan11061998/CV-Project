package com.t3h.Model;

import java.awt.*;
import java.util.ArrayList;

public abstract class Entiy {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;


    protected int x;
    protected int y;
    protected Image img;
    protected int orient;
    protected int speed = 1;

    //phương thức khởi tạo: chỉ khởi tạo cho x, y:  Khởi tạo vị trí đầu tiên cho đối tượng khi vào game
    public Entiy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2D){       //Khởi tạo bút vẽ
        g2D.drawImage(img,x,y,null);
    }

    public void move(ArrayList<MapBoom> arr){
        int xR = x;
        int yR = y;
        switch (orient){
            case LEFT:
                x-=speed;
                break;
            case RIGHT:
                x+=speed;
                break;
            case UP:
                y-=speed;
                break;
            case DOWN:
                y+=speed;
                break;
        }
        boolean check = checkMap(arr);                  //kiểm tra va chạm,nếu xảy ra va chạm thì tọa độ x, y giữ nguyên
        if(check == false){
            x = xR;
            y = yR;
        }
    }

    public Rectangle getRect(){
        if(img == null){
            return new Rectangle();
        }
        Rectangle rect = new Rectangle(x,y,img.getWidth(null),img.getHeight(null));         //khai báo hình chữ nhật bao quanh thực thể
        return rect;
    }

    public boolean checkMap(ArrayList<MapBoom> arr){
        for (MapBoom map:arr) {
            if(map.getBit() ==0){
                continue;
            }
            Rectangle rect = getRect().intersection(map.getRect());         //kiểm tra sự giao nhau bằng  cách tạo một hình chữ nhật mới kích thước bằng sự giao nhau của hai hình chữ nhật cũ
            if(rect.isEmpty() == false){
                return false;
            }
        }
        return true;
    }

    public boolean checkDie(ArrayList<Boom> arr){
        for (Boom b:arr) {
            if(b.checkExplosion(getRect())){
                return true;
            }
        }
        return false;
    }
}
