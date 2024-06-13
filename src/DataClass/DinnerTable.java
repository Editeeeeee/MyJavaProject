package DataClass;

import java.sql.*;

public class DinnerTable {

    public static void setStatus(int tablenumber,String status,Connection conn)
    {
        String sql = "update dinnertable set status = '"+status+"' where tablenum='"+tablenumber+"'";
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }catch (Exception e)
        {
            System.out.println("修改错误"+e.getMessage());
        }
    }
    public static void InsertTable(Connection conn)
    {
        String sql = "Insert into dinnertable values()";
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void DeleteTable(Connection conn,int tablenumber) {
        String sql = "delete from dinnertable where tablenum='"+tablenumber+"'";
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
