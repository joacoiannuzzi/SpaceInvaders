package sprites;

import game.Commons;
import other.Animation;
import other.ImageLoader;
import other.SpriteSheet;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Shield extends Sprite {

    private int lives;
    private int shotQuantity = 0;
    private int startX;
    BufferedImage shieldImg = ImageLoader.load("shield.png");
    private SpriteSheet sheet = new SpriteSheet("shield-sheet.png", 101, 99);
    private Animation anim;

    public Shield(int x) {
        BufferedImage[] images = new BufferedImage[20];
        int n = 0;
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 5; j++) {
                images[n] = sheet.grabImage(i, j);
                n++;
            }
        }
        anim = new Animation(5, images);
        y = GROUND - 100;
        this.startX = x;
        width = SHIELD_WIDTH;
        height = SHIELD_HEIGHT;
        reset();
    }

    public void reset() {

        //setImage(shieldImg);
        x = startX;
        lives = Commons.SHIELD_LIVES;
        setVisible(true);
    }

    public void update() {
        anim.run();
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

    @Override
    public BufferedImage getCurrentImage() {
        return shieldImg;
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