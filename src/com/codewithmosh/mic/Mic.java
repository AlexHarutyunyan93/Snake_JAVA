package com.codewithmosh.mic;

import com.codewithmosh.DirectionEnum;
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

    public Mic(int x, int y, int b_WIDTH, int b_HEIGHT){
        lifeCicle = 100;
        this.steps = 0;
        this.mic_x = x;
        this.mic_y = y;
        micDirection = LEFT;
        founded = false;
        micCheckLevel = 1;
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
    @Override
    public void micMoveToApple(int appleX,int appleY) {
        if(mic_x == appleX && mic_y == appleY) {
            micCheckLevel++;
        } else if(mic_x != appleX && mic_y != appleY) {
            int x = B_WIDTH - mic_x - appleX;
            int y = B_HEIGHT - mic_y - appleY;
            if(x > y) {
                mic_x = mic_x < appleX ? mic_x + 10 : mic_x - 10;
            } else {
                mic_y = mic_y < appleY ? mic_y + 10 : mic_y - 10;
            }
        } else {
            if (mic_x != appleX) {
                mic_x = mic_x < appleX ? mic_x + 10 : mic_x - 10;
            }
            if(mic_y != appleY){
                mic_y = mic_y < appleY ? mic_y + 10 : mic_y - 10;
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
    public void micLookForApple(int appleX, int appleY) {
        lifeCicle--;
        if(!founded){
            if(Math.abs(appleY - mic_y) < (micCheckLevel * 10) || Math.abs(appleX - mic_x) < (micCheckLevel * 10)){
                founded = true;
                lifeCicle += 50;
            }

            if(founded){
                micMoveToApple(appleX, appleY);
            } else {
                micMove(micDirection);
            }
        } else {
            micMoveToApple(appleX, appleY);
        }
    }
}
