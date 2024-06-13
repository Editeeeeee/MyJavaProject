package DataClass;

import ToolClass.DbConn;
import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;

public class UserInfo {
    private String account = null;
    private String password = null;
    private String photo = null;
    private String username = null;
    private String tnumber = null;
    private String sex = null;
    private String address = null;
    private String identity = null;
    private int id;

    public UserInfo(String account, DbConn conn)
    {
        fillSet(account,conn.getConn());
    }

    public UserInfo() {

    }

    public void setInfo(String account, String password, String photo, String username, String tnumber, String sex, String address,String identity)
    {
        this.account = account;
        this.password = password;
        this.address = address;
        this.photo = photo;
        this.tnumber = tnumber;
        this.sex = sex;
        this.username = username;
        this.identity = identity;
    }
    public void insert(Connection conn)
    {
        String sql = "insert into userinfo(account,password,photo,username,tnumber,sex,address,identity) values(?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,account);
            stmt.setString(2,password);
            stmt.setString(3,photo);
            stmt.setString(4,username);
            stmt.setString(5,tnumber);
            stmt.setString(6,sex);
            stmt.setString(7,address);
            stmt.setString(8,identity);
            stmt.executeUpdate();
            stmt.close();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static CachedRowSet queryall(Connection conn)
    {
        String sql = "select * from userinfo";
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
    public static String  query(Connection conn,String account,String value)
    {
        String sql = "select "+value+" from userinfo where account"+"="+"'"+account+"'";
        String val = null;
        CachedRowSet result = null;
        try{
            ResultSet res = null;
            result = new CachedRowSetImpl();
            Statement stmt = conn.createStatement();
            res = stmt.executeQuery(sql);
            result.populate(res);
            stmt.close();
            while(result.next())
            {
                val = result.getString(value);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return val;
    }

    public void fillSet(String account, Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet result = null;
        try {
            String sql = "SELECT * FROM userinfo WHERE account = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, account);
            result = pstmt.executeQuery();
            while (result.next()) {
                this.account = result.getString("account");
                this.username = result.getString("username");
                this.password = result.getString("password");
                this.sex = result.getString("sex");
                this.tnumber = result.getString("tnumber");
                this.address = result.getString("address");
                this.id = result.getInt("id");
                this.identity = result.getString("identity");
                this.photo = result.getString("photo");
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
    public String getAccount() {
        return account;
    }

    public String getUsername() {
        return  username;
    }

    public String getSex() {
        return sex;
    }

    public String getTnumber() {
        return tnumber;
    }

    public String getAddress() {
        return address;
    }

    public String getIdentity() {
        return identity;
    }

    public String getPhoto() {
        return photo;
    }
    public String getPassword()
    {
        return password;
    }
    public int getId()
    {
        return id;
    }
}
