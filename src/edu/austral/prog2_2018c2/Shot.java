package edu.austral.prog2_2018c2;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Shot extends Weapon {

    private int speed = 4;
    private final int H_SPACE = 6;
    private final int V_SPACE = 1;
    private AudioPlayer shotSound = new AudioPlayer("/sounds/player-shot.wav");

    public Shot() {

        BufferedImage shotImg = null;
        try {
            shotImg = ImageIO.read(getClass().getResourceAsStream("/images/shot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setImage(shotImg);
        width = shotImg.getWidth();
        height = shotImg.getHeight();
        die();
    }


    public void appear(int x, int y) {

        setX(x + H_SPACE);
        setY(y - V_SPACE);
        setVisible(true);
        shotSound.play();
    }

    public boolean act() {
        y -= speed;

        if (y > 0) {
            setY(y);
            return true;
        }
        die();
        return false;
    }

    public void animate() {

    }

}
