package ui;

import control.MainControl;
import model.BeanDisConnGoods;
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

public class FrmDiscountGoodsManage extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnAdd = new JButton("添加");
    private JButton btnModify = new JButton("修改");
    private JButton btnDelete = new JButton("删除");
    private JButton btnCancel = new JButton("取消");

    private Object tblDiscountGoodsTitle[]= BeanDisConnGoods.tableTitles;
    private Object tblDiscountGoodsData[][];
    DefaultTableModel tabDiscountGoodsModel=new DefaultTableModel();
    private JTable dataTableDiscountGoods=new JTable(tabDiscountGoodsModel);

    List<BeanDisConnGoods> allDiscountGoods = null;

    private void reloadTable(){
        try {
            allDiscountGoods = MainControl.disConnGoodsManager.AdminLoadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblDiscountGoodsData =  new Object[allDiscountGoods.size()][BeanDisConnGoods.tableTitles.length];
        for(int i=0;i<allDiscountGoods.size();i++){
            for(int j=0;j<BeanDisConnGoods.tableTitles.length;j++)
                tblDiscountGoodsData[i][j]=allDiscountGoods.get(i).getCell(j);
        }
        tabDiscountGoodsModel.setDataVector(tblDiscountGoodsData,tblDiscountGoodsTitle);
        this.dataTableDiscountGoods.validate();
        this.dataTableDiscountGoods.repaint();
    }

    public FrmDiscountGoodsManage (Frame f, String s, boolean b)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnAdd);
        toolBar.add(btnDelete);
        toolBar.add(btnModify);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.dataTableDiscountGoods.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                int i = FrmDiscountGoodsManage.this.dataTableDiscountGoods.getSelectedRow();
                if (i<0){
                    return;
                }
            }
        });
        this.getContentPane().add(new JScrollPane(this.dataTableDiscountGoods), BorderLayout.CENTER);
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
            FrmDiscountGoodsAdd dlg = new FrmDiscountGoodsAdd(this,"满折商品添加",true);
            dlg.setVisible(true);
            this.reloadTable();
        }
        else if (e.getSource() == this.btnDelete)
        {
            int i = FrmDiscountGoodsManage.this.dataTableDiscountGoods.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择相关信息", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MainControl.disConnGoodsManager.delete(allDiscountGoods.get(i).getTableId());
                this.reloadTable();
            }
            catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if (e.getSource() == this.btnModify)
        {
            int i = FrmDiscountGoodsManage.this.dataTableDiscountGoods.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择满折信息", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmDiscountGoodsModify dlg = new FrmDiscountGoodsModify(this,"满折信息修改",true,allDiscountGoods.get(i));
            dlg.setVisible(true);
            this.reloadTable();
        }
    }
}
