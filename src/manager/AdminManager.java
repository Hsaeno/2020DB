package manager;

import javax.swing.*;
import java.net.BindException;
import java.sql.Connection;
import java.sql.SQLException;

import itf.IAdminManager;
import model.BeanAdmin;
import model.BeanUsers;
import util.BaseException;
import util.DBUtil;
import util.DbException;
import util.BusinessException;


public class AdminManager implements IAdminManager {


    @Override
    public BeanAdmin login(String userid, String pwd) throws BaseException {
        Connection conn = null;
        if (userid == null || pwd == null || "".equals(userid) || "".equals(pwd)) {
            throw new BusinessException("账号或密码不能为空");
        }
        try {
            conn = DBUtil.getConnection();
            String sql = " select * from users where admin_id = ? ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userid);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BusinessException("管理员不存在");
            }
            BeanAdmin admin = new BeanAdmin();
            admin.setAdmin_id(rs.getInt(1));
            admin.setAdmin_name(rs.getString(2));
            admin.setAdmin_loginPwd(rs.getString(3));
            if (!admin.getAdmin_loginPwd().equals(pwd))
            {
                throw new BusinessException("密码错误");
            }
            rs.close();
            pst.close();
            return admin;
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

}
