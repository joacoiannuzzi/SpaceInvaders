package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JPanel {
    private ApeInvaders apeInvaders;
    private JButton start = new JButton("Start");
    private JButton scoreBoard = new JButton("Scoreboard");
    private JButton exit = new JButton("Exit");

    public StartMenu(ApeInvaders apeInvaders) {
        this.apeInvaders = apeInvaders;
        start.setBackground(Color.BLACK);
        start.setForeground(Color.green);
        add(start);

        scoreBoard.setBackground(Color.BLACK);
        scoreBoard.setForeground(Color.green);
        add(scoreBoard);

        exit.setBackground(Color.BLACK);
        exit.setForeground(Color.green);
        add(exit);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apeInvaders.startGame();
            }
        });
        scoreBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apeInvaders.showScores();
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
