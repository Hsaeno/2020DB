package ui;

import control.MainControl;
import model.BeanDiscount;
import util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmDiscountManage  extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnAdd = new JButton("添加");
    private JButton btnModify = new JButton("修改");
    private JButton btnDelete = new JButton("删除");
    private JButton btnCancel = new JButton("取消");

    private Object tblDiscountTitle[]= BeanDiscount.tableTitles;
    private Object tblDiscountData[][];
    DefaultTableModel tabDiscountModel=new DefaultTableModel();
    private JTable dataTableDiscount=new JTable(tabDiscountModel);

    List<BeanDiscount> allDiscount = null;

    private void reloadTable(){
        try {
            allDiscount = MainControl.discountManager.AdminLoadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblDiscountData =  new Object[allDiscount.size()][BeanDiscount.tableTitles.length];
        for(int i=0;i<allDiscount.size();i++){
            for(int j=0;j<BeanDiscount.tableTitles.length;j++)
                tblDiscountData[i][j]=allDiscount.get(i).getCell(j);
        }
        tabDiscountModel.setDataVector(tblDiscountData,tblDiscountTitle);
        this.dataTableDiscount.validate();
        this.dataTableDiscount.repaint();
    }

    public FrmDiscountManage (Frame f, String s, boolean b)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnAdd);
        toolBar.add(btnDelete);
        toolBar.add(btnModify);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.dataTableDiscount.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                int i = FrmDiscountManage.this.dataTableDiscount.getSelectedRow();
                if (i<0){
                    return;
                }
            }
        });
        this.getContentPane().add(new JScrollPane(this.dataTableDiscount), BorderLayout.CENTER);
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
            FrmDiscountAdd dlg = new FrmDiscountAdd(this,"满折信息添加",true);
            dlg.setVisible(true);
            this.reloadTable();
        }
        else if (e.getSource() == this.btnDelete)
        {
            int i = FrmDiscountManage.this.dataTableDiscount.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择满折信息", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MainControl.discountManager.delete(allDiscount.get(i).getDis_inf_id());
                this.reloadTable();
            }
            catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if (e.getSource() == this.btnModify)
        {
            int i = FrmDiscountManage.this.dataTableDiscount.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择满折信息", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmDiscountModify dlg = new FrmDiscountModify(this,"满折信息修改",true,allDiscount.get(i));
            dlg.setVisible(true);
            this.reloadTable();
        }
    }
}
