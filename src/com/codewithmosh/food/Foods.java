package com.codewithmosh.food;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Foods {
    private final int initialFoodCount = 3;
    private  ArrayList<Food> barnWithFood = new ArrayList<Food>(initialFoodCount);
    private int B_WIDTH;
    private int B_HEIGHT;

    public Foods(int B_WIDTH,int B_HEIGHT){
        this.B_WIDTH = B_WIDTH;
        this.B_HEIGHT = B_HEIGHT;
        initFoods();
    }

    private void initFoods(){
        for(int i = 0; i < initialFoodCount; i++){
            int randomX = (int) (Math.random() * (B_WIDTH/10-1));
            int randomY = (int) (Math.random() * (B_HEIGHT/10-1));

            barnWithFood.add(new Apple(randomX, randomY));
        }
    }

    private void doDrowing(Graphics g, ImageObserver parent){
        for(int i = 0; i < barnWithFood.size(); i++){
            barnWithFood.get(i).doDrowing(g, parent);
        }
    }
}
