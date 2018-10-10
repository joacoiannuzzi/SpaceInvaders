package edu.austral.prog2_2018c2;

import javax.swing.*;

public class Shield extends Player {

    private final String shieldImg = "src/images/shield.png";


    public Shield() {

        this.lives = 50;
        initShield();
    }

    public void initShield() {

        ImageIcon ii = new ImageIcon(shieldImg);

        width = ii.getImage().getWidth(null);

        setImage(ii.getImage());
        setX(5);
        setY(270);
    }
}
