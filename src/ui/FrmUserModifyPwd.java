package ui;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import control.MainControl;
import model.BeanUsers;
import util.BaseException;

public class FrmUserModifyPwd extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");

    private JLabel labelPwdOld = new JLabel("原密码：");
    private JLabel labelPwd = new JLabel("新密码：");
    private JLabel labelPwd2 = new JLabel("新密码：");
    private JPasswordField edtPwdOld = new JPasswordField(20);
    private JPasswordField edtPwd = new JPasswordField(20);
    private JPasswordField edtPwd2 = new JPasswordField(20);
    public FrmUserModifyPwd(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelPwdOld);
        workPane.add(edtPwdOld);
        workPane.add(labelPwd);
        workPane.add(edtPwd);
        workPane.add(labelPwd2);
        workPane.add(edtPwd2);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(300, 180);
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
        else if(e.getSource()==this.btnOk){
            try {
                MainControl.userManager.changePwd(BeanUsers.currentLoginUser,new String(edtPwdOld.getPassword()),new String(edtPwd.getPassword()),new String(edtPwd2.getPassword()));
                BeanUsers.currentLoginUser = MainControl.userManager.login(BeanUsers.currentLoginUser.getUser_id(),new String(edtPwd.getPassword()));
                JOptionPane.showMessageDialog(null, "修改成功","成功",JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }


    }


}
