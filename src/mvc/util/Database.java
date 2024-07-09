package mvc.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import oracle.jdbc.pool.OracleDataSource;
public class Database {
    private static Database dss = null;
    private static OracleDataSource ds = null;
    private Database(){
        try {
            ds = new OracleDataSource();
            ds.setDataSourceName("HWROracleDataSource");
            ds.setURL("jdbc:oracle:thin:@//wi-dbora.hwr-berlin.de:1521/dbk.hwr-berlin.de");
            ds.setUser("oop2_ss24_g2_p1");
            // Read password from config.txt
            try (BufferedReader reader = new BufferedReader(new FileReader("config.txt"))) {
                String password = reader.readLine();
                ds.setPassword(password);
            } catch (IOException e) {
                System.out.println("Failed to read password from config.txt; " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("connection failed; " + e.getMessage());
        }

    }

public static Database getInstance() {
    if (dss == null) dss = new Database();
    return dss;
}
public Connection getConnection() throws SQLException{
    Connection con = null;
    con = ds.getConnection();
    return con;
}
}