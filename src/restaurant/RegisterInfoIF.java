package restaurant;
import DataClass.UserInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import ToolClass.*;


public class RegisterInfoIF {

    Font font = new Font("楷体",Font.BOLD,19);
    RoundBorder border = new RoundBorder(5);
    Dimension dimension = new Dimension(15, 23);
    Color Textfieldcolor = new Color(1,1,1,0);
    RegisterInfoIF(){

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JFrame fp = new JFrame("注册界面");
        fp.setBounds(800,300,400,400);
        fp.setLayout(new FlowLayout());

        JPanel p = new JPanel();
        p.setBackground(new Color(0xFFFFFFFF, true));
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        p.setBorder(border);
        fp.add(p);
        SelectCirclePanel circlePanel = null;
        JPanel sp = new JPanel();
        try{
            Image pa = ImageIO.read(new File(".\\image\\默认头像.png"));
            circlePanel = new SelectCirclePanel(pa);
            circlePanel.setPreferredSize(new Dimension(60, 60));
            circlePanel.setMaximumSize(new Dimension(60, 60));
            circlePanel.setMinimumSize(new Dimension(60,60));
            circlePanel.setBackground(new Color(0,200,0,0));
            sp.add(circlePanel);
            sp.setBackground(new Color(0,0,0,0));
            p.add(sp);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        JPanel sp1 = new JPanel();
        sp1.setBackground(Textfieldcolor);
        JLabel t1 = new JLabel("账号:");
        t1.setFont(font);
        JTextField account = new JTextField(15);
        account.setPreferredSize(dimension);
        account.setBorder(border);
        sp1.add(t1);
        sp1.add(account);
        p.add(sp1);

        JPanel sp2 = new JPanel();
        sp2.setBackground(Textfieldcolor);
        JLabel t2 = new JLabel("密码:");
        t2.setFont(font);
        JPasswordField password = new JPasswordField(15);
        password.setPreferredSize(dimension);
        password.setBorder(border);
        sp2.add(t2);
        sp2.add(password);
        p.add(sp2);

        JPanel sp3 = new JPanel();
        sp3.setBackground(Textfieldcolor);
        JLabel t3 = new JLabel("名称:");
        t3.setFont(font);
        JTextField username = new JTextField(15);
        username.setPreferredSize(dimension);
        username.setBorder(border);
        sp3.add(t3);
        sp3.add(username);
        p.add(sp3);

        JPanel sp7 = new JPanel();
        JLabel t6 = new JLabel("性别: ");
        t6.setFont(font);
        sp7.setBackground(Textfieldcolor);
        sp7.setPreferredSize(new Dimension(15,30));
        JRadioButton mbutton = new JRadioButton("男");
        mbutton.setFont(new Font("楷体",Font.PLAIN,15));
        mbutton.setBackground(Color.white);
        mbutton.setRolloverEnabled(false);
        mbutton.setFocusPainted(false);
        JRadioButton fbutton = new JRadioButton("女");
        fbutton.setFont(new Font("楷体",Font.PLAIN,15));
        fbutton.setBackground(Color.white);
        fbutton.setRolloverEnabled(false);
        fbutton.setFocusPainted(false);
        ButtonGroup g = new ButtonGroup();
        g.add(mbutton);
        g.add(fbutton);
        sp7.add(t6);
        sp7.add(mbutton);
        sp7.add(fbutton);
        p.add(sp7);

        JPanel sp4 = new JPanel();
        sp4.setBackground(Textfieldcolor);
        JLabel t4 = new JLabel("电话:");
        t4.setFont(font);
        JTextField tnumber = new JTextField(15);
        tnumber.setPreferredSize(dimension);
        tnumber.setBorder(border);
        sp4.add(t4);
        sp4.add(tnumber);
        p.add(sp4);

        JPanel sp5 = new JPanel();
        sp5.setBackground(Textfieldcolor);
        JLabel t5 = new JLabel("地址:");
        t5.setFont(font);
        JTextField address = new JTextField(15);
        address.setPreferredSize(dimension);
        address.setBorder(border);
        sp5.add(t5);
        sp5.add(address);
        p.add(sp5);

        JPanel sp8 = new JPanel();
        sp8.setBackground(new Color(1,1,1,0));
        p.add(sp8);
        ButtonGroup group = new ButtonGroup();
        JRadioButton bt1 = new JRadioButton("后台管理员");
        bt1.setFont(new Font("楷体",Font.PLAIN,15));
        bt1.setBackground(Color.white);
        bt1.setRolloverEnabled(false);
        bt1.setFocusPainted(false);
        JRadioButton bt2 = new JRadioButton("餐台管理员");
        bt2.setBackground(Color.white);
        bt2.setFont(new Font("楷体",Font.PLAIN,15));
        bt2.setRolloverEnabled(false);
        bt2.setSelected(true);
        bt2.setFocusPainted(false);
        JRadioButton bt3 = new JRadioButton("普通用户");
        bt3.setBackground(Color.white);
        bt3.setFont(new Font("楷体",Font.PLAIN,15));
        bt3.setRolloverEnabled(false);
        bt3.setSelected(true);
        bt3.setFocusPainted(false);
        group.add(bt3);
        group.add(bt1);
        group.add(bt2);
        sp8.add(bt1);
        sp8.add(bt2);
        sp8.add(bt3);

        JPanel sp9 = new JPanel();
        JButton submit = new JButton("提交");
        submit.setFont(font);
        submit.setContentAreaFilled(false);
        submit.setBorder(border);
        SelectCirclePanel finalCirclePanel = circlePanel;
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 String saccount = account.getText();//不该低于6位
                 String spassword = password.getText();//不低于6
                 String sphoto = finalCirclePanel.getPhoto();
                 String susername= username.getText();//不为数字
                 String stnumber= tnumber.getText();//11位
                 String ssex = mbutton.isSelected()?"男":"女";//默认为女
                 String saddress= address.getText();//不为空
                 String sidentity ="用户" ;
                 if(bt1.isSelected())
                 {
                     sidentity =  "后台";
                 }
                 else if(bt2.isSelected())
                 {
                     sidentity = "前台";
                 }
                 else if(bt3.isSelected())
                 {
                     sidentity = "用户";
                 }
                 if(saccount.length()>=6&&saccount.length()<=15&&spassword.length()>=6&&spassword.length()<=15&&stnumber.length()==11&&!saddress.isEmpty()) {
                     DbConn con = new DbConn();
                     Connection conn = con.getConn();
                     UserInfo user = new UserInfo(saccount,new DbConn());
                     user.insert(conn);
                     JDialog log =new JDialog(fp,"注册成功",true);
                     log.setBounds(800,300,300,150);
                     log.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

                     log.setVisible(true);
                 }
                 else{
                     JDialog log =new JDialog(fp,"请输入正确格式信息",true);
                     JLabel jlog1 = new JLabel("账号密码应不少于6位数字且不大于15位数字");
                     JLabel jlog2 = new JLabel("地址不为空");
                     JLabel jlog3 = new JLabel("正确格式电话号码应该为11位数字");
                     log.setBounds(800,300,300,150);
                     log.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
                     if(saccount.length()<6||saccount.length()>15||spassword.length()<6||spassword.length()>15)
                     {
                         log.add(jlog1);
                     }
                     else if(saddress.isEmpty()){
                         log.add(jlog2);
                     }
                     else if(stnumber.length()!=11){
                         log.add(jlog3);
                     }
                     log.setVisible(true);
                 }
            }
        });

        sp9.add(submit);
        p.add(sp9);

        fp.pack();
        ImageIcon icon = new ImageIcon(".\\image\\图标.png");
        Image image = icon.getImage();
        fp.setIconImage(image);
        fp.setResizable(false);
        fp.setVisible(true);
    }
    public static void main(String[] args)
    {
        new RegisterInfoIF();
    }
}
