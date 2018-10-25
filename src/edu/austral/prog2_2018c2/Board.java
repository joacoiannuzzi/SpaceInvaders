package edu.austral.prog2_2018c2;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.*;

public class Board extends JPanel implements Runnable, Commons {

    private Dimension d;
    private ArrayList<Alien> aliens;
    private Player player;
    private Ufo ufo;
    private Shield[] shields;
    private int shieldsToRemove = 0;

    private final int ALIEN_INIT_X = 200;
    private final int ALIEN_INIT_Y = 37;
    private int direction = -1;

    private boolean ingame = true;
    private String message = "Game Over";

    private Thread animator;

    private int totalLevels = 5;
    private int currentLevel = 1;

    private int delay = DELAY;

    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.black);

        gameInit();
        setDoubleBuffered(true);
    }

    @Override
    public void addNotify() {

        super.addNotify();
        gameInit();
    }

    public void gameInit() {

        aliens = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                aliens.add(new Alien(ALIEN_INIT_X + 18 * j,
                        ALIEN_INIT_Y + 18 * i));
            }
        }

        shields = new Shield[4];
        int shieldSpace = ((BOARD_WIDTH - 4 * SHIELD_WIDTH) / 5);

        for (int i = 0; i < 4; i++) {
            shields[i] = new Shield(shieldSpace + (SHIELD_WIDTH + shieldSpace) * i);
        }

        ufo = new Ufo();

        player = new Player();

        if (animator == null || !ingame) {

            animator = new Thread(this);
            animator.start();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (ingame) {

            g.drawString("powered: " + player.isPowered(), 0, 100);

            Font small = new Font("Helvetica", Font.BOLD, 15);
            FontMetrics metr = this.getFontMetrics(small);
            g.setFont(small);

            g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);

            for (Shield shield : shields) {
                shield.draw(g);
            }
            for (Alien alien : aliens) {
                Bomb bomb = alien.getBomb();
                bomb.draw(g);
                alien.draw(g);
            }
            player.draw(g, metr);
            if (!player.isVisible()) {
                ingame = false;
            }
            ufo.draw(g);
            g.drawString("Level " + currentLevel,
                    (BOARD_WIDTH - metr.stringWidth("Level " + currentLevel)) / 2,
                    BOARD_HEIGHT - 24);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void gameOver() {

        String sound;

        if (message.equals("Game won!")) {
            sound = "/sounds/won-sound.wav";
        } else {
            sound = "/sounds/mission-failed.wav";
        }
        AudioPlayer gameOverSound = new AudioPlayer(sound);
        gameOverSound.play();

        Graphics g = this.getGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

//        g.setColor(new Color(0, 32, 48));
//        g.fillRect(50, BOARD_HEIGHT / 2 - 50, BOARD_WIDTH - 100, 100);
//        g.setColor(Color.white);
//        g.drawRect(50, BOARD_HEIGHT / 2 - 50, BOARD_WIDTH - 100, 100);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.green);
        g.setFont(small);
        g.drawString(message,
                (BOARD_WIDTH - metr.stringWidth(message)) / 2,
                BOARD_HEIGHT / 2 - 15);

        g.drawString("Score: " + player.getPoints(),
                (BOARD_WIDTH - metr.stringWidth("Score: " + player.getPoints())) / 2,
                (BOARD_HEIGHT) / 2 + 15);
    }

    public void levelScreen() {

        Graphics g = this.getGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.green);
        g.setFont(small);
        g.drawString("Level " + currentLevel,
                (BOARD_WIDTH - metr.stringWidth("Level " + currentLevel)) / 2,
                BOARD_HEIGHT / 2 - 15);

        g.drawString("Score: " + player.getPoints(),
                (BOARD_WIDTH - metr.stringWidth("Score: " + player.getPoints())) / 2,
                (BOARD_HEIGHT) / 2 + 15);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void animationCycle() {

        // power ups
        player.powerDown();

        // player
        player.act();

        // shot
        Shot[] shots = player.getShots();
        for (Shot shot : shots) {
            if (shot.isVisible()) {

                player.attack(shot, ufo);

                for (Alien alien : aliens) {
                    player.attack(shot, alien);
                }
                for (Shield shield : shields) {
                    player.attack(shot, shield);
                }
                player.shotAct(shot);
            }
        }

        // aliens
        for (Alien alien : aliens) {

            if (alien.isVisible()) {
                int alienX = alien.getX();
                if ((alienX >= BOARD_WIDTH - ALIEN_WIDTH - 10 && direction != -1)
                        || (alienX <= 10 && direction != 1)) {

                    direction = -direction;

                    for (Alien alien1 : aliens) {
                        alien1.setY(alien1.getY() + GO_DOWN);
                    }
                }
            }
        }

        for (Alien alien : aliens) {

            if (alien.isVisible() && !player.freezeInvaders()) {

                if (alien.touchGround()) {
                    ingame = false;
                    message = "Invasion!";
                }
                alien.act(direction);
            }
        }

        // bombs
        for (Alien alien: aliens) {
            if (alien.isVisible() && !player.freezeInvaders()) {
                alien.shoot();
            }
            Bomb bomb = alien.getBomb();
            if (bomb.isVisible() && !player.freezeInvaders()) {

                for (Shield shield : shields) {
                    bomb.hit(shield);
                }
                if (bomb.hit(player) && !player.isImmune()) {
                    shieldDetect();
                }
                bomb.act();
            }
        }

        // ufo
        ufo.spawn();
        ufo.act();
    }

    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (ingame) {

            repaint();
            animationCycle();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = delay - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();

            if (player.getKills() == NUMBER_OF_ALIENS_TO_DESTROY) {
                if (currentLevel == totalLevels) {
                    ingame = false;
                    message = "Game won!";
                } else levelUp();
            }

        }

        gameOver();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (ingame) {
                player.keyPressed(e);

                int key = e.getKeyCode();
                if (key == KeyEvent.VK_L) {
                    levelUp();
                }
                if (key == KeyEvent.VK_G) {
                    ingame = false;
                }
                if (key == KeyEvent.VK_U) {
                    ufo.initUfo();
                }
            }
        }
    }

    public void levelUp() {

        currentLevel++;

        resetAliens();

        if (currentLevel != totalLevels) {

            resetShields();
            shieldsToRemove++;

            for (int i = 0; i < shieldsToRemove; i++) {
                shieldRemove();
            }
        }
        else for (Shield shield : shields) {
            shield.die();
        }

        player.resetKills();
        player.resetPosition();

//        Alien.increaseSpeed();
//        Bomb.increaseSpeed();
        delay -=2;

        levelScreen();
    }

    public void resetAliens() {
        for (Alien alien : aliens) {
            alien.reset();
        }
    }

    public void resetShields() {
        for (Shield shield : shields) {
            shield.reset();
        }
    }

    public void shieldRemove() {
        int n = randomWithRange(0, 3);

        if (shields[n].isVisible()) {
            shields[n].die();
        }
        else shieldRemove();
    }

    public void shieldDetect() {

        int counter = 0;
        for (Shield shield : shields) {
            if (shield.isVisible()) {
                 counter++;
            }
        }
        if (counter == 0) {
            return;
        }

        player.setX(chooseShield());
    }

    public int chooseShield() {
        int n = randomWithRange(0, 3);
        Shield shield = shields[n];

        if (shield.isVisible()) {
            return shield.getX() + (shield.getWidth() - player.getWidth()) / 2;
        }
        return chooseShield();
    }
}