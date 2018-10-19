package edu.austral.prog2_2018c2;

import javax.swing.ImageIcon;

public class Alien extends Sprite {

    private static int speed = 1;
    private static int direction;

    public static void increaseSpeed() {
        speed++;
    }

    protected Bomb bomb;
    private String alienImg;
    private AlienType alienType;

    public Alien() {
        name = "ALIEN";
        bomb = new Bomb();
    }

    public void initAlien(int x, int y) {

        this.alienType = new AlienType();
        alienImg = alienType.getImage();

        ImageIcon ii = new ImageIcon(alienImg);
        setImage(ii.getImage());
        width = ii.getImage().getWidth(null);
        height = ii.getImage().getHeight(null);
        this.x = x;
        this.y = y;
        setVisible(true);
        setDying(false);
        direction = -1;
    }

    public void act() {

        this.x += direction * speed;
    }

    public boolean changeDirection() {

        if ((x >= BOARD_WIDTH - ALIEN_WITDH - 10 && direction != -1)
                || (x <= 10 && direction != 1)) {

            direction = -1 * direction;
            //y += GO_DOWN;
            return true;
        }
        return false;
    }

    public boolean touchGround() {
        if (y > GROUND - height) {
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

}