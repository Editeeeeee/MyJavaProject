package DataClass;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.Statement;

public class orderdishs
{
    String dname;
    String price;
    orderdishs(String dname,String price)
    {
        this.dname  = dname;
        this.price = price;
    }
    public static void deleteByOrdernumber(int ordernumber, Connection conn)
    {
        String sql = "delete from ordermenu where ordernumber ="+"'"+ordernumber+"'";
        try{
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public String getDname()
    {
        return dname;
    }
    public String getPrice()
    {
        return price;
    }
}
