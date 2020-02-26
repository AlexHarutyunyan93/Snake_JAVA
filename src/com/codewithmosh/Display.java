package com.codewithmosh;

import com.codewithmosh.mic.Mic;
import com.codewithmosh.snake.Snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Display extends JPanel implements ActionListener {

    private final int B_WIDTH = 1200;
    private final int B_HEIGHT = 800;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 79;
    private final int DELAY = 150;

    private int apple_x;
    private int apple_y;
    private int banana_x;
    private int banana_y;

    private boolean inGame = true;

    private Timer timer;
    private Image apple;
    private Image banana;
    private Image[] micImages;
    private int micsSize;
    private ArrayList<Mic> mics = new ArrayList<Mic>(micsSize);
    private Snake snake;

    public Display() {

        snake = new Snake(900);
        micsSize = 10;
        for(int i = 0; i < micsSize; i++){
            mics.add(new Mic(300,150, B_WIDTH, B_HEIGHT));
        }
        micImages = new Image[micsSize];
        initDisplay();
    }

    private void initDisplay() {
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(snake.getKeyboardListener());
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        snake.loadImages();
        initGame();
    }

    private void loadImages() {
        ImageIcon iib = new ImageIcon("src/resources/banana.jpg");
        banana = iib.getImage();

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");

        for(int i = 0; i < mics.size(); i++){
            micImages[i] = iih.getImage();
        }
    }

    private void initGame() {

        locateApple();
        locateBanana();

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

            g.drawImage(apple, apple_x, apple_y, this);
            g.drawImage(banana, banana_x, banana_y, this);

            for(int i = 0; i < mics.size(); i++){
                g.drawImage(micImages[i], mics.get(i).getX(), mics.get(i).getY(), this);
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

    private void checkApple() {
        if ((snake.getX(0) == banana_x) && (snake.getY(0) == banana_y)) {
            snake.setDots();
            locateBanana();
        }
        if ((snake.getX(0) == apple_x) && (snake.getY(0) == apple_y)) {
            snake.setDots();
            locateApple();
        } else{
            for(int i = 0; i < mics.size(); i++){
               if ((mics.get(i).getX()== apple_x) && (mics.get(i).getY()== apple_y)){
                   locateApple();
               }
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

    private void locateApple() {

        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));

        for(int i = 0; i < mics.size(); i++){
            mics.get(i).setFounded(false);
        }
    }

    private void locateBanana() {

        int r = (int) (Math.random() * RAND_POS);
        banana_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        banana_y = ((r * DOT_SIZE));

//        for(int i = 0; i < mics.length; i++){
//            mics[i].setFounded(false);
//        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < mics.size(); i++) {
           if(mics.get(i).getLifeCicle() == 0){
               mics.remove(i);
           }
        }
        if (inGame) {
            checkApple();
            checkCollision();
            snake.move();
            for(int i = 0; i < mics.size(); i++){
                mics.get(i).micLookForApple(apple_x, apple_y);
            }
        }
        repaint();
    }
}
