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

    private ArrayList<Shield> shields;
    private int shieldsToRemove = 0;
    private int shieldSpace = (BOARD_WIDTH - 4 * SHIELD_WIDTH) / 5;

    private final int ALIEN_INIT_X = 150;
    private final int ALIEN_INIT_Y = 5;
    private int direction = -1;
    private int deaths = 0;

    private boolean ingame = true;
    private final String explImg = "src/images/explosion.png";
    private String message = "Game Over";

    private Thread animator;

    private int totalLevels = 5;
    private int currentLevel = 1;

    private int alienSpeed = 1;
    private int bombSpeed = 1;
    private int shotQuantity = 0;

    private int playerPoints = 0;

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
        newAliens();

        shields = new ArrayList<>();
        newShields();

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

    /*public void drawUFO(Graphics g){
        if (Alien_UFO.isVisible()) {

            g.drawImage(Alien_UFO.getImage(), Alien_UFO.getX(), Alien_UFO.getY(), this);
        }

        if (Alien_UFO.isDying()) {

            Alien_UFO.die();
        }
    }*/

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

            Alien.Bomb b = a.getBomb();

            if (!b.isDestroyed()) {

                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (ingame) {

            g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);

            drawShot(g);
            drawBombing(g);
            drawShields(g);
            drawAliens(g);
            drawPlayer(g);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void gameOver() {

        Graphics g = this.getGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 - 60, BOARD_WIDTH - 100, 100);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 60, BOARD_WIDTH - 100, 100);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2,
                BOARD_WIDTH / 2 - 20);

        g.drawString("Score: " + playerPoints, (BOARD_WIDTH - metr.stringWidth("Score: " + playerPoints)) / 2,
                (BOARD_WIDTH / 2) + 10);
    }

    public void animationCycle() {

        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY && currentLevel == totalLevels) {

            ingame = false;
            message = "Game won!";
        }

        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY && currentLevel != totalLevels) {

            levelUp();
        }


        // player
        player.act();

        // shot
        if (shot.isVisible()) {

            int shotX = shot.getX();
            int shotY = shot.getY();

            for (Alien alien: aliens) {

                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX)
                            && shotX <= (alienX + ALIEN_WIDTH)
                            && shotY >= (alienY)
                            && shotY <= (alienY + ALIEN_HEIGHT)) {
                        ImageIcon ii = new ImageIcon(explImg);
                        alien.setImage(ii.getImage());
                        alien.setDying(true);
                        deaths++;
                        playerPoints += alien.getAlienType().getPoints();
                        shot.die();
                    }
                }

                for (Shield shield : shields) {

                    int shieldX = shield.getX();
                    int shieldY = shield.getY();

                    if (shield.isVisible() && shot.isVisible()) {

                        if (shotX >= (shieldX)
                                && shotX <= (shieldX + SHIELD_WIDTH)
                                && shotY >= (shieldY)
                                && shotY <= (shieldY + SHIELD_HEIGHT)) {

                            shotQuantity++;

                            if (shotQuantity == 5) {
                                shield.getBombed();
                                shotQuantity = 0;
                            }

                            shot.die();


                            if (shield.getLives() == 0) {

                                ImageIcon ii = new ImageIcon(explImg);
                                shield.setImage(ii.getImage());
                                shield.setDying(true);
                            }
                        }
                    }
                }
            }

            int y = shot.getY();
            y -= 10;

            if (y < 0) {
                shot.die();
            } else {
                shot.setY(y);
            }
        }

        // aliens

        for (Alien alien: aliens) {

            int x = alien.getX();

            if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {

                direction = -1;

                for (Alien alien1 : aliens) {
                    alien1.setY(alien1.getY() + GO_DOWN);
                }
            }

            if (x <= BORDER_LEFT && direction != 1) {

                direction = 1;

                for (Alien alien1 : aliens) {
                    alien1.setY(alien1.getY() + GO_DOWN);
                }
            }
        }

        for (Alien alien1 : aliens) {

            if (alien1.isVisible()) {

                int y = alien1.getY();

                if (y > GROUND - ALIEN_HEIGHT) {
                    ingame = false;
                    message = "Invasion!";
                }

                alien1.act(direction * alienSpeed);
            }
        }

        // bombs
        Random generator = new Random();

        for (Alien alien: aliens) {

            int shot = generator.nextInt(15);
            Alien.Bomb b = alien.getBomb();

            if (shot == CHANCE && alien.isVisible() && b.isDestroyed()) {

                b.setDestroyed(false);
                b.setX(alien.getX());
                b.setY(alien.getY());
            }

            int bombX = b.getX();
            int bombY = b.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !b.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + PLAYER_HEIGHT)) {

                    player.getBombed();
                    player.setX(shieldDetect());
                    b.setDestroyed(true);


                    if (player.getLives() == 0) {

                        ImageIcon ii = new ImageIcon(explImg);
                        player.setImage(ii.getImage());
                        player.setDying(true);
                    }
                }
            }

            for (Shield shield : shields) {

                int shieldX = shield.getX();
                int shieldY = shield.getY();

                if (shield.isVisible() && !b.isDestroyed()) {

                    if (bombX >= (shieldX)
                            && bombX <= (shieldX + SHIELD_WIDTH)
                            && bombY >= (shieldY)
                            && bombY <= (shieldY + 50)) {

                        shotQuantity++;

                        if (shotQuantity == 5) {
                            shield.getBombed();
                            shotQuantity = 0;
                        }

                        b.setDestroyed(true);


                        if (shield.getLives() == 0) {

                            ImageIcon ii = new ImageIcon(explImg);
                            shield.setImage(ii.getImage());
                            shield.setDying(true);
                        }
                    }
                }
            }

            if (!b.isDestroyed()) {

                b.setY(b.getY() + bombSpeed);

                if (b.getY() >= GROUND - BOMB_HEIGHT) {
                    b.setDestroyed(true);
                }
            }
        }
    }


    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (ingame) {

            repaint();
            animationCycle();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

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
                        shot = new Shot(x, y);
                    }
                }
            }
        }
    }

    public void levelUp() {

        currentLevel++;

        changeAliens();

        if (currentLevel != totalLevels) {

            changeShields();
            shieldsToRemove++;

            for (int i = 0; i < shieldsToRemove; i++) {
                shieldRemove();
            }
        }

        alienSpeed++;
        bombSpeed++;

        deaths = 0;

    }

    public void newAliens() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {

                Alien alien = new Alien(ALIEN_INIT_X + 18 * j, ALIEN_INIT_Y + 18 * i);
                aliens.add(alien);
            }
        }
    }

    public void changeAliens() {

        int n = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {

                Alien alien = aliens.get(n);

                alien.changeAlienType();
                alien.setX(ALIEN_INIT_X + 18 * j);
                alien.setY(ALIEN_INIT_Y + 18 * i);
                ImageIcon ii = new ImageIcon(alien.getAlienType().getImage());
                alien.setImage(ii.getImage());
                alien.setVisible(true);
                alien.setDying(false);

                Alien.Bomb bomb = alien.getBomb();
                bomb.setDestroyed(true);

                n++;

            }
        }
    }

    public void newShields() {

        for (int i = 0; i < 4; i++) {
            Shield shield = new Shield(shieldSpace + (SHIELD_WIDTH + shieldSpace) * i, 230);
            shields.add(shield);
        }
    }

    public void changeShields(){

        int n = 0;

        for (int i = 0; i < 4; i++) {

            Shield shield = shields.get(n);
            shield.restoreLives();
            ImageIcon ii = new ImageIcon("src/images/shield.png");
            shield.setImage(ii.getImage());
            shield.setDying(false);
            shield.setVisible(true);
            n++;

        }
    }

    public void shieldRemove() {
        int n = randomWithRange(0, 3);

        if (shields.get(n).isVisible()) {

            shields.get(n).setDying(true);
            ImageIcon ii = new ImageIcon(explImg);
            shields.get(n).setImage(ii.getImage());
            shields.get(n).die();
        }
        else {
            shieldRemove();
        }
    }

    public int shieldDetect() {

        int counter = 0;

        for (Shield shield : shields) {
            if (shield.isVisible()) {
                 counter++;
            }
        }

        if (counter == 0) {
            return player.getX();
        }

        int n = randomWithRange(0, 3);

        if (shields.get(n).isVisible()) {
            return shields.get(n).getX() + (SHIELD_WIDTH - PLAYER_WIDTH) / 2;
        }
        return shieldDetect();
    }


    public int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}