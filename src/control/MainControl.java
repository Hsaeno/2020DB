package control;

import itf.IUserManager;
import itf.IAdminManager;
import manager.AdminManager;
import manager.UserManager;

public class MainControl {
    public static IUserManager userManager = new UserManager();
    public static IAdminManager adminManager = new AdminManager();
}
