package sprites;

import game.Commons;
import other.Random;

public class PowerUp implements Commons {

    public static boolean powered = false;
    public static boolean immunity = false;
    public static boolean freezeInvaders = false;
    public static boolean doubleDamage = false;

    public static int shotStreak = 0;

    private long powerTimer;
    private int endPower;

    public void update() {
        if (!powered && shotStreak == SHOT_STREAK) {
            powerUp();
        }
        powerDown();
    }

    public void powerUp() {
        powered = true;
        powerTimer = System.currentTimeMillis();
        endPower = Random.randomWithRange(3000, 5000);
        int random = Random.randomWithRange(1, 10);
        if (random < 7) {
            immunity = true;
        } else if (random < 9) {
            doubleDamage = true;
        } else if (random < 11) {
            freezeInvaders = true;
        }
    }

    public void powerDown() {
        if (powered && System.currentTimeMillis() - powerTimer >= endPower) {
            powered = false;
            shotStreak = 0;
            powerTimer = 0;
            immunity = false;
            freezeInvaders = false;
            doubleDamage = false;
        }
    }
}
