package com.codewithmosh;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public Main(){
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

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame ex = new Main();
            ex.setVisible(true);
        });
    }
}
