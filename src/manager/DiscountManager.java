package manager;

import control.MainControl;
import itf.ICouponManager;
import itf.IDiscountManager;
import model.BeanCoupon;
import model.BeanDiscount;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiscountManager implements IDiscountManager {
    @Override
    public List<BeanDiscount> loadAll() throws BaseException {
        Connection conn = null;
        List<BeanDiscount> result=new ArrayList<BeanDiscount>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from discount order by dis_inf_id";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                BeanDiscount bd = new BeanDiscount();
                bd.setDis_inf_id(rs.getInt(1));
                bd.setDis_inf_content(rs.getString(2));
                bd.setLeastGoods_number(rs.getInt(3));
                bd.setDiscount(rs.getDouble(4));
                bd.setDis_beginTime(rs.getTimestamp(5));
                bd.setDis_endTime(rs.getTimestamp(6));
                if (bd.getDis_endTime().getTime()>System.currentTimeMillis() && bd.getDis_beginTime().getTime()<System.currentTimeMillis())
                {
                    result.add(bd);
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
    public void add(String content, int least_goodsNumber, double discount, Date beginTime, Date endTime) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            if (beginTime.getTime() > endTime.getTime())
            {
                throw new BusinessException("结束时间不能早于开始时间");
            }
            if (discount>1)
            {
                throw new BusinessException("折扣不能大于1");
            }
            String sql = "insert into discount(dis_inf_content,leastgoods_number,discount,dis_beginTime,dis_endTime) values (?,?,?,?,?)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,content);
            pst.setInt(2,least_goodsNumber);
            pst.setDouble(3,discount);
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
            String sql = "delete from discount where dis_inf_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            try{
                pst.executeUpdate();
                MainControl.cartManager.resetCart();
            }
            catch (Exception e)
            {
                throw new BusinessException("该满折有绑定信息,无法删除");
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
    public void modify(int id, String content, int least_goodsNumber, double discount, Date beginTime, Date endTime) throws BaseException {
        Connection conn = null;
        if (beginTime.getTime() > endTime.getTime())
        {
            throw new BusinessException("结束时间不能早于开始时间");
        }
        if (discount > 1)
        {
            throw new BusinessException("折扣不能大于1");
        }
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from discount where dis_inf_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BusinessException("优惠券不存在");
            }
            sql = "update discount set dis_inf_content = ?,leastgoods_number = ?, discount = ?, dis_beginTime = ?,dis_endTime = ? where dis_inf_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,content);
            pst.setInt(2,least_goodsNumber);
            pst.setDouble(3,discount);
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
    public List<BeanDiscount> AdminLoadAll() throws BaseException {
        Connection conn = null;
        List<BeanDiscount> result=new ArrayList<BeanDiscount>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from discount order by dis_inf_id";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                BeanDiscount bd = new BeanDiscount();
                bd.setDis_inf_id(rs.getInt(1));
                bd.setDis_inf_content(rs.getString(2));
                bd.setLeastGoods_number(rs.getInt(3));
                bd.setDiscount(rs.getDouble(4));
                bd.setDis_beginTime(rs.getTimestamp(5));
                bd.setDis_endTime(rs.getTimestamp(6));
                result.add(bd);
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
