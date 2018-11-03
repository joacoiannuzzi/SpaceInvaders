package sprites;

import other.Animation;
import other.SpriteSheet;

import java.awt.image.BufferedImage;

public class Weapon extends Sprite {

    SpriteSheet sheet = new SpriteSheet("proyectiles-sheet.png", 5, 15);
    Animation anim;

    public boolean hit(Sprite entity) {
        if (entity.isVisible() && !entity.isDying() && isVisible()) {

            int entityX = entity.getX();
            int entityY = entity.getY();
            if (x - width >= (entityX)
                    && x <= (entityX + entity.getWidth())
                    && y + height >= (entityY)
                    && y <= (entityY + entity.getHeight())) {

                entity.getHit();
                die();
                return true;
            }
        }
        return false;
    }

    @Override
    public BufferedImage getCurrentImage() {
        return anim.getCurrentImage();
    }
}
