package game;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SpaceInvaders extends JFrame implements Commons {

    public SpaceInvaders() {

            JFrame frame = new JFrame("Space Inavders");
            frame.setVisible(true);
            frame.setSize(250,100);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            frame.add(panel);
            JButton button = new JButton("Start");
            panel.add(button);
            button.addActionListener (new Action1());


    }

    private void initUI() {
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

    class Action1 implements ActionListener {
        public void actionPerformed (ActionEvent e) {

            initUI();

        }
    }
}