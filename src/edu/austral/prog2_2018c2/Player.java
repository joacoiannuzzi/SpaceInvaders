package edu.austral.prog2_2018c2;


import javax.swing.*;
import java.awt.event.KeyEvent;

public class Player extends Sprite {

    private final String playerImg = "src/images/resize.png";
    private int points = 0;

    int lives = 3;
    private int speed = 2;

    public Player() {
        name = "PLAYER";
        initPlayer();
    }

    private void initPlayer() {

        ImageIcon ii = new ImageIcon(playerImg);
        width = ii.getImage().getWidth(null);
        height = ii.getImage().getHeight(null);
        setImage(ii.getImage());
        setX((BOARD_WIDTH - width) / 2);
        setY(GROUND - height);
    }

    public void act() {

        x += dx;

        if (x <= 2) {
            x = 2;
        }

        if (x >= BOARD_WIDTH - width) {
            x = BOARD_WIDTH - width;
        }
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = -1 * speed;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = speed;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 0;
        }
    }

    @Override
    public void getShot() {

        lives--;

        if (lives == 0) {
            super.getShot();
        }
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getLives() {
        return lives;
    }

    public void increaseSpeed() {
        this.speed++;
    }

    public int getPoints() {
        return points;
    }
}