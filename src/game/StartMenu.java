package game;

import other.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class StartMenu extends JPanel implements Commons {

    private ApeInvaders apeInvaders;
    private ArrayList<String> texts = new ArrayList<>();
    private BufferedImage image = ImageLoader.loadSubImage("background.png", 1280, 640);

    public StartMenu(ApeInvaders apeInvaders) {

        this.apeInvaders = apeInvaders;
        texts.add("Play [ENTER]");
        texts.add("ScoreBoard [SPACE]");
        texts.add("Exit [ESC]");

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_ENTER)
                    apeInvaders.startGame();

                if (key == KeyEvent.VK_SPACE)
                    apeInvaders.showScores();

                if (key == KeyEvent.VK_ESCAPE)
                    System.exit(0);
            }
        });
        setFocusable(true);
        requestFocus();
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, -120, 0, BOARD_WIDTH + 140, BOARD_HEIGHT, 0, 0,
                image.getWidth(), image.getHeight(), null);

        g.setColor(Color.green);

        Font title = new Font("Helvetica", Font.BOLD, 50);
        FontMetrics titleMetr = this.getFontMetrics(title);
        g.setFont(title);

        g.drawString("\uD83D\uDC7E APE INVADERS \uD83D\uDC7E", (BOARD_WIDTH - titleMetr.stringWidth("\uD83D\uDC7E APE INVADERS \uD83D\uDC7E")) / 2,
                200);

        Font small = new Font("Helvetica", Font.BOLD, 25);
        FontMetrics metr = this.getFontMetrics(small);
        g.setFont(small);
        g.setColor(Color.WHITE);

        for (int i = 0; i < texts.size(); i++) {
            String text = texts.get(i);
            g.drawString(text, (BOARD_WIDTH - metr.stringWidth(text)) / 2,
                    400 + 50 * i);
        }
    }
}
