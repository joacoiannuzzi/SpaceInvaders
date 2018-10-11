package edu.austral.prog2_2018c2;

import javax.swing.*;

public class Shield extends Player {

    private final String shieldImg = "src/images/shield.png";


    public Shield(int x, int y) {

        this.lives = 1;
        initShield(x, y);
    }

    public void initShield(int x, int y) {

        ImageIcon ii = new ImageIcon(shieldImg);

        setImage(ii.getImage());
        setX(x);
        setY(y);
    }

    @Override
    public void getBombed() {
        lives--;
    }


}
