package ui;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.*;

import control.MainControl;
import model.BeanFresh;
import model.BeanGoods;
import model.BeanUsers;
import util.BaseException;
import util.DBUtil;

public class FrmGoodsModify extends JDialog implements ActionListener{

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("修改");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelId = new JLabel("商品序号");
    private JLabel labelCatagory = new JLabel("商品类别：");
    private JLabel labelName = new JLabel("商品名称：");
    private JLabel labelPrice = new JLabel("商品价格：");
    private JLabel labelVipPrice = new JLabel( "会员价格：");
    private JLabel labelNumber = new JLabel("商品数量：");
    private JLabel labelSpec = new JLabel("商品规格：");
    private JLabel labelDetail = new JLabel("商品描述：");
    private JTextField edtId = new JTextField(15);
    private JTextField edtCatagory = new JTextField(15);
    private JTextField edtName = new JTextField(15);
    private JTextField edtPrice = new JTextField(15);
    private JTextField edtVipPrice = new JTextField(15);
    private JTextField edtNumber = new JTextField(15);
    private JTextField edtSpec = new JTextField(15);
    private JTextArea edtDetail = new JTextArea(10,15);
    public String name;
    public FrmGoodsModify(FrmAdminShowFresh f, String s, boolean b, BeanGoods goods)
    {
        super(f,s,b);
        int id = goods.getCategory_id();
        System.out.println(goods);
        name = goods.getGoods_name();
        String des = goods.getDetail();
        edtId.setText(Integer.toString(id));
        edtId.setEditable(false);
        edtName.setText(name);
        edtCatagory.setText(Integer.toString(goods.getCategory_id()));
        edtPrice.setText(Double.toString(goods.getGoods_price()));
        edtVipPrice.setText(Double.toString(goods.getGoods_price()));
        edtNumber.setText(Integer.toString(goods.getGoods_number()));
        edtSpec.setText(Double.toString(goods.getSpec()));
        edtDetail.setText(goods.getDetail());
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelId);
        workPane.add(edtId);
        workPane.add(labelCatagory);
        workPane.add(edtCatagory);
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
        workPane.add(edtDetail);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.setSize(200, 600);
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        else if(e.getSource()==this.btnOk)
        {
            try{
                MainControl.goodsManager.update(Integer.parseInt(edtId.getText()),Integer.parseInt(edtCatagory.getText()),edtName.getText(),Double.parseDouble(edtPrice.getText()),Double.parseDouble(edtVipPrice.getText()),Integer.parseInt(edtNumber.getText()),Double.parseDouble(edtSpec.getText()),edtDetail.getText(),name);
                JOptionPane.showMessageDialog(null, "修改成功", "成功",JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Exception e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

    }
}
