package sprites;

import other.Animation;
import other.Random;
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
    private HashMap<Integer, Animation> anim;
    private int startX, startY;
    private int[] points;
    private int currentType;

    public Alien(int x, int y) {
        SpriteSheet alienSheet = new SpriteSheet("alien-sheet.png", 60, 54);
        anim = new HashMap<>();
        anim.put(30, new Animation(15,
                alienSheet.grabImage(3, 1),
                alienSheet.grabImage(3, 2),
                alienSheet.grabImage(3, 3)));
        anim.put(20, new Animation(15,
                alienSheet.grabImage(2, 1),
                alienSheet.grabImage(2, 2),
                alienSheet.grabImage(2, 3)));
        anim.put(10, new Animation(15,
                alienSheet.grabImage(1, 1),
                alienSheet.grabImage(1, 2),
                alienSheet.grabImage(1, 3)));

        this.width = ALIEN_WIDTH;
        this.height = ALIEN_HEIGHT;

        bomb = new Bomb();
        startX = x;
        startY = y;

        points = new int[3];
        points[0] = 30;
        points[1] = 20;
        points[2] = 10;

        reset();
    }

    public void reset() {

        currentType = Random.randomWithRange(0, 2);

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
        anim.get(points[currentType]).run();
    }

    public boolean touchGround() {
        return y + height >= GROUND;
    }

    public Bomb getBomb() {

        return bomb;
    }


    public BufferedImage getCurrentImage() {
        return anim.get(points[currentType]).getCurrentImage();
    }

    public int getPoints() {
        return points[currentType];
    }
}