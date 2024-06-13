package restaurant;

import javax.imageio.ImageIO;
import javax.sql.rowset.CachedRowSet;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimerTask;

import DataClass.Menu;
import DataClass.OrdersMenu;
import ToolClass.*;

import static java.lang.Thread.sleep;

public class UserMainIF {
    String username;
    String imagepath;
    OrdersMenu ordersmenu = new OrdersMenu();
    int buycounts = 0;
    int buymoneys = 0;
    UserMainIF(String localaccount){
        //读取头像和姓名信息
        try{
            DbConn conn = new DbConn();
            Statement stmt = conn.getConn().createStatement();
            String sql = "select * from userinfo where account="+"'"+localaccount+"'";
            ResultSet set = stmt.executeQuery(sql);
            while(set.next())
            {
                username = set.getString("username");
                imagepath = set.getString("photo");
            }
            stmt.close();
            set.close();
        }
        catch (Exception e2)
        {
            e2.printStackTrace();
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JFrame fp = new JFrame();
        fp.setLayout(new BorderLayout());


        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        fp.add(p, BorderLayout.CENTER);


        JPanel pd = new JPanel();
        JLabel buycount = new JLabel("购买数量:"+buycounts);;
        JLabel buymouney = new JLabel("总计金额:"+buymoneys+"元");;
        JLabel shopping;
        //商标
        ImagePanel pu = new ImagePanel(".\\image\\背景1.png");
        pu.setLayout(new FlowLayout());
        pu.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pu.setBackground(new Color(0, 178, 255, 123));

        pu.setPreferredSize(new Dimension(550, 80));

        p.add(pu);
        try {
            JPanel p_pm  = new JPanel();

            p_pm.setBackground(new Color(0,0,0,0));
            Image pm = ImageIO.read(new File(".\\image\\图标.png"));
            CirclePanel circlePanel = new CirclePanel(pm);
            circlePanel.setPreferredSize(new Dimension(70, 70));
            circlePanel.setMaximumSize(new Dimension(70, 70));
            circlePanel.setMinimumSize(new Dimension(70, 70));
            circlePanel.setBackground(new Color(0,0,0,0));
            p_pm.add(circlePanel);
            pu.add(p_pm,BorderLayout.WEST);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //店名
        JPanel p_space = new JPanel();
        p_space.setPreferredSize(new Dimension(250,80));
        p_space.setLayout(new BorderLayout());
        p_space.setBackground(new Color(0,0,0,0));
        JLabel space = new JLabel("坤坤餐饮");
        space.setFont(new Font("楷体",Font.BOLD,30));
        space.setForeground(new Color(229, 221, 221));
        space.setBackground(new Color(0,0,0,0));
        p_space.add(space,BorderLayout.CENTER);
        pu.add(p_space,BorderLayout.CENTER);

        //用户头像
        JPanel p_user = new JPanel();
        p_user.setBackground(new Color(0,0,0,0));
        try {
            Image pa = ImageIO.read(new File(imagepath));
            CirclePanel circlePanel = new CirclePanel(pa);
            circlePanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    new personerIF(localaccount,fp);
                }
            });
            circlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            circlePanel.setPreferredSize(new Dimension(40, 40));
            circlePanel.setMaximumSize(new Dimension(40, 40));
            circlePanel.setMinimumSize(new Dimension(40, 40));
            circlePanel.setBackground(new Color(0,0,0,0));
            p_user.add(circlePanel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JLabel user = new JLabel(username);
        user.setBackground(new Color(0,0,0,0));
        user.setFont(new Font("楷书",Font.BOLD,15));
        user.setForeground(new Color(255, 255, 255));
        p_user.add(user);
        pu.add(p_user,BorderLayout.EAST);

        JPanel pc = new JPanel();
        pc.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pc.setBackground(Color.pink);
        pc.setPreferredSize(new Dimension(550, 240));
        pc.setLayout(new BoxLayout(pc,BoxLayout.X_AXIS));


        JPanel pleft = new JPanel();
        JPanel pright = new JPanel();
        pright.setBackground(Color.PINK);
        pright.setPreferredSize(new Dimension(60, 240));
        pright.setLayout(new BoxLayout(pright,BoxLayout.Y_AXIS));
        pc.add(pright);
        p.add(pc);

        JPanel pcl5 = new JPanel();
        pcl5.setLayout(new BorderLayout());
        pcl5.setBackground(Color.white);
        JLabel cl5 = new JLabel("全部");
        cl5.setFont(new Font("楷书",Font.PLAIN,20));
        pcl5.setPreferredSize(new Dimension(60, 48));
        pcl5.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        pcl5.add(cl5,BorderLayout.CENTER);

        JPanel pcl1 = new JPanel();
        pcl1.setLayout(new BorderLayout());
        ClickLabel cl1 = new ClickLabel("主食");
        pcl1.setBackground(Color.white);
        cl1.setFont(new Font("楷书",Font.PLAIN,20));
        pcl1.setPreferredSize(new Dimension(60, 48));
        pcl1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        pcl1.add(cl1,BorderLayout.CENTER);

        JPanel pcl2 = new JPanel();
        pcl2.setLayout(new BorderLayout());
        pcl2.setBackground(Color.white);
        ClickLabel cl2 = new ClickLabel("主菜");
        pcl2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        cl2.setFont(new Font("楷书",Font.PLAIN,20));
        pcl2.setPreferredSize(new Dimension(60, 48));
        pcl2.add(cl2,BorderLayout.CENTER);

        JPanel pcl3 = new JPanel();
        pcl3.setLayout(new BorderLayout());
        pcl3.setBackground(Color.white);
        ClickLabel cl3 = new ClickLabel("小食");
        pcl3.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        cl3.setFont(new Font("楷书",Font.PLAIN,20));
        pcl3.setPreferredSize(new Dimension(60, 48));
        pcl3.add(cl3,BorderLayout.CENTER);

        JPanel pcl4 = new JPanel();
        pcl4.setLayout(new BorderLayout());
        pcl4.setBackground(Color.white);
        ClickLabel cl4 = new ClickLabel("饮料");
        pcl4.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        cl4.setFont(new Font("楷书",Font.PLAIN,20));
        pcl4.setPreferredSize(new Dimension(60, 48));
        pcl4.add(cl4,BorderLayout.CENTER);

        pleft.setLayout(new CardLayout());
        pleft.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pleft.setPreferredSize(new Dimension(500, 240));

        pc.add(pleft);
        //第一页 全部
        JPanel left_p1 = new JPanel();
        JScrollPane Js = new JScrollPane(left_p1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        Js.getVerticalScrollBar().setUnitIncrement(10);
        pleft.add("1",Js);

        DbConn con = new DbConn();
        CachedRowSet set = Menu.queryall(con.getConn());
        try{
            int num = 0;
            while(set.next())
            {
                num++;
                String path,name,thisprice;
                path = set.getString("image");
                name = set.getString("dname");
                thisprice = set.getString("price");

                JPanel jp = new JPanel();
                jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));
                jp.setPreferredSize(new Dimension(250, 120));

                left_p1.add(jp);
                ImagePanel im = new ImagePanel(path);
                im.setPreferredSize(new Dimension(230, 100));
                im.setMaximumSize(new Dimension(230,100));
                im.setMinimumSize(new Dimension(230,100));


                jp.add(im);
                JPanel pl = new JPanel();
                pl.setPreferredSize(new Dimension(230, 20));
                pl.setMaximumSize(new Dimension(230,230));

                pl.setBackground(Color.BLACK);
                pl.setLayout( new GridLayout(1,3));
                pl.setBorder(new EmptyBorder(0,0,0,0));
                JLabel lname = new JLabel(name);
                lname.setFont(new Font("楷体",Font.BOLD,15));
                lname.setForeground(Color.red);
                JLabel lprice = new JLabel("\uFFE5"+thisprice);
                lprice.setFont(new Font("楷体",Font.ITALIC,20));
                lprice.setForeground(Color.yellow);

                JPanel pbutton = new JPanel();
                pbutton.setBackground(Color.BLACK);
                pbutton.setLayout(new BoxLayout(pbutton,BoxLayout.X_AXIS));
                Image image = ImageIO.read(new File("./image/加号.png"));
                AddButtonCirclePanel adb1 = new AddButtonCirclePanel(image);
                adb1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        ordersmenu.adds(name,thisprice);
                        buycount.setText("购买数量:"+ordersmenu.getLength());
                        buymouney.setText("总计金额:"+ordersmenu.getSumMoney()+"元");
                    }
                });
                adb1.setPreferredSize(new Dimension(20,20));
                adb1.setMaximumSize(new Dimension(20,20));
                adb1.setMinimumSize(new Dimension(20,20));
                pbutton.add(adb1);
                adb1.setBackground(Color.BLACK);

                JLabel spe = new JLabel(" ");
                spe.setBackground(Color.BLACK);
                pbutton.add(spe);
                image = ImageIO.read(new File("./image/减号.png"));
                AddButtonCirclePanel adb2 = new AddButtonCirclePanel(image);
                adb2.setPreferredSize(new Dimension(20,20));
                adb2.setMaximumSize(new Dimension(20,20));
                adb2.setMinimumSize(new Dimension(20,20));
                adb2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        ordersmenu.delete(name);
                        buycount.setText("购买数量:"+ordersmenu.getLength());
                        buymouney.setText("总计金额:"+ordersmenu.getSumMoney()+"元");
                    }
                });
                pbutton.add(adb2);
                adb2.setBackground(Color.BLACK);
                pl.add(lname);
                pl.add(lprice);
                pl.add(pbutton);
                jp.add(pl);
            }
            left_p1.setLayout(new GridLayout(num/2,2));
            left_p1.setPreferredSize(new Dimension(250, 240*num/4));

        }catch(Exception e1)
        {
            e1.printStackTrace();
        }
        //第二页 主食
        JPanel left_p2 = new JPanel();
        JScrollPane Js1 = new JScrollPane(left_p2);
        Js1.getVerticalScrollBar().setUnitIncrement(10);
        Js1.setPreferredSize(new Dimension(500, 240));
        pleft.add("2",Js1);

        try{
            Statement stmt = con.getConn().createStatement();
            String sql = "Select * from menu where kind = '主食'";
            ResultSet rset= stmt.executeQuery(sql);
            int num = 0;
            while(rset.next())
            {
                num++;
                String path,name,thisprice;
                path = rset.getString("image");
                name = rset.getString("dname");
                thisprice = rset.getString("price");

                JPanel jp = new JPanel();
                jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));
                jp.setPreferredSize(new Dimension(250, 120));
                left_p2.add(jp);
                ImagePanel im = new ImagePanel(path);
                im.setPreferredSize(new Dimension(230, 100));
                im.setMaximumSize(new Dimension(400, 200));
                im.setMinimumSize(new Dimension(400, 200));
                jp.add(im);
                JPanel pl = new JPanel();
                pl.setPreferredSize(new Dimension(230, 20));
                pl.setMaximumSize(new Dimension(400, 20));
                pl.setMinimumSize(new Dimension(400, 20));
                pl.setBackground(Color.BLACK);
                pl.setLayout( new GridLayout(1,2));
                pl.setBorder(new EmptyBorder(0,0,0,0));
                JLabel lname = new JLabel(name);
                lname.setFont(new Font("楷体",Font.BOLD,15));
                lname.setForeground(Color.red);
                JLabel lprice = new JLabel("\uFFE5"+thisprice);
                lprice.setFont(new Font("楷体",Font.ITALIC,20));
                lprice.setForeground(Color.yellow);

                JPanel pbutton = new JPanel();
                pbutton.setBackground(Color.BLACK);
                pbutton.setLayout(new BoxLayout(pbutton,BoxLayout.X_AXIS));
                Image image = ImageIO.read(new File("./image/加号.png"));
                AddButtonCirclePanel adb1 = new AddButtonCirclePanel(image);
                adb1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        ordersmenu.adds(name,thisprice);
                        buycount.setText("购买数量:"+ordersmenu.getLength());
                        buymouney.setText("总计金额:"+ordersmenu.getSumMoney()+"元");
                    }
                });
                adb1.setPreferredSize(new Dimension(20,20));
                adb1.setMaximumSize(new Dimension(20,20));
                adb1.setMinimumSize(new Dimension(20,20));
                pbutton.add(adb1);
                adb1.setBackground(Color.BLACK);

                JLabel spe = new JLabel(" ");
                spe.setBackground(Color.BLACK);
                pbutton.add(spe);
                image = ImageIO.read(new File("./image/减号.png"));
                AddButtonCirclePanel adb2 = new AddButtonCirclePanel(image);
                adb2.setPreferredSize(new Dimension(20,20));
                adb2.setMaximumSize(new Dimension(20,20));
                adb2.setMinimumSize(new Dimension(20,20));
                adb2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        ordersmenu.delete(name);
                        buycount.setText("购买数量:"+ordersmenu.getLength());
                        buymouney.setText("总计金额:"+ordersmenu.getSumMoney()+"元");
                    }
                });
                pbutton.add(adb2);
                adb2.setBackground(Color.BLACK);
                pl.add(lname);
                pl.add(lprice);
                pl.add(pbutton);
                jp.add(pl);
            }
            left_p2.setLayout(new GridLayout(num/2,2));
            left_p2.setPreferredSize(new Dimension(250, 240*num/4));
            left_p2.setMaximumSize(new Dimension(250, 240*num/4));
            left_p2.setMinimumSize(new Dimension(250, 240*num/4));
        }catch(Exception e){
            e.printStackTrace();
        }
        //第三页 主菜
        JPanel left_p3 = new JPanel();
        JScrollPane Js2 = new JScrollPane(left_p3);
        Js2.getVerticalScrollBar().setUnitIncrement(10);
        Js2.setPreferredSize(new Dimension(500, 240));
        pleft.add("3",Js2);

        try{
            Statement stmt = con.getConn().createStatement();
            String sql = "Select * from menu where kind = '主菜'";
            ResultSet rset= stmt.executeQuery(sql);
            int num = 0;
            while(rset.next())
            {
                num++;
                String path,name,thisprice;
                path = rset.getString("image");
                name = rset.getString("dname");
                thisprice = rset.getString("price");
                JPanel jp = new JPanel();
                jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));
                jp.setPreferredSize(new Dimension(250, 120));

                left_p3.add(jp);
                ImagePanel im = new ImagePanel(path);
                im.setPreferredSize(new Dimension(230, 100));
                im.setMaximumSize(new Dimension(400, 200));
                im.setMinimumSize(new Dimension(400, 200));
                jp.add(im);
                JPanel pl = new JPanel();
                pl.setPreferredSize(new Dimension(230, 20));
                pl.setMaximumSize(new Dimension(400, 20));
                pl.setMinimumSize(new Dimension(400, 20));
                pl.setBackground(Color.BLACK);
                pl.setLayout( new GridLayout(1,2));
                pl.setBorder(new EmptyBorder(0,0,0,0));
                JLabel lname = new JLabel(name);
                lname.setFont(new Font("楷体",Font.BOLD,15));
                lname.setForeground(Color.red);
                JLabel lprice = new JLabel("\uFFE5"+thisprice);
                lprice.setFont(new Font("楷体",Font.ITALIC,20));
                lprice.setForeground(Color.yellow);

                JPanel pbutton = new JPanel();
                pbutton.setBackground(Color.BLACK);
                pbutton.setLayout(new BoxLayout(pbutton,BoxLayout.X_AXIS));
                Image image = ImageIO.read(new File("./image/加号.png"));
                AddButtonCirclePanel adb1 = new AddButtonCirclePanel(image);
                adb1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        ordersmenu.adds(name,thisprice);
                        buycount.setText("购买数量:"+ordersmenu.getLength());
                        buymouney.setText("总计金额:"+ordersmenu.getSumMoney()+"元");
                    }
                });
                adb1.setPreferredSize(new Dimension(20,20));
                adb1.setMaximumSize(new Dimension(20,20));
                adb1.setMinimumSize(new Dimension(20,20));
                pbutton.add(adb1);
                adb1.setBackground(Color.BLACK);

                JLabel spe = new JLabel(" ");
                spe.setBackground(Color.BLACK);
                pbutton.add(spe);
                image = ImageIO.read(new File("./image/减号.png"));
                AddButtonCirclePanel adb2 = new AddButtonCirclePanel(image);
                adb2.setPreferredSize(new Dimension(20,20));
                adb2.setMaximumSize(new Dimension(20,20));
                adb2.setMinimumSize(new Dimension(20,20));
                adb2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        ordersmenu.delete(name);
                        buycount.setText("购买数量:"+ordersmenu.getLength());
                        buymouney.setText("总计金额:"+ordersmenu.getSumMoney()+"元");
                    }
                });
                pbutton.add(adb2);
                adb2.setBackground(Color.BLACK);
                pl.add(lname);
                pl.add(lprice);
                pl.add(pbutton);
                jp.add(pl);
            }
            left_p3.setLayout(new GridLayout(num/2,2));
            left_p3.setPreferredSize(new Dimension(250, 240*num/4));
            left_p3.setMaximumSize(new Dimension(250, 240*num/4));
            left_p3.setMinimumSize(new Dimension(250, 240*num/4));
        }catch(Exception e){
            e.printStackTrace();
        }
        //第四页 小食
        JPanel left_p4 = new JPanel();
        JScrollPane Js4 = new JScrollPane(left_p4);
        Js4.getVerticalScrollBar().setUnitIncrement(10);
        Js2.setPreferredSize(new Dimension(500, 240));
        pleft.add("4",Js4);

        try{
            Statement stmt = con.getConn().createStatement();
            String sql = "Select * from menu where kind = '小食'";
            ResultSet rset= stmt.executeQuery(sql);
            int num = 0;
            while(rset.next())
            {
                num++;
                String path,name,thisprice;
                path = rset.getString("image");
                name = rset.getString("dname");
                thisprice = rset.getString("price");
                JPanel jp = new JPanel();
                jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));
                jp.setPreferredSize(new Dimension(250, 120));
                left_p4.add(jp);
                ImagePanel im = new ImagePanel(path);
                im.setPreferredSize(new Dimension(230, 100));
                im.setMaximumSize(new Dimension(400, 200));
                im.setMinimumSize(new Dimension(400, 200));
                jp.add(im);
                JPanel pl = new JPanel();
                pl.setPreferredSize(new Dimension(230, 20));
                pl.setMaximumSize(new Dimension(400, 20));
                pl.setMinimumSize(new Dimension(400, 20));
                pl.setBackground(Color.BLACK);
                pl.setLayout( new GridLayout(1,2));
                pl.setBorder(new EmptyBorder(0,0,0,0));
                JLabel lname = new JLabel(name);
                lname.setFont(new Font("楷体",Font.BOLD,15));
                lname.setForeground(Color.red);
                JLabel lprice = new JLabel("\uFFE5"+thisprice);
                lprice.setFont(new Font("楷体",Font.ITALIC,20));
                lprice.setForeground(Color.yellow);
                JPanel pbutton = new JPanel();
                pbutton.setBackground(Color.BLACK);
                pbutton.setLayout(new BoxLayout(pbutton,BoxLayout.X_AXIS));
                Image image = ImageIO.read(new File("./image/加号.png"));
                AddButtonCirclePanel adb1 = new AddButtonCirclePanel(image);
                adb1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        ordersmenu.adds(name,thisprice);
                        buycount.setText("购买数量:"+ordersmenu.getLength());
                        buymouney.setText("总计金额:"+ordersmenu.getSumMoney()+"元");
                    }
                });
                adb1.setPreferredSize(new Dimension(20,20));
                adb1.setMaximumSize(new Dimension(20,20));
                adb1.setMinimumSize(new Dimension(20,20));
                pbutton.add(adb1);
                adb1.setBackground(Color.BLACK);

                JLabel spe = new JLabel(" ");
                spe.setBackground(Color.BLACK);
                pbutton.add(spe);
                image = ImageIO.read(new File("./image/减号.png"));
                AddButtonCirclePanel adb2 = new AddButtonCirclePanel(image);
                adb2.setPreferredSize(new Dimension(20,20));
                adb2.setMaximumSize(new Dimension(20,20));
                adb2.setMinimumSize(new Dimension(20,20));
                adb2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        ordersmenu.delete(name);
                        buycount.setText("购买数量:"+ordersmenu.getLength());
                        buymouney.setText("总计金额:"+ordersmenu.getSumMoney()+"元");
                    }
                });
                pbutton.add(adb2);
                adb2.setBackground(Color.BLACK);
                pl.add(lname);
                pl.add(lprice);
                pl.add(pbutton);
                jp.add(pl);
            }
            left_p4.setLayout(new GridLayout(num/2,2));
            left_p4.setPreferredSize(new Dimension(250, 240*num/4));
            left_p4.setMaximumSize(new Dimension(250, 240*num/4));
            left_p4.setMinimumSize(new Dimension(250, 240*num/4));
        }catch(Exception e){
            e.printStackTrace();
        }
        //第五饮料
        JPanel left_p5 = new JPanel();
        JScrollPane Js5 = new JScrollPane(left_p5);
        Js5.getVerticalScrollBar().setUnitIncrement(10);
        Js2.setPreferredSize(new Dimension(500, 240));
        pleft.add("5",Js5);

        try{
            Statement stmt = con.getConn().createStatement();
            String sql = "Select * from menu where kind = '饮料'";
            ResultSet rset= stmt.executeQuery(sql);
            int num = 0;
            while(rset.next())
            {
                num++;
                String path,name,thisprice;
                path = rset.getString("image");
                name = rset.getString("dname");
                thisprice = rset.getString("price");
                JPanel jp = new JPanel();
                jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));
                jp.setPreferredSize(new Dimension(250, 120));
                left_p5.add(jp);
                ImagePanel im = new ImagePanel(path);
                im.setPreferredSize(new Dimension(230, 100));
                im.setMaximumSize(new Dimension(400, 200));
                im.setMinimumSize(new Dimension(400, 200));
                jp.add(im);
                JPanel pl = new JPanel();
                pl.setPreferredSize(new Dimension(230, 20));
                pl.setMaximumSize(new Dimension(400, 20));
                pl.setMinimumSize(new Dimension(400, 20));
                pl.setBackground(Color.BLACK);
                pl.setLayout( new GridLayout(1,2));
                pl.setBorder(new EmptyBorder(0,0,0,0));
                JLabel lname = new JLabel(name);
                lname.setFont(new Font("楷体",Font.BOLD,15));
                lname.setForeground(Color.red);
                JLabel lprice = new JLabel("\uFFE5"+thisprice);
                lprice.setFont(new Font("楷体",Font.ITALIC,20));
                lprice.setForeground(Color.yellow);
                JPanel pbutton = new JPanel();
                pbutton.setBackground(Color.BLACK);
                pbutton.setLayout(new BoxLayout(pbutton,BoxLayout.X_AXIS));
                Image image = ImageIO.read(new File("./image/加号.png"));
                AddButtonCirclePanel adb1 = new AddButtonCirclePanel(image);
                adb1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        ordersmenu.adds(name,thisprice);
                        buycount.setText("购买数量:"+ordersmenu.getLength());
                        buymouney.setText("总计金额:"+ordersmenu.getSumMoney()+"元");
                    }
                });
                adb1.setPreferredSize(new Dimension(20,20));
                adb1.setMaximumSize(new Dimension(20,20));
                adb1.setMinimumSize(new Dimension(20,20));
                pbutton.add(adb1);
                adb1.setBackground(Color.BLACK);

                JLabel spe = new JLabel(" ");
                spe.setBackground(Color.BLACK);
                pbutton.add(spe);
                image = ImageIO.read(new File("./image/减号.png"));
                AddButtonCirclePanel adb2 = new AddButtonCirclePanel(image);
                adb2.setPreferredSize(new Dimension(20,20));
                adb2.setMaximumSize(new Dimension(20,20));
                adb2.setMinimumSize(new Dimension(20,20));
                adb2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        ordersmenu.delete(name);
                        buycount.setText("购买数量:"+ordersmenu.getLength());
                        buymouney.setText("总计金额:"+ordersmenu.getSumMoney()+"元");
                    }
                });
                pbutton.add(adb2);
                adb2.setBackground(Color.BLACK);
                pl.add(lname);
                pl.add(lprice);
                pl.add(pbutton);

                jp.add(pl);
            }
            left_p5.setLayout(new GridLayout(num/2,2));
            left_p5.setPreferredSize(new Dimension(250, 240*num/4));
            left_p5.setMaximumSize(new Dimension(250, 240*num/4));
            left_p5.setMinimumSize(new Dimension(250, 240*num/4));
        }catch(Exception e){
            e.printStackTrace();
        }
        //为分类栏设置点击事件
        cl5.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                pcl5.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.black));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pcl5.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
                CardLayout layout = (CardLayout) pleft.getLayout();
                layout.show(pleft,"1");
                pleft.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                cl5.setForeground(Color.pink);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cl5.setForeground(Color.black);
            }
        });
        cl1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cl1.setForeground(Color.black);
                pcl1.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.black));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pcl1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
                CardLayout layout = (CardLayout) pleft.getLayout();
                layout.show(pleft,"2");
                pleft.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                cl1.setForeground(Color.pink);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cl1.setForeground(Color.black);
            }
        });
        cl2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pcl2.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.black));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pcl2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
                CardLayout layout = (CardLayout) pleft.getLayout();
                layout.show(pleft,"3");
                pleft.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                cl2.setForeground(Color.pink);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cl2.setForeground(Color.black);
            }
        });

        cl3.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                pcl3.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.black));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pcl3.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
                CardLayout layout = (CardLayout) pleft.getLayout();
                layout.show(pleft,"4");
                pleft.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                cl3.setForeground(Color.pink);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cl3.setForeground(Color.black);
            }
        });
        cl4.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                pcl4.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.black));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pcl4.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
                CardLayout layout = (CardLayout) pleft.getLayout();
                layout.show(pleft,"5");
                pleft.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                cl4.setForeground(Color.pink);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cl4.setForeground(Color.black);
            }
        });


        pright.add(pcl5);
        pright.add(pcl1);
        pright.add(pcl2);
        pright.add(pcl3);
        pright.add(pcl4);

        pd.setBackground(Color.BLACK);
        pd.setLayout(new GridLayout(1,3));
        pd.setBorder(new EmptyBorder(0,0,0,0));
        pd.setPreferredSize(new Dimension(550, 40));
        p.add(pd);
        buycount.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.white));
        buycount.setFont(new Font("楷体", Font.PLAIN,20));
        buycount.setForeground(Color.white);
        pd.add(buycount);
        buymouney.setFont(new Font("楷体", Font.PLAIN,20));
        buymouney.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.white));
        buymouney.setForeground(Color.white);
        pd.add(buymouney);
        shopping = new JLabel("购物车");
        shopping.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                shopping.setForeground(Color.yellow);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                shopping.setForeground(Color.white);
                if(Integer.parseInt(ordersmenu.getLength())>0)
                new ShoppingTrolley(ordersmenu,localaccount);
                else{
                    JOptionPane.showMessageDialog(fp,"购物车不能为空");
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                shopping.setFont(new Font("楷体", Font.BOLD,20));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                shopping.setFont(new Font("楷体", Font.PLAIN,20));
            }
        });
        shopping.setFont(new Font("楷体", Font.PLAIN,20));
        shopping.setForeground(Color.white);
        pd.add(shopping);

        javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buycount.setText("购买数量:"+ordersmenu.getLength());
                buymouney.setText("总计金额:"+ordersmenu.getSumMoney()+"元");
            }
        });
        timer.setInitialDelay(0);
        timer.start();

        fp.setBounds(500,300,565,400);
        ImageIcon icon = new ImageIcon(".\\image\\图标.png");
        Image image = icon.getImage();
        fp.setIconImage(image);
        fp.setVisible(true);
        fp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fp.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = fp.getWidth();
                int height = fp.getHeight();
                p_space.setPreferredSize(new Dimension(250+(width-550),80));
                pc.setPreferredSize(new Dimension(width, height - pu.getPreferredSize().height-pd.getPreferredSize().height));
                pleft.setPreferredSize(new Dimension((int) (width * 0.9), pleft.getPreferredSize().height));
                pright.setPreferredSize(new Dimension((int) (width * 0.1), pright.getPreferredSize().height));
                pd.setPreferredSize(new Dimension(width, height - pu.getPreferredSize().height-pc.getPreferredSize().height));
                fp.revalidate(); // 重新布局
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                int width = fp.getWidth();
                int height = fp.getHeight();
                p_space.setPreferredSize(new Dimension(250+(width-550),80));
                pc.setPreferredSize(new Dimension(width, height - pu.getPreferredSize().height-pd.getPreferredSize().height));
                pleft.setPreferredSize(new Dimension((int) (width * 0.9), pleft.getPreferredSize().height));
                pright.setPreferredSize(new Dimension((int) (width * 0.1), pright.getPreferredSize().height));
                pd.setPreferredSize(new Dimension(width, height - pu.getPreferredSize().height-pc.getPreferredSize().height));
                fp.revalidate(); // 重新布局
            }
        });
    }
    public static void main(String[] args)
    {
        new UserMainIF("B20220304104");
    }
}
