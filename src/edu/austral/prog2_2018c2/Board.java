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
    private ArrayList<Shield> shields;
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
        Shot shot = player.getShot();
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

        g.drawString("Streak: " + player.getShotStreak(), 0, BOARD_HEIGHT - 45);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (ingame) {

            g.drawString("powered: " + player.isPowered(), 0, 100);

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

        AudioPlayer failedSound = new AudioPlayer("/sounds/mission-failed.wav");
        //AudioPlayer failedSound = new AudioPlayer("/sounds/mission-failed.wav");
        if (message.equals("game won")) {
        }else{
            failedSound.play();
        }


        Graphics g = this.getGraphics();

        for (Alien alien : aliens) {
            alien.getHit();
            alien.setDying(true);
        }
        drawAliens(g);

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
//        ImageIcon loading = new ImageIcon("src/images/small_alien.gif");
//        g.drawImage(loading.getImage(),
//                BOARD_WIDTH - loading.getImage().getWidth(null) - 10,
//                BOARD_HEIGHT - 100, null);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void animationCycle() {



        // power ups
        player.unPower();

        // player
        player.act();

        // shot
        Shot shot = player.getShot();
        if (shot.isVisible()) {

            player.attack(ufo);

            for (Alien alien : aliens) {
                player.attack(alien);
            }
            for (Shield shield : shields) {
                player.attack(shield);
            }
            player.shotAct();
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

            if (alien.isVisible()) {

                if (alien.touchGround()) {
                    ingame = false;
                    message = "Invasion!";
                }
                alien.act(direction);
            }
        }

        // bombs
        for (Alien alien: aliens) {
            if (alien.isVisible()) {
                alien.initBomb();
            }
            Bomb bomb = alien.getBomb();
            if (bomb.isVisible()) {

                for (Shield shield : shields) {
                    bomb.hit(shield);
                }
                if (bomb.hit(player)) {
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
        else for (Shield shield : shields) {
            shield.die();
        }

        player.resetKills();
        player.resetPosition();

        delay -= 2;

        levelScreen();
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
        Shield shield = shields.get(n);

        if (shield.isVisible()) {
            return shield.getX() + (shield.getWidth() - player.getWidth()) / 2;
        }
        return chooseShield();
    }
}