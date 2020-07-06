package control;

import itf.IFreshManager;
import itf.IUserManager;
import itf.IAdminManager;
import manager.AdminManager;
import manager.FreshManager;
import manager.GoodsManager;
import manager.UserManager;

public class MainControl {
    public static IUserManager userManager = new UserManager();
    public static IAdminManager adminManager = new AdminManager();
    public static IFreshManager freshManager = new FreshManager();
    public static GoodsManager goodsManager = new GoodsManager();
}
