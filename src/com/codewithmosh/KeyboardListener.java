package com.codewithmosh;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static com.codewithmosh.DirectionEnum.*;
import static java.awt.event.KeyEvent.*;

public class KeyboardListener extends KeyAdapter {
    private DirectionEnum keyPressed;

    public KeyboardListener(){
        keyPressed = RIGHT;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
            switch (key){
                case VK_LEFT:
                    keyPressed = keyPressed != RIGHT ? LEFT : keyPressed;
                    break;
                case VK_RIGHT:
                    keyPressed = keyPressed != LEFT ? RIGHT : keyPressed;
                break;
                case VK_UP:
                    keyPressed = keyPressed != DOWN ? UP : keyPressed;
                break;
                case VK_DOWN:
                    keyPressed = keyPressed != UP ? DOWN : keyPressed;
                break;
            }
    }

    public DirectionEnum getPlayerDirecton(){
        return keyPressed;
    }
}
