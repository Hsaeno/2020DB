package ui;

import control.MainControl;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FrmCouponAdd extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnOk = new JButton("添加");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelContent = new JLabel("优惠内容：");
    private JLabel labelLeastMoney= new JLabel("最低金额：");
    private JLabel labelSubMoney = new JLabel("减免金额：");
    private JLabel labelBeginTime = new JLabel("开始时间：");
    private JLabel labelEndTime = new JLabel("结束时间：");
    private JTextField edtContent = new JTextField(25);
    private JTextField edtLeastMoney = new JTextField(25);
    private JTextField edtSubMoney = new JTextField(25);
    private JTextField edtBeginTime = new JTextField(25);
    private JTextField edtEndTime = new JTextField(25);

    public FrmCouponAdd(FrmCouponManage f,String s,boolean b)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelContent);
        workPane.add(edtContent);
        workPane.add(labelLeastMoney);
        workPane.add(edtLeastMoney);
        workPane.add(labelSubMoney);
        workPane.add(edtSubMoney);
        workPane.add(labelBeginTime);
        workPane.add(edtBeginTime);
        workPane.add(labelEndTime);
        workPane.add(edtEndTime);
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
            String content = edtContent.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Double leastMoney;
            Double subMoney;
            Date beginTime;
            Date endTime;
            try{
                leastMoney = Double.parseDouble(edtLeastMoney.getText());
                subMoney = Double.parseDouble(edtSubMoney.getText());
                beginTime = sdf.parse(edtBeginTime.getText());
                endTime =sdf.parse(edtEndTime.getText());
            }
            catch (Exception e1)
            {
                JOptionPane.showMessageDialog(null, "相关信息填写错误","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MainControl.couponManager.add(edtContent.getText(),leastMoney,subMoney,beginTime,endTime);
                JOptionPane.showMessageDialog(null,  "添加成功","提示",JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
