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

    private void loadImages() {
        apple = new ImageIcon("src/resources/apple.png");
        setImage(apple);
    }

    @Override
    public void doDrawing(Graphics g, ImageObserver parent){
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

}
