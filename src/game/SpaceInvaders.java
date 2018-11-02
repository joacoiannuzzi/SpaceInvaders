package game;

import java.awt.*;
import javax.swing.JFrame;

public class SpaceInvaders extends JFrame implements Commons {

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
            Menu menu = new Menu();
            menu.setVisible(true);
        });
    }
}