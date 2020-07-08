package ui;

import control.MainControl;
import model.BeanCoupon;
import model.BeanFresh;
import model.BeanGoods;
import util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmCouponManage  extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnAdd = new JButton("添加");
    private JButton btnModify = new JButton("修改");
    private JButton btnDelete = new JButton("删除");
    private JButton btnCancel = new JButton("取消");

    private Object tblCouponTitle[]= BeanCoupon.tableTitles;
    private Object tblCouponData[][];
    DefaultTableModel tabCouponModel=new DefaultTableModel();
    private JTable dataTableCoupon=new JTable(tabCouponModel);

    List<BeanCoupon> allCoupon = null;

    private void reloadTable(){
        try {
            allCoupon = MainControl.couponManager.loadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblCouponData =  new Object[allCoupon.size()][BeanCoupon.tableTitles.length];
        for(int i=0;i<allCoupon.size();i++){
            for(int j=0;j<BeanCoupon.tableTitles.length;j++)
                tblCouponData[i][j]=allCoupon.get(i).getCell(j);
        }
        tabCouponModel.setDataVector(tblCouponData,tblCouponTitle);
        this.dataTableCoupon.validate();
        this.dataTableCoupon.repaint();
    }

    public FrmCouponManage (Frame f, String s, boolean b)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnAdd);
        toolBar.add(btnDelete);
        toolBar.add(btnModify);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.dataTableCoupon.addMouseListener(new MouseAdapter (){
            public void mouseClicked(MouseEvent e)
            {
                int i = FrmCouponManage.this.dataTableCoupon.getSelectedRow();
                if (i<0){
                    return;
                }
            }
        });
        this.getContentPane().add(new JScrollPane(this.dataTableCoupon), BorderLayout.CENTER);
        this.reloadTable();
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();
        this.btnAdd.addActionListener(this);
        this.btnCancel.addActionListener(this);
        this.btnDelete.addActionListener(this);
        this.btnModify.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        else if (e.getSource() == this.btnAdd)
        {
            FrmCouponAdd dlg = new FrmCouponAdd(this,"优惠券添加",true);
            dlg.setVisible(true);
            this.reloadTable();
        }
        else if (e.getSource() == this.btnDelete)
        {
            int i = FrmCouponManage.this.dataTableCoupon.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择优惠券", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MainControl.couponManager.delete(allCoupon.get(i).getCoupon_id());
                this.reloadTable();
            }
            catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if (e.getSource() == this.btnModify)
        {
            int i = FrmCouponManage.this.dataTableCoupon.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择优惠券", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmCouponModify dlg = new FrmCouponModify(this,"优惠券添加",true,allCoupon.get(i));
            dlg.setVisible(true);
            this.reloadTable();
        }
    }
}
