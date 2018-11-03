package game;

public interface Commons {

    int BOARD_WIDTH = 900;
    int BOARD_HEIGHT = (int) ((700d / 900d) * BOARD_WIDTH);
    int GROUND = BOARD_HEIGHT - 100;
    int GO_DOWN = 20;

    int ALIEN_WIDTH = 55;
    int ALIEN_HEIGHT = (int) ((54d / 60d) * ALIEN_WIDTH);

    int SHIELD_WIDTH = 50;
    int SHIELD_HEIGHT = (int) ((22d / 36d) * SHIELD_WIDTH);

    int PLAYER_WIDTH = 25;
    int PLAYER_HEIGHT = (int) ((160d / 120d) * PLAYER_WIDTH);

    int DUCK_WIDTH = 40;
    int DUCK_HEIGHT = (int) ((190d / 173d) * DUCK_WIDTH);

    int UFO_WIDTH = 50;
    int UFO_HEIGHT = (int) ((106d / 200d) * UFO_WIDTH);

    int PROJECTILE_WIDTH = 3;
    int PROJECTILE_HEIGHT = (int) ((15d / 5d) * PROJECTILE_WIDTH);


    int NUMBER_OF_ALIENS_TO_DESTROY = 24;
    int SHIELD_LIVES = 8;
    int SHOT_STREAK = 4;

    default int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

}