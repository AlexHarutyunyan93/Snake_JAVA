package com.codewithmosh.food;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Apple extends Food {
    private ImageIcon apple;
    public Apple(int x, int y){
        setNutritionally(50);
        loadImages();
        setCoordinates(x, y);

    }

    public void loadImages() {
        ImageIcon apple = new ImageIcon("src/resources/apple.png");
        setImage(apple);
    }

    @Override
    public void doDrowing(Graphics g, ImageObserver parent){
        g.drawImage(apple.getImage(), getX(), getY(), parent);
    }

    @Override
    public int getNutritionally() {
        return super.getNutritionally();
    }

    @Override
    public ImageIcon getImage() {
        return super.getImage();
    }

    @Override
    public int getX() {
        return super.getX();
    }

    @Override
    public int getY() {
        return super.getY();
    }
}
