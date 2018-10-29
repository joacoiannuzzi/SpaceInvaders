package sprites;

import other.AudioPlayer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Shot extends Weapon {

    private int speed = 10;
    private AudioPlayer shotSound = new AudioPlayer("/sounds/player-shot.wav");

    public Shot() {

        BufferedImage shotImg = null;
        try {
            shotImg = ImageIO.read(getClass().getResourceAsStream("/images/shot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setImage(shotImg);
        width = shotImg.getWidth();
        height = shotImg.getHeight();
        die();
    }


    public void appear(int x, int y) {

        setX(x);
        setY(y - 5);
        setVisible(true);
        shotSound.play();
    }

    public boolean act() {
        y -= speed;

        if (y > 0) {
            setY(y);
            return true;
        }
        die();
        return false;
    }

}
