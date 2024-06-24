package mvc.controller;

import mvc.model.GameLog;
import mvc.model.Database;

import java.sql.*;
import java.util.List;

public class DatabaseController {
    private Statement statement;
    private Connection dbConnection;
    private List<String> gameLog;
    private List<String> oldGameLog;

    public DatabaseController() {
        gameLog = new GameLog();
        oldGameLog = new GameLog();
        Database db = Database.getInstance();
        try {
            dbConnection = db.getConnection();
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void storeGameLogInDatabase() {
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

    public void loadGameLogFromDatabase() {
        try {
            String selectQuery = "SELECT * FROM game_log";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                oldGameLog.add(resultSet.getString("log"));
            }
            System.out.println("Game log loaded from database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(oldGameLog);
    }
    public void printGameLog() {
        for (String log : gameLog) {
            System.out.println(log);
        }
    }
    public List<String> getGameLog() {
        return gameLog;
    }


    public void setGameLog(List<String> gameLog) {
        this.gameLog = gameLog;
    }

    public java.sql.Statement getStatement() {
        return statement;
    }

    public void setStatement(java.sql.Statement statement) {
        this.statement = statement;
    }

    public Connection getDbConnection() {
        return dbConnection;
    }

    public void setDbConnection(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<String> getOldGameLog() {
        return oldGameLog;
    }

    public void setOldGameLog(List<String> oldGameLog) {
        this.oldGameLog = oldGameLog;
    }
}
