package ui;

import control.MainControl;
import model.BeanAdmin;
import model.BeanFresh;
import model.BeanGoods;
import model.BeanUsers;
import util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class FrmAdminShowFresh extends JFrame implements ActionListener {

    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_FreshManage=new JMenu("生鲜管理");
    private JMenu menu_GoodsManage=new JMenu("商品管理");

    private JMenuItem  menuItem_FreshAdd=new JMenuItem("生鲜类别添加");
    private JMenuItem  menuItem_FreshModify=new JMenuItem("生鲜类别修改");
    private JMenuItem  menuItem_FreshDelete=new JMenuItem("生鲜类别删除");

    private JMenuItem  menuItem_GoodsAdd=new JMenuItem("商品添加");
    private JMenuItem  menuItem_GoodsModify=new JMenuItem("商品修改");
    private JMenuItem  menuItem_GoodsDelete=new JMenuItem("商品删除");


    private Object tblFreshTitle[]= BeanFresh.tableTitles;
    private Object tblFreshData[][];
    DefaultTableModel tabFreshModel=new DefaultTableModel();
    private JTable dataTableFresh=new JTable(tabFreshModel);

    private Object tblGoodsTitle[]= BeanGoods.tableTitles;
    private Object tblGoodsData[][];
    DefaultTableModel tabGoodsModel=new DefaultTableModel();
    private JTable dataTableGoods=new JTable(tabGoodsModel);

    private BeanFresh curFresh = null;

    private JPanel statusBar = new JPanel();

    List<BeanFresh> allFresh = null;
    List<BeanGoods> freshGoods = null;
    private void reloadFreshTable(){
        try {
            allFresh= MainControl.freshManager.loadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblFreshData =  new Object[allFresh.size()][BeanFresh.tableTitles.length];
        for(int i=0;i<allFresh.size();i++){
            for(int j=0;j<BeanFresh.tableTitles.length;j++)
                tblFreshData[i][j]=allFresh.get(i).getCell(j);
        }
        tabFreshModel.setDataVector(tblFreshData,tblFreshTitle);
        this.dataTableFresh.validate();
        this.dataTableFresh.repaint();
    }

    private void reloadGoodsTable(int FreshIdx)
    {
        if (FreshIdx < 0 )
            return;
        curFresh = allFresh.get(FreshIdx);
        try{
            freshGoods = MainControl.goodsManager.loadAll(curFresh.getCategory_id());
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblGoodsData = new Object[freshGoods.size()][BeanGoods.tableTitles.length];
        for (int i=0;i<freshGoods.size();i++)
            for (int j =0;j<BeanGoods.tableTitles.length;j++)
            {
                tblGoodsData[i][j] = freshGoods.get(i).getCell(j);
            }
        tabGoodsModel.setDataVector(tblGoodsData,tblGoodsTitle);
        this.dataTableGoods.validate();
        this.dataTableGoods.repaint();
    }

    public FrmAdminShowFresh(Frame f, String s, boolean b)
    {
        this.setTitle("商品信息");
        this.getContentPane().add(new JScrollPane(this.dataTableFresh), BorderLayout.WEST);
        this.menu_FreshManage.add(this.menuItem_FreshAdd);
        this.menu_FreshManage.add(this.menuItem_FreshModify);
        this.menu_FreshManage.add(this.menuItem_FreshDelete);
        this.menu_GoodsManage.add(this.menuItem_GoodsAdd);
        this.menu_GoodsManage.add(this.menuItem_GoodsModify);
        this.menu_GoodsManage.add(this.menuItem_GoodsDelete);

        this.menuItem_FreshAdd.addActionListener(this);
        this.menuItem_FreshModify.addActionListener(this);
        this.menuItem_FreshDelete.addActionListener(this);
        this.menuItem_GoodsAdd.addActionListener(this);
        this.menuItem_GoodsModify.addActionListener(this);
        this.menuItem_GoodsDelete.addActionListener(this);

        menubar.add(menu_FreshManage);
        menubar.add(menu_GoodsManage);

        this.setJMenuBar(menubar);

        this.dataTableFresh.addMouseListener(new MouseAdapter (){
            public void mouseClicked(MouseEvent e)
            {
                int i = FrmAdminShowFresh.this.dataTableFresh.getSelectedRow();
                if (i<0){
                    return;
                }
                FrmAdminShowFresh.this.reloadGoodsTable(i);
            }

    });
        this.getContentPane().add(new JScrollPane(this.dataTableGoods), BorderLayout.CENTER);
        this.reloadFreshTable();
        this.setExtendedState(MAXIMIZED_BOTH);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.menuItem_FreshAdd)
        {
            FrmFreshAdd dlg = new FrmFreshAdd(this,"商品信息",true);
            dlg.setVisible(true);
            reloadFreshTable();
        }
        if (e.getSource() == this.menuItem_FreshDelete)
        {
            if (this.curFresh == null)
            {
                JOptionPane.showMessageDialog(null, "请选择生鲜类别", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try{
                MainControl.freshManager.delete(this.curFresh.getCategory_id());
                JOptionPane.showMessageDialog(null, "删除成功", "成功",JOptionPane.INFORMATION_MESSAGE);
                reloadFreshTable();
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        if (e.getSource() == this.menuItem_FreshModify)
        {
            if (this.curFresh == null)
            {
                JOptionPane.showMessageDialog(null, "请选择生鲜类别", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
                FrmFreshModify dlg = new FrmFreshModify(this,"商品修改",true,this.curFresh);
                dlg.setVisible(true);
                reloadFreshTable();
            }
        }
        if (e.getSource() == this.menuItem_GoodsAdd)
        {
            if (this.curFresh == null)
            {
                JOptionPane.showMessageDialog(null, "请选择生鲜类别", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
                FrmGoodsAdd dlg = new FrmGoodsAdd(this,"商品添加",true,this.curFresh.getCategory_id());
                dlg.setVisible(true);
                int i=FrmAdminShowFresh.this.dataTableFresh.getSelectedRow();
                if(i<0) {
                    return;
                }
                FrmAdminShowFresh.this.reloadGoodsTable(i);
                reloadFreshTable();
            }
        }
        if (e.getSource() == this.menuItem_GoodsDelete)
        {
            int i=FrmAdminShowFresh.this.dataTableGoods.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MainControl.goodsManager.delete(this.freshGoods.get(i).getGoods_id());
                JOptionPane.showMessageDialog(null, "删除成功", "成功",JOptionPane.INFORMATION_MESSAGE);
                int j=FrmAdminShowFresh.this.dataTableFresh.getSelectedRow();
                if(j<0) {
                    return;
                }
                FrmAdminShowFresh.this.reloadGoodsTable(j);
                reloadFreshTable();
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        if (e.getSource() == this.menuItem_GoodsModify)
        {
            int i=FrmAdminShowFresh.this.dataTableGoods.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmGoodsModify dlg = new FrmGoodsModify(this,"商品修改",true,this.freshGoods.get(i));
            dlg.setVisible(true);
            int j=FrmAdminShowFresh.this.dataTableFresh.getSelectedRow();
            if(j<0) {
                return;
            }
            FrmAdminShowFresh.this.reloadGoodsTable(j);
            reloadFreshTable();
        }
    }
}
