package mvc.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import oracle.jdbc.pool.OracleDataSource;
public class Database {
    private static Database dbInstance = null;
    private static OracleDataSource oracleDataSource = null;
    private Database(){
        try {
            oracleDataSource = new OracleDataSource();
            oracleDataSource.setDataSourceName("HWROracleDataSource");
            oracleDataSource.setURL("jdbc:oracle:thin:@//wi-dbora.hwr-berlin.de:1521/dbk.hwr-berlin.de");
            oracleDataSource.setUser("oop2_ss24_g2_p1");
            // Read password from config.txt
            try (BufferedReader reader = new BufferedReader(new FileReader("config.txt"))) {
                String password = reader.readLine();
                oracleDataSource.setPassword(password);
            } catch (IOException e) {
                System.out.println("Failed to read password from config.txt; " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("connection failed; " + e.getMessage());
        }
    }

public static Database getInstance() {
    if (dbInstance == null) dbInstance = new Database();
    return dbInstance;
}
public Connection getConnection() throws SQLException{
    Connection dbConnection = null;
    dbConnection = oracleDataSource.getConnection();
    return dbConnection;
}
}