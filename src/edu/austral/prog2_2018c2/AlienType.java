package edu.austral.prog2_2018c2;

public class AlienType {

    private String type;

    public AlienType() {

        int n = (int) (Math.random() * 3 + 1);

        switch (n) {
            case 1:
                type = "BigAlien";
                break;
            case 2:
                type = "MediumAlien";
                break;
            case 3:
                type = "SmallAlien";
                break;
        }
    }
}
