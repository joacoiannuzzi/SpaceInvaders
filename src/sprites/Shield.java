package sprites;

import game.Commons;

import javax.swing.*;

public class Shield extends Sprite {

    private final String shieldImg = "src/images/shield.png";
    private int lives;
    private int shotQuantity = 0;
    private int startX;

    public Shield(int x) {
        y = Commons.GROUND - 60;
        this.startX = x;
        reset();
    }

    public void reset() {

        ImageIcon ii = new ImageIcon(shieldImg);
        setImage(ii.getImage());
        width = ii.getImage().getWidth(null);
        height = ii.getImage().getHeight(null);
        x = startX;
        lives = Commons.SHIELD_LIVES;
        setDying(false);
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