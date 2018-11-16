package sprites;

import game.Commons;
import other.Animation;
import other.SpriteSheet;

import java.awt.image.BufferedImage;

public class Shield extends Sprite {

    private int lives;
    private int shotQuantity = 0;
    private int startX;

    private Animation[] anim;
    private int currentState;

    public Shield(int x) {
        SpriteSheet sheet = new SpriteSheet("shield-sheet.png", 42, 38);
        anim = new Animation[3];
        anim[0] = new Animation(2,
                sheet.grabImage(1, 1),
                sheet.grabImage(1, 2),
                sheet.grabImage(1, 3),
                sheet.grabImage(1, 4),
                sheet.grabImage(1, 5));
        anim[1] = new Animation(2,
                sheet.grabImage(2, 1),
                sheet.grabImage(2, 2),
                sheet.grabImage(2, 3),
                sheet.grabImage(2, 4),
                sheet.grabImage(2, 5));
        anim[2] = new Animation(2,
                sheet.grabImage(3, 1),
                sheet.grabImage(3, 2),
                sheet.grabImage(3, 3),
                sheet.grabImage(3, 4),
                sheet.grabImage(3, 5));

        y = GROUND - 100;
        this.startX = x;
        width = SHIELD_WIDTH;
        height = SHIELD_HEIGHT;
        reset();
    }

    public void reset() {
        x = startX;
        lives = Commons.SHIELD_LIVES;
        currentState = 0;
        setVisible(true);
    }

    public void update() {
        anim[currentState].run();
    }

    @Override
    public void getHit() {

        shotQuantity++;

        if (shotQuantity == 5) {

            shotQuantity = 0;

            lives = (int) (lives - SHIELD_LIVES * 0.1);

            if (lives <= (SHIELD_LIVES * 0.6) && currentState == 0) {
                currentState = 1;
            }
            if (lives <= (SHIELD_LIVES * 0.3) && currentState == 1) {
                currentState = 2;
            }


            if (lives <= 0) {
                super.getHit();
            }
        }

    }

    @Override
    public BufferedImage getCurrentImage() {
        return anim[currentState].getCurrentImage();
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