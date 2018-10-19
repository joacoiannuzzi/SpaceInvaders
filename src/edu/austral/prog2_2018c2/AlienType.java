package edu.austral.prog2_2018c2;

public class AlienType implements Commons {

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
