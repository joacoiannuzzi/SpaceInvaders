package edu.austral.prog2_2018c2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite implements Commons {

    private boolean visible;
    private Image image;
    int x;
    int y;
    private boolean dying;
    int dx;
    int width;
    int height;
    SpriteSheet sheet;


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

    public void setImage(Image image) {

        this.image = image;
    }

    public Image getCurrentImage() {

        return image;
    }


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

    public void setDying(boolean dying) {

        this.dying = dying;
    }

    public boolean isDying() {

        return this.dying;
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
        ImageIcon explosion = new ImageIcon("src/images/explosion.png");
        setImage(explosion.getImage());
        setDying(true);
    }

    public void draw(Graphics g) {
        if (isVisible()) {

            g.drawImage(getCurrentImage(), x, y, null);
        }

        if (isDying()) {
            die();
        }
    }

    public SpriteSheet getSheet() {
        return sheet;
    }
}
