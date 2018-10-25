package edu.austral.prog2_2018c2;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Sprite {

    private final String playerImg = "src/images/ship - Copy.png";
    private int points = 0;
    int lives = 3;
    private int speed = 2;
    private Shot[] shots = new Shot[2];
    private int kills = 0;
    private int shotStreak = 0;
    private boolean powered = false;
    private long powerTimer;
    private int endPower;
    private boolean immunity = false;
    private boolean freezeInvaders = false;
    private boolean doubleDamage = false;

    public Player() {
        ImageIcon ii = new ImageIcon(playerImg);
        width = ii.getImage().getWidth(null);
        height = ii.getImage().getHeight(null);
        setImage(ii.getImage());
        for (int i = 0; i < shots.length; i++) {
            shots[i] = new Shot();

        }
        resetPosition();
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

        if (key == KeyEvent.VK_SPACE) {

            if (!shots[0].isVisible()) {
                shots[0].appear(x, y);
            }
            else if (!shots[1].isVisible() && doubleDamage) {
                shots[1].appear(x, y);
            }
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
    public void getHit() {
        if (!immunity) {
            lives--;

            if (lives == 0)
                super.getHit();
        }
    }


    public void attack(Shot shot, Ufo ufo) {
        if (shot.hit(ufo))
            points += ufo.getPoints();
    }

    public void attack(Shot shot, Alien alien) {
         if (shot.hit(alien)) {
             kills++;
             points += alien.getAlienType().getPoints();

           if (!powered) {
               shotStreak++;
               powerUp();
           }
        }
    }

    public void attack(Shot shot, Shield shield) {
        if (shot.hit(shield) && !powered)
            shotStreak = 0;
    }

    public void powerUp() {
        if (!powered && shotStreak == SHOT_STREAK) {
            powered = true;
            powerTimer = System.currentTimeMillis();
            endPower = randomWithRange(3000, 5000);
            int random = randomWithRange(1, 10);
            if (random < 7) {
                immunity = true;
            } else if (random < 9) {
                doubleDamage = true;
            } else if (random < 11) {
                freezeInvaders = true;
            }
        }
    }

    public void powerDown() {
        if (powered && System.currentTimeMillis() - powerTimer >= endPower) {
            powered = false;
            shotStreak = 0;
            powerTimer = 0;
            immunity = false;
            freezeInvaders = false;
            doubleDamage = false;
        }
    }

    public void resetPosition() {
        setX((BOARD_WIDTH - width) / 2);
        setY(GROUND - height);
    }

    public void shotAct(Shot shot) {
        if (!shot.act() && !powered)
            shotStreak = 0;
    }

    public void draw(Graphics g, FontMetrics metr) {
        super.draw(g);
        for (Shot shot : shots) {
            shot.draw(g);
        }
        g.drawString("Score: " + points, 0,
                BOARD_HEIGHT - 24);

        g.drawString("Lives: " + lives,
                BOARD_WIDTH - metr.stringWidth("Lives: " + lives),
                BOARD_HEIGHT - 24);

        g.drawString("Streak: " + shotStreak, 0, BOARD_HEIGHT - 45);

        g.drawString("Aliens: " + (NUMBER_OF_ALIENS_TO_DESTROY - kills),
                (BOARD_WIDTH - metr.stringWidth("Aliens: " + (NUMBER_OF_ALIENS_TO_DESTROY - kills))) / 2,
                BOARD_HEIGHT - 45);
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

    public Shot[] getShots() {
        return shots;
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

    public boolean freezeInvaders() {
        return freezeInvaders;
    }
}