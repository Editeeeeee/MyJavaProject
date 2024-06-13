package restaurant;

import DataClass.OrdersMenu;
import DataClass.UserInfo;
import DataClass.orderdishs;
import ToolClass.AddButtonCirclePanel;
import ToolClass.DbConn;
import ToolClass.ImagePanel;
import ToolClass.RoundBorder;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ShoppingTrolley {
    ShoppingTrolley(OrdersMenu ordersmenu,String account) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JFrame frame = new JFrame("Shopping Trolley");
        frame.setBounds(500,300,450, 300);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        frame.add(mainPanel, BorderLayout.CENTER);

        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(itemPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        for (orderdishs item : ordersmenu.getSet()) {
            JPanel itemRow = new JPanel(new BorderLayout());
            itemRow.setPreferredSize(new Dimension(400, 40));
            itemRow.setMaximumSize(new Dimension(400, 40));
            itemRow.setMinimumSize(new Dimension(400, 40));
            itemRow.setBackground(Color.pink);
            itemRow.setBorder(new EmptyBorder(5, 5, 5, 5));
            JLabel nameLabel = new JLabel(item.getDname());
            nameLabel.setFont(new Font("楷体", Font.BOLD, 16));
            JLabel priceLabel = new JLabel("\uFFE5" + item.getPrice());
            priceLabel.setFont(new Font("楷体", Font.PLAIN, 14));
            itemRow.add(nameLabel, BorderLayout.CENTER);
            itemRow.add(priceLabel, BorderLayout.WEST);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(Color.pink);
            try {
                Image image = ImageIO.read(new File("./image/减号.png"));
                AddButtonCirclePanel minusButton = new AddButtonCirclePanel(image);
                minusButton.setBackground(Color.pink);
                minusButton.setPreferredSize(new Dimension(20, 20));
                minusButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        ordersmenu.delete(item.getDname());
                        itemPanel.remove(itemRow);
                        itemPanel.revalidate();
                        itemPanel.repaint();
                    }
                });
                buttonPanel.add(minusButton);
            } catch (IOException e) {
                e.printStackTrace();
            }

            itemRow.add(buttonPanel, BorderLayout.EAST);
            itemPanel.add(itemRow);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        ButtonGroup group = new ButtonGroup();
        JRadioButton bt1 = new JRadioButton("外卖");
        bt1.setFont(new Font("楷体",Font.PLAIN,15));
        bt1.setBackground(Color.white);
        bt1.setRolloverEnabled(false);
        bt1.setFocusPainted(false);
        JRadioButton bt2 = new JRadioButton("堂食");
        bt2.setBackground(Color.white);
        bt2.setFont(new Font("楷体",Font.PLAIN,15));
        bt2.setRolloverEnabled(false);
        bt2.setSelected(true);
        bt2.setFocusPainted(false);
        group.add(bt1);
        group.add(bt2);
        buttonPanel.add(bt1);
        buttonPanel.add(bt2);


        JButton saveButton = new JButton("提交订单");
        saveButton.setFont(new Font("楷体", Font.PLAIN, 14));
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> {
            // Add saving logic here
            try{
                String telnumber;
                String way = null;
                String address;
                int ordernumber = 0;
                DbConn cn = new DbConn();

                telnumber = UserInfo.query(cn.getConn(),account,"tnumber");
                address = UserInfo.query(cn.getConn(),account,"address");
                if(bt1.isSelected())way="外卖";
                if(bt2.isSelected())way="堂食";

                String sql = "Insert into orders(telnumber,way,address,account) values(?,?,?,?)";
                PreparedStatement stmt = cn.getConn().prepareStatement(sql);
                stmt.setString(1,telnumber);
                stmt.setString(2,way);
                stmt.setString(3,address);
                stmt.setString(4,account);
                stmt.execute();
                stmt.close();
                sql = "SELECT ordernumber FROM orders ORDER BY ordernumber DESC LIMIT 1;";
                Statement st = cn.getConn().createStatement();
                ResultSet s = st.executeQuery(sql);
                while(s.next())
                {
                    ordernumber = s.getInt("ordernumber");
                }
                st.close();
                sql = "insert into ordermenu values(?,?)";
                for(orderdishs str:ordersmenu.getSet())
                {
                    PreparedStatement pt = cn.getConn().prepareStatement(sql);
                    pt.setInt(1,ordernumber);
                    pt.setString(2,str.getDname());
                    pt.execute();
                    pt.close();
                }
            }catch(Exception e1)
            {
                e1.printStackTrace();
            }
            ordersmenu.deleteall();
            JDialog log =new JDialog(frame,"提交订单成功",true);
            ImagePanel i = new ImagePanel(".\\image\\图标.png");
            log.add(i);
            log.setBounds(800,300,200,200);
            log.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
            log.setVisible(true);
            frame.dispose();
        });
        buttonPanel.add(saveButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ShoppingTrolley(new OrdersMenu(),"0");
    }
}
