package ToolClass;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConn {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/restaurant";
    String name = "root";
    String password = "123456";
    private static Connection Conn;
    public DbConn() {
        try {
            Class.forName(driver);
            Conn = DriverManager.getConnection(url,name,password);

        }catch(Exception e){
            e.printStackTrace();

        }
    }
    public Connection getConn()
    {
        return Conn;
    }
    public static void main(String[] args)
    {
        DbConn cn = new DbConn();
    }

}
