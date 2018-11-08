package game;

import highscore.LeaderBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndMenu extends JPanel  implements Commons {

    private JButton back = new JButton("Back to main screen");
    private JButton exit = new JButton("Exit");

    public EndMenu(ApeInvaders apeInvaders) {

        repaint();
        setBackground(Color.black);
        setForeground(Color.green);
        add(back);
        back.setBackground(Color.black);
        back.setForeground(Color.green);

        add(exit);
        exit.setBackground(Color.black);
        exit.setForeground(Color.green);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apeInvaders.restartGame();
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        LeaderBoard.toText(g);
    }
}
