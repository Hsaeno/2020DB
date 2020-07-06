package ui;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.*;

import control.MainControl;
import model.BeanFresh;
import model.BeanUsers;
import util.BaseException;
import util.DBUtil;

public class FrmGoodsModify extends JDialog implements ActionListener{

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("修改");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelId = new JLabel("序号");
    private JLabel labelName = new JLabel("类别");
    private JLabel labelDetail = new JLabel("描述");
    private JTextField edtId = new JTextField(15);
    private JTextField edtName = new JTextField(15);
    private JTextArea edtDetail = new JTextArea(10,15);
    private String name;
    public FrmGoodsModify(FrmAdminShowFresh f, String s, boolean b, BeanFresh fresh)
    {
        super(f,s,b);
        int id = fresh.getCategory_id();
        name = fresh.getCategory_name();
        String des = fresh.getDescription();
        edtId.setText(Integer.toString(id));
        edtId.setEditable(false);
        edtName.setText(name);
        edtDetail.setText(des);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelId);
        workPane.add(edtId);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelDetail);
        workPane.add(edtDetail);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.setSize(200, 400);
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
                MainControl.freshManager.update(Integer.parseInt(edtId.getText()),this.edtName.getText(),this.edtDetail.getText(),name);
                JOptionPane.showMessageDialog(null, "修改成功", "成功",JOptionPane.INFORMATION_MESSAGE);
            }
            catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

    }
}
