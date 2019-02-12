
package com.era.FingerCheck;

/**
 *
 * @author root
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnectivity {
  
    public static int a;
    public static Connection getConnection() {
        try {
            //step1 load the driver class  
            Class.forName("oracle.jdbc.driver.OracleDriver");

            //step2 create  the connection object  
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@10.11.201.55:1525/iabdb", "BIOTPL", "biotpl");
  
            return con;

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public static  void releaseConnection(Connection con){
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbConnectivity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
