package sprites;

import game.Commons;
import other.Animation;
import other.ImageLoader;
import other.SpriteSheet;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Shield extends Sprite {

    private int lives;
    private int shotQuantity = 0;
    private int startX;

    private ShieldLife ShiledLife;
    private SpriteSheet alienSheet;
    private HashMap<String, Animation> anim;

    public Shield(int x) {
        SpriteSheet sheet = new SpriteSheet("shield-sheet.png", 42, 38);
        anim = new HashMap<>();
        anim.put("100", new Animation(15,
                alienSheet.grabImage(1, 1),
                alienSheet.grabImage(1, 2),
                alienSheet.grabImage(1, 3),
                alienSheet.grabImage(1, 4),
                alienSheet.grabImage(1, 5),
                alienSheet.grabImage(2, 1),
                alienSheet.grabImage(2, 2),
                alienSheet.grabImage(2, 3),
                alienSheet.grabImage(2, 4),
                alienSheet.grabImage(2, 5),
                alienSheet.grabImage(3, 1),
                alienSheet.grabImage(3, 2),
                alienSheet.grabImage(3, 3),
                alienSheet.grabImage(3, 4),
                alienSheet.grabImage(3, 5)));
        anim.put("66", new Animation(15,
                alienSheet.grabImage(1, 1),
                alienSheet.grabImage(1, 2),
                alienSheet.grabImage(1, 3),
                alienSheet.grabImage(1, 4),
                alienSheet.grabImage(1, 5),
                alienSheet.grabImage(2, 1),
                alienSheet.grabImage(2, 2),
                alienSheet.grabImage(2, 3),
                alienSheet.grabImage(2, 4),
                alienSheet.grabImage(2, 5),
                alienSheet.grabImage(3, 1),
                alienSheet.grabImage(3, 2),
                alienSheet.grabImage(3, 3),
                alienSheet.grabImage(3, 4),
                alienSheet.grabImage(3, 5)));
        anim.put("33", new Animation(15,
                alienSheet.grabImage(1, 1),
                alienSheet.grabImage(1, 2),
                alienSheet.grabImage(1, 3),
                alienSheet.grabImage(1, 4),
                alienSheet.grabImage(1, 5),
                alienSheet.grabImage(2, 1),
                alienSheet.grabImage(2, 2),
                alienSheet.grabImage(2, 3),
                alienSheet.grabImage(2, 4),
                alienSheet.grabImage(2, 5),
                alienSheet.grabImage(3, 1),
                alienSheet.grabImage(3, 2),
                alienSheet.grabImage(3, 3),
                alienSheet.grabImage(3, 4),
                alienSheet.grabImage(3, 5)));

        y = GROUND - 100;
        this.startX = x;
        width = SHIELD_WIDTH;
        height = SHIELD_HEIGHT;
        reset();
    }

    public void reset() {
        x = startX;
        lives = Commons.SHIELD_LIVES;
        setVisible(true);
    }

    public void update() {
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
        return anim.get(ShieldLife.getType()).getCurrentImage();
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