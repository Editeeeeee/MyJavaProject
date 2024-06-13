package restaurant;

import DataClass.DinnerTable;
import DataClass.Orders;
import DataClass.OrdersMenu;
import DataClass.orderdishs;
import ToolClass.DbConn;
import com.mysql.cj.jdbc.ConnectionImpl;
import sun.plugin2.message.JavaScriptSlotOpMessage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.Statement;

public class OrderMenu {
    OrderMenu(int ordernumber)
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setBounds(1100,400,100,200);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("菜名");
        model.addColumn("价格");
        JPanel j = new JPanel();
        j.setPreferredSize(new Dimension(130,150));
        j.setLayout(new BorderLayout());
        JTable t = new JTable(model);
        t.setFont(new Font("楷体",Font.PLAIN,12));

        OrdersMenu menu = new OrdersMenu();
        menu.fillSet(ordernumber,new DbConn().getConn());

        int num = 0;
        for(orderdishs s: menu.getSet())
        {
            String sql = "select price from menu where dname='"+s.getDname()+"'";
            int price = 0;
            DbConn c = new DbConn();
            try{
                Statement st = c.getConn().createStatement();
                ResultSet set = st.executeQuery(sql);
                while(set.next())
                {
                    price = set.getInt("price");
                }
                num+=price;
                st.close();
            }catch(Exception e3)
            {
                e3.printStackTrace();
            }


            model.addRow(new Object[]{s.getDname(),Integer.toString(price)});
        }

        JPanel l_button = new JPanel();
        JLabel l = new JLabel("总计："+num);
        JButton givemoney = new JButton("结算");
        givemoney.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Orders order = new Orders();
                order.fillInfo(ordernumber,new DbConn().getConn());
                if(order.getOrderstatus().equals("排队中")||order.getOrderstatus().equals("已完成"))
                {
                    JOptionPane.showMessageDialog(null, "该订单状态不支持结算！");
                }
                else{
                    order.setValueyouwantByOrdernumber(ordernumber,"orderstatus","已完成",new DbConn().getConn());
                    DinnerTable.setStatus(Integer.parseInt(order.getTablenumber()),"空闲",new DbConn().getConn());
                    JOptionPane.showMessageDialog(null, "结算成功，欢迎下次光临！");
                    frame.dispose();
                }


            }
        });
        l_button.add(l);
        l_button.add(givemoney);

        j.add(new JScrollPane(t));
        frame.add(j,BorderLayout.CENTER);
        frame.add( l_button,BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args)
    {
        new OrderMenu(6);
    }
}
