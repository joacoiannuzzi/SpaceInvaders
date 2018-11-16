package sprites;

import other.Animation;
import other.AudioPlayer;
import other.Random;
import other.SpriteSheet;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Ufo extends Sprite {

    private int points;
    private int direction;
    private int speed = 2;
    private long timeToSpawn;
    private long spawnTimer = System.currentTimeMillis();
    private AudioPlayer quackSound = new AudioPlayer("quack2.wav");
    private HashMap<String, Animation> anim;
    private String type;

    public Ufo() {

        anim = new HashMap<>();

        SpriteSheet duckSheet = new SpriteSheet("duck-sheet.png", 173, 190);
        BufferedImage[] duckImages = new BufferedImage[15];
        int n = 0;
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 5; j++) {
                duckImages[n] = duckSheet.grabImage(i, j);
                n++;
            }
        }
        anim.put("duck", new Animation(10, duckImages));

        SpriteSheet ufoSheet = new SpriteSheet("ufo-sheet.png", 200, 106);
        BufferedImage[] ufoImages = new BufferedImage[32];
        int h = 0;
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 8; j++) {
                ufoImages[h] = ufoSheet.grabImage(i, j);
                h++;
            }
        }
        anim.put("ufo", new Animation(0, ufoImages));


        setY(3);
        die();
        resetTimeToSpawn();
    }

    private void resetTimeToSpawn() {
        timeToSpawn = Random.randomWithRange(45000, 60000);
    }

    public void appear() {

        points = Random.randomWithRange(50, 300);

        if (Random.randomWithRange(1, 10) == 1) {
            type = "duck";
            width = DUCK_WIDTH;
            height = DUCK_HEIGHT;
            quackSound.playFromBeginning();
        }
        else {
            type = "ufo";
            width = UFO_WIDTH;
            height = UFO_HEIGHT;
        }

        setVisible(true);
        resetTimeToSpawn();

        int random = Random.randomWithRange(1, 2);
        if (random == 1) {
            direction = 1;
            setX(-width);
        }
        else {
            direction = -1;
            setX(BOARD_WIDTH + width);
        }
    }

    public void act() {

        if (System.currentTimeMillis() - spawnTimer >= timeToSpawn && !isVisible()) {
            appear();
            spawnTimer = System.currentTimeMillis();
        }

        if (isVisible()) {

            this.x += direction * speed;

            if ((x > BOARD_WIDTH && direction == 1) || (x < -width && direction == -1)) {
                die();
            }

            anim.get(type).run();

        }
    }


    public int getPoints() {
        return points;
    }

    public BufferedImage getCurrentImage() {
        return anim.get(type).getCurrentImage();
    }
}
