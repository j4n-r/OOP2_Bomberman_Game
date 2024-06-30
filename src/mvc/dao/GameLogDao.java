package mvc.dao;

import mvc.model.GameLog;

public interface GameLogDao {
    public void saveGameLog(GameLog gameLog);
    public GameLog getGameLog();
}
