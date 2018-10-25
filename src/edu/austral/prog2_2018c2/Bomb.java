package edu.austral.prog2_2018c2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bomb extends Weapon {

    private static int speed = 1;
    public static void increaseSpeed() {
        speed++;
    }
    private final AudioPlayer bombSound = new AudioPlayer("/sounds/alien-shot.wav");

    public Bomb() {
        BufferedImage bombImg = null;
        try {
            bombImg = ImageIO.read(getClass().getResourceAsStream("/images/bomb.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setImage(bombImg);
        width = bombImg.getWidth();
        height = bombImg.getHeight();
    }

    public void reset(int x, int y) {
        this.x = x;
        this.y = y;
        die();
    }

    public void appear(int x, int y) {

        if (randomWithRange(1, 14) == 5 && !isVisible()) {
            setVisible(true);
            this.x = x;
            this.y = y;
            bombSound.play();
        }
    }

    public void act() {

        y += speed;

        if (y + height >= GROUND) {
            die();
        }
    }
}
