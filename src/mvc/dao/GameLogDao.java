package mvc.dao;

import mvc.model.GameLog;

public interface GameLogDao {
     void saveGameLog(GameLog gameLog);
     GameLog getGameLog();
}
