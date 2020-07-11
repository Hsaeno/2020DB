package ui;

import control.MainControl;
import model.BeanGoodsComment;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmCommentModify extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnOk = new JButton("添加");
    private JButton btnCancel = new JButton("取消");

    private JLabel labelContent = new JLabel("评价内容：");
    private JLabel labelStar= new JLabel("评价星级：");

    private JTextArea edtContent = new JTextArea(15,25);
    String[] starScore = {"1","2","3","4","5"};
    private JComboBox edtStar = new JComboBox(starScore);

    BeanGoodsComment beanGoodsComment = null;
    public FrmCommentModify (FrmPurchaseHistory f, String s, boolean b, BeanGoodsComment bgc)
    {
        super(f,s,b);
        beanGoodsComment = bgc;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        edtContent.setText(bgc.getComment_content());
        edtStar.setSelectedItem(bgc.getComment_star());
        workPane.add(labelContent);
        workPane.add(edtContent);
        workPane.add(labelStar);
        workPane.add(edtStar);

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(300, 400);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        else if(e.getSource()==this.btnOk)
        {
            try {
                MainControl.purchaseHistoryManager.modifyComment(beanGoodsComment.getGoods_id(),edtContent.getText(),Integer.parseInt(edtStar.getSelectedItem().toString()));
                JOptionPane.showMessageDialog(null,  "修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
