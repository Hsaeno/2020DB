package ui;

import control.MainControl;
import model.BeanCoupon;
import util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmCouponShow extends JDialog implements ActionListener {

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

    public FrmCouponShow (Frame f, String s, boolean b)
    {
        super(f,s,b);
        this.dataTableCoupon.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                int i = FrmCouponShow.this.dataTableCoupon.getSelectedRow();
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
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
