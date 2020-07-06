package ui;

import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrmAdminManageUserWindow extends JDialog implements ActionListener {
    private JButton btnSeach = new JButton("查询");
    private JTextField edtUserId = new JTextField(22);
    private JPanel workPane = new JPanel();
    private JLabel labelUser = new JLabel("账号:");
    public FrmAdminManageUserWindow(JFrame f, String s, boolean b) {
        super(f,s,b);
        workPane.add(labelUser);
        workPane.add(edtUserId);
        workPane.add(btnSeach);
        this.getContentPane().add(workPane,BorderLayout.CENTER);
        this.setSize(320,100);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();
        btnSeach.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnSeach)
        {

            FrmAdminManageUser dlg = null;
            try {
                dlg = new FrmAdminManageUser(this,"会员信息",true,edtUserId.getText());
            } catch (BaseException ex) {
                ex.printStackTrace();
            }
            dlg.setVisible(true);

        }
    }
}
