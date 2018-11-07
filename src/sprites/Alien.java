package sprites;

import other.Animation;
import other.SpriteSheet;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Alien extends Sprite {

    private static int speed = 1;

    public static void increaseSpeed() {
        speed++;
    }
    public static  void resetSpeed() {
        speed = 1;
    }

    protected Bomb bomb;
    private AlienType alienType;
    private SpriteSheet alienSheet;
    private HashMap<String, Animation> anim;
    private int startX, startY;

    public Alien(int x, int y) {
        alienSheet = new SpriteSheet("alien-sheet.png", 60, 54);
        anim = new HashMap<>();
        anim.put("small", new Animation(15,
                alienSheet.grabImage(3, 1),
                alienSheet.grabImage(3, 2),
                alienSheet.grabImage(3, 3)));
        anim.put("medium", new Animation(15,
                alienSheet.grabImage(2, 1),
                alienSheet.grabImage(2, 2),
                alienSheet.grabImage(2, 3)));
        anim.put("big", new Animation(15,
                alienSheet.grabImage(1, 1),
                alienSheet.grabImage(1, 2),
                alienSheet.grabImage(1, 3)));

        this.width = ALIEN_WIDTH;
        this.height = ALIEN_HEIGHT;

        bomb = new Bomb();
        startX = x;
        startY = y;
        reset();
    }

    public void reset() {

        this.alienType = new AlienType();

        this.x = startX;
        this.y = startY;
        setVisible(true);
        bomb.reset(startX, startY);
    }

    public void shoot() {
        bomb.appear(x, y);
    }

    public void act(int direction) {

        this.x += direction * speed;
        anim.get(alienType.getType()).run();
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
        return anim.get(alienType.getType()).getCurrentImage();
    }

    public int getPoints() {
        return alienType.getPoints();
    }
}