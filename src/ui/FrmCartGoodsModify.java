package ui;

import control.MainControl;
import model.BeanCart;
import model.BeanGoods;
import model.BeanUsers;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmCartGoodsModify extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");

    private JLabel labelGoodsName = new JLabel("商品名称：");
    private JLabel labelNumber = new JLabel("购买数量：");

    private JTextField edtGoodsName = new JTextField(25);
    private JTextField edtNumber = new JTextField(25);
    public BeanCart beanCart = null;
    public  FrmCartGoodsModify (FrmMain f, String s, boolean b, BeanCart bc)
    {
        super(f,s,b);
        beanCart = bc;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelGoodsName);
        edtGoodsName.setText(bc.getGoodsName());
        edtGoodsName.setEditable(false);
        workPane.add(edtGoodsName);
        workPane.add(labelNumber);
        edtNumber.setText(Integer.toString(bc.getGoods_number()));
        workPane.add(edtNumber);
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
            try {
                MainControl.cartManager.modify(beanCart.getCartNumber(),Integer.parseInt(edtNumber.getText()));
                JOptionPane.showMessageDialog(null,  "修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
