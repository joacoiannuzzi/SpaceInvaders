package edu.austral.prog2_2018c2;

import javax.swing.*;

public class UFO extends Alien {


    public UFO(int x, int y) {
        super(x, y);
    }

    private void initUFO(int x, int y) {

        int n = (int) (Math.random() * 10 + 1);

        if (n == 1) {
            alienImg = "src/images/duck.png";
        } else {
            alienImg = "src/images/alien.png";
        }


        bomb = new Bomb(x, y);
        ImageIcon ii = new ImageIcon(alienImg);
        setImage(ii.getImage());


    }
}
