package ui;

import control.MainControl;
import model.BeanFresh;
import model.BeanUsers;
import util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class FrmAdminShowFresh extends JFrame implements ActionListener {

    private Object tblFreshTitle[]= BeanFresh.tableTitles;
    private Object tblFreshData[][];
    DefaultTableModel tabPlanModel=new DefaultTableModel();
    private JTable dataTablePlan=new JTable(tabPlanModel);

    List<BeanFresh> allFresh=null;
    private void reloadFreshTable(){
        try {
            allFresh= MainControl.freshManager.loadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblFreshData =  new Object[allFresh.size()][BeanFresh.tableTitles.length];
        for(int i=0;i<allFresh.size();i++){
            for(int j=0;j<BeanFresh.tableTitles.length;j++)
                tblFreshData[i][j]=allFresh.get(i).getCell(j);
        }
        tabPlanModel.setDataVector(tblFreshData,tblFreshTitle);
        this.dataTablePlan.validate();
        this.dataTablePlan.repaint();
    }

    public FrmAdminShowFresh(Frame f, String s, boolean b)
    {
        this.setTitle("生鲜信息");
        this.getContentPane().add(new JScrollPane(this.dataTablePlan), BorderLayout.WEST);
        this.reloadFreshTable();
        this.setExtendedState(MAXIMIZED_BOTH);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
