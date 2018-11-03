package sprites;

import other.Animation;
import other.AudioPlayer;

public class Shot extends Weapon {

    private int speed = 7;
    private AudioPlayer shotSound = new AudioPlayer("player-shot.wav");

    public Shot() {

        anim = new Animation(5, sheet.grabImage(1, 1),
                sheet.grabImage(1,2),
                sheet.grabImage(1,3),
                sheet.grabImage(1,4),
                sheet.grabImage(1,5),
                sheet.grabImage(1,6)
                );

        width = PROJECTILE_WIDTH;
        height = PROJECTILE_HEIGHT;
        die();
    }


    public void appear(int x, int y) {

        setX(x);
        setY(y - 5);
        setVisible(true);
        shotSound.playFromBeginning();
    }

    public boolean act() {
        if (isVisible()) {
            y -= speed;
            anim.run();

            if (y > 0) {
                setY(y);
                return true;
            }
            die();
            return false;
        }
        return true;
    }
}
