package ui;

import control.MainControl;
import model.BeanCoupon;
import model.BeanGoodsComment;
import util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmCommentShow extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();

    private JButton btnCancel = new JButton("取消");

    private Object tblCommentTitle[]= BeanGoodsComment.tableTitles;
    private Object tblCommentData[][];
    DefaultTableModel tabCommentModel=new DefaultTableModel();
    private JTable dataTableComment=new JTable(tabCommentModel);

    List<BeanGoodsComment> allComment = null;

    private void reloadTable(int goods_id){
        try {
            allComment = MainControl.purchaseHistoryManager.loadAll(goods_id);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblCommentData =  new Object[allComment.size()][BeanGoodsComment.tableTitles.length];
        for(int i=0;i<allComment.size();i++){
            for(int j=0;j<BeanGoodsComment.tableTitles.length;j++)
                tblCommentData[i][j]=allComment.get(i).getCell(j);
        }
        tabCommentModel.setDataVector(tblCommentData,tblCommentTitle);
        this.dataTableComment.validate();
        this.dataTableComment.repaint();
    }

    public FrmCommentShow(FrmPurchaseHistory f, String s, boolean b, int goods_id)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);


        this.getContentPane().add(new JScrollPane(this.dataTableComment), BorderLayout.CENTER);
        this.reloadTable(goods_id);
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();
        this.btnCancel.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
    }
}
