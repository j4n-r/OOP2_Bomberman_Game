package mvc.controller;

import mvc.model.GameLog;
import mvc.model.Database;

import java.sql.*;
import java.util.List;

public class DBConnectionController {
    private Statement statement;
    private Connection dbConnection;

    public DBConnectionController() {
        Database db = Database.getInstance();
        try {
            dbConnection = db.getConnection();
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public Connection getDbConnection() {
        return dbConnection;
    }

    public void setDbConnection(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }
}
