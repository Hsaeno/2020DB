package manager;


import control.MainControl;
import itf.ICartManager;
import model.BeanAddress;
import model.BeanCart;
import model.BeanGoods;
import model.BeanUsers;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
                bc.setUser_id(rs.getString(2));
                bc.setGoodsName(rs.getString(3));
                bc.setGoods_number(rs.getInt(4));
                bc.setGoods_price(rs.getDouble(5));
                bc.setGoods_vip_price(rs.getDouble(6));
                bc.setGoods_promotion_price(rs.getDouble(7));
                bc.setDiscount(rs.getDouble(8));
                bc.setDis_inf_id(rs.getInt(11));
                String sql2 = "select goods_id from goods where goods_name = ?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setString(1,bc.getGoodsName());
                java.sql.ResultSet rs2 = pst2.executeQuery();
                rs2.next();
                bc.setGoods_id(rs2.getInt(1));
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
    public List<BeanCart> loadAll() throws BaseException {
        Connection conn = null;
        List<BeanCart> result=new ArrayList<BeanCart>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from tempcart order by cartNumber ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                BeanCart bc = new BeanCart();
                bc.setCartNumber(rs.getInt(1));
                bc.setUser_id(rs.getString(2));
                bc.setGoodsName(rs.getString(3));
                bc.setGoods_number(rs.getInt(4));
                bc.setGoods_price(rs.getDouble(5));
                bc.setGoods_vip_price(rs.getDouble(6));
                bc.setGoods_promotion_price(rs.getDouble(7));
                bc.setDiscount(rs.getDouble(8));
                bc.setDis_inf_id(rs.getInt(11));
                String sql2 = "select goods_id from goods where goods_name = ?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setString(1,bc.getGoodsName());
                java.sql.ResultSet rs2 = pst2.executeQuery();
                rs2.next();
                bc.setGoods_id(rs2.getInt(1));
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
            double goods_promotion_price = bg.getPromotionPrice();
            //防止多次选择造成库存异常
            String sql = "select * from tempcart where user_id = ? and goods_name = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,user_id);
            pst.setString(2,goods_name);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                throw new BusinessException("该商品已在购物车中,直接修改数量即可");
            }
            sql = "insert into tempcart(goods_name,goods_number,user_id,goods_price,goods_vip_price,goods_promotion_price,goods_discount,least_number,if_discount,dis_inf_id,promotion_id) values (?,?,?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1,goods_name);
            pst.setInt(2,goods_number);
            pst.setString(3,user_id);
            pst.setDouble(4,goods_price);
            pst.setDouble(5,goods_vip_price);
            pst.setDouble(6,goods_promotion_price);
            if (goods_number >= bg.getDiscount_least_number())
            {
                pst.setDouble(7,bg.getDiscount());
                pst.setNull(10,Types.INTEGER);
            }
            else
            { pst.setDouble(7,1.0);
            pst.setInt(10,bg.getDiscountId());
            }
            pst.setInt(8,bg.getDiscount_least_number());
            pst.setDouble(9,bg.getDiscount());
            pst.setInt(11,bg.getPromotionId());
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
    public void deleteAll() throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from tempcart";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
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

            String sql = "select goods_name,least_number,if_discount from tempcart where  cartNumber = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            java.sql.ResultSet rs = pst.executeQuery();
            rs.next();
            sql = "update tempcart set goods_number = ?,goods_discount=? where cartNumber = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,goods_number);
            if (rs.getInt(2) <= goods_number)
                pst.setDouble(2,rs.getDouble(3));
            else
                pst.setDouble(2,1.0);
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
    public double CalculateOrgPrice() throws BaseException {
        Connection conn = null;
        try{
            conn = DBUtil.getConnection();
            String sql = "select goods_price,goods_number from tempcart where user_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,BeanUsers.currentLoginUser.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            double sum = 0;
            while (rs.next())
            {
                sum += rs.getDouble(1) * rs.getInt(2);
            }
            return sum;
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
    public double CalculateSetPrice(int coupon_id,double org_price) throws BaseException {
        Connection conn = null;
        try{
            conn = DBUtil.getConnection();
            boolean vip = BeanUsers.currentLoginUser.getVip();
            double sum = 0;
            double least_money;
            double sub_money = 0;
            if (coupon_id != -1)
            {
                String  sql2 = "select least_money,sub_money from coupon where coupon_id = ?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,coupon_id);
                java.sql.ResultSet rs2 = pst2.executeQuery();
                rs2.next();
                least_money = rs2.getDouble(1);
                sub_money = rs2.getDouble(2);
                if (least_money > org_price)
                {
                    throw new BusinessException("您未达到该优惠券的最低金额");
                }
            }
            String sql = "select goods_number,goods_price,goods_vip_price,goods_promotion_price,goods_discount,promotion_id from tempcart where user_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,BeanUsers.currentLoginUser.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                int goods_number = rs.getInt(1);
                double goods_price = rs.getDouble(2);
                double goods_vip_price =rs.getDouble(3);
                double goods_promotion_price = rs.getDouble(4);
                double goods_discount = rs.getDouble(5);
                int promotion_id = rs.getInt(6);
                int promotion_number = 0;
                if (promotion_id != 0 )
                {
                    String sql2 = "select promotion_number from promotion where promotion_id=?";
                    java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                    pst2.setInt(1,promotion_id);
                    java.sql.ResultSet rs2 = pst2.executeQuery();
                    rs2.next();
                    if (rs2.getInt(1) > goods_number)
                        promotion_number = goods_number;
                    else
                        promotion_number = rs2.getInt(1);
                }
                if (goods_promotion_price < goods_price) {
                    if (!vip)
                    sum += promotion_number * goods_promotion_price * goods_discount + (goods_number - promotion_number)*goods_price*goods_discount;
                    if (vip)
                        sum += promotion_number * goods_promotion_price * goods_discount + (goods_number - promotion_number)*goods_vip_price * goods_discount;
                }
                else{
                    if (vip)
                        sum += goods_vip_price * goods_number * goods_discount;
                    else
                        sum += goods_price * goods_number * goods_discount ;
                }
            }
            if (sum - sub_money <0)
                return 0;
            return sum-sub_money;
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
    public int MakeOrder(BeanAddress ba, int coupon_id, double origin_price, double settle_price, Date requireTime) throws BaseException {
        Connection conn = null;
        try{
            conn = DBUtil.getConnection();
            Calendar c = Calendar.getInstance();
            Date d = new Date(System.currentTimeMillis());
            c.setTime(d);
            c.add(Calendar.HOUR,3);
            java.util.Date date = c.getTime();
            if (requireTime.getTime()<date.getTime())
                throw new BusinessException("要求时间不得早于3小时后");
            String sqlPre = "select goods_name,goods_number,goods_price,goods_vip_price,goods_promotion_price,dis_inf_id,least_number,if_discount,promotion_id from tempcart where user_id=?";
            java.sql.PreparedStatement pstPre = conn.prepareStatement(sqlPre);
            pstPre.setString(1,BeanUsers.currentLoginUser.getUser_id());
            java.sql.ResultSet rsPre = pstPre.executeQuery();

            while (rsPre.next())
            {
                String goods_name;
                int need_number;
                goods_name = rsPre.getString(1);
                need_number = rsPre.getInt(2);

                int promotion_id = rsPre.getInt(9);
                int goods_number = rsPre.getInt(2);
                String sql2 = "select promotion_number from promotion where promotion_id=?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,promotion_id);
                java.sql.ResultSet rs3 = pst2.executeQuery();
                rs3.next();
                if (rs3.getInt(1) > goods_number)
                {
                    String sqlUpdate = "update promotion set promotion_number = promotion_number - ? where promotion_id = ?";
                    java.sql.PreparedStatement pstUpdate = conn.prepareStatement(sqlUpdate);
                    pstUpdate.setInt(1,goods_number);
                    pstUpdate.setInt(2,promotion_id);
                    pstUpdate.executeUpdate();
                }
                else
                {
                    String sqlUpdate = "update promotion set promotion_number = promotion_number - ? where promotion_id = ?";
                    java.sql.PreparedStatement pstUpdate = conn.prepareStatement(sqlUpdate);
                    pstUpdate.setInt(1,rs3.getInt(1));
                    pstUpdate.setInt(2,promotion_id);
                    pstUpdate.executeUpdate();
                }
                String sqlPre2 = "select goods_number from goods where goods_name = ?";
                java.sql.PreparedStatement pstPre2 = conn.prepareStatement(sqlPre2);
                pstPre2.setString(1,goods_name);
                java.sql.ResultSet rs2 = pstPre2.executeQuery();
                if(!rs2.next()){
                    throw new BusinessException(goods_name+"已被下架");
                }
                int remain = rs2.getInt(1);
                if (need_number>remain)
                {
                    throw new BusinessException(goods_name+"库存不足,"+"当前库存为"+remain);
                }
            }
            if (coupon_id != -1)
                {
                    String sql = "insert into orders(address_id,user_id,coupon_id,origin_price,settle_price,order_status,require_time,address_content) values(?,?,?,?,?,'下单',?,?)";
                    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setInt(1,ba.getAddress_id());
                    pst.setString(2,BeanUsers.currentLoginUser.getUser_id());
                    pst.setInt(3,coupon_id);
                    pst.setDouble(4,origin_price);
                    pst.setDouble(5,settle_price);
                    pst.setTimestamp(6,new java.sql.Timestamp(requireTime.getTime()));
                    pst.setString(7,ba.getProvince()+'-'+ba.getCity()+'-'+'-'+ba.getRegion()+'-'+ba.getDetail_address()+'-'+ba.getContact_person()+'-'+ba.getContact_phoneNumber());
                    pst.executeUpdate();
                }
            else{
                String sql = "insert into orders(address_id,user_id,coupon_id,origin_price,settle_price,order_status,require_time,address_content) values(?,?,null,?,?,'下单',?,?)";
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1,ba.getAddress_id());
                pst.setString(2,BeanUsers.currentLoginUser.getUser_id());
                pst.setDouble(3,origin_price);
                pst.setDouble(4,settle_price);
                pst.setTimestamp(5,new java.sql.Timestamp(requireTime.getTime()));
                pst.setString(6,ba.getProvince()+'-'+ba.getCity()+'-'+'-'+ba.getRegion()+'-'+ba.getDetail_address()+'-'+ba.getContact_person()+'-'+ba.getContact_phoneNumber());
                pst.executeUpdate();
            }
            String sql = "select max(order_id) from orders where user_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,BeanUsers.currentLoginUser.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getInt(1);
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
    public void MakeOrderDetail(int order_id) throws BaseException {
        Connection conn = null;
        try{
            conn = DBUtil.getConnection();
            List<BeanCart> lstCart = MainControl.cartManager.loadAll(BeanUsers.currentLoginUser.getUser_id());

            for(int i = 0;i<lstCart.size();i++)
            {
                String sql;
                if (lstCart.get(i).getDis_inf_id() != 0)
                {
                    sql = "insert into order_detail(order_id,goods_id,dis_inf_id,goods_number,price,discount) values(?,?,?,?,?,?)";
                    java.sql.PreparedStatement pst =conn.prepareStatement(sql);
                    pst.setInt(1,order_id);
                    pst.setInt(2,lstCart.get(i).getGoods_id());
                    pst.setInt(3,lstCart.get(i).getDis_inf_id());
                    pst.setInt(4,lstCart.get(i).getGoods_number());
                    pst.setDouble(5,lstCart.get(i).getGoods_price());
                    pst.setDouble(6,lstCart.get(i).getDiscount());
                    pst.executeUpdate();
                    String sql2 = "update goods set goods_number = goods_number-? where goods_id = ?";
                    java.sql.PreparedStatement pst2 =conn.prepareStatement(sql2);
                    pst2.setInt(1,lstCart.get(i).getGoods_number());
                    pst2.setInt(2,lstCart.get(i).getGoods_id());
                    pst2.executeUpdate();
                }
                else {
                    sql = "insert into order_detail(order_id,goods_id,dis_inf_id,goods_number,price,discount) values(?,?,null,?,?,?)";
                    java.sql.PreparedStatement pst =conn.prepareStatement(sql);
                    pst.setInt(1,order_id);
                    pst.setInt(2,lstCart.get(i).getGoods_id());
                    pst.setInt(3,lstCart.get(i).getGoods_number());
                    pst.setDouble(4,lstCart.get(i).getGoods_price());
                    pst.setDouble(5,lstCart.get(i).getDiscount());
                    pst.executeUpdate();
                    String sql2 = "update goods set goods_number = goods_number-? where goods_id = ?";
                    java.sql.PreparedStatement pst2 =conn.prepareStatement(sql2);
                    pst2.setInt(1,lstCart.get(i).getGoods_number());
                    pst2.setInt(2,lstCart.get(i).getGoods_id());
                    pst2.executeUpdate();


                }
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
    public void resetCart() throws BaseException {
        Connection conn = null;
        List<BeanCart> allCart = null;
        List<BeanGoods> allGoods = null;
        try{
            conn = DBUtil.getConnection();
            allCart = loadAll();
            deleteAll();
            allGoods = MainControl.goodsManager.loadAll();
            for (int i = 0;i<allCart.size();i++)
            {
                for (int j = 0;j<allGoods.size();j++)
                {
                    if (allCart.get(i).getGoodsName().equals(allGoods.get(j).getGoods_name()))
                    {
                        add(allGoods.get(j),allCart.get(i).getGoods_number(),allCart.get(i).getUser_id());
                    }
                }
            }
    }
        catch (Exception e) {
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
