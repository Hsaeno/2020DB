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
    public static DiscountManager discountManager = new DiscountManager();
    public static DisConnGoodsManager disConnGoodsManager = new DisConnGoodsManager();
    public static MenuManager menuManager = new MenuManager();
    public static MenuRecManager menuRecManager = new MenuRecManager();
    public static AddressManager addressManager = new AddressManager();
    public static CartManager cartManager = new CartManager();
    public static PurchaseHistoryManager purchaseHistoryManager = new PurchaseHistoryManager();
}
