package manager;


import itf.ICartManager;
import model.BeanAddress;
import model.BeanCart;
import model.BeanGoods;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartManager implements ICartManager {
    @Override
    public List<BeanCart> loadAll(String  user_id) throws BaseException {
        Connection conn = null;
        List<BeanCart> result=new ArrayList<BeanCart>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from tempcart where user_id = ? order by cartNumber ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,user_id);
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                BeanCart bc = new BeanCart();
                bc.setCartNumber(rs.getInt(1));
                bc.setGoodsName(rs.getString(3));
                bc.setGoods_number(rs.getInt(4));
                bc.setGoods_price(rs.getDouble(5));
                bc.setGoods_vip_price(rs.getDouble(6));
                result.add(bc);
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
    public void add(BeanGoods bg,int goods_number,String user_id) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String goods_name = bg.getGoods_name();
            double goods_price =bg.getGoods_price();
            double goods_vip_price = bg.getVip_price();
            String sql = "select goods_number from goods where goods_name = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,goods_name);
            java.sql.ResultSet rs = pst.executeQuery();
            rs.next();
            int remain = rs.getInt(1);
            if (goods_number>remain)
            {
                throw new BusinessException("该商品库存不足,当前库存为"+remain);
            }
            //防止多次选择造成库存异常
            sql = "select * from tempcart where user_id = ? and goods_name = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,user_id);
            pst.setString(2,goods_name);
            rs = pst.executeQuery();
            if (rs.next())
            {
                throw new BusinessException("该商品已在购物车中,直接修改数量即可");
            }
            sql = "insert into tempcart(goods_name,goods_number,user_id,goods_price,goods_vip_price) values (?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1,goods_name);
            pst.setInt(2,goods_number);
            pst.setString(3,user_id);
            pst.setDouble(4,goods_price);
            pst.setDouble(5,goods_vip_price);
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
            String sql = "delete from tempcart where cartnumber = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
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
    public void deleteAll(String user_id) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from tempcart where user_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,user_id);
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
    public void modify(int id, int goods_number) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select goods_number from tempcart where  cartNumber = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            java.sql.ResultSet rs = pst.executeQuery();
            rs.next();
            int remain = rs.getInt(1);
            if (goods_number>remain)
            {
                throw new BusinessException("该商品库存不足,当前库存为"+remain);
            }
            sql = "update tempcart set goods_number = ? where cartNumber = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,goods_number);
            pst.setInt(2,id);
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
