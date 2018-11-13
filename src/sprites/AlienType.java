package sprites;

import game.Commons;
import other.Random;

public class AlienType implements Commons {

    private String type;

    public AlienType() {

        int n = Random.randomWithRange(1, 3);

        switch (n) {
            case 1:
                type = "100";
                break;
            case 2:
                type = "66";
                break;
            case 3:
                type = "33";
                break;

        }

    }

    public String getType() {
        return type;
    }
}
