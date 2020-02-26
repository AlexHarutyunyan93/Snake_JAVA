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
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Display extends JPanel implements ActionListener {

    private final int B_WIDTH = 600;
    private final int B_HEIGHT = 400;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 150;

    private int apple_x;
    private int apple_y;

    private boolean inGame = true;

    private Timer timer;
    private Image apple;
    private Image[] micImages;
    private Mic[] mics = new Mic[10];
    private Snake snake;

    public Display() {

        snake = new Snake(900);

        for(int i = 0; i < mics.length; i++){
            mics[i] = new Mic(300,150);
        }
        micImages = new Image[10];
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
        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");

        for(int i = 0; i < mics.length; i++){
            micImages[i] = iih.getImage();
        }
    }

    private void initGame() {

        locateApple();

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

            for(int i = 0; i < mics.length; i++){
                g.drawImage(micImages[i], mics[i].getX(), mics[i].getY(), this);
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
        if ((snake.getX(0) == apple_x) && (snake.getY(0) == apple_y)) {
            snake.setDots();
            locateApple();
        } else{
            for(int i = 0; i < mics.length; i++){
               if ((mics[i].getX()== apple_x) && (mics[i].getY()== apple_y)){
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

        for(int i = 0; i < mics.length; i++){
            mics[i].setFounded(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {
            checkApple();
            checkCollision();
            snake.move();
            for(int i = 0; i < mics.length; i++){
                mics[i].micLookForApple(apple_x, apple_y);
            }
        }
        repaint();
    }
}
