package edu.austral.prog2_2018c2;

import javax.swing.*;

public class Ufo extends Sprite {

    private String UfoImg;
    private int points;
    private int direction;
    private int speed = 2;

    public Ufo() {
        name = "UFO";
    }

    public void initUfo() {

        points = randomWithRange(50, 300);

        if (randomWithRange(1, 10) == 1) {

            UfoImg = "src/images/duck.png";
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

        this.x += direction * speed;

        if ((x > BOARD_WIDTH && direction == 1) || (x < -1 * width && direction == -1)) {
            die();
        }
    }

    public boolean spawn(long time) {

        int random = randomWithRange(1, 500);

        if ((time >= 45000 && time <= 60000 && random == 1)
                || (time > 60000)) {

            initUfo();
            return true;
        }
        return false;
    }

    public int getPoints() {
        return points;
    }
}
