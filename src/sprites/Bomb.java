package sprites;

import other.Animation;
import other.AudioPlayer;
import other.Random;

public class Bomb extends Weapon {

    private static int speed = 1;
    public static void increaseSpeed() {
        speed++;
    }
    public static  void resetSpeed() {
        speed = 1;
    }
    //private final AudioPlayer bombSound = new AudioPlayer("alien-shot.wav");

    public Bomb() {

        anim = new Animation(5, sheet.grabImage(2, 1),
                sheet.grabImage(2, 2),
                sheet.grabImage(2, 3),
                sheet.grabImage(2, 4),
                sheet.grabImage(2, 5),
                sheet.grabImage(2, 6)
                );
        width = PROJECTILE_WIDTH;
        height = PROJECTILE_HEIGHT;
    }

    public void reset(int x, int y) {
        this.x = x;
        this.y = y;
        die();
    }

    public void appear(int x, int y) {

        if (Random.randomWithRange(1, 14) == 5 && !isVisible()) {
            setVisible(true);
            this.x = x;
            this.y = y;
            //bombSound.playFromBeginning();
        }
    }

    public void act() {

        y += speed;
        anim.run();

        if (y + height >= GROUND) {
            die();
        }
    }
}
