
package com.t3h.Model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Boss extends Entiy {
    private Random random = new Random();

    private Image[] arrImg = {
            new ImageIcon(getClass().getResource("/images/blink_left.png")).getImage(),             // đọc file ảnh ra và hiển thị lên
            new ImageIcon(getClass().getResource("/images/blink_right.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/blink_up.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/blink_down.png")).getImage()
    };

    public Boss(int x, int y) {
        super(x, y);
        orient = DOWN;
    }

    @Override
    public void draw(Graphics2D g2D) {
        img = arrImg[orient];
        super.draw(g2D);
    }

    @Override
    public void move(ArrayList<MapBoom> arr) {
        genarateOrient();
        super.move(arr);
    }

    private void genarateOrient() {
        int percent = random.nextInt(100);
        if (percent > 95) {
            orient = random.nextInt(4);
        }
    }
}
