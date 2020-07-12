package ui;

import control.MainControl;
import model.BeanCart;
import model.BeanFresh;
import model.BeanGoods;
import model.BeanUsers;
import util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmGoodsSearch extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnBuy = new JButton("加入购物车");
    private JButton btnLookComment = new JButton("查看评论");
    private JButton btnCancel = new JButton("返回");

    private Object tblGoodsTitle[]= BeanGoods.tableTitles2;
    private Object tblGoodsData[][];
    DefaultTableModel tabGoodsModel=new DefaultTableModel();
    private JTable dataTableGoods=new JTable(tabGoodsModel);

    List<BeanGoods> allGoods = null;

    private void reloadGoodsTable(String inf)
    {
        try{
            allGoods = MainControl.goodsManager.loadSearch(inf);
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

    public FrmGoodsSearch (Frame f, String s, boolean b,String inf)
    {
        super(f,s,b);

        toolBar.add(this.btnLookComment);
        toolBar.add(this.btnBuy);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.dataTableGoods.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                int i = FrmGoodsSearch.this.dataTableGoods.getSelectedRow();
                if (i<0){
                    return;
                }
            }
        });
        this.getContentPane().add(new JScrollPane(this.dataTableGoods), BorderLayout.CENTER);
        this.reloadGoodsTable(inf);
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();

        this.btnCancel.addActionListener(this);
        this.btnBuy.addActionListener(this);
        this.btnLookComment.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnCancel)
        {
            this.setVisible(false);
        }
        else if (e.getSource() == this.btnBuy)
        {
            int i = FrmGoodsSearch.this.dataTableGoods.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmSearchGoodsAdd dlg = new FrmSearchGoodsAdd(this,"商品数量填写",true,allGoods.get(i));
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.btnLookComment)
        {
            int i = FrmGoodsSearch.this.dataTableGoods.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmCommentShow dlg = null;
            dlg = new FrmCommentShow(this,"商品评论",true,allGoods.get(i).getGoods_id());
            dlg.setVisible(true);
        }
    }
}
