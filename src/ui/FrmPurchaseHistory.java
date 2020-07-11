package ui;

import control.MainControl;
import model.*;
import util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmPurchaseHistory extends JFrame implements ActionListener {
    private JMenuBar menubar=new JMenuBar(); ;

     private JMenu menu_GoodsComment=new JMenu("商品评论");
    private JMenu menu_Order=new JMenu("订单相关");

    private JMenuItem  menuItem_CommentAdd=new JMenuItem("评论添加");
    private JMenuItem  menuItem_CommentModify=new JMenuItem("评论修改");
    private JMenuItem  menuItem_CommentShow=new JMenuItem("评论查看");
    private JMenuItem  menuItem_MakeSure=new JMenuItem("确认收货");
    private JMenuItem  menuItem_Return=new JMenuItem("申请退货");


    private Object tblOrderTitle[]= BeanOrder.tableTitles;
    private Object tblOrderData[][];
    DefaultTableModel tabOrderModel=new DefaultTableModel();
    private JTable dataTableOrder=new JTable(tabOrderModel);

    private Object tblOrderDetailTitle[]= BeanOrderDetail.tableTitles;
    private Object tblOrderDetailData[][];
    DefaultTableModel tabOrderDetailModel=new DefaultTableModel();
    private JTable dataTableOrderDetail=new JTable(tabOrderDetailModel);

    private BeanOrder curOrder = null;

    private JPanel statusBar = new JPanel();

    List<BeanOrder> allOrder = null;
    List<BeanOrderDetail> OrderDetail = null;
    private void reloadOrderTable(){
        try {
            allOrder= MainControl.purchaseHistoryManager.loadAllOrder();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblOrderData =  new Object[allOrder.size()][BeanOrder.tableTitles.length];
        for(int i=0;i<allOrder.size();i++){
            for(int j=0;j<BeanOrder.tableTitles.length;j++)
                tblOrderData[i][j]=allOrder.get(i).getCell(j);
        }
        tabOrderModel.setDataVector(tblOrderData,tblOrderTitle);
        this.dataTableOrder.validate();
        this.dataTableOrder.repaint();
    }

    private void reloadOrderDetailTable(int FreshIdx)
    {
        if (FreshIdx < 0 )
            return;
        curOrder = allOrder.get(FreshIdx);
        try{
            OrderDetail = MainControl.purchaseHistoryManager.loadAllOrderDetail(curOrder.getOrder_id());
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblOrderDetailData = new Object[OrderDetail.size()][BeanOrderDetail.tableTitles.length];
        for (int i=0;i<OrderDetail.size();i++)
            for (int j =0;j<BeanOrderDetail.tableTitles.length;j++)
            {
                tblOrderDetailData[i][j] = OrderDetail.get(i).getCell(j);
            }
        tabOrderDetailModel.setDataVector(tblOrderDetailData,tblOrderDetailTitle);
        this.dataTableOrderDetail.validate();
        this.dataTableOrderDetail.repaint();
    }
    public FrmPurchaseHistory(Frame f, String s, boolean b)
    {
        this.setTitle("消费信息");
        this.getContentPane().add(new JScrollPane(this.dataTableOrder), BorderLayout.WEST);
        this.menu_GoodsComment.add(this.menuItem_CommentAdd);
        this.menu_GoodsComment.add(this.menuItem_CommentModify);
        this.menu_GoodsComment.add(this.menuItem_CommentShow);

        this.menu_Order.add(this.menuItem_MakeSure);
        this.menu_Order.add(this.menuItem_Return);

        this.menuItem_CommentAdd.addActionListener(this);
        this.menuItem_CommentModify.addActionListener(this);
        this.menuItem_CommentShow.addActionListener(this);
        this.menuItem_MakeSure.addActionListener(this);
        this.menuItem_Return.addActionListener(this);

        menubar.add(menu_GoodsComment);
        menubar.add(menu_Order);

        this.setJMenuBar(menubar);

        this.dataTableOrder.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                int i = FrmPurchaseHistory.this.dataTableOrder.getSelectedRow();
                if (i<0){
                    return;
                }
                FrmPurchaseHistory.this.reloadOrderDetailTable(i);
            }

        });
        this.getContentPane().add(new JScrollPane(this.dataTableOrderDetail), BorderLayout.CENTER);
        this.reloadOrderTable();
        this.setExtendedState(MAXIMIZED_BOTH);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.menuItem_CommentAdd)
        {
            int i = FrmPurchaseHistory.this.dataTableOrderDetail.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmCommentAdd dlg = new FrmCommentAdd(this,"新增商品评论",true,OrderDetail.get(i).getGoods_id());
            dlg.setVisible(true);
        }
        if (e.getSource() == this.menuItem_CommentModify)
        {
            int i = FrmPurchaseHistory.this.dataTableOrderDetail.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmCommentModify dlg = null;
            try {
                dlg = new FrmCommentModify(this,"修改商品评论",true, MainControl.purchaseHistoryManager.Search(OrderDetail.get(i).getGoods_id(), BeanUsers.currentLoginUser.getUser_id()));
            } catch (BaseException ex) {
                ex.printStackTrace();
            }
            dlg.setVisible(true);
        }
        if (e.getSource() == this.menuItem_CommentShow)
        {
            int i = FrmPurchaseHistory.this.dataTableOrderDetail.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmCommentShow dlg = null;
            dlg = new FrmCommentShow(this,"修改商品评论",true,OrderDetail.get(i).getGoods_id());
            dlg.setVisible(true);
        }
        if (e.getSource() == this.menuItem_MakeSure)
        {
            int i = FrmPurchaseHistory.this.dataTableOrder.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择订单", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MainControl.purchaseHistoryManager.MakeSure(allOrder.get(i).getOrder_id());
                this.reloadOrderTable();
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        if (e.getSource() == this.menuItem_Return)
        {
            int i = FrmPurchaseHistory.this.dataTableOrder.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择订单", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MainControl.purchaseHistoryManager.Back(allOrder.get(i).getOrder_id());
                this.reloadOrderTable();
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
