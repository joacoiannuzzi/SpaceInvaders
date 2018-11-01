package sprites;

import other.AudioPlayer;
import other.ImageLoader;

import java.awt.image.BufferedImage;

public class Shot extends Weapon {

    private int speed = 10;
    private AudioPlayer shotSound = new AudioPlayer("player-shot.wav");

    public Shot() {

        BufferedImage shotImg = ImageLoader.load("shot.png");
        setImage(shotImg);
        width = shotImg.getWidth();
        height = shotImg.getHeight();
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
