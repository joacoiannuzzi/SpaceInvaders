package game;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class SpaceInvaders extends JFrame implements Commons {

    public SpaceInvaders() {
        Menu menu = new Menu();
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setSize(300,170);
        menu.setVisible(true);

    }

    public void initUI() {
        add(new Board());
        setTitle("Space Invaders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);

    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            SpaceInvaders ex = new SpaceInvaders();
            ex.setVisible(true);
        });
    }
}