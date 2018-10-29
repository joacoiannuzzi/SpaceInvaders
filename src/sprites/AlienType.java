package sprites;

import other.Animation;
import game.Commons;
import other.SpriteSheet;

public class AlienType implements Commons {

    private String type, image;
    private int points, width, height;
    private SpriteSheet sheet;
    private Animation animation;

    public AlienType() {

        int n = randomWithRange(1, 3);

        switch (n) {
            case 1:
                type = "BigAlien";
                points = 10;
//                image = "src/images/big-alien.png";
                sheet = new SpriteSheet("/images/big-alien.png", 12, 17);
                animation = new Animation(15,
                        sheet.grabImage(1, 1),
                        sheet.grabImage(1, 2),
                        sheet.grabImage(1, 3),
                        sheet.grabImage(1, 4));
                width = 12;
                height = 17;
                break;
            case 2:
                type = "MediumAlien";
                points = 20;
                //image = "src/images/medium-alien.png";
                sheet = new SpriteSheet("/images/medium-alien.png", 13, 14);
                animation = new Animation(15,
                        sheet.grabImage(1, 1),
                        sheet.grabImage(1, 2),
                        sheet.grabImage(1, 3));
                width = 13;
                height = 14;
                break;
            case 3:
                type = "SmallAlien";
                points = 30;
                //image = "src/images/small_alien.png";
                sheet = new SpriteSheet("/images/small-alien.png", 10, 14);
                animation = new Animation(15,
                        sheet.grabImage(1, 1),
                        sheet.grabImage(1, 2));
                width = 10;
                height = 14;
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

    public Animation getAnimation() {
        return animation;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public SpriteSheet getSheet() {
        return sheet;
    }
}
