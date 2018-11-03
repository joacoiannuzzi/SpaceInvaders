package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu_over extends JFrame implements Commons {
    private JButton Exit;
    private JButton Retry;

    private SpaceInvaders st = new SpaceInvaders();
    JPanel panel;

    public Menu_over(){

        setTitle("Game Over");
        setSize(295,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLocation(820,650);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        setUndecorated(true);

        panel = new JPanel();

        Retry = new JButton("Retry");
        Retry.setBackground(Color.BLACK);
        Retry.setForeground(Color.green);
        panel.add(Retry);

        Exit = new JButton("Exit");
        Exit.setBackground(Color.BLACK);
        Exit.setForeground(Color.green);
        panel.add(Exit);

        setLayout(new FlowLayout());

        theHandler handler_over = new theHandler();
        Exit.addActionListener(handler_over);
        Retry.addActionListener(handler_over);

        add(panel);

        setVisible(true);
    }

    private  class theHandler implements ActionListener,Commons {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == Retry) {

                setVisible(false);
                st.initUI();

            }
            if (e.getSource() == Exit) {

                System.exit(0);

            }
        }
    }

    public static void endmenu() {

        EventQueue.invokeLater(Menu_over::new);

    }

    /* public static void main(String[] args) {
        EventQueue.invokeLater(Menu_over::new);
    }*/


}

