package ui;

import control.MainControl;
import model.BeanGoods;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmGoodsAdd extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelName = new JLabel("商品名称：");
    private JLabel labelPrice = new JLabel("商品价格：");
    private JLabel labelVipPrice = new JLabel("会员价格：");
    private JLabel labelNumber= new JLabel("商品数量：");
    private JLabel labelSpec = new JLabel("商品规格：");
    private JLabel labelDetail = new JLabel("商品描述：");

    private JTextField edtName = new JTextField(25);
    private JTextField edtPrice= new JTextField(25);
    private JTextField edtVipPrice = new JTextField(25);
    private JTextField edtNumber = new JTextField(25);
    private JTextField edtSpec = new JTextField(25);
    private JTextArea edtDes = new JTextArea(10,25);

    public int catagory_id;

    public FrmGoodsAdd(JFrame f, String s, boolean b,int id){
        super(f,s,b);
        catagory_id = id;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelPrice);
        workPane.add(edtPrice);
        workPane.add(labelVipPrice);
        workPane.add(edtVipPrice);
        workPane.add(labelNumber);
        workPane.add(edtNumber);
        workPane.add(labelSpec);
        workPane.add(edtSpec);
        workPane.add(labelDetail);
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
            if (edtPrice.getText() == null || edtNumber.getText() == null || edtSpec.getText() == null || edtVipPrice.getText() == null || "".equals(edtPrice.getText()) || "".equals(edtVipPrice.getText())
            || "".equals(edtNumber.getText()) || "".equals(edtSpec.getText()))
            {
                JOptionPane.showMessageDialog(null, "相关信息不能为空","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try{
                Integer.parseInt(edtNumber.getText());
            }
            catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "数量格式错误","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try{
                MainControl.goodsManager.add(catagory_id,edtName.getText(),Double.parseDouble(edtPrice.getText()),Double.parseDouble(edtVipPrice.getText()),Integer.parseInt(edtNumber.getText()),
                        Double.parseDouble(edtSpec.getText()),edtDes.getText());
                JOptionPane.showMessageDialog(null,  "添加成功","提示",JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            }
            catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
