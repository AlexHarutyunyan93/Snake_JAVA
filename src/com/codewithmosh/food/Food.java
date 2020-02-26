package com.codewithmosh.food;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;


class Food {
    private int nutritionally;
    private ImageIcon image;
    private int x;
    private int y;

    public int getNutritionally() {
        return nutritionally;
    }

    public void setNutritionally(int nutritionally) {
        this.nutritionally = nutritionally;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void doDrowing(Graphics g, ImageObserver parent) {}
}
