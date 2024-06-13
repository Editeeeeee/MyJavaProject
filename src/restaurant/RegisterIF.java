package restaurant;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import javax.sql.rowset.CachedRowSet;
import javax.swing.*;

import DataClass.UserInfo;
import ToolClass.*;

public class RegisterIF {
    RegisterIF()
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JFrame fp = new JFrame("登录界面");
        fp.setBounds(500,300,550,400);
        fp.setLayout(new BorderLayout());
        ImagePanel p = new ImagePanel(".\\image\\背景.png");//背景图片
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        fp.add(p,BorderLayout.CENTER);
        //获取本地目录
        String s = System.getProperty("user.dir");
        System.out.println(fp);


        JPanel p4 = new JPanel();
        p4.setBackground(new Color(0, 168, 162,0));
        JLabel l3 = new JLabel("欢迎来到大众点餐");
        l3.setFont(new Font("楷体",Font.BOLD,40));
        l3.setForeground(new Color(124, 220, 250, 100));

        p4.add(l3);
        p.add(p4);


        JPanel p1 = new JPanel();
        p1.setBackground(new Color(1,1,1,0));
        JLabel l1 = new JLabel("账号：");
        l1.setForeground(Color.BLACK);
        l1.setFont(new Font("楷体",Font.BOLD,25));
        p1.add(l1);
        JTextField id = new JTextField(15);
        Dimension dim = new Dimension(200, 30); // 200是宽度，20是高度
        id.setPreferredSize(dim);
        p1.add(id);
        p.add(p1);


        JPanel p2 = new JPanel();
        p2.setBackground(new Color(1,1,1,0));
        JLabel l2 = new JLabel("密码：");
        l2.setForeground(Color.BLACK);
        l2.setFont(new Font("楷体",Font.BOLD,25));
        p2.add(l2);
        JPasswordField password = new JPasswordField(15);
        Dimension dim1 = new Dimension(200, 30); // 200是宽度，20是高度
        password.setPreferredSize(dim1);
        p2.add(password);
        p.add(p2);

        JPanel p3 = new JPanel();
        p3.setBackground(new Color(1,1,1,0));
        p.add(p3);
        //注册
        JButton zuce = new JButton("注册");
        zuce.setBackground(Color.CYAN);
        zuce.setRolloverEnabled(false);
        // zuce.setBackground(new Color(0, 178, 255));
        zuce.setFont(new Font("楷体",Font.PLAIN,20));
        zuce.setFocusPainted(false);
        zuce.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                new RegisterInfoIF();
            }

        });
        p3.add(zuce);

        JLabel space = new JLabel("              ");
        p3.add(space);

        //登录
        JButton denglu = new JButton("登录");
        denglu.setBackground(Color.CYAN);
        denglu.setRolloverEnabled(false);
        // denglu.setBackground(new Color(0, 178, 255));
        denglu.setFont(new Font("楷体",Font.PLAIN,20));
        denglu.setFocusPainted(false);
        denglu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                String ID = new String(id.getText());
                String ps = new String(password.getPassword());
                if(ID.length()<6||ID.length()>15||ps.length()<6||ps.length()>15)
                {
                    JDialog log =new JDialog(fp,"请输入正确账号密码",true);
                    ImagePanel i = new ImagePanel(".\\image\\图标.png");
                    log.add(i);
                    log.setBounds(800,300,200,200);
                    log.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
                    log.setVisible(true);
                }
                else{
                    DbConn c = new DbConn();
                    CachedRowSet set =  UserInfo.queryall(c.getConn());
                    try{
                        while(set.next())
                        {
                            if(ID.equals(set.getString("account"))&&ps.equals(set.getString("password")))
                            {
                                if(UserInfo.query(c.getConn(), ID,"identity").equals("用户"))
                                {
                                    fp.dispose();//关闭窗口
                                    new UserMainIF(ID);
                                }
                                else if(UserInfo.query(c.getConn(), ID,"identity").equals("前台"))
                                {
                                    fp.dispose();
                                    JDialog log =new JDialog(fp,"前台登录成功",true);
                                    ImagePanel i = new ImagePanel(".\\image\\图标.png");
                                    log.add(i);
                                    log.setBounds(800,300,200,200);
                                    log.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
                                    log.setVisible(true);
                                    new Recept();
                                }
                                else if(UserInfo.query(c.getConn(), ID,"identity").equals("后台"))
                                {
                                    JDialog log =new JDialog(fp,"后台登录成功",true);
                                    ImagePanel i = new ImagePanel(".\\image\\图标.png");
                                    log.add(i);
                                    log.setBounds(800,300,200,200);
                                    log.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
                                    log.setVisible(true);
                                }
                                break;
                            }
                        }
                    }catch(Exception e)
                    {
                        e.printStackTrace();
                        System.out.println("Register.java column 126");
                    }
                }
            }

        });
        p3.add(denglu);
        fp.setVisible(true);
        ImageIcon icon = new ImageIcon(".\\image\\图标.png");
        Image image = icon.getImage();
        fp.setIconImage(image);

        fp.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });//检测窗口是否关闭，如果是，则结束运行
    }
    public static void main(String[] args)
    {
        RegisterIF f = new RegisterIF();
    }
}
