package highscore;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Score> {

    public int compare(Score score1, Score score2) {

        int sc1 = score1.getPoints();
        int sc2 = score2.getPoints();

        return Integer.compare(sc2, sc1);
    }
}

