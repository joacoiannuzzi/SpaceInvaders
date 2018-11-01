package sprites;

import other.Animation;
import game.Commons;
import other.SpriteSheet;

import java.awt.image.BufferedImage;

public class Alien extends Sprite {

    private static int speed = 1;

    public static void increaseSpeed() {
        speed++;
    }

    protected Bomb bomb;
    private AlienType alienType;
    private SpriteSheet sheet;
    private Animation anim;
    private int startX, startY;

    public Alien(int x, int y) {
        bomb = new Bomb();
        startX = x;
        startY = y;
        reset();
    }

    public void reset() {

        this.alienType = new AlienType();
        this.anim = alienType.getAnimation();
        this.width = 63;
        this.height = 63;
        this.x = startX;
        this.y = startY;
        setVisible(true);
        setDying(false);
        bomb.reset(startX, startY);
    }

    public void shoot() {
        bomb.appear(x, y);
    }

    public void act(int direction) {

        this.x += direction * speed;
        anim.run();
    }

    public boolean touchGround() {
        if (y + height >= GROUND) {
            return true;
        }
        return false;
    }

    public Bomb getBomb() {

        return bomb;
    }

    public AlienType getAlienType() {
        return alienType;
    }


    public BufferedImage getCurrentImage() {
        return anim.getCurrentImage();
    }

    public Animation getAnim() {
        return anim;
    }
}