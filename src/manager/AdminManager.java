package manager;

import javax.swing.*;
import java.net.BindException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

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
            String sql = " select * from admin where admin_id = ? ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userid);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BusinessException("管理员不存在");
            }
            BeanAdmin admin = new BeanAdmin();
            admin.setAdmin_id(rs.getString(1));
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

    @Override
    public BeanAdmin reg(String admin_id, String pwd1, String pwd2, String admin_name) throws BaseException {
        Connection conn = null;
        if(admin_id.length()>20 || admin_id.length()<1)
        {
            throw new BusinessException("账号长度应在1-20个字符之间");
        }
        if ("".equals(pwd1) || "".equals(pwd2) || pwd1== null || pwd2==null )
        {
            throw new BusinessException("密码不能为空");
        }
        if(!pwd1.equals(pwd2))
        {
            throw new BusinessException("两次密码不一致!");
        }
        if(admin_name.length()>10 || admin_name.length()<1)
        {
            throw new BusinessException("名称应在1-10个字符之间");
        }
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from admin where admin_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, admin_id);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                throw new BusinessException("管理员账号已存在!");
            }
            sql = "insert into admin(admin_id,admin_name,admin_login_pwd) values (?,?,?) ";
            BeanAdmin admin = new BeanAdmin();
            pst = conn.prepareStatement(sql);
            pst.setString(1,admin_id);
            pst.setString(2,admin_name);
            pst.setString(3,pwd1);
            pst.executeUpdate();
            sql = "select * from users where user_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, admin_id);
            rs = pst.executeQuery();
            if(rs.next())
            {
                admin.setAdmin_id(admin_id);
                admin.setAdmin_name(admin_name);
                admin.setAdmin_loginPwd(pwd1);
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

    @Override
    public void changePwd(BeanAdmin admin, String oldPwd, String newPwd, String newPwd2) throws BaseException {
        Connection conn = null;
        if(!oldPwd.equals(admin.getAdmin_loginPwd()))
        {
            throw new BusinessException("原密码错误");
        }
        if("".equals(newPwd) || "".equals(newPwd2)|| "".equals(oldPwd) || newPwd==null || newPwd2==null || oldPwd==null )
        {
            throw new BusinessException("密码不能为空");
        }
        if (!newPwd.equals(newPwd2))
        {
            throw new BusinessException("两次密码不一致");
        }
        try {
            conn = DBUtil.getConnection();
            String sql = "update admin set admin_login_pwd = ? where admin_id = ? ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,newPwd);
            pst.setString(2,admin.getAdmin_id());
            pst.executeUpdate();
            pst.close();
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
