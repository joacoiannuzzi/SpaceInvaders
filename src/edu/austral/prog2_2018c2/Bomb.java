package edu.austral.prog2_2018c2;

import javax.swing.*;

public class Bomb extends Weapon {

    private static int speed = 1;
    public static void increaseSpeed() {
        speed++;
    }

    private final String bombImg = "src/images/bomb.png";

    public Bomb() {

        ImageIcon ii = new ImageIcon(bombImg);
        setImage(ii.getImage());
        width = ii.getImage().getWidth(null);
        height = ii.getImage().getHeight(null);
        die();
    }

    public void initBomb(Alien alien) {

        if (randomWithRange(1, 14) == 5 && alien.isVisible() && !isVisible()) {

            setVisible(true);
            x = alien.getX();
            y = alien.getY();
        }
    }

    public void act() {

        y += speed;

        if (y >= GROUND - height) {
            die();
        }
    }
}
