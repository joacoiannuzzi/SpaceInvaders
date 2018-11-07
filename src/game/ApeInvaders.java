package game;

import java.awt.*;
import javax.swing.*;

public class ApeInvaders extends JFrame implements Commons {

    private Board board;
    private StartMenu startMenu;
    private EndMenu endMenu;

    public ApeInvaders(){
        board = new Board(this);

        setTitle("Ape invaders");
        setSize(BOARD_WIDTH,BOARD_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(null);
        //setUndecorated(true);
        setVisible(true);
        restartGame();

    }


     public void startGame() {
        remove(startMenu);
        add(board);
        revalidate();
        board.startGame();
     }

     public void showScores() {
        remove(startMenu);
        remove(board);
        endMenu = new EndMenu(this);
        add(endMenu);
        revalidate();
     }

     public void restartGame() {
        startMenu = new StartMenu(this);
        board = new Board(this);
        if (endMenu != null)
            remove(endMenu);
        add(startMenu);
        revalidate();
     }


    public static void main(String[] args) {

        EventQueue.invokeLater(ApeInvaders::new);
    }


}
