package edu.austral.prog2_2018c2;

public class AlienType {

    private String type;
    private int points;

    public AlienType() {

        int n = (int) (Math.random() * 3 + 1);

        switch (n) {
            case 1:
                type = "BigAlien";
                points = 10;
                break;
            case 2:
                type = "MediumAlien";
                points = 20;
                break;
            case 3:
                type = "SmallAlien";
                points = 30;
                break;
        }
    }
}
