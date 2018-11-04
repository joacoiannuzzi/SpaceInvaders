package game;
import highscore.LeaderBoard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Menu extends JFrame implements Commons {
    private JButton start;
    private JButton scoreBoard;
    private SpaceInvaders si = new SpaceInvaders();
    private LeaderBoard lb = new LeaderBoard();
    JPanel panel;

    public Menu(){

        setTitle("Space invaders");
        setSize(200,50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        setUndecorated(true);

        panel = new JPanel();
        start = new JButton("start");
        start.setBackground(Color.BLACK);
        start.setForeground(Color.green);
        panel.add(start);

        scoreBoard = new JButton("Scoreboard");
        scoreBoard.setBackground(Color.BLACK);
        scoreBoard.setForeground(Color.green);
        panel.add(scoreBoard);

        setLayout(new FlowLayout());

        theHandler handler = new theHandler();
        start.addActionListener(handler);
        scoreBoard.addActionListener(handler);

        add(panel);

        setVisible(true);
    }

    private  class theHandler implements ActionListener,Commons {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == start) {
                setVisible(false);
                si.initUI();

            }
            if (e.getSource() == scoreBoard) {

                JOptionPane.showMessageDialog(null, LeaderBoard.toText(),"Leaderboard", JOptionPane.PLAIN_MESSAGE);


            }
        }
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(Menu::new);
    }


}
