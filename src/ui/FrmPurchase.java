package ui;

import control.MainControl;
import model.BeanAdmin;
import model.BeanGoods;
import model.BeanPurchaseTable;
import util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmPurchase extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JLabel labelGoods = new JLabel("商品：");
    private JLabel labelNumber = new JLabel("数量：");
    private JButton btnOk = new JButton("订购");
    private JButton btnCancel = new JButton("取消");

    private JTextField edtGoods = new JTextField(15);
    private JTextField edtNumber = new JTextField(15);

    private Object tblTitle[]={"名称","类别","数量"};
    private Object tblData[][];
    List<BeanPurchaseTable> purchaseTables = null;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);
    public int orderId;
    private void reloadTable()
    {
      try {
          purchaseTables = MainControl.purchaseTabManager.returnOrderGoods(BeanAdmin.currentLoginAdmin,orderId);
          tblData = new Object[purchaseTables.size()][3];
          for (int i = 0;i<purchaseTables.size();i++)
          {
              tblData[i][0] = purchaseTables.get(i).getGoods_name();
              tblData[i][1] = purchaseTables.get(i).getCatagory_id();
              tblData[i][2] = purchaseTables.get(i).getPurchase_number();
          }
          tablmod.setDataVector(tblData,tblTitle);
          this.dataTable.validate();
          this.dataTable.repaint();
      } catch (BaseException e) {
          e.printStackTrace();
      }
    }


    public FrmPurchase(FrmMain f, String s, boolean b, int id)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(labelGoods);
        toolBar.add(edtGoods);
        toolBar.add(labelNumber);
        toolBar.add(edtNumber);
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        orderId = id;
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();
        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel) {
            this.setVisible(false);
            return;
        }
        else if (e.getSource() == this.btnOk){
            String name = this.edtGoods.getText();
            String number = this.edtNumber.getText();
            try{
                MainControl.purchaseTabManager.add(BeanAdmin.currentLoginAdmin.getAdmin_id(),name,number,orderId);
                JOptionPane.showMessageDialog(null, "采购成功","成功",JOptionPane.INFORMATION_MESSAGE);
                this.reloadTable();
            }
            catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
