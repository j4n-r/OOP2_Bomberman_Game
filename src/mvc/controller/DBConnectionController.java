package mvc.controller;

import mvc.util.Database;

import java.sql.*;

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



    public Connection getDbConnection() {
        return dbConnection;
    }


}
