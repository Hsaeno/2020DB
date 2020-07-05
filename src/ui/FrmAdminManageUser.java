package ui;

import control.MainControl;
import model.BeanUsers;
import util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmAdminManageUser extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Object tblUserTitle[] = BeanUsers.tableTitles;
    private Object tblUserData[][];
    private JButton btnCancel = new JButton("退出");
    DefaultTableModel tabUserModel = new DefaultTableModel();
    private JTable dataTableUser = new JTable(tabUserModel);

    List<BeanUsers> allUser = null;
    private void reloadUserTable()
    {
        try{
            allUser = MainControl.userManager.loadAll();
        }
        catch (BaseException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }

        tblUserData = new Object[allUser.size()][BeanUsers.tableTitles.length];
        for (int i=0;i<allUser.size();i++)
            for (int j = 0;j<BeanUsers.tableTitles.length;j++)
                tblUserData[i][j] = allUser.get(i).getCell(j);

        tabUserModel.setDataVector(tblUserData,tblUserTitle);
        this.dataTableUser.validate();
        this.dataTableUser.repaint();
    }
    public FrmAdminManageUser(JFrame f, String s, boolean b)
    {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelName);
        workPane.add(edtName);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(320, 180);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        }
    }
}
