package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import control.MainControl;
import model.BeanUsers;
import util.BaseException;
public class FrmFreshAdd extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnOk = new JButton("添加");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelName = new JLabel("名称：");
    private JLabel labelDes = new JLabel("描述：");
    private JTextField edtName = new JTextField(25);
    private JTextArea edtDes = new JTextArea(10,25);
    public FrmFreshAdd(FrmAdminShowFresh f, String s, boolean b)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelDes);
        workPane.add(edtDes);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(300, 350);
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
        else if(e.getSource()==this.btnOk)
        {
            String name = edtName.getText();
            String des = edtDes.getText();
            try {
                MainControl.freshManager.add(name,des);
                JOptionPane.showMessageDialog(null,  "添加成功","提示",JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
