package game;
import java.awt.FlowLayout;
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
        super("Space invaders");
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
            if (e.getSource()==start){
                SpaceInvaders ex = new SpaceInvaders();
                ex.initUI();
                ex.setVisible(true);

            }
        }
    }


}
