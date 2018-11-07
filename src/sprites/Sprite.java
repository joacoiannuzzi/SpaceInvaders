package sprites;

import game.Commons;
import other.ImageLoader;
import other.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Sprite implements Commons {

    private boolean visible;
    int x;
    int y;
    int width;
    int height;


    public Sprite() {

        visible = true;
    }

    public void die() {

        visible = false;
    }

    public boolean isVisible() {

        return visible;
    }

    protected void setVisible(boolean visible) {

        this.visible = visible;
    }

    public abstract BufferedImage getCurrentImage();


    public void setX(int x) {

        this.x = x;
    }

    public void setY(int y) {

        this.y = y;
    }

    public int getY() {

        return y;
    }

    public int getX() {

        return x;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void getHit() {
        die();
    }

    public void draw(Graphics g) {
        if (isVisible()) {

            g.drawImage(getCurrentImage(), x, y, x + width, y + height, 0, 0,
                    getCurrentImage().getWidth(), getCurrentImage().getHeight(), null);
        }
    }
}
