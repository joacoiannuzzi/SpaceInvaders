package edu.austral.prog2_2018c2;

public class AlienType {

    private String type, image;
    private int points;

    public AlienType() {

        int n = (int) (Math.random() * 3 + 1);

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

        int range = (300 - 50) + 1;
        int i = (int)(Math.random() * range) + 50;

        points = i;

        int range_J = (1 - 1) + 1;
        int j = (int)(Math.random() * range_J) + 1;

        if (j == 1){

            image = "src/images/duck.png";

        }
        else {

            image = "src/images/alien";
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
}
