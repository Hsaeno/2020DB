package manager;

import itf.IMenuRecManager;
import model.BeanMenu;
import model.BeanMenuRecommend;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuRecManager implements IMenuRecManager {
    @Override
    public List<BeanMenuRecommend> loadAll() throws BaseException {
        Connection conn = null;
        List<BeanMenuRecommend> result = new ArrayList<BeanMenuRecommend>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from menu_recommand order by tabid";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanMenuRecommend bmr = new BeanMenuRecommend();
                bmr.setTabid(rs.getInt(1));
                String sql2 = "select goods_name from goods where goods_id = ?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,rs.getInt(2));
                java.sql.ResultSet rs2 = pst2.executeQuery();
                rs2.next();
                bmr.setGoods_name(rs2.getString(1));
                bmr.setMenu_id(rs.getInt(3));
                bmr.setRecommend_description(rs.getString(4));
                result.add(bmr);
            }
            return result;
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
    public void add(String goods_name, int menu_id, String description) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            if ("".equals(description) || description == null) {
                throw new BusinessException("介绍不能为空");
            }
            String sql = "select goods_id from goods where goods_name = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, goods_name);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next()) {
                throw new BusinessException("查询不到该商品");
            }
            int goods_id = rs.getInt(1);
            sql = "select * from menu where menu_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,menu_id);
            rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BusinessException("查询不到该菜谱");
            }
            sql = "insert into menu_recommand(goods_id,menu_id,recommend_description) values (?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, goods_id);
            pst.setInt(2, menu_id);
            pst.setString(3, description);
            pst.executeUpdate();
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
    public void delete(int id) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from menu_recommand where tabid = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            try{
                pst.executeUpdate();
            }
            catch (Exception e)
            {
                throw new BusinessException("该推荐菜谱有绑定信息,无法删除");
            }
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
    public void modify(int id, String goods_name, int menu_id, String description) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            if ("".equals(description) || description == null) {
                throw new BusinessException("介绍不能为空");
            }
            String sql = "select goods_id from goods where goods_name = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, goods_name);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next()) {
                throw new BusinessException("查询不到该商品");
            }
            int goods_id = rs.getInt(1);
            sql = "select * from menu where menu_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,menu_id);
            rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BusinessException("查询不到该菜谱");
            }
            sql = "update menu_recommand set goods_id = ?,menu_id =?,recommend_description =? where tabid = ? ";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, goods_id);
            pst.setInt(2, menu_id);
            pst.setString(3, description);
            pst.setInt(4, id);
            pst.executeUpdate();
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
