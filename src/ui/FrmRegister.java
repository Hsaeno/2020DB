package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import control.MainControl;
import model.BeanUsers;
import util.BaseException;

public class FrmRegister extends JDialog implements ActionListener{

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("注册");
    private Button btnCancel = new Button("取消");

    private JLabel labelUser = new JLabel("用户：");
    private JLabel labelPwd = new JLabel("密码：");
    private JLabel labelPwd2 = new JLabel("密码：");
    private JLabel labelName = new JLabel("姓名：");
    private JLabel labelSex = new JLabel(" 性别：   ");
    private JLabel labelEmail = new JLabel("邮箱：");

    private JLabel labelPhone = new JLabel("手机：");
    private JLabel labelCity = new JLabel("城市：");
    private JTextField edtUserId = new JTextField(15);
    private JPasswordField edtPwd = new JPasswordField(15);
    private JPasswordField edtPwd2 = new JPasswordField(15);
    private JTextField edtName = new JTextField(15);
    private JRadioButton btnSexM = new JRadioButton("男    ",true);
    private JRadioButton btnSexW = new JRadioButton("女                        ");
    private ButtonGroup btnGroup = new ButtonGroup();
    private JTextField edtEmail = new JTextField(15);
    private JTextField edtPhone = new JTextField(15);
    private String[] city =  {"上海","杭州","北京"};
    private JComboBox edtCity = new JComboBox(city);
    private JLabel Five = new JLabel("                                     ");
    public FrmRegister(Dialog f,String s,boolean b)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelUser);
        workPane.add(edtUserId);
        workPane.add(labelPwd);
        workPane.add(edtPwd);
        workPane.add(labelPwd2);
        workPane.add(edtPwd2);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelSex);
        btnGroup.add(btnSexM);
        btnGroup.add(btnSexW);
        workPane.add(btnSexM);
        workPane.add(btnSexW);
        workPane.add(labelEmail);
        workPane.add(edtEmail);
        workPane.add(labelPhone);
        workPane.add(edtPhone);
        workPane.add(labelCity);
        workPane.add(edtCity);
        workPane.add(Five);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(250, 400);
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
            String sex = "";
            if(this.btnSexM.isSelected())
            {
                sex = this.btnSexM.getText();
            }
            if(this.btnSexW.isSelected())
            {
                sex = this.btnSexW.getText();
            }
            String email = new String(this.edtEmail.getText());
            String phone = new String(this.edtPhone.getText());
            String city = new String(this.edtCity.getSelectedItem().toString());
            try {
                BeanUsers user=MainControl.userManager.reg(userid,pwd1,pwd2,name,sex,email,phone,city);
                JOptionPane.showMessageDialog(null, "注册成功","成功",JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
