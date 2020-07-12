package ui;

import control.MainControl;
import model.BeanUsers;
import util.BaseException;

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
    private JTextField edtUserId = new JTextField(20);
    private JRadioButton one = new JRadioButton("   ------15元------1个月");
    private JRadioButton three = new JRadioButton("------45元------3个月",true);
    private JRadioButton six = new JRadioButton("   ------90元------6个月");
    private JRadioButton twelve = new JRadioButton("------168元------12个月");
    private ButtonGroup btnGroup = new ButtonGroup();
    private JLabel tip = new JLabel("扫描下方二维码进行付款");
    private JLabel ZFB = new JLabel(new ImageIcon("ZFB.png"));
    public FrmVipJoin(FrmMain f, String s, boolean b)
    {
        super(f,s,b);
        String id = BeanUsers.currentLoginUser.getUser_id();
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelUser);
        edtUserId.setText(id+"（自己）");
        edtUserId.setEditable(false);
        workPane.add(edtUserId);
        btnGroup.add(one);
        btnGroup.add(three);
        btnGroup.add(six);
        btnGroup.add(twelve);
        workPane.add(one);
        workPane.add(three);
        workPane.add(six);
        workPane.add(twelve);
        workPane.add(tip);
        workPane.add(ZFB);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(400, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        else if(e.getSource()==this.btnOk){
            int mode = 0;
            if (one.isSelected())
                mode = 1;
            if (three.isSelected())
                mode = 3;
            if (six.isSelected())
                mode = 6;
            if (twelve.isSelected())
                mode = 12;
            try{
                MainControl.userManager.ManageVip(BeanUsers.currentLoginUser,mode);
                BeanUsers.currentLoginUser = MainControl.userManager.login(BeanUsers.currentLoginUser.getUser_id(),BeanUsers.currentLoginUser.getUser_pwd());
                JOptionPane.showMessageDialog(null, "订阅成功","成功",JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            }
            catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
