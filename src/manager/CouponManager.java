package manager;


import itf.ICouponManager;
import model.BeanCoupon;
import model.BeanFresh;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CouponManager implements ICouponManager {
    @Override
    public List<BeanCoupon> loadAll() throws BaseException {
        Connection conn = null;
        List<BeanCoupon> result=new ArrayList<BeanCoupon>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from coupon order by coupon_id";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                BeanCoupon bf = new BeanCoupon();
                bf.setCoupon_id(rs.getInt(1));
                bf.setCoupon_content(rs.getString(2));
                bf.setLeast_money(rs.getDouble(3));
                bf.setSub_money(rs.getDouble(4));
                bf.setCp_beginTime(rs.getTimestamp(5));
                bf.setCp_endTime(rs.getTimestamp(6));
                if ((bf.getCp_endTime().getTime()>System.currentTimeMillis() )&& (bf.getCp_beginTime().getTime()<System.currentTimeMillis()))
                {
                    result.add(bf);
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
    public void add(String content, double least_money, double sub_money, Date beginTime, Date endTime) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            if (beginTime.getTime() > endTime.getTime())
            {
                throw new BusinessException("结束时间不能早于开始时间");
            }
            if (sub_money > least_money)
            {
                throw new BusinessException("减免金额不能大于最低金额");
            }
            String sql = "insert into coupon(coupon_content,least_money,sub_money,cp_beginTime,cp_endTime) values (?,?,?,?,?)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,content);
            pst.setDouble(2,least_money);
            pst.setDouble(3,sub_money);
            pst.setTimestamp(4,new java.sql.Timestamp(beginTime.getTime()));
            pst.setTimestamp(5,new java.sql.Timestamp(endTime.getTime()));
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
            String sql = "delete from coupon where coupon_id = ?";
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
    public void modify(int id, String content, double least_money, double sub_money, Date beginTime, Date endTime) throws BaseException {
        Connection conn = null;
        if (beginTime.getTime() > endTime.getTime())
        {
            throw new BusinessException("结束时间不能早于开始时间");
        }
        if (sub_money > least_money)
        {
            throw new BusinessException("减免金额不能大于最低金额");
        }
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from coupon where coupon_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BusinessException("优惠券不存在");
            }
            sql = "update coupon set coupon_content = ?,least_money = ?, sub_money = ?, cp_beginTime = ?,cp_endTime = ? where coupon_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,content);
            pst.setDouble(2,least_money);
            pst.setDouble(3,sub_money);
            pst.setTimestamp(4,new java.sql.Timestamp(beginTime.getTime()));
            pst.setTimestamp(5,new java.sql.Timestamp(endTime.getTime()));
            pst.setInt(6,id);
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
    public BeanCoupon loadByPrice(int price) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from coupon where least<?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,price);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next())
            {
                return null;
            }
            BeanCoupon bc = new BeanCoupon();
            bc.setCoupon_id(rs.getInt(1));
            bc.setCoupon_content(rs.getString(2));
            bc.setLeast_money(rs.getDouble(3));
            bc.setSub_money(rs.getDouble(4));
            bc.setCp_beginTime(rs.getTimestamp(5));
            bc.setCp_endTime(rs.getTimestamp(6));
            return bc;
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
    public List<BeanCoupon> AdminLoadAll() throws BaseException {
        Connection conn = null;
        List<BeanCoupon> result=new ArrayList<BeanCoupon>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from coupon order by coupon_id";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                BeanCoupon bf = new BeanCoupon();
                bf.setCoupon_id(rs.getInt(1));
                bf.setCoupon_content(rs.getString(2));
                bf.setLeast_money(rs.getDouble(3));
                bf.setSub_money(rs.getDouble(4));
                bf.setCp_beginTime(rs.getTimestamp(5));
                bf.setCp_endTime(rs.getTimestamp(6));
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
}
