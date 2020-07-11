package manager;

import itf.IPromotionManager;
import itf.IPurchaseHistoryManager;
import model.BeanGoodsComment;
import model.BeanOrder;
import model.BeanOrderDetail;
import model.BeanUsers;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseHistoryManager implements IPurchaseHistoryManager {
    @Override
    public List<BeanOrder> loadAllOrder() throws BaseException {
        Connection conn = null;
        List<BeanOrder> result = new ArrayList<BeanOrder>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from orders where user_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,BeanUsers.currentLoginUser.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                BeanOrder bo  = new BeanOrder();
                bo.setOrder_id(rs.getInt(1));
                String sql2 = "select * from address where address_id = ?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,rs.getInt(2));
                java.sql.ResultSet rs2 = pst2.executeQuery();
                rs2.next();
                bo.setAddress_content(rs2.getString(3)+'-'+rs2.getString(4)+'-'+rs2.getString(5)+'-'+rs2.getString(6)+'-'+rs2.getString(7));
                if (rs.getInt(4) != 0)
                {
                    sql2 = "select coupon_content from coupon where coupon_id = ?";
                    pst2 = conn.prepareStatement(sql2);
                    pst2.setInt(1,rs.getInt(4));
                    rs2 = pst2.executeQuery();
                    rs2.next();
                    bo.setCoupon_content(rs2.getString(1));
                }
                else
                {
                    bo.setCoupon_content("无");
                }
                bo.setOrigin_price(rs.getDouble(5));
                bo.setSettle_price(rs.getDouble(6));
                bo.setOrder_status(rs.getString(8));
                result.add(bo);
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
    public List<BeanOrderDetail> loadAllOrderDetail(int order_id) throws BaseException {
        Connection conn = null;
        List<BeanOrderDetail> result = new ArrayList<BeanOrderDetail>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from order_detail where order_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,order_id);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                String sql2 = "select goods_name from goods where goods_id = ?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,rs.getInt(3));
                java.sql.ResultSet rs2 =  pst2.executeQuery();
                rs2.next();
                BeanOrderDetail bod = new BeanOrderDetail();
                bod.setGoods_name(rs2.getString(1));
                bod.setGoods_id(rs.getInt(3));
                if (rs.getInt(4)!= 0)
                {
                    String sql3 = "select dis_inf_content from discount where dis_inf_id = ?";
                    java.sql.PreparedStatement pst3 = conn.prepareStatement(sql3);
                    pst3.setInt(1,rs.getInt(4));
                    java.sql.ResultSet rs3 = pst3.executeQuery();
                    rs3.next();
                    String dis_content = rs3.getString(1);
                    bod.setDis_content(dis_content);
                }
                else {
                    bod.setDis_content("无");
                }
                bod.setNumber(rs.getInt(5));
                bod.setPrice(rs.getDouble(6));
                bod.setDiscount(rs.getDouble(7));
                result.add(bod);
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
    public void addComment(int goods_id, String comment,int star) throws BaseException{
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods_comment where goods_id = ? and user_id= ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,goods_id);
            pst.setString(2,BeanUsers.currentLoginUser.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next())
                throw new BusinessException("您已经为该商品增加过评论.您现在可以查看或修改该商品的评论");
            sql = "insert into goods_comment(goods_id,user_id,comment_content,comment_date,comment_star) values (?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,goods_id);
            pst.setString(2,BeanUsers.currentLoginUser.getUser_id());
            pst.setString(3,comment);
            pst.setTimestamp(4,new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setInt(5,star);
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
    public List<BeanGoodsComment> loadAll(int goods_id) throws BaseException {
        Connection conn = null;
        List<BeanGoodsComment> result = new ArrayList<BeanGoodsComment>();
        try{
            conn = DBUtil.getConnection();
            String sql = "select user_id,comment_content,comment_date,comment_star from goods_comment where goods_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,goods_id);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                BeanGoodsComment bgc = new BeanGoodsComment();
                bgc.setUser_id(rs.getString(1));
                bgc.setComment_content(rs.getString(2));
                bgc.setComment_date(rs.getTimestamp(3));
                bgc.setComment_star(rs.getInt(4));
                result.add(bgc);
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
    public BeanGoodsComment Search(int goods_id, String user_id) throws BaseException {
        Connection conn = null;
        BeanGoodsComment bgc = new BeanGoodsComment();
        try{
            conn = DBUtil.getConnection();
            String sql = "select user_id,comment_content,comment_date,comment_star,goods_id from goods_comment where goods_id = ? and user_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,goods_id);
            pst.setString(2,user_id);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                bgc.setUser_id(rs.getString(1));
                bgc.setComment_content(rs.getString(2));
                bgc.setComment_date(rs.getTimestamp(3));
                bgc.setComment_star(rs.getInt(4));
                bgc.setGoods_id(rs.getInt(5));
            }
            return bgc;
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
    public void modifyComment(int goods_id, String comment, int star) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update  goods_comment set comment_content = ?,comment_date = ?,comment_star = ? where goods_id=? and user_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,comment);
            pst.setTimestamp(2,new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setInt(3,star);
            pst.setInt(4,goods_id);
            pst.setString(5,BeanUsers.currentLoginUser.getUser_id());
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
    public void MakeSure(int orders) throws BaseException {
        Connection conn = null;
        try{
            conn = DBUtil.getConnection();
            String sql = "select order_status from orders where order_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,orders);
            java.sql.ResultSet rs = pst.executeQuery();
            rs.next();
            if (!rs.getString(1).equals("配送"))
                throw new BusinessException("订单状态不支持确认收货");
            sql = "update orders set order_status = '送达' where order_id = ? ";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,orders);
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
    public void Back(int orders) throws BaseException {
        Connection conn = null;
        try{
            conn = DBUtil.getConnection();
            String sql = "select order_status from orders where order_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,orders);
            java.sql.ResultSet rs = pst.executeQuery();
            rs.next();
            if (rs.getString(1).equals("退货"))
                throw new BusinessException("订单状态不支持退货");
            sql = "update orders set order_status = '退货' where order_id = ? ";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,orders);
            pst.executeUpdate();
            sql = "select goods_id,goods_number from order_detail where order_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,orders);
            rs = pst.executeQuery();
            while (rs.next())
            {
                int goods_id = rs.getInt(1);
                int goods_number = rs.getInt(2);
                String sql2 = "update goods set goods_number = goods_number + ? where goods_id = ?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,goods_number);
                pst2.setInt(2,goods_id);
                pst2.executeUpdate();
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
    public void AdminUpdateOrder(int order_id) throws BaseException {
        Connection conn = null;
        try{
            conn = DBUtil.getConnection();
            String sql = "select order_status from orders where order_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,order_id);
            java.sql.ResultSet rs = pst.executeQuery();
            rs.next();
            if (!rs.getString(1).equals("下单"))
                throw new BusinessException("订单状态不支持确认收货");
            sql = "update orders set order_status = '配送' where order_id = ? ";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,order_id);
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
    public List<BeanOrder> AdminLoadAll() throws BaseException {
        Connection conn = null;
        List<BeanOrder> result = new ArrayList<BeanOrder>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from orders where order_status = '下单'";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                BeanOrder bo  = new BeanOrder();
                bo.setOrder_id(rs.getInt(1));
                String sql2 = "select * from address where address_id = ?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,rs.getInt(2));
                java.sql.ResultSet rs2 = pst2.executeQuery();
                rs2.next();
                bo.setAddress_content(rs2.getString(3)+'-'+rs2.getString(4)+'-'+rs2.getString(5)+'-'+rs2.getString(6)+'-'+rs2.getString(7));
                if (rs.getInt(4) != 0)
                {
                    sql2 = "select coupon_content from coupon where coupon_id = ?";
                    pst2 = conn.prepareStatement(sql2);
                    pst2.setInt(1,rs.getInt(4));
                    rs2 = pst2.executeQuery();
                    rs2.next();
                    bo.setCoupon_content(rs2.getString(1));
                }
                else
                {
                    bo.setCoupon_content("无");
                }
                bo.setOrigin_price(rs.getDouble(5));
                bo.setSettle_price(rs.getDouble(6));
                bo.setOrder_status(rs.getString(8));
                result.add(bo);
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
