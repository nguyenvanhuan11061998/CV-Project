package com.t3h.Model;

import com.t3h.Manager.SoundManager;

import javax.swing.*;
import java.awt.*;

public class Boom {
    private int x;
    private int y;
    private Image[] images = {
            new ImageIcon(getClass().getResource("/images/boom1.gif")).getImage(),
            new ImageIcon(getClass().getResource("/images/no1.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/no2.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/no3.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/no4.png")).getImage()
    };

    private int time = 250;
    private int index = 0;

    public Boom(int x, int y) {
        this.x = x;
        this.y = y;
    }



    public boolean draw(Graphics2D g2d){
        time --;
        if(time < 0){
            return true;
        }
        if(time == 80){
            SoundManager.play("bigexplode.wav");
        }
        if(time < 20){
            index = 4;
        }else if (time <40){
            index = 3;
        }else if(time<80){
            index = 1;
        }else {
            index = 0;
        }

        int xD = x - images[index].getWidth(null)/2;
        int yD = y - images[index].getHeight(null)/2;
        g2d.drawImage(images[index],xD,yD,null);
        return false;
    }

    public boolean checkExplosion(Rectangle rect){              //kiểm tra nổ
        if(index != 3 ){
            return false;
        }

        int w = images[index].getWidth(null);
        int h = images[index].getHeight(null);
        int xD = x - w/2;
        int yD = y - h/2;
        Rectangle rW = new Rectangle(x-1,yD,2,h);
        Rectangle rH = new Rectangle(xD,y-1,w,1);
        return (rect.intersection(rW).isEmpty()==false)||(rect.intersection(rH).isEmpty()==false);
    }
}
