package com.codewithmosh.mic;

import com.codewithmosh.DirectionEnum;
import com.codewithmosh.food.Food;

import javax.swing.*;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.codewithmosh.DirectionEnum.*;

public class Mic implements MicSchema {
    private int steps;
    private int B_WIDTH;
    private int B_HEIGHT;
    private int mic_x;
    private int mic_y;
    private DirectionEnum micPrevDirection;
    private DirectionEnum micDirection;
    private boolean founded;
    private int micCheckLevel;
    private int lifeCicle;
    private Image micImage;
    private Food food = null;

    public Mic(int x, int y, int b_WIDTH, int b_HEIGHT){
        B_WIDTH = b_WIDTH;
        B_HEIGHT = b_HEIGHT;
        lifeCicle = 150;
        this.steps = 0;
        this.mic_x = x;
        this.mic_y = y;
        micDirection = LEFT;
        founded = false;
        micCheckLevel = 10;
        loadImages();
    }

    public int getX(){
        return mic_x;
    }

    public int getY(){
        return mic_y;
    }

    public void setFounded(boolean f){
        founded = f;
    }

    public int getLifeCicle(){
        return lifeCicle;
    }

    public void loadImages() {
        ImageIcon iih = new ImageIcon("src/resources/head.png");
        micImage = iih.getImage();
    }

    public void doDrawing(Graphics g, ImageObserver parent){
        g.drawImage(micImage, getX(), getY(), parent);
    }
    private void addLifeCicle(){
        lifeCicle += 50;
        micCheckLevel++;
    }

    private void removeFood(){
        this.food = null;
    }

    @Override
    public void micMoveToFood(int foodX, int foodY) {
        if(mic_x == foodX && mic_y == foodY) {
            addLifeCicle();
            setFounded(false);
            removeFood();
        } else if(mic_x != foodX && mic_y != foodY) {
            int x = Math.abs(mic_x - foodX);
            int y = Math.abs(mic_y - foodY);
            if(x > y) {
                mic_x = mic_x < foodX ? mic_x + 10 : mic_x - 10;
            } else {
                mic_y = mic_y < foodY ? mic_y + 10 : mic_y - 10;
            }
        } else {
            if (mic_x != foodX) {
                mic_x = mic_x < foodX ? mic_x + 10 : mic_x - 10;
            }
            if(mic_y != foodY){
                mic_y = mic_y < foodY ? mic_y + 10 : mic_y - 10;
            }

        }
    }

    @Override
    public void micMove(DirectionEnum direction) {
        if(steps > 4){
            changeMoveDirection();
            checkBords();
            steps = 0;
        }
        switch (direction){
            case LEFT:
                mic_x-=10;
                break;
            case UP:
                mic_y-=10;
                break;
            case RIGHT:
                mic_x+=10;
                break;
            case DOWN:
                mic_y+=10;
                break;
            default:
                break;
        }
        steps++;
    }

    @Override
    public void checkBords() {
        if(mic_x < 20 || mic_y < 20 || mic_x > B_WIDTH-20 || mic_y > B_HEIGHT-20) {
            changeMoveDirection();
        }
    }

    @Override
    public void changeMoveDirection() {
        DirectionEnum[] next = {LEFT,RIGHT,UP,DOWN};
        DirectionEnum nextDirection = next[(int) Math.floor(Math.random()*4)];
        if(nextDirection != micPrevDirection){
            micPrevDirection = micDirection;
            micDirection = nextDirection;
        } else {
            changeMoveDirection();
        }
    }

    @Override
    public void micLookForFood(List<Food> foods) {
        lifeCicle--;
        List<Integer> farFromFood = new ArrayList<>();
        List<Food> availibleFoods = new ArrayList<>();

        if(!founded){
            for(int i = 0; i < foods.size(); i++){
                int fFFX = Math.abs(foods.get(i).getX() - mic_x);
                int fFFY = Math.abs(foods.get(i).getY() - mic_y);

                if(fFFX < (micCheckLevel * 10) && fFFY < (micCheckLevel * 10)){
                    farFromFood.add(fFFX + fFFY);
                    availibleFoods.add(foods.get(i));
                }
            }
            setFounded(farFromFood.size() != 0);
            if(founded){
                int min = Collections.min(farFromFood);
                int index = farFromFood.indexOf(min);
                food = availibleFoods.get(index);
                micMoveToFood(food.getX(), food.getY());
            } else {
                micMove(micDirection);
            }
        } else {
            if(food != null){
                micMoveToFood(food.getX(), food.getY());
            } else {
                micMove(micDirection);
            }
        }
    }
}
