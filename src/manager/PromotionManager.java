package manager;

import control.MainControl;
import itf.IPromotionManager;
import model.BeanCoupon;
import model.BeanPromotion;
import org.hibernate.boot.spi.InFlightMetadataCollector;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PromotionManager implements IPromotionManager {
    @Override
    public List<BeanPromotion> loadAll() throws BaseException {
        Connection conn = null;
        List<BeanPromotion> result=new ArrayList<BeanPromotion>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from promotion order by promotion_id";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                BeanPromotion bp = new BeanPromotion();
                int goods_id = rs.getInt(2);
                String sql2 ="select goods_name from goods where goods_id = ?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,goods_id);
                java.sql.ResultSet rs2 = pst2.executeQuery();
                rs2.next();
                String name = rs2.getString(1);
                bp.setPromotion_id(rs.getInt(1));
                bp.setGoods_id(rs.getInt(2));
                bp.setGoods_name(name);
                bp.setPromotion_price(rs.getDouble(3));
                bp.setPromotion_number(rs.getInt(4));
                bp.setPromotion_beginTime(rs.getTimestamp(5));
                bp.setPromotion_endTime(rs.getTimestamp(6));
                if ((bp.getPromotion_endTime().getTime()>System.currentTimeMillis() )&& (bp.getPromotion_beginTime().getTime()<System.currentTimeMillis()))
                {
                    result.add(bp);
                }
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
    public void add(String goods_name, double promotion_price, int number, Date beginTime, Date endTime) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            if (beginTime.getTime() > endTime.getTime())
            {
                throw new BusinessException("结束时间不能早于开始时间");
            }
            String sql = "select goods_id,goods_price,vip_price,goods_number from goods where goods_name = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,goods_name);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BusinessException("商品不存在");
            }
            int goods_id = rs.getInt(1);
            double goods_price = rs.getDouble(2);
            double vip_price = rs.getDouble(3);
            int goods_number = rs.getInt(4);
            if (promotion_price > goods_price || promotion_price > vip_price)
            {
                throw new BusinessException("促销价格不得高于会员价和普通价格");
            }
            if (number > goods_number)
            {
                throw new BusinessException("促销数量不得大于商品库存");
            }
            sql = "select promotion_endTime,promotion_beginTime from promotion where goods_id = ?";
            pst =conn.prepareStatement(sql);
            pst.setInt(1,goods_id);
            rs = pst.executeQuery();
            while (rs.next())
            {
                if (rs.getTimestamp(1).getTime() > beginTime.getTime()  && rs.getTimestamp(2).getTime() < endTime.getTime())
                {
                    throw new BusinessException("同一件商品在相同时间内不得进行两次促销");
                }
            }
            sql = "insert into promotion(goods_id,promotion_price,promotion_number,promotion_beginTime,promotion_endTime) values (?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,goods_id);
            pst.setDouble(2,promotion_price);
            pst.setInt(3,number);
            pst.setTimestamp(4,new java.sql.Timestamp(beginTime.getTime()));
            pst.setTimestamp(5,new java.sql.Timestamp(endTime.getTime()));
            pst.executeUpdate();
            MainControl.cartManager.resetCart();
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
            String sql = "delete from promotion where promotion_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            try{
                pst.executeUpdate();
                MainControl.cartManager.resetCart();
            }
            catch (Exception e)
            {
                throw new BusinessException("该促销有绑定信息,不支持更改");
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
    public void modify(int id, String goods_name, double promotion_price, int number, Date beginTime, Date endTime) throws BaseException {
        Connection conn = null;
        if (beginTime.getTime() > endTime.getTime())
        {
            throw new BusinessException("结束时间不能早于开始时间");
        }
        try {
            conn = DBUtil.getConnection();
            String sql = "select goods_id,goods_price,vip_price,goods_number from goods where goods_name = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,goods_name);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BusinessException("商品不存在");
            }
            int goods_id = rs.getInt(1);
            double goods_price = rs.getDouble(2);
            double vip_price = rs.getDouble(3);
            int goods_number = rs.getInt(4);
            if (promotion_price > goods_price || promotion_price > vip_price)
            {
                throw new BusinessException("促销价格不得高于会员价和普通价格");
            }
            if (number > goods_number)
            {
                throw new BusinessException("促销数量不得大于商品库存");
            }
            sql = "select promotion_endTime,promotion_beginTime,promotion_id from promotion where goods_id = ?";
            pst =conn.prepareStatement(sql);
            pst.setInt(1,goods_id);
            rs = pst.executeQuery();
            while (rs.next())
            {
                if ((rs.getTimestamp(1).getTime() > beginTime.getTime() && rs.getTimestamp(2).getTime() < endTime.getTime()) && rs.getInt(3) != id)
                {
                    throw new BusinessException("同一件商品在相同时间内不得进行两次促销");
                }
            }
            sql = "update promotion set goods_id = ?,promotion_price = ?, promotion_number = ?, promotion_beginTime = ?,promotion_endTime = ? where promotion_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,goods_id);
            pst.setDouble(2,promotion_price);
            pst.setInt(3,number);
            pst.setTimestamp(4,new java.sql.Timestamp(beginTime.getTime()));
            pst.setTimestamp(5,new java.sql.Timestamp(endTime.getTime()));
            pst.setInt(6,id);
            pst.executeUpdate();
            MainControl.cartManager.resetCart();
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
    public List<BeanPromotion> AdminLoadAll() throws BaseException {
        Connection conn = null;
        List<BeanPromotion> result=new ArrayList<BeanPromotion>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from promotion order by promotion_id";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                BeanPromotion bp = new BeanPromotion();
                int goods_id = rs.getInt(2);
                String sql2 ="select goods_name from goods where goods_id = ?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,goods_id);
                java.sql.ResultSet rs2 = pst2.executeQuery();
                rs2.next();
                String name = rs2.getString(1);
                bp.setPromotion_id(rs.getInt(1));
                bp.setGoods_id(rs.getInt(2));
                bp.setGoods_name(name);
                bp.setPromotion_price(rs.getDouble(3));
                bp.setPromotion_number(rs.getInt(4));
                bp.setPromotion_beginTime(rs.getTimestamp(5));
                bp.setPromotion_endTime(rs.getTimestamp(6));
                result.add(bp);
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
}
