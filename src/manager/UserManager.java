package manager;

import javax.swing.*;
import java.net.BindException;
import java.sql.Connection;
import java.sql.SQLException;

import itf.IUserManager;
import model.BeanUsers;
import util.BaseException;

public class UserManager implements IUserManager {
    @Override
    public BeanUsers reg(String userid, String pwd, String pwd2) throws BaseException {
        return null;
    }

    @Override
    public BeanUsers login(String userid, String pwd) throws BaseException {
        return null;
    }

    @Override
    public void changePwd(BeanUsers user, String oldPwd, String newPwd, String newPwd2) throws BaseException {

    }
}
