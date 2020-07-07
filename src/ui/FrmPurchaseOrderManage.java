package ui;

import control.MainControl;
import model.BeanAdmin;
import model.BeanPurchaseTable;
import util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static java.awt.Frame.MAXIMIZED_BOTH;

public class FrmPurchaseOrderManage extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("更新");
    private JButton btnCancel = new JButton("取消");



    private String[] status =  {"下单","在途","入库"};
    private JComboBox edtStatus = new JComboBox(status);

    private Object tblTitle[]={"订单号","状态"};
    private Object tblData[][];
    List<BeanPurchaseTable> purchaseTables = null;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);

    private BeanPurchaseTable curTable = null;

    private void reloadTable()
    {
        try {
            purchaseTables = MainControl.purchaseTabManager.loadSimpleTable(BeanAdmin.currentLoginAdmin);}
        catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
            tblData = new Object[purchaseTables.size()][2];
            for (int i = 0;i<purchaseTables.size();i++)
            {
                tblData[i][0] = purchaseTables.get(i).getPurchase_orderId();
                tblData[i][1] = purchaseTables.get(i).getPurchase_status();
            }
            tablmod.setDataVector(tblData,tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
    }

    public FrmPurchaseOrderManage(FrmMain f, String s, boolean b)
    {
        super(f,s,b);
        workPane.add(edtStatus);
        workPane.add(btnOk);
        workPane.add(btnCancel);
        this.getContentPane().add(workPane, BorderLayout.NORTH);
        this.dataTable.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                int i = FrmPurchaseOrderManage.this.dataTable.getSelectedRow();
                if (i<0){
                    return;
                }
            }

        });
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
        this.reloadTable();
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        else if(e.getSource()==this.btnOk) {
            int i = FrmPurchaseOrderManage.this.dataTable.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择订单", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MainControl.purchaseTabManager.updateStatus(purchaseTables.get(i).getPurchase_orderId(),this.edtStatus.getSelectedItem().toString());
                this.reloadTable();
            }
            catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        }
}
