package game;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Menu extends JFrame implements Commons {
    private JButton start;
    private JButton scoreBoard;
    private SpaceInvaders si = new SpaceInvaders();
    JPanel panel;

    public Menu(){

        setTitle("Space invaders");
        setSize(300,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        panel = new JPanel();
        start = new JButton("start");
        panel.add(start);

        scoreBoard = new JButton("Scoreboard");
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

            }
        }
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(Menu::new);
    }


}
