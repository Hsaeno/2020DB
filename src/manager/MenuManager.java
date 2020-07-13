package manager;

import itf.IMenuManager;
import model.BeanMenu;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuManager implements IMenuManager {
    @Override
    public List<BeanMenu> loadAll() throws BaseException {
        Connection conn = null;
        List<BeanMenu> result=new ArrayList<BeanMenu>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from menu order by menu_id";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
              BeanMenu bm = new BeanMenu();
              bm.setMenu_id(rs.getInt(1));
              bm.setMenu_name(rs.getString(2));
              bm.setMaterial(rs.getString(3));
              bm.setStep(rs.getString(4));
              result.add(bm);
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
    public void add(String name, String material, String step) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            if ("".equals(name) || "".equals(material) || "".equals(step) || name==null || material==null || step==null)
                {
                    throw new BusinessException("相关信息不能为空");
                }

            String sql = "insert into menu(menu_name,material,step)  values (?,?,?)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            pst.setString(2,material);
            pst.setString(3,step);
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
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from menu where menu_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            try{
                pst.executeUpdate();
            }
            catch (Exception e)
            {
                throw new BusinessException("该菜谱有绑定信息,无法删除");
            }
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
    public void modify(int id, String name, String material, String step) throws BaseException {
        Connection conn = null;
        if ("".equals(name) || "".equals(material) || "".equals(step) || name==null || material==null || step==null)
        {
            throw new BusinessException("相关信息不能为空");
        }
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from menu where menu_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BusinessException("菜谱不存在");
            }
            sql = "update menu set menu_name = ?,material = ?,step = ? where menu_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            pst.setString(2,material);
            pst.setString(3,step);
            pst.setInt(4,id);
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
}
