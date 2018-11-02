package highscore;

import javax.swing.*;
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
        highScores = sort(highScores);
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
            outputStream.writeObject(highScores);

            outputStream.flush();
            outputStream.close();
        }
        catch (IOException ignored) {
        }
    }

    public static String toText() {

        ArrayList<Score> highScores = deserialize();

        String scores = "";

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

        for (int i = 0; i < highScores.size(); i++) {
            Score score = highScores.get(i);
            scores += String.format("%-10d %-30s %-15d %10s %n",
                    i + 1, score.getName(), score.getPoints(),
                    dateFormat.format(score.getDate()));
        }
        return scores;
    }
}

