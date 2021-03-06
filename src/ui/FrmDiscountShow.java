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

public class FrmDiscountShow extends JDialog implements ActionListener {


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

    public FrmDiscountShow (Frame f, String s, boolean b)
    {
        super(f,s,b);

        this.dataTableDiscountGoods.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                int i = FrmDiscountShow.this.dataTableDiscountGoods.getSelectedRow();
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
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
