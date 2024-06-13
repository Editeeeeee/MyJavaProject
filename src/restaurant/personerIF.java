package restaurant;
import DataClass.DinnerTable;
import DataClass.Orders;
import DataClass.UserInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import ToolClass.*;


public class personerIF {

    UserInfo userinfo ;
    Font font = new Font("楷体",Font.BOLD,19);
    RoundBorder border = new RoundBorder(5);
    Dimension dimension = new Dimension(15, 23);
    Color Textfieldcolor = new Color(1,1,1,0);
    private int row,col;
    personerIF(String personaccount,JFrame frame){

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        DbConn cn  = new DbConn();
        userinfo = new UserInfo();
        userinfo.fillSet(personaccount,cn.getConn());



        JFrame fp = new JFrame("个人中心");
        fp.setBounds(800,300,400,400);
        fp.setLayout(new FlowLayout());

        ImagePanel p = new ImagePanel("./image/主背景.png");
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        p.setBorder(border);
        fp.add(p);
        SelectCirclePanel circlePanel = null;
        JPanel sp = new JPanel();
        sp.setBackground(new Color(0,0,0,0));
        try{
            Image pa = ImageIO.read(new File(userinfo.getPhoto()));
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
        sp1.setBackground(new Color(0,0,0,0));
        JLabel lname = new JLabel("姓名："+userinfo.getUsername());
        sp1.add(lname);
        p.add(sp1);

        JPanel sp2 = new JPanel();
        sp2.setBackground(new Color(0,0,0,0));
        JLabel lsex = new JLabel("性别："+userinfo.getSex());
        sp2.add(lsex);
        p.add(sp2);

        JPanel sp3 = new JPanel();
        sp3.setBackground(new Color(0,0,0,0));
        JLabel ltnumber = new JLabel("电话号码："+userinfo.getTnumber());
        sp3.add(ltnumber);
        p.add(sp3);

        JPanel sp4 = new JPanel();
        sp4.setBackground(new Color(0,0,0,0));
        JLabel laddress = new JLabel("地址："+userinfo.getAddress());
        sp4.add(laddress);
        p.add(sp4);

        JPanel sp5 = new JPanel();
        sp5.setBackground(new Color(0,0,0,0));
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("订单号");
        model.addColumn("我的电话");
        model.addColumn("就餐方式");
        model.addColumn("送餐地址");
        model.addColumn("就餐桌号");
        model.addColumn("订单状态");
        JTable t = new JTable(model) {
            // 禁止表格编辑
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        t.setFont(new Font("楷体",Font.PLAIN,13));
        sp5.add(new JScrollPane(t));
        p.add(sp5);

        JPanel sp6 = new JPanel();
        sp6.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.black));
        sp6.setPreferredSize(new Dimension(100,30));
        sp6.setMinimumSize(new Dimension(100,30));
        sp6.setMaximumSize(new Dimension(100,30));
        sp6.setBackground(Color.CYAN);
        JLabel updateinfo = new JLabel("修改个人信息");
        updateinfo.setFont(new Font("楷体",Font.PLAIN,15));
        sp6.add(updateinfo);
        sp6.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                updateinfo.setFont(new Font("楷体",Font.BOLD,15));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                updateinfo.setFont(new Font("楷体",Font.PLAIN,15));
                new UpdateUserinfo(userinfo,frame,fp);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                sp6.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sp6.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.black));
            }
        });
        p.add(sp6);

        JPanel sp7 = new JPanel();
        sp7.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.black));
        sp7.setPreferredSize(new Dimension(100,30));
        sp7.setMinimumSize(new Dimension(100,30));
        sp7.setMaximumSize(new Dimension(100,30));
        sp7.setBackground(Color.CYAN);
        JLabel exit = new JLabel("退出登录");
        exit.setFont(new Font("楷体",Font.PLAIN,15));
        sp7.add(exit);
        sp7.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                exit.setFont(new Font("楷体",Font.BOLD,15));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                exit.setFont(new Font("楷体",Font.PLAIN,15));
                new RegisterIF();
                frame.dispose();
                fp.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                sp7.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,Color.black));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sp7.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.black));
            }
        });
        p.add(sp7);

        javax.swing.Timer timer = new javax.swing.Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //为表格添加内容
                try{
                    model.setRowCount(0);
                    Statement stmt = cn.getConn().createStatement();
                    String query = "SELECT * FROM orders where account="+"'"+personaccount+"'";

                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        // Retrieve data from the result set and add it to the table model
                        model.addRow(new Object[]{
                                rs.getInt("ordernumber"),
                                rs.getString("telnumber"),
                                rs.getString("way"),
                                rs.getString("address"),
                                rs.getString("tablenumber"),
                                rs.getString("orderstatus")
                        });
                    }

                }catch(Exception e2){
                    e2.printStackTrace();
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();
        t.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    row = t.rowAtPoint(e.getPoint());
                    col = t.columnAtPoint(e.getPoint());
                    if (row >= 0 && col >= 0) {
                        // Perform action based on the cell clicked
                        JDialog log =new JDialog(fp,"选择操作",true);
                        log.setBounds(800,300,300,150);
                        log.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
                        log.setLayout(new GridLayout(2,1));

                        JButton bt3 = new JButton("订单详情");
                        bt3.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                int ordernumber = Integer.parseInt(model.getValueAt(row,0).toString());
                                log.dispose();
                                new OrderMenu(ordernumber);
                            }
                        });
                        log.add(bt3);
                        JButton bt2 = new JButton("删除订单");
                        bt2.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                int ordernumber = Integer.parseInt(model.getValueAt(row,0).toString());
                                Orders order =new Orders();
                                order.fillInfo(ordernumber,new DbConn().getConn());
                                if(order.getOrderstatus().equals("已完成")||order.getOrderstatus().equals("已处理"))
                                {
                                    JOptionPane.showMessageDialog(null, "此订单已无法撤回！");
                                }
                                else{
                                    Orders.deleteByOrderNumber(ordernumber,new DbConn().getConn());
                                    JOptionPane.showMessageDialog(null, "删除成功！");
                                }

                                log.dispose();
                            }
                        });
                        log.add(bt2);
                        log.setVisible(true);
                    }
                }
            }
        });


        fp.pack();
        ImageIcon icon = new ImageIcon(".\\image\\图标.png");
        Image image = icon.getImage();
        fp.setIconImage(image);
        fp.setVisible(true);
    }
    public static void main(String[] args)
    {
        new personerIF("B20220304104",new JFrame());
    }
}
