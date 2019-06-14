package com.t3h.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class BoomFrame extends JFrame {
    public static final int W = 695;
    public static final int H = 640;

    public BoomFrame() throws HeadlessException {
        setTitle("Game boom");
        setSize(W,H);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        BoomPanel boomPanel = new BoomPanel();
        add(boomPanel);
    }


}
