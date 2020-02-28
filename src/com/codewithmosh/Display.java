package com.codewithmosh;

import com.codewithmosh.food.Food;
import com.codewithmosh.food.Foods;
import com.codewithmosh.mic.Mic;
import com.codewithmosh.snake.Snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Display extends JPanel implements ActionListener {

    private final int B_WIDTH = 1600;
    private final int B_HEIGHT = 1000;
    private final int ALL_DOTS = 900;
    private final int DELAY = 100;

    private boolean inGame = true;

    private Timer timer;
    private int micsSize;
    private ArrayList<Mic> mics = new ArrayList<>(micsSize);
    private Snake snake;
    private Foods foods;
    private List<Food> foodsList;


    public Display() {
        foods = new Foods(B_WIDTH, B_HEIGHT);
        foodsList = foods.getFoodsList();
        snake = new Snake(ALL_DOTS);
        micsSize = 100;

        for(int i = 0; i < micsSize; i++){
            mics.add(new Mic((int) (Math.random() * (B_HEIGHT/10-1))*10,(int) (Math.random() * (B_HEIGHT/10-1))*10, B_WIDTH, B_HEIGHT));
        }
        initDisplay();
    }

    private void initDisplay() {
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(snake.getKeyboardListener());
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        initGame();
    }
    private void initGame() {
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        if (inGame) {
            foods.doDrawing(g, this);

            for(int i = 0; i < mics.size(); i++){
                mics.get(i).doDrawing(g,this);
            }

            snake.doDrawing(g, this);

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    private void checkFood(){
        /////mic check
        for(int i = 0; i < foodsList.size(); i++) {
            for(int j = 0; j < mics.size(); j++) {
                if (mics.get(j).getX() == foodsList.get(i).getX() && mics.get(j).getY() == foodsList.get(i).getY())
                    foods.eatFood(i);
            }
        }
        //// snake check
        for(int i = 0; i < foodsList.size(); i++){
            if ((snake.getX(0) == foodsList.get(i).getX()) &&
                    (snake.getY(0) == foodsList.get(i).getY())) {
                snake.setDots();
                foods.eatFood(i);
            }
        }
    }

    private void checkCollision() {

        for (int z = snake.getDots(); z > 0; z--) {

            if ((z > 4) && (snake.getX(0) == snake.getX(z)) && (snake.getY(0) == snake.getY(z))) {
                inGame = false;
            }
        }

        if (snake.getY(0) >= B_HEIGHT) {
            inGame = false;
        }

        if (snake.getY(0) < 0) {
            inGame = false;
        }

        if (snake.getX(0) >= B_WIDTH) {
            inGame = false;
        }

        if (snake.getX(0) < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for(int i = 0; i < mics.size(); i++) {
           if(mics.get(i).getLifeCicle() == 0){
               mics.remove(i);
           }
        }

        if (inGame) {
            checkFood();
            checkCollision();
            snake.move();

            for(int i = 0; i < mics.size(); i++){
                mics.get(i).micLookForFood(foodsList);
            }
        }

        repaint();
    }
}
