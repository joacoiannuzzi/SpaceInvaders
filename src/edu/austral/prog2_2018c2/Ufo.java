package edu.austral.prog2_2018c2;

import javax.swing.*;

public class Ufo extends Sprite {

    private String UfoImg;
    private int points;
    private int direction;
    private int speed = 2;
    private long timeToSpawn = randomWithRange(45000, 60000);
    private long spawnTimer = System.currentTimeMillis();
    AudioPlayer quackSound = new AudioPlayer("/sounds/quack.wav");

    public Ufo() {
        name = "UFO";
    }

    public void initUfo() {

        points = randomWithRange(50, 300);

        if (randomWithRange(1, 10) == 1) {
            UfoImg = "src/images/duck.png";
            quackSound.play();
        }
        else {
            UfoImg = "src/images/alien.png";
        }

        ImageIcon ii = new ImageIcon(UfoImg);
        setImage(ii.getImage());
        width = ii.getImage().getWidth(null);
        height = ii.getImage().getHeight(null);
        setVisible(true);
        setDying(false);
        timeToSpawn = randomWithRange(45000, 60000);

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

        if (isVisible()) {
            this.x += direction * speed;
            if ((x > BOARD_WIDTH && direction == 1) || (x < -width && direction == -1)) {
                die();
            }
        }
    }

    public void spawn() {

        if (System.currentTimeMillis() - spawnTimer >= timeToSpawn && !isVisible()) {
            initUfo();
            spawnTimer = System.currentTimeMillis();
        }
    }

    public int getPoints() {
        return points;
    }
}
