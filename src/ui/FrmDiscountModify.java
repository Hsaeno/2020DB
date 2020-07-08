package ui;

import control.MainControl;
import model.BeanCoupon;
import model.BeanDiscount;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FrmDiscountModify extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnOk = new JButton("修改");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelId = new JLabel("优惠券号：");
    private JLabel labelContent = new JLabel("满折内容：");
    private JLabel labelLeastNumber= new JLabel("适用数量：");
    private JLabel labelDiscount = new JLabel("折扣信息：");
    private JLabel labelBeginTime = new JLabel("开始时间：");
    private JLabel labelEndTime = new JLabel("结束时间：");
    private JTextField edtId= new JTextField(25);
    private JTextField edtContent = new JTextField(25);
    private JTextField edtLeastNumber = new JTextField(25);
    private JTextField edtDiscount = new JTextField(25);
    private JTextField edtBeginTime = new JTextField(25);
    private JTextField edtEndTime = new JTextField(25);

    public  FrmDiscountModify(FrmDiscountManage f, String s, boolean b, BeanDiscount bd)
    {
        super(f,s,b);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String id = Integer.toString(bd.getDis_inf_id());
        String content = bd.getDis_inf_content();
        String leastNumber = Integer.toString(bd.getLeastGoods_number());
        String discount = Double.toString(bd.getDiscount());
        String beginTime = sdf.format(bd.getDis_beginTime());
        String endTime = sdf.format(bd.getDis_beginTime());
        edtId.setText(id);
        edtId.setEditable(false);
        edtContent.setText(content);
        edtLeastNumber.setText(leastNumber);
        edtDiscount.setText(discount);
        edtBeginTime.setText(beginTime);
        edtEndTime.setText(endTime);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelId);
        workPane.add(edtId);
        workPane.add(labelContent);
        workPane.add(edtContent);
        workPane.add(labelLeastNumber);
        workPane.add(edtLeastNumber);
        workPane.add(labelDiscount);
        workPane.add(edtDiscount);
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
            int leastNumber;
            double discount;
            Date beginTime;
            Date endTime;
            try{
                leastNumber = Integer.parseInt(edtLeastNumber.getText());
                discount = Double.parseDouble(edtDiscount.getText());
                beginTime = sdf.parse(edtBeginTime.getText());
                endTime =sdf.parse(edtEndTime.getText());
            }
            catch (Exception e1)
            {
                JOptionPane.showMessageDialog(null, "相关信息填写不符合规范","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MainControl.discountManager.modify(Integer.parseInt(edtId.getText()),edtContent.getText(),leastNumber,discount,beginTime,endTime);
                JOptionPane.showMessageDialog(null,  "修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
