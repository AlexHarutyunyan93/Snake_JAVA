package com.codewithmosh;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Display extends JPanel implements ActionListener {

    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 150;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;
    private int mic_x;
    private int mic_y;
    private boolean founded = false;
    private int step = 0;
    private final String[] micDirections = {"LEFT", "UP", "RIGHT", "DOWN"};
    private String prevDirection = micDirections[0];
    private String micDirection = micDirections[0];
    private int micCheckLevel = 10;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image mic;
    private Image head;

    public Display() {

        initDisplay();
    }

    private void initDisplay() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {

        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();


        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();
        mic = iih.getImage();
    }

    private void micLookForApple(){
        if(!founded){
            if(Math.abs(apple_y - mic_y) < (micCheckLevel * 10) || Math.abs(apple_x - mic_x) < (micCheckLevel * 10)){
                founded = true;
            }

            if(founded){
                micMoveToApple();
            } else {
                micMove();
            }
        } else {
            micMoveToApple();
        }
    }
    /////////////////////////////////////////////////////////////
    private void changeMoveDirection(){
        int nextDirection = (int) Math.floor(Math.random()*4);
        if(micDirections[nextDirection] != prevDirection){
            prevDirection = new String(micDirection);
            micDirection = micDirections[nextDirection];
        } else {
            changeMoveDirection();
        }
    }
    /////////////////////////////////////////////////////////////
    private void checkBords(){
        if(mic_x < 20 || mic_y < 20 || mic_x > B_WIDTH-20 || mic_y > B_HEIGHT-20) {
            changeMoveDirection();
        }
    }

    private void micMove(){
        if(step > 4){
            changeMoveDirection();
            checkBords();
            step = 0;
        }
        switch (micDirection){
            case "LEFT":
                mic_x-=10;
                break;
            case "UP":
                mic_y-=10;
                break;
            case "RIGHT":
                mic_x+=10;
                break;
            case "DOWN":
                mic_y+=10;
                break;
            default:
                break;
        }
        step++;
    }
///////////////////////////////////////
    private void micMoveToApple(){
        if(mic_x == apple_x && mic_y == apple_y) {
            locateApple();
            micCheckLevel++;
        } else if(mic_x != apple_x && mic_y != apple_y) {
            int x = B_WIDTH - mic_x - apple_x;
            int y = B_HEIGHT - mic_y - apple_y;
            if(x > y) {
                mic_x = mic_x < apple_x ? mic_x + 10 : mic_x - 10;
            } else {
                mic_y = mic_y < apple_y ? mic_y + 10 : mic_y - 10;
            }
        } else {
            if (mic_x != apple_x) {
                mic_x = mic_x < apple_x ? mic_x + 10 : mic_x - 10;
            }
            if(mic_y != apple_y){
                mic_y = mic_y < apple_y ? mic_y + 10 : mic_y - 10;
            }

        }
    }
    private void initGame() {
        mic_x = 200;
        mic_y = 200;

        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

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
            g.drawImage(mic, mic_x, mic_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

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

        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            dots++;
            locateApple();
        }
    }

    private void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
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
        founded = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {
            checkApple();
            checkCollision();
            move();

            micLookForApple();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}
