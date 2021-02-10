package com.codewithmosh.food;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Foods {
    private final int initialFoodCount = 50;
    private  ArrayList<Food> barnWithFood = new ArrayList<Food>(initialFoodCount);
    public int B_WIDTH;
    public int B_HEIGHT;

    public Foods(int B_WIDTH,int B_HEIGHT){
        this.B_WIDTH = B_WIDTH;
        this.B_HEIGHT = B_HEIGHT;
        initFoods();
    }

    public ArrayList<Food> getFoodsList(){
        return this.barnWithFood;
    }

    public void addFood(){
        int randomX = (int) (Math.random() * (B_WIDTH/10-1))*10;
        int randomY = (int) (Math.random() * (B_HEIGHT/10-1))*10;
        barnWithFood.add(new Apple(randomX, randomY));
    }
    public void eatFood(int index){
        barnWithFood.remove(index);
        if(barnWithFood.size() == 0){
            addFood();
        }
    }

    private void initFoods(){
        for(int i = 0; i < initialFoodCount; i++){
            addFood();
        }
    }

    public void doDrawing(Graphics g, ImageObserver parent){
        for(int i = 0; i < barnWithFood.size(); i++){
            barnWithFood.get(i).doDrawing(g, parent);
        }
    }
}
