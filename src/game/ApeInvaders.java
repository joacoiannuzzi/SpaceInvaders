package game;
import highscore.LeaderBoard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ApeInvaders extends JFrame implements Commons {
    private JButton start;
    private JButton scoreBoard;
    private JButton Exit;
    private JButton Retry;
    private LeaderBoard lb = new LeaderBoard();
    public JPanel panel;

    public Board board = new Board();

    public ApeInvaders(){

        setTitle("Space invaders");
        setSize(200,50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(null);
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

        Retry = new JButton("Retry");
        Retry.setBackground(Color.BLACK);
        Retry.setForeground(Color.green);
        panel.add(Retry);
        Retry.setVisible(false);

        Exit = new JButton("Exit");
        Exit.setBackground(Color.BLACK);
        Exit.setForeground(Color.green);
        panel.add(Exit);
        Exit.setVisible(false);

        theHandler handler = new theHandler();
        start.addActionListener(handler);
        scoreBoard.addActionListener(handler);
        Exit.addActionListener(handler);
        Retry.addActionListener(handler);

        add(panel);

        setVisible(true);
    }

    public void menuover() {

    }

    private  class theHandler implements ActionListener, Commons {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == start) {

                start.setVisible(false);
                scoreBoard.setVisible(false);

                add(board);
                setTitle("Space Invaders");
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setSize(BOARD_WIDTH, BOARD_HEIGHT);
                setLocationRelativeTo(null);
                setResizable(false);
                getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.WHITE));
                board.startGame();

            }
            if (e.getSource() == scoreBoard) {

                JOptionPane.showMessageDialog(null, LeaderBoard.toText(),"Leaderboard", JOptionPane.PLAIN_MESSAGE);


            }
            if (e.getSource() == Retry) {

                setVisible(false);
                e.setSource(start);

            }
            if (e.getSource() == Exit) {

                System.exit(0);

            }
        }
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(ApeInvaders::new);
    }

    public static void endmenu() {

        EventQueue.invokeLater(getFrames()::new);

    }


}
