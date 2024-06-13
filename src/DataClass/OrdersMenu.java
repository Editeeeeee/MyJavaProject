package DataClass;

import ToolClass.DbConn;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;

public class OrdersMenu {
    HashSet<orderdishs> set;
    public OrdersMenu()
    {
        set = new HashSet<orderdishs>();
    }
    public void adds(String dname,String price)
    {
        set.add(new orderdishs(dname,price));
    }
    public void delete(String dname)
    {
        if(set.size()>0) {
            for (orderdishs s : set) {
                if (s.dname == dname) {
                    set.remove(s);
                    break;
                }
            }
        }
    }
    public void deleteall()
    {
        set.removeAll(set);
    }
    public String getLength()
    {
        return Integer.toString(set.size());
    }
    public String getSumMoney()
    {
        int num = 0;
        for(orderdishs s:set)
        {
            num+=Integer.parseInt(s.price);
        }
        return Integer.toString(num);
    }
    public HashSet<orderdishs> getSet()
    {
        return set;
    }

    public void fillSet(int ordernumber,Connection con)
    {
        String sql = "select * from ordermenu where ordernumber='"+ordernumber+"'";
        try{
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(sql);
            while(rset.next())
            {
                set.add(new orderdishs(rset.getString("dname"),"0"));
            }
            stmt.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void printSet()
    {
        for(orderdishs s:set)
        {
            System.out.println(s.dname+","+s.price);
        }
    }
}
