package control;

import itf.IFreshManager;
import itf.IUserManager;
import itf.IAdminManager;
import manager.*;

public class MainControl {
    public static IUserManager userManager = new UserManager();
    public static IAdminManager adminManager = new AdminManager();
    public static IFreshManager freshManager = new FreshManager();
    public static GoodsManager goodsManager = new GoodsManager();
    public static PurchaseTabManager purchaseTabManager = new PurchaseTabManager();
    public static CouponManager couponManager = new CouponManager();
    public static PromotionManager promotionManager = new PromotionManager();
}
