package edu.austral.prog2_2018c2;

public class AlienType {

    private String type, image;
    private int points;

    public AlienType() {

        int n = randomWithRange(1, 3);

        switch (n) {
            case 1:
                type = "BigAlien";
                points = 10;
                image = "src/images/big_alien.png";
                break;
            case 2:
                type = "MediumAlien";
                points = 20;
                image = "src/images/medium_alien.png";
                break;
            case 3:
                type = "SmallAlien";
                points = 30;
                image = "src/images/small_alien.png";
                break;
        }
    }

    public AlienType(String ufo) {

        type = "UFO";

        points = randomWithRange(50, 300);

        int n = randomWithRange(1, 10);

        if (n == 1) {
            image = "src/images/duck.png";
        } else {
            image = "src/images/alien.png";
        }

    }

    public String getType() {
        return type;
    }

    public int getPoints() {
        return points;
    }

    public String getImage() {
        return image;
    }

    public int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
