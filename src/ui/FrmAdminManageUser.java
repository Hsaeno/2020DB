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
    private JLabel labelNo = new JLabel("查询不到该用户");

    BeanUsers User = null;
    private void reloadUserTable(String userid) throws BaseException {
        User = MainControl.userManager.load(userid);

        if (User == null)
        {
            workPane.add(labelNo);
            this.getContentPane().add(workPane, BorderLayout.CENTER);
        }
        else
        {

            tblUserData = new Object[1][BeanUsers.tableTitles.length];
            for (int i=0;i<1;i++)
                for (int j = 0;j<BeanUsers.tableTitles.length;j++)
                    tblUserData[i][j] = User.getCell(j);

            tabUserModel.setDataVector(tblUserData,tblUserTitle);
            this.dataTableUser.validate();
            this.dataTableUser.repaint();
            this.getContentPane().add(new JScrollPane(this.dataTableUser));
            System.out.println(User);
        }
    }
    public FrmAdminManageUser(FrmAdminManageUserWindow f, String s, boolean b,String userid) throws BaseException {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        this.setSize(1000, 200);
        this.reloadUserTable(userid);


        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnCancel.addActionListener(this);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        }
    }

