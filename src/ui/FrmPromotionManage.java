package ui;

import control.MainControl;
import model.BeanCoupon;
import util.BaseException;
import model.BeanPromotion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmPromotionManage extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnAdd = new JButton("添加");
    private JButton btnModify = new JButton("修改");
    private JButton btnDelete = new JButton("删除");
    private JButton btnCancel = new JButton("取消");

    private Object tblPromotionTitle[]= BeanPromotion.tableTitles;
    private Object tblPromotionData[][];
    DefaultTableModel tabPromotionModel = new DefaultTableModel();
    private JTable dataTablePromotion = new JTable(tabPromotionModel);

    List<BeanPromotion> allPromotion = null;

    private void reloadTable(){
        try {
            allPromotion = MainControl.promotionManager.AdminLoadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblPromotionData =  new Object[allPromotion.size()][BeanCoupon.tableTitles.length];

        for(int i=0;i<allPromotion.size();i++){
            for(int j=0;j<BeanCoupon.tableTitles.length;j++)
                tblPromotionData[i][j]=allPromotion.get(i).getCell(j);
        }
        tabPromotionModel.setDataVector(tblPromotionData,tblPromotionTitle);
        this.dataTablePromotion.validate();
        this.dataTablePromotion.repaint();
    }

    public FrmPromotionManage(Frame f, String s, boolean b)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnAdd);
        toolBar.add(btnDelete);
        toolBar.add(btnModify);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.dataTablePromotion.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                int i = FrmPromotionManage.this.dataTablePromotion.getSelectedRow();
                if (i<0){
                    return;
                }
            }
        });
        this.getContentPane().add(new JScrollPane(this.dataTablePromotion), BorderLayout.CENTER);
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
            FrmPromotionAdd dlg = new FrmPromotionAdd(this,"限时促销信息添加",true);
            dlg.setVisible(true);
            this.reloadTable();
        }
        else if (e.getSource() == this.btnDelete)
        {
            int i = FrmPromotionManage.this.dataTablePromotion.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择相应的限时促销信息", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MainControl.promotionManager.delete(allPromotion.get(i).getPromotion_id());
                this.reloadTable();
            }
            catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if (e.getSource() == this.btnModify)
        {
            int i = FrmPromotionManage.this.dataTablePromotion.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择相应的限时促销信息", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmPromotionModify dlg = new FrmPromotionModify(this,"限时促销修改",true,allPromotion.get(i));
            dlg.setVisible(true);
            this.reloadTable();
        }
    }
}
