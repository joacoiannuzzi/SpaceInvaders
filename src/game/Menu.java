package game;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Menu extends JFrame{
    private JButton start;
    private JButton scoreBoard;
    private Board board;
    private SpaceInvaders spaceInvaders;

    public Menu(){

        setTitle("Space invaders");
        setSize(300,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new FlowLayout());

        start = new JButton("start");
        add(start);

        scoreBoard = new JButton("Scoreboard");
        add(scoreBoard);

        theHandler handler = new theHandler();
        start.addActionListener(handler);
        scoreBoard.addActionListener(handler);

    }
    private  class theHandler implements ActionListener,Commons{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == start) {
                setVisible(false);
                SpaceInvaders ex = new SpaceInvaders();
                ex.initUI();
                ex.setVisible(true);

            }
            if (e.getSource() == scoreBoard) {

            }
        }
    }


}
