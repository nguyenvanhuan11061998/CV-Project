package com.t3h.Manager;

import com.t3h.Model.Boom;
import com.t3h.Model.Boss;
import com.t3h.Model.MapBoom;
import com.t3h.Model.Player;

import java.awt.*;
import java.util.ArrayList;

public class GameManager {
    private ArrayList<Boss> arrBoss;
    private Player player;
    private ArrayList<Boom> arrBoom;
    private MapManager mapManager = new MapManager();

    public void initGame(){
        SoundManager soundManager = new SoundManager();
        SoundManager.play("menu.wav");
        mapManager.readMap("mapGame1.txt");
        arrBoom = new ArrayList<>();
        player = new Player(50,30);
        arrBoss = new ArrayList<>();
        initBoss();
    }

    private void initBoss() {
        if(arrBoss.size() >2){
            return;
        }
        arrBoss.add(new Boss(200,100));
        arrBoss.add(new Boss(200,270));
        arrBoss.add(new Boss(200,515));
        arrBoss.add(new Boss(400,500));
    }

    public void draw(Graphics2D g2d){
        for (MapBoom map: mapManager.getArrMap()) {
            map.draw(g2d);
        }


        for (int i = arrBoom.size() - 1; i >=0 ; i--) {
            boolean check = arrBoom.get(i).draw(g2d);
            if(check){
                arrBoom.remove(i);
            }
        }

        for (Boss b: arrBoss ) {
            b.draw(g2d);
        }
        player.draw(g2d);

    }

    public void movePlayer(int newOrient){
        player.changeOrient(newOrient);
        player.move(mapManager.getArrMap());
    }

    public boolean AI(){                           //các hành động tự động
        for (int i = arrBoss.size() - 1;i>=0;i--) {
            arrBoss.get(i).move(mapManager.getArrMap());
            boolean check = arrBoss.get(i).checkDie(arrBoom);
            if(check){
                SoundManager.play("wahoo.wav");
                arrBoss.remove(i);
                initBoss();
            }
        }
        for (MapBoom map:mapManager.getArrMap()) {
            map.checkBoom(arrBoom);
        }
        return player.checkDie(arrBoom,arrBoss );
    }

    public void playerFire(){                   //phương thức thả boom
        player.fire(arrBoom);
    }

}
