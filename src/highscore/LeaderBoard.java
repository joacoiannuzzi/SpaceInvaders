package highscore;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class LeaderBoard {

    private static final String HIGHSCORE_FILE = "src/highscore/highscores.dat";
    private static File file = new File(HIGHSCORE_FILE);

    public static void createFile() {

        if (!file.exists()) {
            try {
                file.createNewFile();
                ArrayList<Score> highScores = new ArrayList<>();
                serialize(highScores);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void rank(int score) {
        ArrayList<Score> highScores = deserialize();
        if (highScores.size() > 0 ) {
            if (score > highScores.get(0).getPoints()) {
                create(score, "New highest score!");
                return;
            }
        }
        if (highScores.size() >= 10) {
            if (score > highScores.get(9).getPoints()) {
                create(score, "You have reached the top scores!");
                return;
            }
        }
        if (highScores.size() < 10) {
            create(score, "You have reached the top scores!");
        }
    }

    public static void create(int score, String message) {
        String playerName = JOptionPane.showInputDialog(message + "\nEnter your name!");

        while (Objects.equals(playerName, "") || playerName == null)
            playerName = JOptionPane.showInputDialog("Enter your name, please!");

        addScore(playerName, score);
    }

    public static void clear() {
        ArrayList<Score> highScores = deserialize();
        highScores.clear();
        serialize(highScores);
    }

    public static void remove(int position) {
        ArrayList<Score> highScores = deserialize();
        highScores.remove(position - 1);
        serialize(highScores);
    }

    private static ArrayList<Score> sort(ArrayList<Score> highScores) {

        ScoreComparator comparator = new ScoreComparator();
        highScores.sort(comparator);

        while (highScores.size() > 10) {
            highScores.remove(10);
        }
        return highScores;
    }

    public static void addScore(String name, int score) {
        ArrayList<Score> highScores = deserialize();
        highScores.add(new Score(name, score));
        serialize(highScores);
    }

    public static ArrayList<Score> deserialize() {
        ArrayList<Score> highScores = null;
        createFile();
        try {
            ObjectInputStream inputStream = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(HIGHSCORE_FILE)));
            highScores = (ArrayList<Score>) inputStream.readObject();

            sort(highScores);

            inputStream.close();
        }
        catch (IOException | ClassNotFoundException ignored) {
        }
        return highScores;
    }

    public static void serialize(ArrayList<Score> highScores) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(HIGHSCORE_FILE)));
            highScores = sort(highScores);
            outputStream.writeObject(highScores);

            outputStream.flush();
            outputStream.close();
        }
        catch (IOException ignored) {
        }
    }

    public static void toText(Graphics g) {


        ArrayList<Score> highScores = deserialize();

//        if (line >= highScores.size()) {
//            return "";
//        }

        String scores = "";

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

//        g.drawString("");

        for (int i = 0; i < highScores.size(); i++) {

            Score score = highScores.get(i);
//
            g.drawString("" + (i + 1), 10, 50 + 20 * i);
            g.drawString(score.getName(), 70, 50 + 20 * i);
            g.drawString("" + score.getPoints(),400,50 + 20 * i);
            g.drawString("" + dateFormat.format(score.getDate()),700,50 + 20 * i);
        }
    }


}

