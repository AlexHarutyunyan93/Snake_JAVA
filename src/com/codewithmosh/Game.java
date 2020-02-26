package com.codewithmosh;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {

    public Game(){
        EventQueue.invokeLater(() -> {
            JFrame ex = new Game();
            ex.setVisible(true);
        });
        initUI();
    }
    private void initUI() {

        add(new Display());

        setResizable(false);
        pack();

        setTitle("SnakeSchema");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
