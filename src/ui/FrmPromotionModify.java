package ui;

import control.MainControl;
import model.BeanCoupon;
import model.BeanPromotion;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FrmPromotionModify extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnOk = new JButton("修改");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelId = new JLabel("促销序号：");
    private JLabel labelGoodsName = new JLabel("促销商品：");
    private JLabel labelPromotionMoney= new JLabel("促销金额：");
    private JLabel labelPromotionNumber = new JLabel("促销数量：");
    private JLabel labelBeginTime = new JLabel("开始时间：");
    private JLabel labelEndTime = new JLabel("结束时间：");
    private JTextField edtId= new JTextField(25);
    private JTextField edtGoodsName = new JTextField(25);
    private JTextField edtPromotionMoney = new JTextField(25);
    private JTextField edtPromotionNumber = new JTextField(25);
    private JTextField edtBeginTime = new JTextField(25);
    private JTextField edtEndTime = new JTextField(25);
    public int id;
    public FrmPromotionModify(FrmPromotionManage f, String s, boolean b, BeanPromotion bp)
    {
        super(f,s,b);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        id = bp.getPromotion_id();
        String goodsName = bp.getGoods_name();
        String PromotionMoney = Double.toString(bp.getPromotion_price());
        String PromotionNumber = Integer.toString(bp.getPromotion_number());
        String beginTime = sdf.format(bp.getPromotion_beginTime());
        String endTime = sdf.format(bp.getPromotion_endTime());
        edtId.setText(Integer.toString(bp.getPromotion_id()));
        edtId.setEditable(false);
        edtGoodsName.setText(goodsName);
        edtPromotionMoney.setText(PromotionMoney);
        edtPromotionNumber.setText(PromotionNumber);
        edtBeginTime.setText(beginTime);
        edtEndTime.setText(endTime);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelId);
        workPane.add(edtId);
        workPane.add(labelGoodsName);
        workPane.add(edtGoodsName);
        workPane.add(labelPromotionMoney);
        workPane.add(edtPromotionMoney);
        workPane.add(labelPromotionNumber);
        workPane.add(edtPromotionNumber);
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
            String goodsName = edtGoodsName.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            double promotionMoney;
            int promotionNumber;
            Date beginTime;
            Date endTime;
            try{
                promotionMoney = Double.parseDouble(edtPromotionMoney.getText());
                promotionNumber = Integer.parseInt(edtPromotionNumber.getText());
                beginTime = sdf.parse(edtBeginTime.getText());
                endTime =sdf.parse(edtEndTime.getText());
            }
            catch (Exception e1)
            {
                JOptionPane.showMessageDialog(null, "相关信息填写不符合规范","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MainControl.promotionManager.modify(id,goodsName,promotionMoney,promotionNumber,beginTime,endTime);
                JOptionPane.showMessageDialog(null,  "修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
