package ui;

import control.MainControl;
import model.BeanGoods;
import model.BeanMenu;
import model.BeanMenuRecommend;
import util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmMenuShow extends JFrame implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnBuy = new JButton("加入购物车");

    private Object tblMenuTitle[]= BeanMenu.tableTitles;
    private Object tblMenuData[][];
    DefaultTableModel tabMenuModel=new DefaultTableModel();
    private JTable dataTableMenu=new JTable(tabMenuModel);

    private Object tblGoodsTitle[]= BeanGoods.tableTitles3;
    private Object tblGoodsData[][];
    DefaultTableModel tabGoodsModel=new DefaultTableModel();
    private JTable dataTableGoods=new JTable(tabGoodsModel);

    private BeanMenu curMenu = null;

    private JPanel statusBar = new JPanel();

    List<BeanGoods> menuGoods = null;
    List<BeanMenu> allMenu = null;

    private void reloadMenuTable(){
        try {
            allMenu = MainControl.menuManager.loadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblMenuData =  new Object[allMenu.size()][BeanMenu.tableTitles.length];
        for(int i=0;i<allMenu.size();i++){
            for(int j=0;j<BeanMenu.tableTitles.length;j++)
                tblMenuData[i][j]=allMenu.get(i).getCell(j);
        }
        tabMenuModel.setDataVector(tblMenuData,tblMenuTitle);
        this.dataTableMenu.validate();
        this.dataTableMenu.repaint();
    }
    private void reloadGoodsTable(int menuIdx)
    {
        if (menuIdx < 0 )
            return;
        curMenu = allMenu.get(menuIdx);
        try{
            menuGoods = MainControl.goodsManager.loadAllByMenu(curMenu.getMenu_id());
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblGoodsData = new Object[menuGoods.size()][BeanGoods.tableTitles3.length];
        for (int i=0;i<menuGoods.size();i++)
            for (int j =0;j<BeanGoods.tableTitles3.length;j++)
            {
                tblGoodsData[i][j] = menuGoods.get(i).getCell3(j);
            }
        tabGoodsModel.setDataVector(tblGoodsData,tblGoodsTitle);
        this.dataTableGoods.validate();
        this.dataTableGoods.repaint();
    }

    public FrmMenuShow (Frame f, String s, boolean b)
    {
        this.setTitle("菜谱推荐");
        toolBar.add(this.btnBuy);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.getContentPane().add(new JScrollPane(this.dataTableMenu), BorderLayout.WEST);

        this.dataTableMenu.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                int i = FrmMenuShow.this.dataTableMenu.getSelectedRow();
                if (i<0){
                    return;
                }
                FrmMenuShow.this.reloadGoodsTable(i);
            }

        });
        this.getContentPane().add(new JScrollPane(this.dataTableGoods), BorderLayout.CENTER);
        this.reloadMenuTable();
        this.setExtendedState(MAXIMIZED_BOTH);
        this.btnBuy.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnBuy)
        {
            int i = FrmMenuShow.this.dataTableGoods.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmSearchGoodsAdd dlg = new FrmSearchGoodsAdd(this,"商品数量填写",true,menuGoods.get(i));
            dlg.setVisible(true);
        }
    }
}
