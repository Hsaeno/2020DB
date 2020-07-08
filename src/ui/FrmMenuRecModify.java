package ui;

import control.MainControl;
import model.BeanMenu;
import model.BeanMenuRecommend;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmMenuRecModify extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnOk = new JButton("修改");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelId = new JLabel("推荐序号：");
    private JLabel labelGoodsName = new JLabel("商品名称：");
    private JLabel labelMenuId= new JLabel("菜谱序号：");
    private JLabel labelDes = new JLabel("菜谱描述：");
    private JTextField edtId = new JTextField(25);
    private JTextField edtGoodsName = new JTextField(25);
    private JTextField edtMenuId = new JTextField(25);
    private JTextArea edtDes = new JTextArea(10,25);

    public FrmMenuRecModify(FrmMenuRecManage f, String s, boolean b, BeanMenuRecommend bmr)
    {
        super(f,s,b);
        edtId.setText(Integer.toString(bmr.getTabid()));
        edtId.setEditable(false);
        edtGoodsName.setText(bmr.getGoods_name());
        edtMenuId.setText(Integer.toString(bmr.getMenu_id()));
        edtDes.setText(bmr.getRecommend_description());
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelId);
        workPane.add(edtId);
        workPane.add(labelGoodsName);
        workPane.add(edtGoodsName);
        workPane.add(labelMenuId);
        workPane.add(edtMenuId);
        workPane.add(labelDes);
        workPane.add(edtDes);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(300, 350);
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
            String goods_name = edtGoodsName.getText();
            String des = edtDes.getText();
            int menu_id;
            try{
                menu_id = Integer.parseInt(edtMenuId.getText());
            }
            catch (Exception e1)
            {
                JOptionPane.showMessageDialog(null, "相关信息填写错误","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MainControl.menuRecManager.modify(Integer.parseInt(edtId.getText()),goods_name,menu_id,des);
                JOptionPane.showMessageDialog(null,  "添加成功","提示",JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
