package ui;

import control.MainControl;
import model.BeanAddress;
import model.BeanCoupon;
import model.BeanUsers;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FrmAddressModify extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnOk = new JButton("修改");
    private JButton btnCancel = new JButton("取消");

    private JLabel labelProvince= new JLabel("省自治区：");
    private JLabel labelCity = new JLabel("所在城市：");
    private JLabel labelRegion = new JLabel("所在区域：");
    private JLabel labelDetailAddress = new JLabel("详细地址：");
    private JLabel labelContactPerson = new JLabel("联系人名：");
    private JLabel labelContactPhone = new JLabel("联系电话：");

    private String[] province = {"上海","北京","浙江"};
    private JComboBox edtProvince = new JComboBox(province);
    private String[] city1 = {"黄浦区","徐汇区","浦东区"};
    private String[] city2 = {"东城区","朝阳区","海淀区"};
    private String[] city3 = {"杭州市","宁波市","台州市"};
    private JComboBox edtCity = new JComboBox(city1);

    private JTextField edtRegion = new JTextField(25);
    private JTextField edtDetailAddress = new JTextField(25);
    private JTextField edtContactPerson = new JTextField(25);
    private JTextField edtContactPhone = new JTextField(25);
    public int id;
    public FrmAddressModify(FrmAddressManage f, String s, boolean b, BeanAddress ba)
    {
        super(f,s,b);
        id = ba.getAddress_id();
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        edtProvince.setSelectedItem(ba.getProvince());
        if (ba.getProvince().equals("上海"))
        {
            edtCity.removeAllItems();
            for (int i = 0;i<city1.length;i++)
                edtCity.addItem(city1[i]);
            edtCity.setSelectedItem(ba.getCity());
        }
        else if (ba.getProvince().equals("北京"))
        {
            edtCity.removeAllItems();
            for (int i = 0;i<city2.length;i++)
                edtCity.addItem(city2[i]);
            edtCity.setSelectedItem(ba.getCity());
        }
        else {
            edtCity.removeAllItems();
            for (int i = 0;i<city3.length;i++)
                edtCity.addItem(city3[i]);
            edtCity.setSelectedItem(ba.getCity());
        }
        edtCity.setSelectedItem(ba.getCity());
        edtRegion.setText(ba.getRegion());
        edtDetailAddress.setText(ba.getDetail_address());
        edtContactPerson.setText(ba.getContact_person());
        edtContactPhone.setText(ba.getContact_phoneNumber());
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelProvince);
        workPane.add(edtProvince);
        workPane.add(labelCity);
        workPane.add(edtCity);
        workPane.add(labelRegion);
        workPane.add(edtRegion);
        workPane.add(labelDetailAddress);
        workPane.add(edtDetailAddress);
        workPane.add(labelContactPerson);
        workPane.add(edtContactPerson);
        workPane.add(labelContactPhone);
        workPane.add(edtContactPhone);

        edtProvince.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    if (e.getItem().equals("上海")){
                        edtCity.removeAllItems();
                        for (int i = 0;i<city1.length;i++)
                            edtCity.addItem(city1[i]);
                    }
                    if (e.getItem().equals("北京")){
                        edtCity.removeAllItems();
                        for (int i = 0;i<city2.length;i++)
                            edtCity.addItem(city2[i]);
                    }
                    if (e.getItem().equals("浙江")){
                        edtCity.removeAllItems();
                        for (int i = 0;i<city3.length;i++)
                            edtCity.addItem(city3[i]);
                    }
                }
            }
        });

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
            String userId = BeanUsers.currentLoginUser.getUser_id();
            String province = new String(edtProvince.getSelectedItem().toString());
            String city = new String(edtCity.getSelectedItem().toString());
            String region = edtRegion.getText();
            String detailAddress = edtDetailAddress.getText();
            String contactPer = edtContactPerson.getText();
            String contactPhone = edtContactPhone.getText();
            try {
                MainControl.addressManager.modify(id,userId,province,city,region,detailAddress,contactPer,contactPhone);
                JOptionPane.showMessageDialog(null,  "添加成功","提示",JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
