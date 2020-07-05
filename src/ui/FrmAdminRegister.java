package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import control.MainControl;
import model.BeanAdmin;
import util.BaseException;

public class FrmAdminRegister extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("添加");
    private JButton btnCancel = new JButton("取消");

    private JLabel labelUser = new JLabel("用户：");
    private JLabel labelName = new JLabel("名称：");
    private JLabel labelPwd = new JLabel("密码：");
    private JLabel labelPwd2 = new JLabel("密码：");
    private JTextField edtUserId = new JTextField(20);
    private JTextField edtName = new JTextField(20);
    private JPasswordField edtPwd = new JPasswordField(20);
    private JPasswordField edtPwd2 = new JPasswordField(20);
    public FrmAdminRegister(FrmMain f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelUser);
        workPane.add(edtUserId);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelPwd);
        workPane.add(edtPwd);
        workPane.add(labelPwd2);
        workPane.add(edtPwd2);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(300, 180);
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        else if(e.getSource()==this.btnOk){
            String userid=this.edtUserId.getText();
            String pwd1=new String(this.edtPwd.getPassword());
            String pwd2=new String(this.edtPwd2.getPassword());
            String name = new String(this.edtName.getText());
            try {
                BeanAdmin admin=MainControl.adminManager.reg(userid,pwd1,pwd2,name);
                JOptionPane.showMessageDialog(null, "增加成功","成功",JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }

        }


    }
}
