package ui;

import model.BeanUsers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmVipJoin extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确认");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelUser = new JLabel("开通账号：");
    private JTextField edtUserId = new JTextField(15);
    private JRadioButton one = new JRadioButton("15元----1个月");
    private JRadioButton three = new JRadioButton("45元----3个月",true);
    private JRadioButton six = new JRadioButton("90元----6个月");
    private JRadioButton twelve = new JRadioButton("168元----12个月");
    private ButtonGroup btnGroup = new ButtonGroup();
    public FrmVipJoin(FrmMain f, String s, boolean b)
    {
        super(f,s,b);
        String id = BeanUsers.currentLoginUser.getUser_id();
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelUser);
        edtUserId.setText(id+"(自己)");
        btnGroup.add(one);
        btnGroup.add(three);
        btnGroup.add(six);
        btnGroup.add(twelve);
        workPane.add(one);
        workPane.add(three);
        workPane.add(six);
        workPane.add(twelve);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(250, 250);
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {

    }
}
