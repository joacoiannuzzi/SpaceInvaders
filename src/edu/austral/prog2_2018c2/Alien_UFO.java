package edu.austral.prog2_2018c2;

public class Alien_UFO {

    private void Alien_UFO_Init() {

        int n = (int) (Math.random() * 10 + 1);

        if (n == 1) {
             String alienImg = "src/images/Duck.png";
        } else {
             String alienImg = "src/images/alien.png";
        }
    }
}
