package edu.austral.prog2_2018c2;


import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends Sprite {

    private final String playerImg = "src/images/ship - Copy.png";
    private int points = 0;
    int lives = 3;
    private int speed = 2;
    private Shot shot;
    private int kills = 0;
    private int shotStreak = 0;
    private boolean powered = false;
    private long powerTimer;
    private int endPower;
    private boolean immunity = false;

    public Player() {
        name = "PLAYER";
        initPlayer();
    }

    private void initPlayer() {

        ImageIcon ii = new ImageIcon(playerImg);
        width = ii.getImage().getWidth(null);
        height = ii.getImage().getHeight(null);
        setImage(ii.getImage());
        resetPosition();
        shot = new Shot();
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

        if (key == KeyEvent.VK_SPACE && !shot.isVisible())
            shot.initShot(x, y);

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
    public void getHit() {
        if (!immunity) {
            lives--;

            if (lives == 0)
                super.getHit();
        }
    }
    public void attack(Ufo ufo) {
        if (shot.hit(ufo))
            points += ufo.getPoints();
    }

    public void attack(Alien alien) {
        if (shot.hit(alien)) {
            kills++;
            points += alien.getAlienType().getPoints();

            if (!powered) {
                shotStreak++;
                powerUp();
            }
        }
    }

    public void attack(Shield shield) {
        if (shot.hit(shield))
            shotStreak = 0;

    }

    public void powerUp() {
        if (!powered && shotStreak == SHOT_STREAK) {
            powered = true;
            immunity = true;
            powerTimer = System.currentTimeMillis();
            endPower = randomWithRange(3000, 5000);
        }
    }

    public void unPower() {
        if (powered && System.currentTimeMillis() - powerTimer >= endPower) {
            powered = false;
            immunity = false;
            shotStreak = 0;
            powerTimer = 0;
        }
    }

    public void resetPosition() {
        setX((BOARD_WIDTH - width) / 2);
        setY(GROUND - height);
    }

    public void shotAct() {
        if (!shot.act())
            shotStreak = 0;
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

    public Shot getShot() {
        return shot;
    }

    public boolean isPowered() {
        return powered;
    }

    public int getKills() {
        return kills;
    }

    public void resetKills() {
        kills = 0;
    }

    public int getShotStreak() {
        return shotStreak;
    }

    public boolean isImmune(){return immunity;}
}