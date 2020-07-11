package ui;

import control.MainControl;
import model.BeanAddress;
import model.BeanCoupon;
import model.BeanUsers;
import util.BaseException;
import util.DBUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class FrmOrder extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");

    private JLabel labelOriPrice = new JLabel("订单原价：");
    private JLabel labelCoupon = new JLabel("            优惠方案：                     ");
    private JLabel labelAddress = new JLabel("       收货地址：");
    private JLabel labelTime = new JLabel("     送达时间：");
    private JLabel labelSettlePrice = new JLabel("                                     结算金额：");

    private JTextField edtOriPrice = new JTextField(15);
    private JComboBox edtCoupon = new JComboBox ();
    private JComboBox edtAddress = new JComboBox();
    private JTextField edtTime = new JTextField(15);
    private JLabel edtSettlePrice = new JLabel();

    List<BeanAddress>beanAddresses = null;
    List<BeanCoupon>beanCoupons = null;
    double orgPrice;
    double setPrice;

    private String getDoubleString(double number) {
        String numberStr;
        if (((int) number * 1000) == (int) (number * 1000)) {
            //如果是一个整数
            numberStr = String.valueOf((int) number);
        } else {
            DecimalFormat df = new DecimalFormat("######0.00");
            numberStr = df.format(number);
        }
        return numberStr;
    }

    public FrmOrder (Frame f, String s, boolean b, List<BeanAddress>ba, List<BeanCoupon> bc) throws BaseException {
        super(f,s,b);
        beanAddresses = ba;
        beanCoupons = bc;

        String[] coupon = new String[bc.size()];
        String[] address = new String[ba.size()];
        edtCoupon.addItem("无");
        for(int i = 0;i<bc.size();i++)
            {
                coupon[i] = bc.get(i).getCoupon_content();
                edtCoupon.addItem(coupon[i]);
            }
        for(int i  = 0;i<ba.size();i++)
        {
            address[i] = ba.get(i).getProvince()+'-'+ba.get(i).getCity()+'-'+ba.get(i).getRegion()+'-'+ba.get(i).getDetail_address()+'-'+ba.get(i).getContact_person()+'-'+ba.get(i).getContact_phoneNumber();
            edtAddress.addItem(address[i]);
        }
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelOriPrice);
        edtOriPrice.setText(getDoubleString(MainControl.cartManager.CalculateOrgPrice()));
        orgPrice = MainControl.cartManager.CalculateOrgPrice();
        edtSettlePrice.setText(getDoubleString(MainControl.cartManager.CalculateSetPrice(-1,orgPrice)));
        workPane.add(edtOriPrice);
        workPane.add(labelCoupon);
        workPane.add(edtCoupon);
        workPane.add(labelAddress);
        workPane.add(edtAddress);
        workPane.add(labelTime);
        workPane.add(edtTime);
        workPane.add(labelSettlePrice);
        workPane.add(edtSettlePrice);

        edtCoupon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (edtCoupon.getSelectedIndex() == 0) {
                    try {
                        edtSettlePrice.removeAll();
                        edtSettlePrice.setText(getDoubleString(MainControl.cartManager.CalculateSetPrice(-1,orgPrice)));
                    } catch (BaseException e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    try {
                        edtSettlePrice.removeAll();
                        edtSettlePrice.setText(getDoubleString(MainControl.cartManager.CalculateSetPrice(bc.get(edtCoupon.getSelectedIndex()-1).getCoupon_id(),orgPrice)));
                    } catch (BaseException e1) {
                        edtCoupon.setSelectedItem("无");
                        JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);

                    }
                }
            }
        });

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(550, 250);
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date requireDate = null;
            try{
                requireDate = sdf.parse(edtTime.getText());
            }
            catch (Exception e1)
            {
                JOptionPane.showMessageDialog(null, "日期格式错误","错误",JOptionPane.ERROR_MESSAGE);
            }
            try {
                int order_id;
                if (edtAddress.getSelectedIndex() ==-1)
                    JOptionPane.showMessageDialog(null, "请先添加地址","错误",JOptionPane.ERROR_MESSAGE);
                if (edtCoupon.getSelectedIndex()==0)
                {
                    order_id = MainControl.cartManager.MakeOrder(beanAddresses.get(edtAddress.getSelectedIndex()).getAddress_id(),-1,orgPrice, Double.parseDouble(edtSettlePrice.getText()),requireDate);
                    MainControl.cartManager.MakeOrderDetail(order_id);
                }
                else {
                    order_id = MainControl.cartManager.MakeOrder(beanAddresses.get(edtAddress.getSelectedIndex()).getAddress_id(),beanCoupons.get(edtCoupon.getSelectedIndex()-1).getCoupon_id(),orgPrice, Double.parseDouble(edtSettlePrice.getText()),requireDate);
                    MainControl.cartManager.MakeOrderDetail(order_id);
                }
                JOptionPane.showMessageDialog(null,  "下单成功","提示",JOptionPane.INFORMATION_MESSAGE);
                MainControl.cartManager.deleteAll(BeanUsers.currentLoginUser.getUser_id());
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
