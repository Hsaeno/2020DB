package ui;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.tools.Tool;

import model.BeanUsers;
import model.BeanAdmin;
import util.BaseException;
import control.MainControl;


public class FrmLogin extends JDialog implements ActionListener {
    public static int flag = -1;  //用户是1,管理员是0;
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnUserLogin = new JButton("用户登陆");
    private JButton btnAdminLogin = new JButton("管理员登陆");
    private JButton btnCancel = new JButton("退出");
    private JButton btnRegister = new JButton("用户注册");

    private JLabel labelUser = new JLabel("账号:");
    private JLabel labelPwd = new JLabel("密码:");
    private JTextField edtUserId = new JTextField(22);
    private JPasswordField edtPwd = new JPasswordField(22);

    public FrmLogin(Frame f,String s,boolean b)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnRegister);
        toolBar.add(this.btnAdminLogin);
        toolBar.add(this.btnUserLogin);
        toolBar.add(this.btnCancel);
        this.getContentPane().add(toolBar,BorderLayout.SOUTH);
        workPane.add(labelUser);
        workPane.add(edtUserId);
        workPane.add(labelPwd);
        workPane.add(edtPwd);
        this.getContentPane().add(workPane,BorderLayout.CENTER);
        this.setSize(320,140);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();
        btnAdminLogin.addActionListener(this);
        btnUserLogin.addActionListener(this);
        btnCancel.addActionListener(this);
        btnRegister.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnUserLogin) {
            String userid = this.edtUserId.getText();
            String pwd = new String(this.edtPwd.getPassword());
            try {
                BeanUsers.currentLoginUser = MainControl.userManager.login(userid, pwd);
                if (pwd.equals(BeanUsers.currentLoginUser.getUser_pwd())) {
                    flag = 1;
                } else {
                    JOptionPane.showMessageDialog(null, "密码错误", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            setVisible(false);
        } else if (e.getSource() == this.btnCancel) {
            System.exit(0);
        } else if (e.getSource() == this.btnAdminLogin) {
            String admin_id = this.edtUserId.getText();
            String pwd = new String(this.edtPwd.getPassword());
            try {
                BeanAdmin.currentLoginAdmin = MainControl.adminManager.login(admin_id, pwd);
                if (pwd.equals(BeanAdmin.currentLoginAdmin.getAdmin_loginPwd())) {
                    flag = 0;
                } else {
                    JOptionPane.showMessageDialog(null, "密码错误", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            setVisible(false);
        } else if (e.getSource() == this.btnRegister) {
            FrmRegister dlg = new FrmRegister(this, "注册", true);
            dlg.setVisible(true);

        }
    }
}