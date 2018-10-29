package sprites;


import other.Animation;
import game.Commons;
import other.SpriteSheet;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Sprite {

    private final String playerImg = "/images/ship - Copy.png";
    private int points = 0;
    int lives = 3;
    private int speed = 3;
    int dx;
    //private Shot[] shots = new Shot[2];
    private int kills = 0;
    private int shotStreak = 0;
    private boolean powered = false;
    private long powerTimer;
    private int endPower;
    private boolean immunity = false;
    private boolean freezeInvaders = false;
    private boolean doubleDamage = false;
    private Animation blue;
    private SpriteSheet force;


    public Player() {

        force = new SpriteSheet("/images/force-sheet.png", 26, 32);
        sheet = new SpriteSheet(playerImg, 18, 24);
        blue = new Animation(1,
                force.grabImage(1, 1),
                force.grabImage(1, 2),
                force.grabImage(1, 3),
                force.grabImage(1, 4),
                force.grabImage(1, 5),
                force.grabImage(1,6));

        width = sheet.grabImage(1, 1).getWidth();
        height = sheet.grabImage(1, 1).getHeight();

//        for (int i = 0; i < shots.length; i++) {
//            shots[i] = new Shot();
//        }
        resetPosition();
    }

    public void act() {

        x += dx;

        if (x <= 2) {
            x = 2;
        }

        if (x >= Commons.BOARD_WIDTH - width) {
            x = Commons.BOARD_WIDTH - width;
        }
        if (immunity) {
            blue.run();
        }

        powerDown();
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = -1 * speed;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = speed;
        }

//        if (key == KeyEvent.VK_SPACE) {
//
//            if (!shots[0].isVisible()) {
//                if (doubleDamage) {
//                    shots[0].appear(x, y);
//                    shots[1].appear(x + width, y);
//                } else {
//                    shots[0].appear(x + width / 2, y);
//                }
//            }
//        }

        if (key == KeyEvent.VK_P) {
            powerUp();
        }
        if (key == KeyEvent.VK_L) {
            kills = Commons.NUMBER_OF_ALIENS_TO_DESTROY;
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
               power();
           }
        }
    }

    public void attack(Shot shot, Shield shield) {
        if (shot.hit(shield) && !powered)
            shotStreak = 0;
    }

    public void power() {
        if (!powered && shotStreak == Commons.SHOT_STREAK) {
            powerUp();
        }
    }

    public void powerUp() {
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
        setX((Commons.BOARD_WIDTH - width) / 2);
        setY(Commons.GROUND - height);
    }

    public void shotAct(Shot shot) {
        if (!shot.act() && !powered)
            shotStreak = 0;
    }

    public void draw(Graphics g, FontMetrics metr) {
        super.draw(g);
        if (immunity) {
            g.drawImage(getforce(), x - 4, y - 4, null);
        }

//        for (Shot shot : shots) {
//            shot.draw(g);
//        }
        g.drawString("Score: " + points, 0,
                Commons.BOARD_HEIGHT - 24);

        g.drawString("Lives: " + lives,
                Commons.BOARD_WIDTH - metr.stringWidth("Lives: " + lives),
                Commons.BOARD_HEIGHT - 24);

        g.drawString("Streak: " + shotStreak, 0, Commons.BOARD_HEIGHT - 45);

        g.drawString("Aliens: " + (Commons.NUMBER_OF_ALIENS_TO_DESTROY - kills),
                (Commons.BOARD_WIDTH - metr.stringWidth("Aliens: " + (Commons.NUMBER_OF_ALIENS_TO_DESTROY - kills))) / 2,
                Commons.BOARD_HEIGHT - 45);
    }

    public BufferedImage getCurrentImage() {
        return sheet.grabImage(1, 1);
    }

    public BufferedImage getforce() {
        return blue.getCurrentImage();
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

//    public Shot[] getShots() {
//        return shots;
//    }


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

    public boolean isDoubleDamage() {
        return doubleDamage;
    }
}