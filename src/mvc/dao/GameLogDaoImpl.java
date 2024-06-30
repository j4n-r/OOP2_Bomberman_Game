package mvc.dao;

import mvc.controller.DBConnectionController;
import mvc.model.GameLog;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class GameLogDaoImpl implements GameLogDao {
    private GameLog gameLog;
    private GameLog replayGameLog;
    private Statement statement;
    private Connection dbConnection;

    public GameLogDaoImpl() {
        gameLog = new GameLog();
        replayGameLog = new GameLog();
        DBConnectionController dbConnectionController = new DBConnectionController();
        statement = dbConnectionController.getStatement();
        dbConnection = dbConnectionController.getDbConnection();

    }
    @Override
    public void saveGameLog(GameLog gameLog) {
        try {
            String dropQuery = "DROP TABLE game_log";
            statement.executeUpdate(dropQuery);

            String createQuery = "CREATE TABLE game_log (log VARCHAR2(255))";
            System.out.println("Creating table game_log");
            statement.executeUpdate(createQuery);

            for (String log : gameLog) {
                String stringQuery = "INSERT INTO game_log (log) VALUES ('" + log + "')";
                statement.executeUpdate(stringQuery);
            }
            System.out.println("Game log stored in database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GameLog getGameLog() {
        try {
            String selectQuery = "SELECT * FROM game_log";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                replayGameLog.add(resultSet.getString("log"));
            }
            System.out.println("Game log loaded from database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(replayGameLog);
        return replayGameLog;
    }
}
