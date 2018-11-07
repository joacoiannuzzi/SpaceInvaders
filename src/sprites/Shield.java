package sprites;

import game.Commons;
import other.ImageLoader;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Shield extends Sprite {

    private int lives;
    private int shotQuantity = 0;
    private int startX;
    BufferedImage shieldImg = ImageLoader.load("shield.png");

    public Shield(int x) {
        y = GROUND - 100;
        this.startX = x;
        reset();
    }

    public void reset() {

        setImage(shieldImg);
        width = SHIELD_WIDTH;
        height = SHIELD_HEIGHT;
        x = startX;
        lives = Commons.SHIELD_LIVES;
        setVisible(true);
    }

    @Override
    public void getHit() {

        shotQuantity++;

        if (shotQuantity == 5) {

            shotQuantity = 0;

            lives = (int) (lives - Commons.SHIELD_LIVES * 0.1);

            if (lives <= 0) {
                super.getHit();
            }
        }

    }

    public int getLives() {
        return lives;
    }

    public int getShotQuantity() {
        return shotQuantity;
    }

    public void setShotQuantity(int shotQuantity) {
        this.shotQuantity = shotQuantity;
    }
}