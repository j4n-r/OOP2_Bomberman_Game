package mvc.controller;

import mvc.model.Cell;
import mvc.model.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController {
    private Statement statement;
    private Connection dbConnection;
    private List<Cell[][]> gameLog = new ArrayList<>();

    public DatabaseController() {
        Database db = Database.getInstance();
        try {
            dbConnection = db.getConnection();
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cell[][]> getGameLog() {
        return gameLog;
    }

    public void setGameLog(List<Cell[][]> gameLog) {
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
}
