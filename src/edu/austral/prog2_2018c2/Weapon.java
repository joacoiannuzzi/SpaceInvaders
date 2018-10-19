package edu.austral.prog2_2018c2;

public class Weapon extends Sprite {

    public boolean hit(Sprite entity) {
        if (entity.isVisible()) {

            int entityX = entity.getX();
            int entityY = entity.getY();
            if (x >= (entityX)
                    && x <= (entityX + entity.getWidth())
                    && y >= (entityY)
                    && y <= (entityY + entity.getHeight())) {

                entity.getShot();
                die();
                return true;
            }
        }
        return false;
    }
}
