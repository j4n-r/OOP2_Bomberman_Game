package mvc.model;

import java.util.ArrayList;
import java.util.List;

public class GameLog extends ArrayList<String> {
    public GameLog() {
        super();
    }
    public void printGameLog() {
        for (String log : this) {
            System.out.println(log);
        }
    }

}