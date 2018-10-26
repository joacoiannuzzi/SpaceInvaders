package edu.austral.prog2_2018c2;

import java.awt.image.BufferedImage;

public class Ufo extends Sprite {

    private int points;
    private int direction;
    private int speed = 2;
    private long timeToSpawn;
    private long spawnTimer = System.currentTimeMillis();
    AudioPlayer quackSound = new AudioPlayer("/sounds/quack2.wav");
    private Animation duckAnim, ufoAnim;
    SpriteSheet sheet2;
    private boolean duck;

    public Ufo() {

        sheet = new SpriteSheet("/images/ufo-duck.png", 33, 36);
        BufferedImage[] images = new BufferedImage[7];
        images[0] = sheet.grabImage(1, 1);
        images[1] = sheet.grabImage(1, 2);
        images[2] = sheet.grabImage(1, 3);
        images[3] = sheet.grabImage(1, 4);
        images[4] = sheet.grabImage(2, 1);
        images[5] = sheet.grabImage(2, 2);
        images[6] = sheet.grabImage(2, 3);
        duckAnim = new Animation(0, images);

        sheet2 = new SpriteSheet("/images/ufo.png", 36, 19);
        BufferedImage[] images2 = new BufferedImage[13];
        images2[0] = sheet2.grabImage(1, 1);
        images2[1] = sheet2.grabImage(1, 2);
        images2[2] = sheet2.grabImage(1, 3);
        images2[3] = sheet2.grabImage(1, 4);
        images2[4] = sheet2.grabImage(2, 1);
        images2[5] = sheet2.grabImage(2, 2);
        images2[6] = sheet2.grabImage(2, 3);
        images2[7] = sheet2.grabImage(2, 4);
        images2[8] = sheet2.grabImage(3, 1);
        images2[9] = sheet2.grabImage(3, 2);
        images2[10] = sheet2.grabImage(3, 3);
        images2[11] = sheet2.grabImage(3, 4);
        images2[12] = sheet2.grabImage(4, 1);
        ufoAnim = new Animation(1, images2);


        setY(3);
        die();
        resetTimeToSpawn();
    }

    private void resetTimeToSpawn() {
        timeToSpawn = randomWithRange(45000, 60000);
    }

    public void initUfo() {

        points = randomWithRange(50, 300);

        if (randomWithRange(1, 10) == 1) {
            duck = true;
            width = duckAnim.getCurrentImage().getWidth();
            height = duckAnim.getCurrentImage().getHeight();
            quackSound.play();
        }
        else {
            width = ufoAnim.getCurrentImage().getWidth();
            height = ufoAnim.getCurrentImage().getHeight();
        }
        setVisible(true);
        setDying(false);
        resetTimeToSpawn();

        int random = randomWithRange(1, 2);
        if (random == 1) {
            direction = 1;
            setX(-1 * width);
        }
        else {
            direction = -1;
            setX(BOARD_WIDTH + width);
        }
    }

    public void act() {

        if (System.currentTimeMillis() - spawnTimer >= timeToSpawn && !isVisible()) {
            initUfo();
            spawnTimer = System.currentTimeMillis();
        }

        if (isVisible()) {
            this.x += direction * speed;
            if ((x > BOARD_WIDTH && direction == 1) || (x < -width && direction == -1)) {
                duck = false;
                die();
            }
            if (duck) {
                duckAnim.run();
            } else
                ufoAnim.run();

        }
    }


    public int getPoints() {
        return points;
    }

    public BufferedImage getCurrentImage() {
        if (duck) {
            return duckAnim.getCurrentImage();
        } else
            return ufoAnim.getCurrentImage();
    }
}
