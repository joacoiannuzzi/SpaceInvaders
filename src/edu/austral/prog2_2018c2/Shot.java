package edu.austral.prog2_2018c2;

import javax.swing.ImageIcon;

public class Shot extends Weapon {

    private int speed = 4;

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
