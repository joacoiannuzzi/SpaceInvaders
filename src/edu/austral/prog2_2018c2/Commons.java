package edu.austral.prog2_2018c2;

public interface Commons {
    int BOARD_WIDTH = 500;
    int BOARD_HEIGHT = 450;
    int GROUND = BOARD_HEIGHT - 80;
    int GO_DOWN = 15;
    int ALIEN_WIDTH = 13;
    int SHIELD_WIDTH = 36;
    int NUMBER_OF_ALIENS_TO_DESTROY = 24;
    int DELAY = 17;
    int SHIELD_LIVES = 10;
    int SHOT_STREAK = 2;

    default int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

}