package ui;

import control.MainControl;
import model.BeanAdmin;
import model.BeanUsers;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmUserModifyInf extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelPwd = new JLabel("确认密码：");
    private JLabel labelName = new JLabel("姓名：");
    private JLabel labelEmail = new JLabel("邮箱：");

    private JLabel labelPhone = new JLabel("手机：");
    private JLabel labelCity = new JLabel("城市：");
    private JTextField edtUserId = new JTextField(15);
    private JPasswordField edtPwd = new JPasswordField(13);
    private JPasswordField edtPwd2 = new JPasswordField(15);
    private JTextField edtName = new JTextField(15);
    private ButtonGroup btnGroup = new ButtonGroup();
    private JTextField edtEmail = new JTextField(15);
    private JTextField edtPhone = new JTextField(15);
    private String[] city =  {"上海","杭州","北京"};
    private JComboBox edtCity = new JComboBox(city);
    private JLabel Five = new JLabel("                                     ");
    public FrmUserModifyInf(FrmMain f, String s, boolean b)
    {
        super(f,s,b);
        String name = BeanUsers.currentLoginUser.getUser_name();
        String email = BeanUsers.currentLoginUser.getUser_email();
        String phone = BeanUsers.currentLoginUser.getUser_phoneNumber();
        String city = BeanUsers.currentLoginUser.getUser_city();
        edtName.setText(name);
        edtEmail.setText(email);
        edtPhone.setText(phone);
        edtCity.setSelectedItem(city);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelEmail);
        workPane.add(edtEmail);
        workPane.add(labelPhone);
        workPane.add(edtPhone);
        workPane.add(labelCity);
        workPane.add(edtCity);
        workPane.add(Five);
        workPane.add(labelPwd);
        workPane.add(edtPwd);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(250, 250);
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        else if(e.getSource()==this.btnOk){
            try {
                MainControl.userManager.changeInf(BeanUsers.currentLoginUser, new String(this.edtPwd.getPassword()),new String(this.edtName.getText()),new String(this.edtEmail.getText()),new String(this.edtPhone.getText()),new String(this.edtCity.getSelectedItem().toString()));
                BeanUsers.currentLoginUser = MainControl.userManager.login(BeanUsers.currentLoginUser.getUser_id(),new String(edtPwd.getPassword()));
                JOptionPane.showMessageDialog(null, "修改成功","成功",JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}

