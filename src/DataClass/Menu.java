package DataClass;

import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Menu {
    private String dname;
    private int price;
    private String kind;
    private String introduce;
    private int id;
    private String image;
    public Menu(String dname,int price,String introduce,String kind,String image)
    {
        this.dname = dname;
        this.kind = kind;
        this.introduce = introduce;
        this.price = price;
        this.image = image;
    }
    public Menu(){

    }
    public void setInfo(String dname,int price,String introduce,String kind,String image)
    {
        this.dname = dname;
        this.kind = kind;
        this.introduce = introduce;
        this.price = price;
        this.image = image;
    }
    public void insert(Connection conn)
    {
        String sql = "insert into userinfo(dname,kind,introduce,price,image) values(?,?,?,?,?)";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,dname);
            stmt.setString(2,kind);
            stmt.setString(3,introduce);
            stmt.setInt(4,price);
            stmt.setString(5,image);
            stmt.executeUpdate();
            stmt.close();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static CachedRowSet queryall(Connection conn)
    {
        String sql = "select * from menu";
        CachedRowSet result = null;
        try{
            ResultSet res = null;
            result = new CachedRowSetImpl();
            Statement stmt = conn.createStatement();
            res = stmt.executeQuery(sql);
            result.populate(res);
            stmt.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

}
