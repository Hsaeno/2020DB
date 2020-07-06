package ui;

import model.BeanUsers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class FrmShowVip extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JLabel labelUser = new JLabel("账号：        ");
    private JLabel labelTime = new JLabel("到期时间：");
    private JTextField edtUserId = new JTextField(25);
    private JTextField edtTime = new JTextField(25);
    private JButton btnCancel = new JButton("退出");
    public FrmShowVip(Frame f, String s, boolean b){
        String id = BeanUsers.currentLoginUser.getUser_id();
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        edtUserId.setText(id);
        edtUserId.setEditable(false);
        workPane.add(labelUser);
        workPane.add(edtUserId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(BeanUsers.currentLoginUser.getVip())
        {
            edtTime.setText(sdf.format(BeanUsers.currentLoginUser.getVip_endTime()));
        }
        else{
            edtTime.setText("您还不是会员哦!");
        }
        edtTime.setEditable(false);
        workPane.add(labelTime);
        workPane.add(edtTime);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(400, 140);
        this.btnCancel.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
    }
}
