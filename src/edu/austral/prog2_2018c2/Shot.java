package edu.austral.prog2_2018c2;

import javax.swing.ImageIcon;

public class Shot extends Weapon {

    private int speed = 4;
    private int streak = 0;

    private final String shotImg = "src/images/shot.png";
    private final int H_SPACE = 6;
    private final int V_SPACE = 1;


    public void initShot(int x, int y) {

        ImageIcon ii = new ImageIcon(shotImg);
        setImage(ii.getImage());

        setX(x + H_SPACE);
        setY(y - V_SPACE);
        setVisible(true);
    }

    public void act() {
        y -= speed;

        if (y < 0) {
            die();
            streak = 0;
        }
        else {
            setY(y);
        }
    }

    public int getStreak() {
        return streak;
    }

    public void resetStreak() {
        this.streak = 0;
    }

    public void increaseStreak() {
        streak++;
    }

}
