package manager;

import javax.swing.*;
import java.net.BindException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

import itf.IUserManager;
import model.BeanUsers;
import util.BaseException;
import util.DBUtil;
import util.DbException;
import util.BusinessException;


public class UserManager implements IUserManager {
    @Override
    public BeanUsers reg(String userid, String pwd, String pwd2,String name,String sex,String email,String phoneNumber,String city) throws BaseException {
        Connection conn = null;
        if(userid.length()>20 || userid.length()<1)
        {
            throw new BusinessException("账号长度应在1-20个字符之间");
        }
        if ("".equals(pwd) || "".equals(pwd2) || pwd== null || pwd2==null )
        {
            throw new BusinessException("密码不能为空");
        }
        if(!pwd.equals(pwd2))
        {
            throw new BusinessException("两次密码不一致!");
        }
        if(name.length()>10 || name.length()<1)
        {
            throw new BusinessException("名称应在1-10个字符之间");
        }
        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
        if (!(p.matcher(email).matches()))
        {
            throw new BusinessException("邮箱格式错误");
        }
        if(phoneNumber.length()!=11)
        {
            throw new BusinessException("请输入11位格式手机号码");
        }
        for(int i = 0;i<phoneNumber.length();i++)
        {
            if (!Character.isDigit(phoneNumber.charAt(i)))
            {
                throw new BusinessException("手机号码应为纯数字");
            }
        }
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from users where user_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userid);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                throw new BusinessException("用户账号已存在!");
            }
            sql = "insert into users(user_id,user_name,user_sex,user_pwd,user_phoneNumber,user_email,user_city,user_regTime,vip) values (?,?,?,?,?,?,?,?,?) ";
            BeanUsers user = new BeanUsers();
            pst = conn.prepareStatement(sql);
            pst.setString(1, userid);
            pst.setString(2, name);
            pst.setString(3, sex);
            pst.setString(4, pwd);
            pst.setString(5, phoneNumber);
            pst.setString(6,email);
            pst.setString(7,city);
            pst.setTimestamp(8,new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setInt(9,0);
            pst.executeUpdate();
            sql = "select * from users where user_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, userid);
            rs = pst.executeQuery();
            if(rs.next())
            {
                user.setUser_id(rs.getString(1));
                user.setUser_name(rs.getString(2));
                user.setUser_sex(rs.getString(3));
                user.setUser_pwd(rs.getString(4));
                user.setUser_phoneNumber(rs.getString(5));
                user.setUser_email(rs.getString(6));
                user.setUser_city(rs.getString(7));
                user.setUser_regTime(rs.getTimestamp(8));
                user.setVip(rs.getBoolean(9));
                user.setVip_endTime(rs.getTimestamp(10));
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
            user.setUser_id(rs.getString(1));
            user.setUser_name(rs.getString(2));
            user.setUser_sex(rs.getString(3));
            user.setUser_pwd(rs.getString(4));
            user.setUser_phoneNumber(rs.getString(5));
            user.setUser_email(rs.getString(6));
            user.setUser_city(rs.getString(7));
            user.setUser_regTime(rs.getTimestamp(8));
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
