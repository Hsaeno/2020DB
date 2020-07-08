package ui;

import control.MainControl;
import model.BeanMenuRecommend;
import model.BeanMenu;
import util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmMenuRecManage extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnAdd = new JButton("添加");
    private JButton btnModify = new JButton("修改");
    private JButton btnDelete = new JButton("删除");
    private JButton btnCancel = new JButton("取消");

    private Object tblMenuRecTitle[]= BeanMenuRecommend.tableTitles;
    private Object tblMenuRecData[][];
    DefaultTableModel tabMenuRecModel=new DefaultTableModel();
    private JTable dataTableMenuRec=new JTable(tabMenuRecModel);

    List<BeanMenuRecommend> allMenuRec = null;

    private void reloadTable(){
        try {
            allMenuRec = MainControl.menuRecManager.loadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblMenuRecData =  new Object[allMenuRec.size()][BeanMenuRecommend.tableTitles.length];
        for(int i=0;i<allMenuRec.size();i++){
            for(int j=0;j<BeanMenuRecommend.tableTitles.length;j++)
                tblMenuRecData[i][j]=allMenuRec.get(i).getCell(j);
        }
        tabMenuRecModel.setDataVector(tblMenuRecData,tblMenuRecTitle);
        this.dataTableMenuRec.validate();
        this.dataTableMenuRec.repaint();
    }

    public FrmMenuRecManage (Frame f, String s, boolean b)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnAdd);
        toolBar.add(btnDelete);
        toolBar.add(btnModify);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.dataTableMenuRec.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                int i = FrmMenuRecManage.this.dataTableMenuRec.getSelectedRow();
                if (i<0){
                    return;
                }
            }
        });
        this.getContentPane().add(new JScrollPane(this.dataTableMenuRec), BorderLayout.CENTER);
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
            FrmMenuRecAdd dlg = new FrmMenuRecAdd(this,"菜谱添加",true);
            dlg.setVisible(true);
            this.reloadTable();
        }
        else if (e.getSource() == this.btnDelete)
        {
            int i =FrmMenuRecManage.this.dataTableMenuRec.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择菜谱", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MainControl.menuRecManager.delete(allMenuRec.get(i).getTabid());
                this.reloadTable();
            }
            catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if (e.getSource() == this.btnModify)
        {
            int i = FrmMenuRecManage.this.dataTableMenuRec.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择菜谱", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmMenuRecModify dlg = new FrmMenuRecModify(this,"菜谱修改",true,allMenuRec.get(i));
            dlg.setVisible(true);
            this.reloadTable();
        }
    }
}