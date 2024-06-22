package mvc.model;

import java.util.ArrayList;
import java.util.List;

public class GameLog extends ArrayList<String> {
    private List<String> logs;

    public GameLog() {
        this.logs = new ArrayList<>();
    }

    public void addLog(String log) {
        this.logs.add(log);
    }

    public List<String> getLogs() {
        return this.logs;
    }
}