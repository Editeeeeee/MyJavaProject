package DataClass;

import java.sql.*;

public class Orders {
    private int ordernumber;
    private String telnumber;
    private String way;
    private String address;
    private String tablenumber;
    private String  courier;
    private String orderstatus;
    private String account;

    public void fillInfo(int ordernumber,Connection conn)
    {
        PreparedStatement pstmt = null;
        ResultSet result = null;
        try {
            String sql = "SELECT * FROM orders WHERE ordernumber = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ordernumber);
            result = pstmt.executeQuery();
            while (result.next()) {
                this.account = result.getString("account");
                this.ordernumber = result.getInt("ordernumber");
                this.way = result.getString("way");
                this.tablenumber = result.getString("tablenumber");
                this.courier = result.getString("courier");
                this.address = result.getString("address");
                this.orderstatus = result.getString("orderstatus");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取信息错误：" + e.getMessage());
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public static void setValueyouwantByOrdernumber(int ordernumber,String valuename,String value,Connection conn)
    {
        String sql = "UPDATE orders SET " + valuename + " = '" + value + "' WHERE ordernumber = '" + ordernumber + "'";
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }catch(Exception e)
        {
            System.out.println("修改失败：" + e.getMessage());
        }
    }
    public static void deleteByOrderNumber(int ordernumber,Connection conn)
    {
        String sql = "delete from orders where ordernumber="+"'"+ordernumber+"'";
        try{
            Statement stmt = conn.createStatement();
            if(stmt.executeUpdate(sql)>0)
            {
                orderdishs.deleteByOrdernumber(ordernumber,conn);
            }
            stmt.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    public int getOrdernumber()
    {
        return ordernumber;
    }
    public String getTelnumber()
    {
        return telnumber;
    }
    public String getWay()
    {
        return way;
    }
    public String getAddress()
    {
        return address;
    }
    public String getTablenumber()
    {
        return tablenumber;
    }
    public String getCourier()
    {
        return courier;
    }
    public String getOrderstatus()
    {
        return orderstatus;
    }
    public String getAccount()
    {
        return account;
    }
}
