package edu.austral.prog2_2018c2;

public class Weapon extends Sprite {

    public boolean hit(Sprite entity) {
        if (entity.isVisible() && isVisible()) {

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
}
