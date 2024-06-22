package mvc.model;

import java.sql.*;
import oracle.jdbc.pool.OracleDataSource;
public class OracleDsSingleton {
    private static OracleDsSingleton dss = null;
    private static OracleDataSource ds = null;
    private OracleDsSingleton(){
        try {
            ds = new OracleDataSource();
            ds.setDataSourceName("HWROracleDataSource");
            ds.setURL("jdbc:oracle:thin:@//wi-dbora.hwr-berlin.de:1521/dbk.hwr-berlin.de");
            ds.setUser("oop2_ss24_g2_p1");
            ds.setPassword("oop2_ss24_g2_p1");
        } catch (SQLException e) {
            System.out.println("connection failed; " + e.getMessage());
        }

    }

public static OracleDsSingleton getInstance() {
    if (dss == null) dss = new OracleDsSingleton();
    return dss;
}
public Connection getConnection() throws SQLException{
    Connection con = null;
    con = ds.getConnection();
    return con;
}
}