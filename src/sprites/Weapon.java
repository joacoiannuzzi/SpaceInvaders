package sprites;

public class Weapon extends Sprite {

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
}
