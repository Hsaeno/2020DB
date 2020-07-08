package ui;

import control.MainControl;
import model.BeanMenu;
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

public class FrmMenuManage extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();

    private JButton btnAdd = new JButton("添加");
    private JButton btnModify = new JButton("修改");
    private JButton btnDelete = new JButton("删除");
    private JButton btnCancel = new JButton("取消");

    private Object tblMenuTitle[]= BeanMenu.tableTitles;
    private Object tblMenuData[][];
    DefaultTableModel tabMenuModel=new DefaultTableModel();
    private JTable dataTableMenu=new JTable(tabMenuModel);

    List<BeanMenu> allMenu = null;

    private void reloadTable(){
        try {
            allMenu = MainControl.menuManager.loadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblMenuData =  new Object[allMenu.size()][BeanMenu.tableTitles.length];
        for(int i=0;i<allMenu.size();i++){
            for(int j=0;j<BeanMenu.tableTitles.length;j++)
                tblMenuData[i][j]=allMenu.get(i).getCell(j);
        }
        tabMenuModel.setDataVector(tblMenuData,tblMenuTitle);
        this.dataTableMenu.validate();
        this.dataTableMenu.repaint();
    }

    public FrmMenuManage (Frame f, String s, boolean b)
    {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnAdd);
        toolBar.add(btnDelete);
        toolBar.add(btnModify);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.dataTableMenu.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                int i = FrmMenuManage.this.dataTableMenu.getSelectedRow();
                if (i<0){
                    return;
                }
            }
        });
        this.getContentPane().add(new JScrollPane(this.dataTableMenu), BorderLayout.CENTER);
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
            FrmMenuAdd dlg = new FrmMenuAdd(this,"菜谱添加",true);
            dlg.setVisible(true);
            this.reloadTable();
        }
        else if (e.getSource() == this.btnDelete)
        {
            int i =FrmMenuManage.this.dataTableMenu.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择菜谱", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MainControl.menuManager.delete(allMenu.get(i).getMenu_id());
                this.reloadTable();
            }
            catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if (e.getSource() == this.btnModify)
        {
            int i = FrmMenuManage.this.dataTableMenu.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择菜谱", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmMenuModify dlg = new FrmMenuModify(this,"菜谱修改",true,allMenu.get(i));
            dlg.setVisible(true);
            this.reloadTable();
        }
    }
}
