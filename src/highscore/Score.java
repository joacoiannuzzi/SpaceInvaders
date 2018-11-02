package highscore;

import java.io.Serializable;
import java.util.Date;

public class Score implements Serializable {

    private int points;
    private String name;
    private Date date;

    public Score(String name, int points) {
        this.points = points;
        this.name = name;
        this.date = new Date();
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }
}
