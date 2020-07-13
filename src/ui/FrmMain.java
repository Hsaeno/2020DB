package ui;
import control.MainControl;
import model.*;
import util.BaseException;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class FrmMain extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;

    private JButton btnMakeOrder = new JButton("下单");
    private JButton btnAdd = new JButton("添加");
    private JButton btnModify = new JButton("修改");
    private JButton btnDelete = new JButton("删除");
    private JButton btnCancel = new JButton("清空");
    private JButton btnSearch = new JButton("查询");
    private JButton btnRefresh= new JButton("刷新");
    private JButton btnLookComment = new JButton("查看评论");

    private JTextField edtSearch  = new JTextField(15);

    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_PersonalManage=new JMenu("个人信息管理");
    private JMenu menu_AdminManage=new JMenu("管理员信息管理");
    private JMenu menu_UserManage=new JMenu("用户信息管理");
    private JMenu menu_VipManage=new JMenu("超级会员");
    private JMenu menu_GoodsManage = new JMenu("关于商品");
    private JMenu menu_couponManage = new JMenu("优惠信息相关");
    private JMenu menu_purchaseManage = new JMenu("采购相关");
    private JMenu menu_MenuManage = new JMenu("菜谱相关");
    private JMenu menu_OrderManage = new JMenu("订单管理");
    private JMenu menu_couponShow = new JMenu("优惠信息");
    private JMenu menu_RecommendShow = new JMenu("商品推荐");
    private JMenu menu_PurchaseHistoryManage = new JMenu("订单查看");

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
    private JMenuItem  menuItem_MenuInfManage=new JMenuItem("菜谱信息");
    private JMenuItem  menuItem_MenuRecManage=new JMenuItem("菜谱推荐");
    private JMenuItem  menuItem_AddressManage=new JMenuItem("地址管理");
    private JMenuItem  menuItem_PurchaseHistoryManage = new JMenuItem("我的订单");

    private JMenuItem  menuItem_RecommendShowByMenu = new JMenuItem("菜谱相关推荐");   //根据菜谱选商品
    private JMenuItem  menuItem_RecommendShowByGoods = new JMenuItem("商品相关推荐");  //当前商品菜谱相关的
    private JMenuItem  menuItem_RecommendShowByPerson = new JMenuItem("个人相关推荐"); //历史订单按购买次数最多排序推荐


    private JMenuItem  menuItem_CouponShow =new JMenuItem("优惠券信息");
    private JMenuItem  menuItem_DiscountShow =new JMenuItem("满折信息");
    private JMenuItem  menuItem_PromotionShow =new JMenuItem("限时促销信息");

    private JMenuItem  menuItem_OrderManage = new JMenuItem("订单配送");

    private FrmLogin dlgLogin = null;
    private JPanel statusBar = new JPanel();

    private Object tblFreshTitle[]= BeanFresh.tableTitles2;
    private Object tblFreshData[][];
    DefaultTableModel tabFreshModel=new DefaultTableModel();
    private JTable dataTableFresh=new JTable(tabFreshModel);


    private Object tblGoodsTitle[]= BeanGoods.tableTitles2;
    private Object tblGoodsData[][];
    DefaultTableModel tabGoodsModel=new DefaultTableModel();
    private JTable dataTableGoods=new JTable(tabGoodsModel);

    private Object tblCartTitle[]= BeanCart.tableTitles;
    private Object tblCartData[][];
    DefaultTableModel tabCartModel=new DefaultTableModel();
    private JTable dataTableCart=new JTable(tabCartModel);

    private JPanel toolBar = new JPanel();

    private BeanFresh curFresh = null;

    List<BeanFresh> allFresh = null;
    List<BeanGoods> freshGoods = null;
    List<BeanCart> allCart = null;

    private void reloadFreshTable(){
        try {
            allFresh= MainControl.freshManager.loadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblFreshData =  new Object[allFresh.size()][BeanFresh.tableTitles2.length];
        for(int i=0;i<allFresh.size();i++){
            for(int j=0;j<BeanFresh.tableTitles2.length;j++)
                tblFreshData[i][j]=allFresh.get(i).getCell2(j);
        }
        tabFreshModel.setDataVector(tblFreshData,tblFreshTitle);
        this.dataTableFresh.validate();
        this.dataTableFresh.repaint();
    }

    private void reloadGoodsTable(int FreshIdx)
    {
        if (FreshIdx < 0 )
            return;
        curFresh = allFresh.get(FreshIdx);
        try{
            freshGoods = MainControl.goodsManager.loadAllRemain(curFresh.getCategory_id());
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblGoodsData = new Object[freshGoods.size()][BeanGoods.tableTitles2.length];
        for (int i=0;i<freshGoods.size();i++)
            for (int j =0;j<BeanGoods.tableTitles2.length;j++)
            {
                tblGoodsData[i][j] = freshGoods.get(i).getCell2(j);
            }
        tabGoodsModel.setDataVector(tblGoodsData,tblGoodsTitle);
        this.dataTableGoods.validate();
        this.dataTableGoods.repaint();
    }

    private void reloadCartTable(){
        try {
            allCart = MainControl.cartManager.loadAll(BeanUsers.currentLoginUser.getUser_id());
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblCartData =  new Object[allCart.size()][BeanCart.tableTitles.length];
        for(int i=0;i<allCart.size();i++){
            for(int j=0;j<BeanCart.tableTitles.length;j++)
                tblCartData[i][j]=allCart.get(i).getCell(j);
        }
        tabCartModel.setDataVector(tblCartData,tblCartTitle);
        this.dataTableCart.validate();
        this.dataTableCart.repaint();
    }

    public FrmMain()
    {
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("生鲜网超");
        dlgLogin = new FrmLogin(this,"登录",true);
        dlgLogin.setVisible(true);
        if (FrmLogin.flag == 1 )
        {
            this.getContentPane().add(new JScrollPane(this.dataTableFresh), BorderLayout.WEST);
            this.menu_PersonalManage.add(this.menuItem_UserModifyPwd);
            this.menu_PersonalManage.add(this.menuItem_AddressManage);
            this.menu_PersonalManage.add(this.menuItem_UserModifyInf);
            this.menuItem_UserModifyPwd.addActionListener(this);
            this.menuItem_UserModifyInf.addActionListener(this);
            this.menuItem_AddressManage.addActionListener(this);
            this.menu_VipManage.add(this.menuItem_VipJoin);
            this.menu_VipManage.add(this.menuItem_VipInf);
            this.menuItem_VipJoin.addActionListener(this);
            this.menuItem_VipInf.addActionListener(this);
            this.menu_PurchaseHistoryManage.add(this.menuItem_PurchaseHistoryManage);
            this.menuItem_PurchaseHistoryManage.addActionListener(this);
            this.menu_couponShow.add(this.menuItem_DiscountShow);
            this.menu_couponShow.add(this.menuItem_CouponShow);
            this.menu_couponShow.add(this.menuItem_PromotionShow);
            this.menuItem_CouponShow.addActionListener(this);
            this.menuItem_DiscountShow.addActionListener(this);
            this.menuItem_PromotionShow.addActionListener(this);
            this.menu_RecommendShow.add(this.menuItem_RecommendShowByMenu);
            this.menu_RecommendShow.add(this.menuItem_RecommendShowByGoods);
            this.menu_RecommendShow.add(this.menuItem_RecommendShowByPerson);
            this.menuItem_RecommendShowByMenu.addActionListener(this);
            this.menuItem_RecommendShowByGoods.addActionListener(this);
            this.menuItem_RecommendShowByPerson.addActionListener(this);
            menubar.add(menu_PersonalManage);
            menubar.add(menu_VipManage);
            menubar.add(menu_PurchaseHistoryManage);
            menubar.add(menu_couponShow);
            menubar.add(menu_RecommendShow);
            this.setJMenuBar(menubar);
            this.dataTableFresh.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e)
                {
                    int i = FrmMain.this.dataTableFresh.getSelectedRow();
                    if (i<0){
                        return;
                    }
                    FrmMain.this.reloadGoodsTable(i);
                }

            });
            this.getContentPane().add(new JScrollPane(this.dataTableGoods), BorderLayout.CENTER);
            this.reloadFreshTable();
            this.setExtendedState(MAXIMIZED_BOTH);
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
            toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
            toolBar.add(btnLookComment);
            toolBar.add(btnAdd);
            toolBar.add(btnModify);
            toolBar.add(btnDelete);
            toolBar.add(btnMakeOrder);
            toolBar.add(btnCancel);
            toolBar.add(edtSearch);
            toolBar.add(btnSearch);
            toolBar.add(btnRefresh);
            this.getContentPane().add(toolBar,BorderLayout.NORTH);
            this.getContentPane().add(statusBar,BorderLayout.SOUTH);
            this.getContentPane().add(new JScrollPane(this.dataTableCart), BorderLayout.EAST);
            this.reloadCartTable();
            this.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    System.exit(0);
                }
            });
            this.setVisible(true);
            this.btnAdd.addActionListener(this);
            this.btnCancel.addActionListener(this);
            this.btnDelete.addActionListener(this);
            this.btnModify.addActionListener(this);
            this.btnMakeOrder.addActionListener(this);
            this.btnSearch.addActionListener(this);
            this.btnLookComment.addActionListener(this);
            this.btnRefresh.addActionListener(this);
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
            this.menu_MenuManage.add(this.menuItem_MenuInfManage);
            this.menu_MenuManage.add(this.menuItem_MenuRecManage);
            this.menu_OrderManage.add(this.menuItem_OrderManage);
            this.menuItem_OrderManage.addActionListener(this);
            this.menuItem_MenuRecManage.addActionListener(this);
            this.menuItem_MenuInfManage.addActionListener(this);
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
            menubar.add(menu_MenuManage);
            menubar.add(menu_OrderManage);
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
            FrmCouponManage dlg = new FrmCouponManage(this,"优惠券",true);
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
        else if (e.getSource() == this.menuItem_MenuInfManage)
        {
            FrmMenuManage dlg = new FrmMenuManage(this,"菜谱管理",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_MenuRecManage)
        {
            FrmMenuRecManage dlg = new FrmMenuRecManage(this,"菜谱推荐管理",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_AddressManage)
        {
            FrmAddressManage dlg = new FrmAddressManage(this,"地址管理",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.btnAdd)
        {
            int i = FrmMain.this.dataTableGoods.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmCartGoodsAdd dlg = new FrmCartGoodsAdd(this,"商品数量填写",true,freshGoods.get(i));
            dlg.setVisible(true);
            this.reloadCartTable();
        }
        else if (e.getSource() == this.btnCancel)
        {
            try {
                MainControl.cartManager.deleteAll(BeanUsers.currentLoginUser.getUser_id());
                this.reloadCartTable();
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if (e.getSource() == this.btnDelete)
        {
            int i = FrmMain.this.dataTableCart.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择购物车内的商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MainControl.cartManager.delete(allCart.get(i).getCartNumber());
                this.reloadCartTable();
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if (e.getSource() == this.btnModify)
        {
            int i = FrmMain.this.dataTableCart.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmCartGoodsModify dlg = new  FrmCartGoodsModify(this,"商品数量修改",true,allCart.get(i));
            dlg.setVisible(true);
            this.reloadCartTable();
        }
        else if (e.getSource() == this.btnMakeOrder)
        {
            List<BeanCoupon> allCoupon = null;
            List<BeanAddress> allAddress = null;
            try {
                allCoupon = MainControl.couponManager.loadAll();
                allAddress = MainControl.addressManager.loadUserAddress(BeanUsers.currentLoginUser);
            } catch (BaseException ex) {
                ex.printStackTrace();
            }
            FrmOrder dlg = null;
            try {
                if (allCart.size()==0)
                {   JOptionPane.showMessageDialog(null, "请先选择商品", "错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dlg = new FrmOrder(this,"下单",true,allAddress,allCoupon);
            } catch (BaseException ex) {
                ex.printStackTrace();
            }
            dlg.setVisible(true);
            this.reloadCartTable();
        }
        else if (e.getSource() == this.menuItem_PurchaseHistoryManage)
        {
            FrmPurchaseHistory dlg = new FrmPurchaseHistory(this,"消费情况",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_OrderManage)
        {
            FrmAdminManageOrder dlg = new FrmAdminManageOrder(this,"订单更新",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_CouponShow)
        {
            FrmCouponShow dlg = new FrmCouponShow(this,"优惠券信息",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_PromotionShow)
        {
            FrmPromotionShow dlg = new FrmPromotionShow(this,"促销信息",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_DiscountShow)
        {
            FrmDiscountShow dlg = new FrmDiscountShow(this,"满折信息",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.btnSearch)
        {
            FrmGoodsSearch dlg = new FrmGoodsSearch(this,"商品查询",true,edtSearch.getText());
            dlg.setVisible(true);
            this.reloadCartTable();
        }
        else if (e.getSource() == this.menuItem_RecommendShowByMenu)
        {
            FrmMenuShow dlg = new FrmMenuShow(this,"菜谱查看",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_RecommendShowByPerson)
        {
            FrmRecByPerson dlg = new FrmRecByPerson(this,"商品推荐",true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.menuItem_RecommendShowByGoods)
        {
            int i = FrmMain.this.dataTableGoods.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmRecByGoods dlg = new FrmRecByGoods(this,"商品推荐",true,freshGoods.get(i).getGoods_id());
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.btnLookComment)
        {
            int i = FrmMain.this.dataTableGoods.getSelectedRow();
            if (i < 0)
            {
                JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmCommentShow dlg = null;
            dlg = new FrmCommentShow(this,"查看商品评论",true,freshGoods.get(i).getGoods_id());
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.btnRefresh)
        {
            reloadCartTable();
        }
}}
