package restaurant;

import DataClass.DinnerTable;
import DataClass.Orders;
import ToolClass.DbConn;
import com.mysql.cj.x.protobuf.MysqlxResultset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

public class Recept {
    private JComboBox<String> statusComboBox;
    private JComboBox<String> tableComboBox;
    private JComboBox<String> deliveryComboBox;

    private String[] str = new String[1000];
    private  int row;
    private int col;
    Recept(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JFrame f = new JFrame();
        f.setBounds(800, 300, 500, 500);
        f.setLayout(new FlowLayout());

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        f.add(p);

        JPanel p3 = new JPanel();
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel tableLabel = new JLabel("餐桌号:");
        tableComboBox = new JComboBox<>();
        topPanel.add(tableLabel);
        topPanel.add(tableComboBox);

        JLabel deliveryLabel = new JLabel("配送员:");
        deliveryComboBox = new JComboBox<>();
        topPanel.add(deliveryLabel);
        topPanel.add(deliveryComboBox);

        p.add(p3);

        p3.add(topPanel);


        JPanel p0 = new JPanel();
        p.add(p0);
        JLabel l = new JLabel("订单分类");
        l.setFont(new Font("楷体",Font.PLAIN,15));
        p0.add(l);
        JRadioButton button1 = new JRadioButton("未处理");
        button1.setFont(new Font("楷体",Font.PLAIN,15));
        button1.setFocusPainted(false);
        JRadioButton button2 = new JRadioButton("已处理");
        button2.setFont(new Font("楷体",Font.PLAIN,15));
        button2.setFocusPainted(false);
        JRadioButton button3 = new JRadioButton("已完成");
        button3.setFont(new Font("楷体",Font.PLAIN,15));
        button3.setFocusPainted(false);
        JRadioButton button4 = new JRadioButton("全部订单");
        button4.setSelected(true);
        button4.setFont(new Font("楷体",Font.PLAIN,15));
        button4.setFocusPainted(false);
        ButtonGroup group = new ButtonGroup();
        group.add(button1);
        group.add(button2);
        group.add(button3);
        group.add(button4);
        p0.add(button1);
        p0.add(button2);
        p0.add(button3);
        p0.add(button4);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("订单号");
        model.addColumn("客户电话");
        model.addColumn("就餐方式");
        model.addColumn("送餐地址");
        model.addColumn("就餐桌号");
        model.addColumn("订单状态");
        model.addColumn("配送员");
        JPanel p_table = new JPanel();
        p_table.setLayout(new BorderLayout());
        p_table.setPreferredSize(new Dimension(500,150));
        JTable t = new JTable(model) {
            // 禁止表格编辑
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        t.setFont(new Font("楷体",Font.PLAIN,12));
        p_table.add(new JScrollPane(t));
        p.add(p_table);

        DefaultTableModel model1 = new DefaultTableModel();
        model1.addColumn(" 编号");
        model1.addColumn("菜名");
        model1.addColumn("价格");
        model1.addColumn("类别");

        JPanel p1_table = new JPanel();
        p1_table.setLayout(new BorderLayout());
        p1_table.setPreferredSize(new Dimension(500,150));
        JTable t1 = new JTable(model1)
        {
            // 禁止表格编辑
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        t1.setFont(new Font("楷体",Font.PLAIN,12));
        p1_table.add(new JScrollPane(t1));
        p.add(p1_table);

        try {
            DbConn cn = new DbConn();
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        try {
            model1.setRowCount(0);
            DbConn cn = new DbConn();
            Statement stmt = cn.getConn().createStatement();
            String query = "SELECT * FROM menu";

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                // Retrieve data from the result set and add it to the table model
                model1.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("dname"),
                        rs.getString("price"),
                        rs.getString("kind"),
                });
            }
            //x.addItem("");
            // Load delivery staff
            // Assuming there's a table named "delivery_staff" with a column named "name"

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        try {
            model.setRowCount(0);
            DbConn cn = new DbConn();
            Statement stmt = cn.getConn().createStatement();
            String query = "SELECT * FROM orders";

            if (button1.isSelected()) {
                query += " WHERE orderstatus = '排队中'";
            } else if (button2.isSelected()) {
                query += " WHERE orderstatus = '已处理'";
            } else if (button3.isSelected()) {
                query += " WHERE orderstatus = '已完成'";
            }

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                // Retrieve data from the result set and add it to the table model
                model.addRow(new Object[]{
                        rs.getInt("ordernumber"),
                        rs.getString("telnumber"),
                        rs.getString("way"),
                        rs.getString("address"),
                        rs.getString("tablenumber"),
                        rs.getString("orderstatus"),
                        rs.getString("courier")
                });
            }
            query = "SELECT DISTINCT tablenum,status FROM dinnertable";
            rs = stmt.executeQuery(query);

            tableComboBox.removeAllItems();
            while (rs.next()) {
                tableComboBox.addItem(rs.getString("tablenum") + " " + rs.getString("status"));
            }

            // Load delivery staff
            // Assuming there's a table named "delivery_staff" with a column named "name"
            query = "SELECT name FROM takeman";
            rs = stmt.executeQuery(query);
            deliveryComboBox.removeAllItems();
            while (rs.next()) {
                deliveryComboBox.addItem(rs.getString("name"));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        JPanel sn_addTable = new JPanel();
        JSpinner sn = new JSpinner(new SpinnerNumberModel(0,0,tableComboBox.getItemCount(),1));

        sn.setFont(new Font("楷体",Font.PLAIN,13));
        JButton addTable = new JButton("新增餐台");
        addTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int value = (int) sn.getValue();
                for(int i = 0;i<value;i++)
                {
                    DinnerTable.InsertTable(new DbConn().getConn());
                }
                JOptionPane.showMessageDialog(null, "成功添加"+value+"个餐桌！");
                try{
                    DbConn cn = new DbConn();
                    Statement stmt = cn.getConn().createStatement();
                    String query = "SELECT * FROM orders";
                    ResultSet rs;
                    query = "SELECT DISTINCT tablenum,status FROM dinnertable";
                    rs = stmt.executeQuery(query);
                    tableComboBox.removeAllItems();
                    while (rs.next()) {
                        tableComboBox.addItem(rs.getString("tablenum") + " " + rs.getString("status"));
                    }
                    query = "SELECT name FROM takeman";
                    rs = stmt.executeQuery(query);
                    deliveryComboBox.removeAllItems();
                    while (rs.next()) {
                        deliveryComboBox.addItem(rs.getString("name"));
                    }
                }catch(Exception e2)
                {

                }
                sn.setModel(new SpinnerNumberModel(0,0,tableComboBox.getItemCount(),1));
            }
        });
        addTable.setFont(new Font("楷体",Font.PLAIN,13));
        JButton deleteTable = new JButton("删除餐台");
        deleteTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int value = (int) sn.getValue();

                DinnerTable.DeleteTable(new DbConn().getConn(),value);

                JOptionPane.showMessageDialog(null, "成功删除"+value+"号餐桌！");
                try{
                    DbConn cn = new DbConn();
                    Statement stmt = cn.getConn().createStatement();
                    String query = "SELECT * FROM orders";
                    ResultSet rs;
                    query = "SELECT DISTINCT tablenum,status FROM dinnertable";
                    rs = stmt.executeQuery(query);
                    tableComboBox.removeAllItems();
                    while (rs.next()) {
                        tableComboBox.addItem(rs.getString("tablenum") + " " + rs.getString("status"));
                    }
                    query = "SELECT name FROM takeman";
                    rs = stmt.executeQuery(query);
                    deliveryComboBox.removeAllItems();
                    while (rs.next()) {
                        deliveryComboBox.addItem(rs.getString("name"));
                    }
                }catch(Exception e2)
                {

                }
                sn.setModel(new SpinnerNumberModel(0,0,tableComboBox.getItemCount(),1));
            }
        });
        deleteTable.setFont(new Font("楷体",Font.PLAIN,13));
        sn_addTable.add(sn);
        sn_addTable.add(addTable);
        sn_addTable.add(deleteTable);
        p.add(sn_addTable);
        javax.swing.Timer timer = new javax.swing.Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.setRowCount(0);
                    DbConn cn = new DbConn();
                    Statement stmt = cn.getConn().createStatement();
                    String query = "SELECT * FROM orders";

                    if (button1.isSelected()) {
                        query += " WHERE orderstatus = '排队中'";
                    } else if (button2.isSelected()) {
                        query += " WHERE orderstatus = '已处理'";
                    } else if (button3.isSelected()) {
                        query += " WHERE orderstatus = '已完成'";
                    }

                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        // Retrieve data from the result set and add it to the table model
                        model.addRow(new Object[]{
                                rs.getInt("ordernumber"),
                                rs.getString("telnumber"),
                                rs.getString("way"),
                                rs.getString("address"),
                                rs.getString("tablenumber"),
                                rs.getString("orderstatus"),
                                rs.getString("courier")
                        });
                    }
                    //x.addItem("");
                        // Load delivery staff
                        // Assuming there's a table named "delivery_staff" with a column named "name"

                } catch (Exception e1) {
                    e1.printStackTrace();
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
                        JDialog log =new JDialog(f,"选择操作",true);
                        log.setBounds(800,300,300,150);
                        log.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
                        log.setLayout(new GridLayout(2,1));
                        if(model.getValueAt(row,5).equals("排队中"))
                        {
                            JButton bt1 = new JButton("分配餐桌或送餐员给该订单");
                            bt1.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    int ordernumber = Integer.parseInt(model.getValueAt(row,0).toString());
                                    Orders order = new Orders();
                                    order.fillInfo(ordernumber,new DbConn().getConn());
                                    if(order.getWay().equals("堂食"))
                                    {
                                        String[] parts = tableComboBox.getSelectedItem().toString().split(" ");
                                        if(parts[1].equals("空闲"))
                                        {
                                            Orders.setValueyouwantByOrdernumber(ordernumber,"tablenumber",parts[0],new DbConn().getConn());
                                            DinnerTable.setStatus(Integer.parseInt(parts[0]),"占用",new DbConn().getConn());
                                            Orders.setValueyouwantByOrdernumber(ordernumber,"orderstatus","已处理",new DbConn().getConn());
                                            try{
                                                DbConn cn = new DbConn();
                                                Statement stmt = cn.getConn().createStatement();
                                                String query = "SELECT * FROM orders";
                                                ResultSet rs;
                                                query = "SELECT DISTINCT tablenum,status FROM dinnertable";
                                                rs = stmt.executeQuery(query);
                                                tableComboBox.removeAllItems();
                                                while (rs.next()) {
                                                    tableComboBox.addItem(rs.getString("tablenum") + " " + rs.getString("status"));
                                                }
                                                query = "SELECT name FROM takeman";
                                                rs = stmt.executeQuery(query);
                                                deliveryComboBox.removeAllItems();
                                                while (rs.next()) {
                                                    deliveryComboBox.addItem(rs.getString("name"));
                                                }
                                            }catch(Exception e2)
                                            {

                                            }

                                        }else{
                                            JOptionPane.showMessageDialog(null, "请选择空闲餐桌！");
                                        }

                                    }else if(order.getWay().equals("外卖"))
                                    {
                                        Orders.setValueyouwantByOrdernumber(ordernumber,"courier",deliveryComboBox.getSelectedItem().toString(),new DbConn().getConn());
                                        Orders.setValueyouwantByOrdernumber(ordernumber,"orderstatus","已处理",new DbConn().getConn());
                                    }
                                    log.dispose();
                                }

                            });
                            log.add(bt1);
                        }

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
                                Orders.deleteByOrderNumber(ordernumber,new DbConn().getConn());
                                log.dispose();
                            }
                        });
                        log.add(bt2);
                        log.setVisible(true);
                    }
                }
            }
        });


        f.pack();
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
    }
    public static void main(String[] args)
    {
        new Recept();
    }
}
