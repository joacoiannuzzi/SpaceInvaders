package game;

import highscore.LeaderBoard;
import other.Animation;
import other.AudioPlayer;
import other.Random;
import other.SpriteSheet;
import sprites.*;
import sprites.PowerUp;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.image.BufferedImage;
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

    private boolean ingame = false;
    private String message = "Game Over";
    private SpriteSheet sheet = new SpriteSheet("background.png", 1280, 640);
    private Animation backgroundAnim;
    private int goDown = GO_DOWN;

    private Thread animator;
    private long desiredFPS = 60;
    private long desiredDeltaLoop = (1000*1000*1000) / desiredFPS;
    private long averageFps = 0;

    private int totalLevels = 5;
    private int currentLevel = 1;

    private AudioPlayer gameSound;
    private AudioPlayer levelupSound = new AudioPlayer("levelup.wav");
    private ApeInvaders apeInvaders;



    public Board(ApeInvaders apeInvaders) {
        this.apeInvaders = apeInvaders;
        gameSound = new AudioPlayer("ape-invaders.wav");
        gameSound.changeVolume(-10);
        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);

        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setPreferredSize(d);
        setBackground(Color.black);

        BufferedImage[] background = new BufferedImage[24];
        int n = 0;
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 6; j++) {
                background[n] = sheet.grabImage(i, j);
                n++;
            }
        }

        backgroundAnim = new Animation(5, background);

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
                aliens.add(new Alien(ALIEN_INIT_X + (ALIEN_WIDTH + 5) * j,
                        ALIEN_INIT_Y + (ALIEN_HEIGHT - 20) * i));
            }
        }

        shields = new Shield[4];
        int shieldSpace = ((BOARD_WIDTH - 4 * SHIELD_WIDTH) / 5);

        for (int i = 0; i < 4; i++) {
            shields[i] = new Shield(shieldSpace + (SHIELD_WIDTH + shieldSpace) * i);
        }

        ufo = new Ufo();

        player = new Player();
        Alien.resetSpeed();
        Bomb.resetSpeed();



    }

    public void startGame() {

        requestFocus();
        if (animator == null || !ingame) {

            animator = new Thread(this);
            animator.start();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (ingame) {

            g.drawImage(backgroundAnim.getCurrentImage(), -120, 0,
                    BOARD_WIDTH + 140, GROUND, 0, 0,
                    backgroundAnim.getCurrentImage().getWidth(),
                    backgroundAnim.getCurrentImage().getHeight(), null );
            backgroundAnim.run();
            g.setColor(Color.green);

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
            player.draw(g);

            ufo.draw(g);

            g.drawString("Level " + currentLevel,
                    (BOARD_WIDTH - metr.stringWidth("Level " + currentLevel)) / 2,
                    BOARD_HEIGHT - 40);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void gameOver() {

        gameSound.close();

        PowerUp.shotStreak = 0;

        if (message.equals("Game won!")) {
            AudioPlayer wonSound = new AudioPlayer("won-sound.wav");
            wonSound.playFromBeginning();
        } else {
            AudioPlayer lostSound = new AudioPlayer("mission-failed.wav");
            lostSound.playFromBeginning();
        }

        Graphics g = this.getGraphics();

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);
        g.setFont(small);

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(Color.green);
        g.drawString(message,
                (BOARD_WIDTH - metr.stringWidth(message)) / 2,
                BOARD_HEIGHT / 2 - 15);

        g.drawString("Score: " + player.getPoints(),
                (BOARD_WIDTH - metr.stringWidth("Score: " + player.getPoints())) / 2,
                (BOARD_HEIGHT) / 2 + 15);


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LeaderBoard.rank(player.getPoints());
        apeInvaders.showScores();

    }

    public void levelScreen() {

        gameSound.stop();

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
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameSound.loop(5);
    }

    public void animationCycle() {

        // player && shots
        player.attack(ufo);

        for (Alien alien : aliens) {
            player.attack(alien);
        }
        for (Shield shield : shields) {
            player.attack(shield);
        }
        player.act();

        // aliens
        for (Alien alien : aliens) {

            if (alien.isVisible()) {
                int alienX = alien.getX();
                if ((alienX >= BOARD_WIDTH - alien.getWidth() - 10 && direction != -1)
                        || (alienX <= 10 && direction != 1)) {

                    direction = -direction;

                    for (Alien alien1 : aliens) {
                        alien1.setY(alien1.getY() + goDown);
                    }
                }
            }
        }

        for (Alien alien : aliens) {

            if (alien.isVisible() && !PowerUp.freezeInvaders) {

                if (alien.touchGround()) {
                    ingame = false;
                    message = "Invasion!";
                }
                alien.act(direction);
            }
        }

        // bombs
        for (Alien alien: aliens) {
            if (alien.isVisible() && !PowerUp.freezeInvaders) {
                alien.shoot();
            }
            Bomb bomb = alien.getBomb();
            if (bomb.isVisible() && !PowerUp.freezeInvaders) {

                for (Shield shield : shields) {
                    bomb.hit(shield);
                }
                if (bomb.hit(player) && !PowerUp.immunity) {
                    shieldDetect();
                }
                bomb.act();
            }
        }

        for (Shield shield : shields) {
            shield.update();
        }

        // ufo
        ufo.act();
    }

    @Override
    public void run() {

        gameSound.loop(5);
        repaint();
        levelScreen();
        ingame = true;
        long timeThen;
        long timeNow;
        long totalTime = 0;
        long gapTo;

        int framecount = 0;

        while (ingame) {

            timeThen = System.nanoTime();

            repaint();
            animationCycle();

            gapTo = desiredDeltaLoop + timeThen;
            timeNow = System.nanoTime();
            while (gapTo > timeNow) {
                try { Thread.sleep(1);
                } catch (InterruptedException ignored) {}
                timeNow = System.nanoTime();
            }

            totalTime += System.nanoTime() - timeThen;
            framecount++;
            if (framecount == desiredFPS) {
                averageFps = (long) 1000.0 / ((totalTime / framecount) / 1000000);
                framecount = 0;
                totalTime = 0;
            }


            if (player.getKills() == NUMBER_OF_ALIENS_TO_DESTROY) {
                if (currentLevel == totalLevels) {
                    ingame = false;
                    message = "Game won!";
                } else levelUp();
            }
            if (!player.isVisible()) {
                ingame = false;
            }
        }

        gameOver();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            if (ingame) {
                player.keyReleased(e);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (ingame) {
                player.keyPressed(e);

                if (key == KeyEvent.VK_G) {
                    ingame = false;
                }
                if (key == KeyEvent.VK_U) {
                    ufo.appear();
                }
                if (key == KeyEvent.VK_D) {
                    ufo.duckappear();
                }
                if (key == KeyEvent.VK_1) {
                    message = "Game won!";
                    ingame = false;
                }
                if (key == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        }
    }

    public void levelUp() {

        gameSound.stop();
        levelupSound.playFromBeginning();

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

        ufo.die();

        player.reset();

        Alien.increaseSpeed();
        Bomb.increaseSpeed();

        backgroundAnim.increaseSpeed();
        goDown--;

        levelScreen();

        gameSound.playFromBeginning();
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
        int n = Random.randomWithRange(0, 3);

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
        int n = Random.randomWithRange(0, 3);
        Shield shield = shields[n];

        if (shield.isVisible()) {
            return shield.getX() + (shield.getWidth() - player.getWidth()) / 2;
        }
        return chooseShield();
    }
}