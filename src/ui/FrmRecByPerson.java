package ui;

import control.MainControl;
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

public class FrmRecByPerson extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnBuy = new JButton("加入购物车");
    private JButton btnCancel = new JButton("返回");

    private Object tblGoodsTitle[]= BeanGoods.tableTitles2;
    private Object tblGoodsData[][];
    DefaultTableModel tabGoodsModel=new DefaultTableModel();
    private JTable dataTableGoods=new JTable(tabGoodsModel);

    List<BeanGoods> allGoods = null;

    private void reloadGoodsTable()
    {
        try{
            allGoods = MainControl.goodsManager.loadRecByPerson();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblGoodsData = new Object[allGoods.size()][BeanGoods.tableTitles2.length];
        for (int i=0;i<allGoods.size();i++)
            for (int j =0;j<BeanGoods.tableTitles2.length;j++)
            {
                tblGoodsData[i][j] = allGoods.get(i).getCell2(j);
            }
        tabGoodsModel.setDataVector(tblGoodsData,tblGoodsTitle);
        this.dataTableGoods.validate();
        this.dataTableGoods.repaint();
    }

    public FrmRecByPerson  (Frame f, String s, boolean b)
    {
        super(f,s,b);

        toolBar.add(this.btnBuy);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.dataTableGoods.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                int i = FrmRecByPerson.this.dataTableGoods.getSelectedRow();
                if (i<0){
                    return;
                }
            }
        });
        this.getContentPane().add(new JScrollPane(this.dataTableGoods), BorderLayout.CENTER);
        this.reloadGoodsTable();
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();

        this.btnCancel.addActionListener(this);
        this.btnBuy.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnCancel)
        {
            this.setVisible(false);
        }
        else if (e.getSource() == this.btnBuy)
        {
            int i = FrmRecByPerson .this.dataTableGoods.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmSearchGoodsAdd dlg = new FrmSearchGoodsAdd(this,"商品数量填写",true,allGoods.get(i));
            dlg.setVisible(true);
        }
    }
}
