package edu.austral.prog2_2018c2;

import java.awt.image.BufferedImage;

public class Animation {

    private int speed, count, index, frames;
    private BufferedImage currentImage;
    private BufferedImage[] images;

    public Animation(int speed, BufferedImage... images) {
        this.speed = speed;
        this.images = images;
        this.frames = images.length;
        currentImage = images[0];
    }

    public void run() {
        count++;
        if (count > speed) {
            count = 0;
            nextImage();
        }
    }

    private void nextImage() {
        currentImage = images[index];
        index++;
        if (index >= frames) {
            index = 0;
        }
    }

    public void reset() {
        currentImage = images[0];
        count = index = 0;
    }

    public BufferedImage getCurrentImage() {
        return currentImage;
    }
}
