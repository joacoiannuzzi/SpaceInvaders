package sprites;

import game.Commons;

public class AlienType implements Commons {

    private String type;
    private int points;

    public AlienType() {

        int n = randomWithRange(1, 3);

        switch (n) {
            case 1:
                type = "big";
                points = 10;
                break;
            case 2:
                type = "medium";
                points = 20;

                break;
            case 3:
                type = "small";
                points = 30;
                break;
        }

    }

    public String getType() {
        return type;
    }

    public int getPoints() {
        return points;
    }

}
