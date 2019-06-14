package com.t3h.ui;

import com.t3h.Manager.GameManager;
import com.t3h.Manager.SoundManager;
import com.t3h.Model.Entiy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BoomPanel extends JPanel implements Runnable, KeyListener {
    private GameManager manager = new GameManager();
    private boolean[] flag = new boolean[256];



    public BoomPanel() {
        setBackground(Color.WHITE);
        manager.initGame();
        Thread t = new Thread(this);

        t.start();                                   //bắt đầu thực hiện, chỉ được phép gọi một lần sau Thread.

        setFocusable(true);                         //đăng ký sự kiện bàn phím
        addKeyListener(this);

    }

    @Override
    protected void paintComponent(Graphics g) {               //g: bút vẽ đồ họa
        Graphics2D g2D = (Graphics2D) g;
        super.paintComponent(g2D);
//        g2D.setColor(Color.red);                               //Màu nét vẽ

        //Vẽ hình học
//        g2D.setStroke(new BasicStroke(10));             //Kích thước nét vẽ hình học
//        g2D.drawLine(0,0,200,200);
//        g2D.drawOval(200,200,100,50);
//        g2D.drawRect(300,300,100,100);

        //Vẽ chuỗi ký tự
//        g2D.setFont(new Font(null,Font.ITALIC,20));
//        g2D.drawString("Hello world",400,200);

        manager.draw(g2D);
    }

    @Override
    public void run() {
        while (true) {
            boolean isDie = manager.AI();
            if(isDie){
                SoundManager.play("luigi_haha.wav");
                int result = JOptionPane.showConfirmDialog(null,"bạn muốn chơi lại?","game over",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(result == JOptionPane.YES_OPTION){
                    manager.initGame();
                    flag = new boolean[256];
                }else{
                    System.exit(0);
                }
            }

            manager.AI();
            if(flag[KeyEvent.VK_LEFT]){
                manager.movePlayer(Entiy.LEFT);
            }
            else if(flag[KeyEvent.VK_RIGHT]){
                manager.movePlayer(Entiy.RIGHT);
            }
            else if(flag[KeyEvent.VK_UP]){
                manager.movePlayer(Entiy.UP);
            }
            else if(flag[KeyEvent.VK_DOWN]){
                manager.movePlayer(Entiy.DOWN);
            }

            if(flag[KeyEvent.VK_SPACE]){
                manager.playerFire();
            }



            repaint();                              //cập nhật giao diện
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        flag[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        flag[e.getKeyCode()] = false;
    }
}