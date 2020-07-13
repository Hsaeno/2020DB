package ui;

import control.MainControl;
import model.BeanAddress;
import util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmAddressManage extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnAdd = new JButton("添加");
    private JButton btnModify = new JButton("修改");
    private JButton btnDelete = new JButton("删除");
    private JButton btnCancel = new JButton("取消");

    private Object tblAddressTitle[]= BeanAddress.tableTitles;
    private Object tblAddressData[][];
    DefaultTableModel tabAddressModel=new DefaultTableModel();
    private JTable dataTableAddress=new JTable(tabAddressModel);

    List<BeanAddress> allAddress = null;

    private void reloadTable(){
        try {
            allAddress = MainControl.addressManager.loadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblAddressData =  new Object[allAddress.size()][BeanAddress.tableTitles.length];
        for(int i=0;i<allAddress.size();i++){
            for(int j=0;j<BeanAddress.tableTitles.length;j++)
                tblAddressData[i][j]=allAddress.get(i).getCell(j);
        }
        tabAddressModel.setDataVector(tblAddressData,tblAddressTitle);
        this.dataTableAddress.validate();
        this.dataTableAddress.repaint();
    }

    public FrmAddressManage (Frame f, String s, boolean b)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnAdd);
        toolBar.add(btnDelete);
        toolBar.add(btnModify);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.dataTableAddress.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                int i = FrmAddressManage.this.dataTableAddress.getSelectedRow();
                if (i<0){
                    return;
                }
            }
        });
        this.getContentPane().add(new JScrollPane(this.dataTableAddress), BorderLayout.CENTER);
        this.reloadTable();
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();
        this.btnAdd.addActionListener(this);
        this.btnCancel.addActionListener(this);
        this.btnDelete.addActionListener(this);
        this.btnModify.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        else if (e.getSource() == this.btnAdd)
        {
            FrmAddressAdd dlg = new FrmAddressAdd(this,"地址添加",true);
            dlg.setVisible(true);
            this.reloadTable();
        }
        else if (e.getSource() == this.btnDelete)
        {
            int i = FrmAddressManage.this.dataTableAddress.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择地址", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MainControl.addressManager.delete(allAddress.get(i).getAddress_id());
                this.reloadTable();
            }
            catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if (e.getSource() == this.btnModify)
        {
            int i = FrmAddressManage.this.dataTableAddress.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择地址", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmAddressModify dlg = new FrmAddressModify(this,"地址修改",true,allAddress.get(i));
            dlg.setVisible(true);
            this.reloadTable();
        }
    }
}
