package manager;

import javax.swing.*;
import java.net.BindException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import itf.IFreshManager;
import itf.IAdminManager;
import model.BeanAdmin;
import model.BeanFresh;
import model.BeanUsers;
import util.BaseException;
import util.DBUtil;
import util.DbException;
import util.BusinessException;

public class FreshManager implements IFreshManager{
    @Override
    public List<BeanFresh> loadAll() throws BaseException {
        Connection conn = null;
        List<BeanFresh> result=new ArrayList<BeanFresh>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from fresh  order by catagory_id";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                BeanFresh bf = new BeanFresh();
                bf.setCategory_id(rs.getInt(1));
                bf.setCategory_name(rs.getString(2));
                bf.setDescription(rs.getString(3));
                result.add(bf);
            }
            return result;
        }
        catch (SQLException e) {
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
    public BeanFresh add(String name, String description) throws BaseException {
        Connection conn = null;
        if ("".equals(name) || name == null)
        {
            throw new BusinessException("类别名称不能为空");
        }
        if (description.length()>50 || description.length()<1)
        {
            throw new BusinessException("描述请控制在1-50个字符之间");
        }
        try{
            conn = DBUtil.getConnection();
            String sql = "select * from fresh where name = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                throw new BusinessException("该类别已存在,请更换");
            }
            sql = "insert into fresh(catagory_name,description) values(?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            pst.setString(2,description);
            pst.executeUpdate();
            BeanFresh bf = new BeanFresh();
            sql = "select catagory_id from fresh where catagory_name = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            pst.executeUpdate();
            int id = rs.getInt(1);
            bf.setCategory_id(id);
            bf.setCategory_name(name);
            bf.setDescription(description);
            return bf;
        }
        catch (SQLException e) {
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
    public void update(int id,String name, String description) throws BaseException {
        Connection conn = null;
        if ("".equals(name) || name == null)
        {
            throw new BusinessException("类别名称不能为空");
        }
        if (description.length()>50 || description.length()<1)
        {
            throw new BusinessException("描述请控制在1-50个字符之间");
        }
        try{
            conn = DBUtil.getConnection();
            String sql = "select * from fresh where name = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                throw new BusinessException("该名称已存在,请更换");
            }
            sql = "update fresh set catagory_name = ?,description =? where catagory_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            pst.setString(2,description);
            pst.setInt(3,id);
            pst.executeUpdate();
        }
        catch (SQLException e) {
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
    public void delete(int id) throws BaseException {

    }
}
