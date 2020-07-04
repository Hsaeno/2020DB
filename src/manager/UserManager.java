package manager;

import javax.swing.*;
import java.net.BindException;
import java.sql.Connection;
import java.sql.SQLException;

import itf.IUserManager;
import model.BeanUsers;
import util.BaseException;
import util.DBUtil;
import util.DbException;
import util.BusinessException;


public class UserManager implements IUserManager {
    @Override
    public BeanUsers reg(String userid, String pwd, String pwd2) throws BaseException {
        return null;
    }

    @Override
    public BeanUsers login(String userid, String pwd) throws BaseException {
        Connection conn = null;
        if (userid == null || pwd == null || "".equals(userid) || "".equals(pwd)) {
            throw new BusinessException("账号或密码不能为空");
        }
        try {
            conn = DBUtil.getConnection();
            String sql = " select * from users where user_id = ? ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userid);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BusinessException("用户不存在");
            }
            BeanUsers user = new BeanUsers();
            user.setUser_id(rs.getInt(1));
            user.setUser_name(rs.getString(2));
            user.setUser_sex(rs.getString(3));
            user.setUser_pwd(rs.getString(4));
            user.setUser_phoneNumber(rs.getString(5));
            user.setUser_email(rs.getString(6));
            user.setUser_city(rs.getString(7));
            user.setUser_regTime(rs.getString(8));
            user.setVip(rs.getBoolean(9));
            user.setVip_endTime(rs.getTimestamp(10));
            if (!user.getUser_pwd().equals(pwd))
            {
                throw new BusinessException("密码错误");
            }
            rs.close();
            pst.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

        }
    }

    @Override
    public void changePwd(BeanUsers user, String oldPwd, String newPwd, String newPwd2) throws BaseException {

    }
}
