package ui;

import control.MainControl;
import model.BeanCoupon;
import model.BeanPromotion;
import util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmPromotionShow  extends JDialog implements ActionListener {

    private Object tblPromotionTitle[] = BeanPromotion.tableTitles;
    private Object tblPromotionData[][];
    DefaultTableModel tabPromotionModel = new DefaultTableModel();
    private JTable dataTablePromotion = new JTable(tabPromotionModel);

    List<BeanPromotion> allPromotion = null;

    private void reloadTable() {
        try {
            allPromotion = MainControl.promotionManager.loadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblPromotionData = new Object[allPromotion.size()][BeanCoupon.tableTitles.length];

        for (int i = 0; i < allPromotion.size(); i++) {
            for (int j = 0; j < BeanCoupon.tableTitles.length; j++)
                tblPromotionData[i][j] = allPromotion.get(i).getCell(j);
        }
        tabPromotionModel.setDataVector(tblPromotionData, tblPromotionTitle);
        this.dataTablePromotion.validate();
        this.dataTablePromotion.repaint();
    }

    public FrmPromotionShow(Frame f, String s, boolean b) {
        super(f, s, b);

        this.dataTablePromotion.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = FrmPromotionShow.this.dataTablePromotion.getSelectedRow();
                if (i < 0) {
                    return;
                }
            }
        });
        this.getContentPane().add(new JScrollPane(this.dataTablePromotion), BorderLayout.CENTER);
        this.reloadTable();
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
