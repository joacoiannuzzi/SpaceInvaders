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
                image = "src/images/duck.png";
                break;
            case 2:
                type = "MediumAlien";
                points = 20;
                image = "src/images/alien.png";
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

        //points = 10;  un random con los puntos

        image = "src/images/duck.png";
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
