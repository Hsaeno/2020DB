package ui;

import control.MainControl;
import model.BeanGoodsComment;
import model.BeanUsers;
import util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmMyComment extends JDialog implements ActionListener {


    private Object tblCommentTitle[]= BeanGoodsComment.tableTitles2;
    private Object tblCommentData[][];
    DefaultTableModel tabCommentModel=new DefaultTableModel();
    private JTable dataTableComment=new JTable(tabCommentModel);

    private JPanel toolBar = new JPanel();
    private JButton btnOK = new JButton("修改");

    List<BeanGoodsComment> allComment = null;

    private void reloadTable(){
        try {
            allComment = MainControl.purchaseHistoryManager.loadAllByUser();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblCommentData =  new Object[allComment.size()][BeanGoodsComment.tableTitles2.length];
        for(int i=0;i<allComment.size();i++){
            for(int j=0;j<BeanGoodsComment.tableTitles2.length;j++)
                tblCommentData[i][j]=allComment.get(i).getCell2(j);
        }
        tabCommentModel.setDataVector(tblCommentData,tblCommentTitle);
        this.dataTableComment.validate();
        this.dataTableComment.repaint();
    }

    public FrmMyComment(FrmPurchaseHistory f, String s, boolean b)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOK);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        this.getContentPane().add(new JScrollPane(this.dataTableComment), BorderLayout.CENTER);
        this.reloadTable();
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();
        this.btnOK.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnOK)
        {
            int i = FrmMyComment.this.dataTableComment.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmMyCommentModify dlg = null;
            try {
                dlg = new FrmMyCommentModify(this,"修改商品评论",true, MainControl.purchaseHistoryManager.Search(allComment.get(i).getGoods_id(), BeanUsers.currentLoginUser.getUser_id()));
                this.reloadTable();
            } catch (BaseException ex) {
                ex.printStackTrace();
            }
            dlg.setVisible(true);
        }
    }
}
