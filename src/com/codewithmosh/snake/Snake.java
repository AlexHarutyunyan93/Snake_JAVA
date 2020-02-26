package com.codewithmosh.snake;

import com.codewithmosh.DirectionEnum;
import com.codewithmosh.KeyboardListener;
import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Snake extends SnakeSchema {
    private int dots;
    private int x[];
    private int y[];
    private Image head;
    private Image body;
    private  int DOT_SIZE;
    private DirectionEnum moveDirection;
    private KeyboardListener keyboardListener;

    public Snake(int allDots){
        keyboardListener = new KeyboardListener();
        moveDirection = keyboardListener.getPlayerDirecton();

        DOT_SIZE = 10;
        dots = 3;
        x = new int[allDots];
        y = new int[allDots];

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }
    }

    public int getX(int index){
        return x[index];
    }

    public int getY(int index){
        return y[index];
    }

    public void setDots(){
        dots++;
    }

    public int getDots(){
        return dots;
    }

    public KeyboardListener getKeyboardListener(){
        return keyboardListener;
    }

    public void loadImages() {

        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        body = iid.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();

    }

    public void doDrawing(Graphics g, ImageObserver parent){
        for (int z = 0; z < dots; z++) {
            if (z == 0) {
                g.drawImage(head, x[z], y[z], parent);
            } else {
                g.drawImage(body, x[z], y[z], parent);
            }
        }
    }

    public void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        switch(keyboardListener.getPlayerDirecton()){
            case LEFT:
                x[0] -= DOT_SIZE;
                break;
            case RIGHT:
                x[0] += DOT_SIZE;
                break;
            case UP:
                y[0] -= DOT_SIZE;
                break;
            case DOWN:
                y[0] += DOT_SIZE;
                break;
        }
    }

}
