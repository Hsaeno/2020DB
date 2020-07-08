package ui;

import control.MainControl;
import model.BeanCoupon;
import model.BeanMenu;
import util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FrmMenuModify extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnOk = new JButton("修改");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelId = new JLabel("菜谱序号：");
    private JLabel labelName = new JLabel("菜谱名称：");
    private JLabel labelMaterial= new JLabel("菜谱材料：");
    private JLabel labelStep = new JLabel("菜谱步骤：");
    private JTextField edtId = new JTextField(25);
    private JTextField edtName = new JTextField(25);
    private JTextField edtMaterial = new JTextField(25);
    private JTextArea edtStep = new JTextArea(10,25);

    public FrmMenuModify(FrmMenuManage f, String s, boolean b, BeanMenu bm)
    {
        super(f,s,b);
        edtId.setText(Integer.toString(bm.getMenu_id()));
        edtId.setEditable(false);
        edtName.setText(bm.getMenu_name());
        edtMaterial.setText(bm.getMaterial());
        edtStep.setText(bm.getStep());
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelId);
        workPane.add(edtId);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelMaterial);
        workPane.add(edtMaterial);
        workPane.add(labelStep);
        workPane.add(edtStep);
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
            String material = edtMaterial.getText();
            String step = edtStep.getText();
            try {
                MainControl.menuManager.modify(Integer.parseInt(edtId.getText()),name,material,step);
                JOptionPane.showMessageDialog(null,  "添加成功","提示",JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
