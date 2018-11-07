package game;

import highscore.LeaderBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndMenu extends JPanel  implements Commons {

    private JButton back = new JButton("Back to main screen");
    private JButton exit = new JButton("Exit");
//    private JButton retry = new JButton("Retry");

    public EndMenu(ApeInvaders apeInvaders) {

        repaint();
        add(back);
//        add(retry);
        add(exit);

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

        //g.setColor(Color.BLACK);
        //g.drawRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        LeaderBoard.toText(g);
    }
}
