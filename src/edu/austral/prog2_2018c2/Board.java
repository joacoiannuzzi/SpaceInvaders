package edu.austral.prog2_2018c2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel implements Runnable, Commons {

    private Dimension d;
    private ArrayList<Alien> aliens;
    private Player player;
    private Shot shot;
    private Ufo ufo;
    private ArrayList<Shield> shields;
    private int shieldsToRemove = 0;

    private final int ALIEN_INIT_X = 200;
    private final int ALIEN_INIT_Y = 37;
    private int direction = -1;
    private int kills = 0;

    private boolean ingame = true;
    private String message = "Game Over";

    private Thread animator;

    private int totalLevels = 5;
    private int currentLevel = 1;

    private long ufoSpawnTimer;

    private int delay = DELAY;

    private long powerTimer;
    private boolean powered = false;
    private long timer;

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
        for (int i = 0; i < 24; i++) {
            aliens.add(new Alien());
        }
        generateAliens();

        shields = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            shields.add(new Shield());
        }
        generateShields();

        ufo = new Ufo();

        player = new Player();

        shot = new Shot();

        if (animator == null || !ingame) {

            animator = new Thread(this);
            animator.start();
        }
    }

    public void drawAliens(Graphics g) {

        for (Alien alien : aliens) {

            if (alien.isVisible()) {

                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isDying()) {

                alien.die();
            }
        }
    }

    public void drawUFO(Graphics g) {

        if (ufo.isVisible()) {

            g.drawImage(ufo.getImage(), ufo.getX(), ufo.getY(), this);
        }

        if (ufo.isDying()) {

            ufo.die();
        }
    }

    public void drawPlayer(Graphics g) {

        if (player.isVisible()) {

            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {

            player.die();
            ingame = false;
        }
    }

    public void drawShields(Graphics g) {

        for (Shield shield : shields) {

            if (shield.isVisible()) {

                g.drawImage(shield.getImage(), shield.getX(), shield.getY(), this);
            }

            if (shield.isDying()) {

                shield.die();
            }
        }
    }

    public void drawShot(Graphics g) {

        if (shot.isVisible()) {

            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    public void drawBombing(Graphics g) {

        for (Alien a : aliens) {

            Bomb b = a.getBomb();

            if (b.isVisible()) {

                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    public void drawTexts(Graphics g) {

        Font small = new Font("Helvetica", Font.BOLD, 15);
        FontMetrics metr = this.getFontMetrics(small);
        g.setFont(small);

        g.drawString("Score: " + player.getPoints(), 0,
                BOARD_HEIGHT - 24);

        g.drawString("Lives: " + player.getLives(),
                BOARD_WIDTH - metr.stringWidth("Lives: " + player.getLives()),
                BOARD_HEIGHT - 24);

        g.drawString("Level " + currentLevel,
                (BOARD_WIDTH - metr.stringWidth("Level " + currentLevel)) / 2,
                BOARD_HEIGHT - 24);

        g.drawString("Streak: " + shot.getStreak(), 0, BOARD_HEIGHT - 45);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (ingame) {

            g.drawString("Powered: " + powered, 0, 170);
            g.drawString("Timer: " + timer, 0, 150);

            g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);

            drawShot(g);
            drawBombing(g);
            drawShields(g);
            drawAliens(g);
            drawPlayer(g);
            drawUFO(g);
            drawTexts(g);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void gameOver() {

        Graphics g = this.getGraphics();

        ImageIcon ii = new ImageIcon(explImg);

        for (Alien alien : aliens) {
            alien.setImage(ii.getImage());
            alien.setDying(true);
        }
        drawAliens(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_HEIGHT / 2 - 50, BOARD_WIDTH - 100, 100);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_HEIGHT / 2 - 50, BOARD_WIDTH - 100, 100);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message,
                (BOARD_WIDTH - metr.stringWidth(message)) / 2,
                BOARD_HEIGHT / 2 - 15);

        g.drawString("Score: " + player.getPoints(),
                (BOARD_WIDTH - metr.stringWidth("Score: " + player.getPoints())) / 2,
                (BOARD_HEIGHT) / 2 + 15);
    }

    public void animationCycle() {

        if (kills == NUMBER_OF_ALIENS_TO_DESTROY) {

            if (currentLevel == totalLevels) {
                ingame = false;
                message = "Game won!";
            } else {
                levelUp();
            }
        }

        // power ups
        if (powered) {

            long powerEndTime = System.currentTimeMillis() - powerTimer;
            int random = randomWithRange(1, 70);

            if ((powerEndTime >= 3000 && powerEndTime <= 5000 && random == 1)
                    || (powerEndTime > 5000)) {

                powered = false;
                shot.resetStreak();
                powerTimer = 0;

            }
        }

        // player
        player.act();

        // shot
        if (shot.isVisible()) {

            if (shot.hit(ufo)) {

                player.addPoints(ufo.getPoints());
            }

            for (Alien alien : aliens) {

                if (shot.hit(alien)) {

                    kills++;
                    player.addPoints(alien.getAlienType().getPoints());

                    if (!powered) {
                        shot.increaseStreak();

                        if (shot.getStreak() == 4) {
                            powered = true;
                            powerTimer = System.currentTimeMillis();
                        }
                    }
                }
            }

            for (Shield shield : shields) {

                if (shot.hit(shield)) {

                    shot.resetStreak();
                }
            }

            shot.act();
        }

        // aliens
        for (Alien alien : aliens) {

            if (alien.changeDirection()) {

               for (Alien alien1 : aliens) {
                   alien1.setY(alien1.getY() + GO_DOWN);
               }
            }
            //alien.changeDirection();

            if (alien.isVisible()) {

                if (alien.touchGround()) {
                    ingame = false;
                    message = "Invasion!";
                }

                alien.act();
            }
        }

        // ufo
        if (!ufo.isVisible()) {

            long ufoTimer = System.currentTimeMillis() - ufoSpawnTimer;
            timer = System.currentTimeMillis() - ufoSpawnTimer;

            if (ufo.spawn(ufoTimer)) {
                ufoSpawnTimer = System.currentTimeMillis();
            }
        }
        if (ufo.isVisible()) {
            ufo.act();
        }

        // bombs
        for (Alien alien : aliens) {

            Bomb b = alien.getBomb();

            b.initBomb(alien);

            if (b.isVisible()) {

                for (Shield shield : shields) {
                    b.hit(shield);
                }

                if (b.hit(player)) {
                    shieldDetect();
                }

                b.act();
            }
        }
    }


    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();
        ufoSpawnTimer = System.currentTimeMillis();

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

            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {

                if (ingame) {
                    if (!shot.isVisible()) {
                        shot.initShot(x, y);
                    }
                }
            }
        }
    }

    public void levelUp() {

        currentLevel++;

        generateAliens();

        if (currentLevel != totalLevels) {

            generateShields();
            shieldsToRemove++;

            for (int i = 0; i < shieldsToRemove; i++) {
                shieldRemove();
            }
        }
        else {
            for (Shield shield : shields) {
                shield.die();
            }
        }

        kills = 0;

        delay -= 2;

    }

    public void generateAliens() {

        int n = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                aliens.get(n).initAlien(ALIEN_INIT_X + 18 * j,
                        ALIEN_INIT_Y + 18 * i);
                n++;
            }
        }
    }

    public void generateShields() {

        int shieldSpace = (BOARD_WIDTH - 4 * shields.get(0).getWidth()) / 5;
        for (int i = 0; i < 4; i++) {
            Shield shield = shields.get(i);
            shield.initShield(shieldSpace + (shield.getWidth() + shieldSpace) * i);
        }
    }

    public void shieldRemove() {
        int n = randomWithRange(0, 3);

        if (shields.get(n).isVisible()) {

            shields.get(n).die();
        }
        else {
            shieldRemove();
        }
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
        Shield shield = shields.get(n);

        if (shield.isVisible()) {
            return shield.getX() + (shield.getWidth() - player.getWidth()) / 2;
        }
        return chooseShield();
    }
}