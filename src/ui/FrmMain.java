package ui;
import control.MainControl;
import model.BeanAdmin;
import model.BeanUsers;
import util.BaseException;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;


public class FrmMain extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_PersonalManage=new JMenu("个人信息管理");
    private JMenu menu_AdminManage=new JMenu("管理员信息管理");
    private JMenu menu_UserManage=new JMenu("用户信息管理");
    private JMenu menu_VipManage=new JMenu("超级会员");
    private JMenu menu_GoodsManage = new JMenu("关于商品");
    private JMenu menu_couponManage = new JMenu("优惠信息相关");
    private JMenu menu_purchaseManage = new JMenu("采购相关");
    private JMenuItem  menuItem_UserModifyInf=new JMenuItem("个人信息修改/查看");
    private JMenuItem  menuItem_UserModifyPwd=new JMenuItem("密码修改");
    private JMenuItem  menuItem_AdminModifyPwd=new JMenuItem("密码修改");
    private JMenuItem  menuItem_AdminAdd=new JMenuItem("管理员增加");
    private JMenuItem  menuItem_AdminManageUser=new JMenuItem("用户信息查看");
    private JMenuItem  menuItem_VipJoin=new JMenuItem("加入/续费会员");
    private JMenuItem  menuItem_VipInf=new JMenuItem("查看会员信息");
    private JMenuItem  menuItem_GoodsManage=new JMenuItem("商品信息管理");
    private JMenuItem  menuItem_CouponManage=new JMenuItem("优惠券管理");
    private JMenuItem  menuItem_PromotionManage=new JMenuItem("限时促销管理");
    private JMenuItem  menuItem_DiscountManage=new JMenuItem("满折管理");
    private JMenuItem  menuItem_DiscountGoodsManage=new JMenuItem("满折商品设置");
    private JMenuItem  menuItem_PurchaseManage=new JMenuItem("采购商品");
    private JMenuItem  menuItem_PurchaseOrderManage=new JMenuItem("采购订单状态更新");


    private FrmLogin dlgLogin = null;
    private JPanel statusBar = new JPanel();

    public FrmMain()
    {
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("生鲜网超管理系统");
        dlgLogin = new FrmLogin(this,"登录",true);
        dlgLogin.setVisible(true);
        if (FrmLogin.flag == 1 )
        {
            this.menu_PersonalManage.add(this.menuItem_UserModifyPwd);
            this.menu_PersonalManage.add(this.menuItem_UserModifyInf);
            this.menuItem_UserModifyPwd.addActionListener(this);
            this.menuItem_UserModifyInf.addActionListener(this);
            this.menu_VipManage.add(this.menuItem_VipJoin);
            this.menu_VipManage.add(this.menuItem_VipInf);
            this.menuItem_VipJoin.addActionListener(this);
            this.menuItem_VipInf.addActionListener(this);
            menubar.add(menu_PersonalManage);
            menubar.add(menu_VipManage);
            this.setJMenuBar(menubar);

            statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));

            if (!BeanUsers.currentLoginUser.getVip())
            {
                JLabel label=new JLabel("您好! 尊敬的用户:"+ BeanUsers.currentLoginUser.getUser_name());
                statusBar.add(label);
            }
            else {
                JLabel label=new JLabel("您好! 尊敬的会员:"+ BeanUsers.currentLoginUser.getUser_name());
                statusBar.add(label);
            }
            this.getContentPane().add(statusBar,BorderLayout.SOUTH);
            this.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    System.exit(0);
                }
            });
            this.setVisible(true);
        }
        else if(FrmLogin.flag == 0)
        {
            this.setExtendedState(Frame.MAXIMIZED_BOTH);
            this.menu_AdminManage.add(this.menuItem_AdminModifyPwd);
            this.menu_AdminManage.add(this.menuItem_AdminAdd);
            this.menu_UserManage.add(this.menuItem_AdminManageUser);
            this.menu_GoodsManage.add(this.menuItem_GoodsManage);
            this.menu_purchaseManage.add(this.menuItem_PurchaseManage);
            this.menu_purchaseManage.add(this.menuItem_PurchaseOrderManage);
            this.menu_couponManage.add(this.menuItem_CouponManage);
            this.menu_couponManage.add(this.menuItem_PromotionManage);
            this.menu_couponManage.add(this.menuItem_DiscountManage);
            this.menu_couponManage.add(this.menuItem_DiscountGoodsManage);
            this.menuItem_AdminModifyPwd.addActionListener(this);
            this.menuItem_AdminAdd.addActionListener(this);
            this.menuItem_AdminManageUser.addActionListener(this);
            this.menuItem_GoodsManage.addActionListener(this);
            this.menuItem_PurchaseManage.addActionListener(this);
            this.menuItem_PurchaseOrderManage.addActionListener(this);
            this.menuItem_CouponManage.addActionListener(this);
            this.menuItem_PromotionManage.addActionListener(this);
            this.menuItem_DiscountManage.addActionListener(this);
            this.menuItem_DiscountGoodsManage.addActionListener(this);
            menubar.add(menu_GoodsManage);
            menubar.add(menu_UserManage);
            menubar.add(menu_AdminManage);
            menubar.add(menu_couponManage);
            menubar.add(menu_purchaseManage);
            this.setJMenuBar(menubar);


            //状态栏
            statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel label=new JLabel("管理员:"+ BeanAdmin.currentLoginAdmin.getAdmin_name());
            statusBar.add(label);
            this.getContentPane().add(statusBar,BorderLayout.SOUTH);
            this.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    System.exit(0);
                }
            });
            this.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.menuItem_UserModifyPwd){
            FrmUserModifyPwd dlg=new FrmUserModifyPwd(this,"密码修改",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_UserModifyInf)
        {
            FrmUserModifyInf dlg = new FrmUserModifyInf(this,"信息修改",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_AdminModifyPwd)
        {
            FrmAdminModifyPwd dlg=new FrmAdminModifyPwd(this,"密码修改",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_AdminAdd)
        {
            FrmAdminRegister dlg = new FrmAdminRegister(this,"管理员增加",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_VipJoin)
        {
            FrmVipJoin dlg = new FrmVipJoin(this,"会员订阅",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_AdminManageUser)
        {
            FrmAdminManageUserWindow dlg = new FrmAdminManageUserWindow(this,"查看用户信息",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_VipInf)
        {    FrmShowVip dlg = new FrmShowVip(this,"会员信息",true);
             dlg.setVisible(true);
    }
        else if (e.getSource() == this.menuItem_GoodsManage)
        {
            FrmAdminShowFresh dlg = new FrmAdminShowFresh(this,"商品信息",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_PurchaseManage)
        {
            try {
                FrmPurchase dlg = new FrmPurchase(this,"商品采购",true, MainControl.purchaseTabManager.returnNextOderId(BeanAdmin.currentLoginAdmin));
                dlg.setVisible(true);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource() == this.menuItem_PurchaseOrderManage)
        {
            FrmPurchaseOrderManage dlg = new FrmPurchaseOrderManage(this,"订单状态更新",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_CouponManage)
        {
            FrmCouponManage dlg = new FrmCouponManage(this,"优惠信息",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_PromotionManage)
        {
            FrmPromotionManage dlg = new FrmPromotionManage(this,"限时促销",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_DiscountManage)
        {
            FrmDiscountManage dlg = new FrmDiscountManage(this,"满折管理",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_DiscountGoodsManage)
        {
            FrmDiscountGoodsManage dlg = new FrmDiscountGoodsManage(this,"满折商品管理",true);
            dlg.setVisible(true);
        }
}}
