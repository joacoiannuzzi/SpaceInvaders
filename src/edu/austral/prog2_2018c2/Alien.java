package edu.austral.prog2_2018c2;

import javax.swing.ImageIcon;

public class Alien extends Sprite {

    protected Bomb bomb;
    protected String alienImg = "src/images/small_alien.png";
    private AlienType alienType;

    public Alien(int x, int y) {

        initAlien(x, y);
    }

    public Alien(String ufo) {
        alienType = new AlienType("");
        alienImg = alienType.getImage();
        ImageIcon ii = new ImageIcon(alienImg);
        setImage(ii.getImage());
        setVisible(false);

        this.x = 0;
        this.y = 0;
    }

    private void initAlien(int x, int y) {

        this.alienType = new AlienType();
        alienImg = alienType.getImage();

        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);
        ImageIcon ii = new ImageIcon(alienImg);
        setImage(ii.getImage());
    }

    public void act(int direction) {

        this.x += direction;
    }

    public Bomb getBomb() {

        return bomb;
    }

    public AlienType getAlienType() {
        return alienType;
    }

    public void changeAlienType() {
        this.alienType = new AlienType();
    }

    public void changeAlienType(String ufo) {
        this.alienType = new AlienType("");
    }

    public class Bomb extends Sprite {

        private final String bombImg = "src/images/bomb.png";
        private boolean destroyed;

        public Bomb(int x, int y) {

            initBomb(x, y);
        }

        private void initBomb(int x, int y) {

            setDestroyed(true);
            this.x = x;
            this.y = y;
            ImageIcon ii = new ImageIcon(bombImg);
            setImage(ii.getImage());

        }

        public void setDestroyed(boolean destroyed) {

            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {

            return destroyed;
        }
    }
}