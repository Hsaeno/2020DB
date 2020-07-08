package ui;

import control.MainControl;
import model.BeanDisConnGoods;
import model.BeanDiscount;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FrmDiscountGoodsModify extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnOk = new JButton("修改");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelId = new JLabel("表单编号");
    private JLabel labelDisId = new JLabel("满折编号：");
    private JLabel labelGoodsName = new JLabel("商品名称：");
    private JLabel labelBeginTime = new JLabel("开始时间：");
    private JLabel labelEndTime = new JLabel("结束时间：");
    private JTextField edtId= new JTextField(25);
    private JTextField edtDisId= new JTextField(25);
    private JTextField edtGoodsName = new JTextField(25);
    private JTextField edtBeginTime = new JTextField(25);
    private JTextField edtEndTime = new JTextField(25);

    public  FrmDiscountGoodsModify(FrmDiscountGoodsManage f, String s, boolean b, BeanDisConnGoods bdcg)
    {
        super(f,s,b);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int id = bdcg.getTableId();
        int disId = bdcg.getDis_id();
        edtId.setText(Integer.toString(bdcg.getTableId()));
        edtId.setEditable(false);
        edtDisId.setText(Integer.toString(disId));
        edtGoodsName.setText(bdcg.getGoods_name());
        edtBeginTime.setText(sdf.format(bdcg.getBegin_time()));
        edtEndTime.setText(sdf.format(bdcg.getEnd_time()));
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelId);
        workPane.add(edtId);
        workPane.add(labelDisId);
        workPane.add(edtDisId);
        workPane.add(labelGoodsName);
        workPane.add(edtGoodsName);
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
            String goods_name = edtGoodsName.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int dis_id;
            Date beginTime;
            Date endTime;
            try{
                dis_id = Integer.parseInt(edtDisId.getText());
                beginTime = sdf.parse(edtBeginTime.getText());
                endTime =sdf.parse(edtEndTime.getText());
            }
            catch (Exception e1)
            {
                JOptionPane.showMessageDialog(null, "相关信息填写不符合规范","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MainControl.disConnGoodsManager.modify(Integer.parseInt(edtId.getText()),dis_id,goods_name,beginTime,endTime);
                JOptionPane.showMessageDialog(null,  "修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
