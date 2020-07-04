package ui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;




public class FrmMain extends JFrame implements ActionListener{
    private FrmLogin dlgLogin = null;
    public FrmMain()
    {
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("生鲜网超管理系统");
        dlgLogin = new FrmLogin(this,"登录",true);
        dlgLogin.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
    }
}
