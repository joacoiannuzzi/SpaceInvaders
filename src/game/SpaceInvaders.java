package game;

import javax.swing.JFrame;

public class SpaceInvaders extends JFrame implements Commons {

    Board board;

    public SpaceInvaders() {
        board = new Board();
    }


    public void initUI() {

        add(board);
        setTitle("Space Invaders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        board.startGame();

    }


}