package sprites;


import other.Animation;
import other.ImageLoader;
import other.SpriteSheet;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Player extends Sprite {

    private int points = 0;
    private int lives = 3;
    private int speed = 3;
    private int direction;
    private Shot[] shots = new Shot[6];
    private int kills = 0;
    private int shotStreak = 0;
    private boolean powered = false;
    private long powerTimer;
    private int endPower;
    private boolean immunity = false;
    private boolean freezeInvaders = false;
    private boolean doubleDamage = false;
    private Animation immunityForce;
    private boolean multipleShots = false;
    private HashMap<Integer, Animation> shipAnim;
    private BufferedImage heart = ImageLoader.load("heart.png");
    private BufferedImage shotPic = ImageLoader.load("shot.png");


    public Player() {

        shipAnim = new HashMap<>();

        SpriteSheet forceSheet = new SpriteSheet("force-sheet.png", 26, 32);
        SpriteSheet playerSheet = new SpriteSheet("ship-sheet.png", 120, 160);

        shipAnim.put(-1, new Animation(0,
                playerSheet.grabImage(1, 1),
                playerSheet.grabImage(2, 1),
                playerSheet.grabImage(3, 1)));

        shipAnim.put(0, new Animation(0,
                playerSheet.grabImage(1, 2),
                playerSheet.grabImage(2, 2),
                playerSheet.grabImage(3, 2)));

        shipAnim.put(1, new Animation(0,
                playerSheet.grabImage(1, 3),
                playerSheet.grabImage(2, 3),
                playerSheet.grabImage(3, 3)));

        immunityForce = new Animation(1,       //mientras mas chico mas rapido
                forceSheet.grabImage(1, 1),
                forceSheet.grabImage(1, 2),
                forceSheet.grabImage(1, 3),
                forceSheet.grabImage(1, 4),
                forceSheet.grabImage(1, 5),
                forceSheet.grabImage(1,6));

        width = PLAYER_WIDTH;
        height = PLAYER_HEIGHT;

        for (int i = 0; i < shots.length; i++) {
            shots[i] = new Shot();
        }
        resetPosition();
    }

    public void act() {

        x += direction * speed;

        if (x <= 2) {
            x = 2;
        }
        if (x >= BOARD_WIDTH - width) {
            x = BOARD_WIDTH - width;
        }
        if (immunity) {
            immunityForce.run();
        }
        powerDown();

        for (Shot shot : shots) {
            if (!shot.act() && !powered)
                shotStreak = 0;
        }
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            direction = -1 ;
        }

        if (key == KeyEvent.VK_RIGHT) {

            direction = 1;
        }

        if (key == KeyEvent.VK_SPACE) {

            if (multipleShots) {
                for (Shot shot : shots) {
                    if (!shot.isVisible()) {
                        shot.appear(x + width / 2, y);
                        return;
                    }
                }
            }

            if (!shots[0].isVisible()) {
                if (doubleDamage) {
                    shots[0].appear(x, y);
                    shots[1].appear(x + width, y);
                } else {
                    shots[0].appear(x + width / 2, y);
                }
            }
        }

        if (key == KeyEvent.VK_P) {
            powerUp();
        }
        if (key == KeyEvent.VK_L) {
            kills = NUMBER_OF_ALIENS_TO_DESTROY;
        }
        if (key == KeyEvent.VK_M) {
            if (multipleShots) {
                multipleShots = false;
                return;
            }
            multipleShots = true;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {

            direction = 0;
        }
    }

    @Override
    public void getHit() {
        if (!immunity) {
            lives--;
            shipAnim.get(-1).run();
            shipAnim.get(0).run();
            shipAnim.get(1).run();

            if (lives == 0)
                super.getHit();
        }
    }

    public void attack(Ufo ufo) {
        for (Shot shot : shots) {
            if (shot.hit(ufo))
                points += ufo.getPoints();
        }
    }

    public void attack(Alien alien) {
        for (Shot shot : shots) {
            if (shot.hit(alien)) {
                kills++;
                points += alien.getPoints();

                if (!powered) {
                    shotStreak++;
                    power();
                }
            }
        }
    }

    public void attack(Shield shield) {
        for (Shot shot : shots) {
            if (shot.hit(shield) && !powered)
                shotStreak = 0;
        }
    }

    public void power() {
        if (!powered && shotStreak == SHOT_STREAK) {
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
        setX((BOARD_WIDTH - width) / 2);
        setY(GROUND - height);
    }

    public void draw(Graphics g, FontMetrics metr) {
        super.draw(g);

        if (immunity) {
            g.drawImage(getforce(), x - 4, y - 4, x + width + 4, y + height + 4 ,
                    0, 0, getforce().getWidth(), getforce().getHeight(), null);
        }

        for (Shot shot : shots) {
            shot.draw(g);
        }

        g.drawString("Score: " + points,
                (BOARD_WIDTH - metr.stringWidth("Score: " + points)) / 2,
                BOARD_HEIGHT - 70);

        for (int i = 0; i < lives; i++) {

            int heartX = BOARD_WIDTH - 50 - (25 * i);
            int heartY = BOARD_HEIGHT - 80;

            g.drawImage(heart, heartX, heartY, heartX + 35, heartY + 35,
                    0, 0, heart.getWidth(), heart.getHeight(), null);
        }

        for (int i = 0; i < shotStreak; i++) {

            int shotX = 10 + (25 * i);
            int shotY = BOARD_HEIGHT - 75;

            g.drawImage(shotPic, shotX, shotY, shotX + 20, shotY + 30,
                    0, 0, shotPic.getWidth(), shotPic.getHeight(), null);
        }
    }

    public BufferedImage getCurrentImage() {
        return shipAnim.get(direction).getCurrentImage();
    }

    public BufferedImage getforce() {
        return immunityForce.getCurrentImage();
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

    public Shot[] getShots() {
        return shots;
    }
}