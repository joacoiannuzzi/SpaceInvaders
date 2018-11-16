package game;

import other.ImageLoader;

import java.awt.*;
import javax.swing.*;

public class ApeInvaders extends JFrame implements Commons {

    private Board board;
    private StartMenu startMenu;
    private EndMenu endMenu;

    public ApeInvaders(){
        board = new Board(this);

        setTitle("Ape Invaders");
        setIconImage(ImageLoader.load("alien.png"));
        setSize(BOARD_WIDTH,BOARD_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        //getContentPane().setBackground(null);
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
        board = new Board(this);
        startMenu = new StartMenu(this);
        if (endMenu != null)
            remove(endMenu);
        add(startMenu);
        startMenu.requestFocus();
        revalidate();
     }


    public static void main(String[] args) {

        EventQueue.invokeLater(ApeInvaders::new);
    }
}