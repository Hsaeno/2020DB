package manager;

import itf.IGoodsManager;
import model.BeanFresh;
import model.BeanGoods;
import model.BeanUsers;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GoodsManager implements IGoodsManager {
    @Override
    public List<BeanGoods> loadAll(int catagory_id) throws BaseException {
        Connection conn = null;
        List<BeanGoods> result=new ArrayList<BeanGoods>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods  where catagory_id = ? order by goods_id ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,catagory_id);
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                BeanGoods bg = new BeanGoods();
                bg.setGoods_id(rs.getInt(1));
                bg.setCategory_id(rs.getInt(2));
                bg.setGoods_name(rs.getString(3));
                bg.setGoods_price(rs.getDouble(4));
                bg.setVip_price(rs.getDouble(5));
                bg.setGoods_number(rs.getInt(6));
                bg.setSpec(rs.getDouble(7));
                bg.setDetail(rs.getString(8));

                String sql2 = "select sum(goods_number) from order_detail where goods_id=?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,bg.getGoods_id());
                java.sql.ResultSet rs2 = pst2.executeQuery();
                rs2.next();
                bg.setGoods_sellNumber(rs2.getInt(1));

                double SumStar = 5;
                String sqlStar = "select sum(comment_star) from goods_comment where goods_id = ?";
                java.sql.PreparedStatement pstStar = conn.prepareStatement(sqlStar);
                pstStar.setInt(1,bg.getGoods_id());
                java.sql.ResultSet rsStar = pstStar.executeQuery();
                rsStar.next();
                SumStar += rsStar.getInt(1);
                sqlStar = "select count(*) from goods_comment where goods_id = ?";
                pstStar = conn.prepareStatement(sqlStar);
                pstStar.setInt(1,bg.getGoods_id());
                rsStar = pstStar.executeQuery();
                rsStar.next();
                bg.setStar(SumStar / (rsStar.getInt(1)+1));

                result.add(bg);
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
    public BeanGoods add(int catagory_id,String name,double price,double vip_price,int goods_number,double Spec,String detail) throws BaseException {

        Connection conn = null;
        if ("".equals(name) || name == null)
        {
            throw new BusinessException("商品名称不能为空");
        }
        if (detail.length()>50 || detail.length()<1)
        {
            throw new BusinessException("描述请控制在1-50个字符之间");
        }
        if (price<0 || vip_price<0)
        {
            throw new BusinessException("请输入正确的价格");
        }
        if (goods_number<0)
        {
            throw new BusinessException("请输入正确的数量");
        }
        if (vip_price > price)
        {
            throw new BusinessException("会员价不得高于正常价格");
        }
        try{
            conn = DBUtil.getConnection();
            String sql = "select * from goods where goods_name = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                throw new BusinessException("该商品名称已存在,请更换");
            }
            sql = "insert into goods(catagory_id,goods_name,goods_price,spec,vip_price,detail,goods_number) values(?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,catagory_id);
            pst.setString(2,name);
            pst.setDouble(3,price);
            pst.setDouble(4,Spec);
            pst.setDouble(5,vip_price);
            pst.setString(6,detail);
            pst.setInt(7,goods_number);
            pst.executeUpdate();
            BeanGoods bg = new BeanGoods();
            sql = "select * from goods where goods_name = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            rs = pst.executeQuery();
            rs.next();
            bg.setGoods_id(rs.getInt(1));
            bg.setCategory_id(rs.getInt(2));
            bg.setGoods_name(rs.getString(3));
            bg.setGoods_price(rs.getDouble(4));
            bg.setVip_price(rs.getDouble(5));
            bg.setGoods_number(rs.getInt(6));
            bg.setSpec(rs.getDouble(7));
            bg.setDetail(rs.getString(8));
            return bg;
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
    public void update(int goods_id,int catagory_id,String name,double price,double vip_price,int goods_number,double Spec,String detail,String oldName) throws BaseException {
        Connection conn = null;
        if ("".equals(name) || name == null)
        {
            throw new BusinessException("商品名称不能为空");
        }
        if (detail.length()>50 || detail.length()<1)
        {
            throw new BusinessException("描述请控制在1-50个字符之间");
        }
        if (price<0 || vip_price<0)
        {
            throw new BusinessException("请输入正确的价格");
        }
        if (goods_number<0)
        {
            throw new BusinessException("请输入正确的数量");
        }
        if (vip_price > price)
        {
            throw new BusinessException("会员价不得高于正常价格");
        }
        try{
            conn = DBUtil.getConnection();
            String sql = "select * from goods where goods_name = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next() && !name.equals(oldName))
            {
                throw new BusinessException("该名称已存在,请更换");
            }
            sql = "update goods set catagory_id =? , goods_name = ?,goods_price = ? ,spec = ?,vip_price = ?, detail =?,goods_number = ? where goods_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,catagory_id);
            pst.setString(2,name);
            pst.setDouble(3,price);
            pst.setDouble(4,Spec);
            pst.setDouble(5,vip_price);
            pst.setString(6,detail);
            pst.setInt(7,goods_number);
            pst.setInt(8,goods_id);
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
    public void delete(int goods_id) throws BaseException {
        Connection conn = null;
        try{
            conn = DBUtil.getConnection();
            String sql = "delete from goods where goods_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,goods_id);
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
    public List<BeanGoods> loadAllRemain(int catagory_id) throws BaseException {
        Connection conn = null;
        List<BeanGoods> result=new ArrayList<BeanGoods>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods  where catagory_id = ? and goods_number > 0 order by goods_id ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,catagory_id);
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                BeanGoods bg = new BeanGoods();
                bg.setGoods_id(rs.getInt(1));
                bg.setCategory_id(rs.getInt(2));
                bg.setGoods_name(rs.getString(3));
                bg.setGoods_price(rs.getDouble(4));
                bg.setVip_price(rs.getDouble(5));
                bg.setGoods_number(rs.getInt(6));
                bg.setSpec(rs.getDouble(7));
                bg.setDetail(rs.getString(8));
                String sql2 = "select promotion_price,promotion_number,promotion_beginTime,promotion_endTime from promotion where goods_id = ? ";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,rs.getInt(1));
                java.sql.ResultSet rs2 = pst2.executeQuery();
                bg.setPromotionPrice(bg.getGoods_price());
                while (rs2.next())
                {
                    if (rs2.getInt(2) > 0 && rs2.getTimestamp(3).getTime() < System.currentTimeMillis() && rs2.getTimestamp(4).getTime() > System.currentTimeMillis())
                    {
                        bg.setPromotionPrice(rs2.getDouble(1));
                        break;
                    }
                }
                sql2 = "select a.dis_inf_id,a.dis_inf_content,a.dis_beginTime,a.dis_endTime,leastgoods_number,discount from discount a,dis_conn_goods b where a.dis_inf_id = b.dis_inf_id and b.goods_id = ?  ";
                pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,rs.getInt(1));
                rs2 = pst2.executeQuery();
                bg.setDiscount(1.0);
                bg.setDiscountContent("无");
                bg.setDiscount_least_number(1);
                while (rs2.next())
                {
                    if (rs2.getTimestamp(3).getTime()<System.currentTimeMillis() && rs2.getTimestamp(4).getTime() > System.currentTimeMillis())
                    {
                        bg.setDiscountId(rs2.getInt(1));
                        bg.setDiscountContent(rs2.getString(2));
                        bg.setDiscount_least_number(rs2.getInt(5));
                        bg.setDiscount(rs2.getDouble(6));
                    break;
                    }
                }

                sql2 = "select sum(goods_number) from order_detail where goods_id=?";
                pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,bg.getGoods_id());
                rs2 = pst2.executeQuery();
                rs2.next();
                bg.setGoods_sellNumber(rs2.getInt(1));


                double SumStar = 5;
                String sqlStar = "select sum(comment_star) from goods_comment where goods_id = ?";
                java.sql.PreparedStatement pstStar = conn.prepareStatement(sqlStar);
                pstStar.setInt(1,bg.getGoods_id());
                java.sql.ResultSet rsStar = pstStar.executeQuery();
                rsStar.next();
                SumStar += rsStar.getInt(1);
                sqlStar = "select count(*) from goods_comment where goods_id = ?";
                pstStar = conn.prepareStatement(sqlStar);
                pstStar.setInt(1,bg.getGoods_id());
                rsStar = pstStar.executeQuery();
                rsStar.next();
                bg.setStar(SumStar / (rsStar.getInt(1)+1));

                result.add(bg);
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
    public List<BeanGoods> loadSearch(String inf) throws BaseException {
        Connection conn = null;
        List<BeanGoods> result=new ArrayList<BeanGoods>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from goods  where goods_name like ? and goods_number > 0 order by goods_id ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,"%"+inf+"%");
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                BeanGoods bg = new BeanGoods();
                bg.setGoods_id(rs.getInt(1));
                bg.setCategory_id(rs.getInt(2));
                bg.setGoods_name(rs.getString(3));
                bg.setGoods_price(rs.getDouble(4));
                bg.setVip_price(rs.getDouble(5));
                bg.setGoods_number(rs.getInt(6));
                bg.setSpec(rs.getDouble(7));
                bg.setDetail(rs.getString(8));
                String sql2 = "select promotion_price,promotion_number,promotion_beginTime,promotion_endTime from promotion where goods_id = ? ";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,rs.getInt(1));
                java.sql.ResultSet rs2 = pst2.executeQuery();
                bg.setPromotionPrice(bg.getGoods_price());
                while (rs2.next())
                {
                    if (rs2.getInt(2) > 0 && rs2.getTimestamp(3).getTime() < System.currentTimeMillis() && rs2.getTimestamp(4).getTime() > System.currentTimeMillis())
                    {
                        bg.setPromotionPrice(rs2.getDouble(1));
                        break;
                    }
                }
                sql2 = "select catagory_name from fresh where catagory_id = ?";
                pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,bg.getCategory_id());
                rs2 = pst2.executeQuery();
                rs2.next();
                bg.setCategory_name(rs2.getString(1));


                sql2 = "select sum(goods_number) from order_detail where goods_id=?";
                pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,bg.getGoods_id());
                rs2 = pst2.executeQuery();
                rs2.next();
                bg.setGoods_sellNumber(rs2.getInt(1));

                sql2 = "select a.dis_inf_id,a.dis_inf_content,a.dis_beginTime,a.dis_endTime,leastgoods_number,discount from discount a,dis_conn_goods b where a.dis_inf_id = b.dis_inf_id and b.goods_id = ?  ";
                pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,rs.getInt(1));
                rs2 = pst2.executeQuery();
                bg.setDiscount(1.0);
                bg.setDiscountContent("无");
                bg.setDiscount_least_number(1);


                while (rs2.next())
                {
                    if (rs2.getTimestamp(3).getTime()<System.currentTimeMillis() && rs2.getTimestamp(4).getTime() > System.currentTimeMillis())
                    {
                        bg.setDiscountId(rs2.getInt(1));
                        bg.setDiscountContent(rs2.getString(2));
                        bg.setDiscount_least_number(rs2.getInt(5));
                        bg.setDiscount(rs2.getDouble(6));
                        break;
                    }
                }

                double SumStar = 5;
                String sqlStar = "select sum(comment_star) from goods_comment where goods_id = ?";
                java.sql.PreparedStatement pstStar = conn.prepareStatement(sqlStar);
                pstStar.setInt(1,bg.getGoods_id());
                java.sql.ResultSet rsStar = pstStar.executeQuery();
                rsStar.next();
                SumStar += rsStar.getInt(1);
                sqlStar = "select count(*) from goods_comment where goods_id = ?";
                pstStar = conn.prepareStatement(sqlStar);
                pstStar.setInt(1,bg.getGoods_id());
                rsStar = pstStar.executeQuery();
                rsStar.next();
                bg.setStar(SumStar / (rsStar.getInt(1)+1));

                result.add(bg);
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
    public List<BeanGoods> loadAllByMenu(int menu_id) throws BaseException {
        Connection conn = null;
        List<BeanGoods> result=new ArrayList<BeanGoods>();
        try {
            conn = DBUtil.getConnection();
            String s = "select goods_id from menu_recommand  where menu_id = ?";
            java.sql.PreparedStatement p = conn.prepareStatement(s);
            p.setInt(1,menu_id);
            java.sql.ResultSet r = p.executeQuery();
            while(r.next())
            {
                String sql = "select * from goods  where goods_id = ? and goods_number > 0  ";
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1,r.getInt(1));
                java.sql.ResultSet rs = pst.executeQuery();
                while(rs.next())
                {
                    BeanGoods bg = new BeanGoods();
                    bg.setGoods_id(rs.getInt(1));
                    bg.setCategory_id(rs.getInt(2));
                    bg.setGoods_name(rs.getString(3));
                    bg.setGoods_price(rs.getDouble(4));
                    bg.setVip_price(rs.getDouble(5));
                    bg.setGoods_number(rs.getInt(6));
                    bg.setSpec(rs.getDouble(7));
                    bg.setDetail(rs.getString(8));
                    String sql2 = "select promotion_price,promotion_number,promotion_beginTime,promotion_endTime from promotion where goods_id = ? ";
                    java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                    pst2.setInt(1,rs.getInt(1));
                    java.sql.ResultSet rs2 = pst2.executeQuery();
                    bg.setPromotionPrice(bg.getGoods_price());
                    while (rs2.next())
                    {
                        if (rs2.getInt(2) > 0 && rs2.getTimestamp(3).getTime() < System.currentTimeMillis() && rs2.getTimestamp(4).getTime() > System.currentTimeMillis())
                        {
                            bg.setPromotionPrice(rs2.getDouble(1));
                            break;
                        }
                    }
                    sql2 = "select catagory_name from fresh where catagory_id = ?";
                    pst2 = conn.prepareStatement(sql2);
                    pst2.setInt(1,bg.getCategory_id());
                    rs2 = pst2.executeQuery();
                    rs2.next();
                    bg.setCategory_name(rs2.getString(1));


                    sql2 = "select sum(goods_number) from order_detail where goods_id=?";
                    pst2 = conn.prepareStatement(sql2);
                    pst2.setInt(1,bg.getGoods_id());
                    rs2 = pst2.executeQuery();
                    rs2.next();
                    bg.setGoods_sellNumber(rs2.getInt(1));

                    sql2 = "select a.dis_inf_id,a.dis_inf_content,a.dis_beginTime,a.dis_endTime,leastgoods_number,discount from discount a,dis_conn_goods b where a.dis_inf_id = b.dis_inf_id and b.goods_id = ?  ";
                    pst2 = conn.prepareStatement(sql2);
                    pst2.setInt(1,rs.getInt(1));
                    rs2 = pst2.executeQuery();
                    bg.setDiscount(1.0);
                    bg.setDiscountContent("无");
                    bg.setDiscount_least_number(1);


                    while (rs2.next())
                    {
                        if (rs2.getTimestamp(3).getTime()<System.currentTimeMillis() && rs2.getTimestamp(4).getTime() > System.currentTimeMillis())
                        {
                            bg.setDiscountId(rs2.getInt(1));
                            bg.setDiscountContent(rs2.getString(2));
                            bg.setDiscount_least_number(rs2.getInt(5));
                            bg.setDiscount(rs2.getDouble(6));
                            break;
                        }
                    }


                    double SumStar = 5;
                    String sqlStar = "select sum(comment_star) from goods_comment where goods_id = ?";
                    java.sql.PreparedStatement pstStar = conn.prepareStatement(sqlStar);
                    pstStar.setInt(1,bg.getGoods_id());
                    java.sql.ResultSet rsStar = pstStar.executeQuery();
                    rsStar.next();
                    SumStar += rsStar.getInt(1);
                    sqlStar = "select count(*) from goods_comment where goods_id = ?";
                    pstStar = conn.prepareStatement(sqlStar);
                    pstStar.setInt(1,bg.getGoods_id());
                    rsStar = pstStar.executeQuery();
                    rsStar.next();
                    bg.setStar(SumStar / (rsStar.getInt(1)+1));

                    result.add(bg);
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
    public List<BeanGoods> loadRecByPerson() throws BaseException {
        Connection conn = null;
        List<BeanGoods> result=new ArrayList<BeanGoods>();
        try {
            conn = DBUtil.getConnection();
            String s = "select goods_id from orders,order_detail  where order_detail.order_id=orders.order_id and orders.user_id = ? group by goods_id order by count(*) desc";
            java.sql.PreparedStatement p = conn.prepareStatement(s);
            p.setString(1, BeanUsers.currentLoginUser.getUser_id());
            java.sql.ResultSet r = p.executeQuery();
            while(r.next())
            {
                String sql = "select * from goods  where goods_id = ? and goods_number > 0  ";
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1,r.getInt(1));
                java.sql.ResultSet rs = pst.executeQuery();
                while(rs.next())
                {
                    BeanGoods bg = new BeanGoods();
                    bg.setGoods_id(rs.getInt(1));
                    bg.setCategory_id(rs.getInt(2));
                    bg.setGoods_name(rs.getString(3));
                    bg.setGoods_price(rs.getDouble(4));
                    bg.setVip_price(rs.getDouble(5));
                    bg.setGoods_number(rs.getInt(6));
                    bg.setSpec(rs.getDouble(7));
                    bg.setDetail(rs.getString(8));
                    String sql2 = "select promotion_price,promotion_number,promotion_beginTime,promotion_endTime from promotion where goods_id = ? ";
                    java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                    pst2.setInt(1,rs.getInt(1));
                    java.sql.ResultSet rs2 = pst2.executeQuery();
                    bg.setPromotionPrice(bg.getGoods_price());
                    while (rs2.next())
                    {
                        if (rs2.getInt(2) > 0 && rs2.getTimestamp(3).getTime() < System.currentTimeMillis() && rs2.getTimestamp(4).getTime() > System.currentTimeMillis())
                        {
                            bg.setPromotionPrice(rs2.getDouble(1));
                            break;
                        }
                    }
                    sql2 = "select catagory_name from fresh where catagory_id = ?";
                    pst2 = conn.prepareStatement(sql2);
                    pst2.setInt(1,bg.getCategory_id());
                    rs2 = pst2.executeQuery();
                    rs2.next();
                    bg.setCategory_name(rs2.getString(1));


                    sql2 = "select sum(goods_number) from order_detail where goods_id=?";
                    pst2 = conn.prepareStatement(sql2);
                    pst2.setInt(1,bg.getGoods_id());
                    rs2 = pst2.executeQuery();
                    rs2.next();
                    bg.setGoods_sellNumber(rs2.getInt(1));

                    sql2 = "select a.dis_inf_id,a.dis_inf_content,a.dis_beginTime,a.dis_endTime,leastgoods_number,discount from discount a,dis_conn_goods b where a.dis_inf_id = b.dis_inf_id and b.goods_id = ?  ";
                    pst2 = conn.prepareStatement(sql2);
                    pst2.setInt(1,rs.getInt(1));
                    rs2 = pst2.executeQuery();
                    bg.setDiscount(1.0);
                    bg.setDiscountContent("无");
                    bg.setDiscount_least_number(1);


                    while (rs2.next())
                    {
                        if (rs2.getTimestamp(3).getTime()<System.currentTimeMillis() && rs2.getTimestamp(4).getTime() > System.currentTimeMillis())
                        {
                            bg.setDiscountId(rs2.getInt(1));
                            bg.setDiscountContent(rs2.getString(2));
                            bg.setDiscount_least_number(rs2.getInt(5));
                            bg.setDiscount(rs2.getDouble(6));
                            break;
                        }
                    }

                    double SumStar = 5;
                    String sqlStar = "select sum(comment_star) from goods_comment where goods_id = ?";
                    java.sql.PreparedStatement pstStar = conn.prepareStatement(sqlStar);
                    pstStar.setInt(1,bg.getGoods_id());
                    java.sql.ResultSet rsStar = pstStar.executeQuery();
                    rsStar.next();
                    SumStar += rsStar.getInt(1);
                    sqlStar = "select count(*) from goods_comment where goods_id = ?";
                    pstStar = conn.prepareStatement(sqlStar);
                    pstStar.setInt(1,bg.getGoods_id());
                    rsStar = pstStar.executeQuery();
                    rsStar.next();
                    bg.setStar(SumStar / (rsStar.getInt(1)+1));

                    result.add(bg);
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
    public List<BeanGoods> loadRecByGoods(int goods_id) throws BaseException {
        Connection conn = null;
        List<BeanGoods> result=new ArrayList<BeanGoods>();
        try {
            conn = DBUtil.getConnection();
            Set<Integer> menuSet = new HashSet<>();
            Set<Integer> goodsSet = new HashSet<>();
            String s = "select menu_id from menu_recommand where goods_id = ?";
            java.sql.PreparedStatement p = conn.prepareStatement(s);
            p.setInt(1,goods_id);
            java.sql.ResultSet r = p.executeQuery();
            while (r.next())
            {
                menuSet.add(r.getInt(1));
            }
            for (int menu_id : menuSet)
            {
                s = "select goods_id from menu_recommand where menu_id = ?";
                p = conn.prepareStatement(s);
                p.setInt(1,menu_id);
                r = p.executeQuery();
                while (r.next())
                {
                    goodsSet.add(r.getInt(1));
                }
            }
            for(int goodsId : goodsSet)
            {
                if (goods_id == goodsId)
                    continue;
                String sql = "select * from goods  where goods_id = ? and goods_number > 0  ";
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1,goodsId);
                java.sql.ResultSet rs = pst.executeQuery();
                while(rs.next())
                {
                    BeanGoods bg = new BeanGoods();
                    bg.setGoods_id(rs.getInt(1));
                    bg.setCategory_id(rs.getInt(2));
                    bg.setGoods_name(rs.getString(3));
                    bg.setGoods_price(rs.getDouble(4));
                    bg.setVip_price(rs.getDouble(5));
                    bg.setGoods_number(rs.getInt(6));
                    bg.setSpec(rs.getDouble(7));
                    bg.setDetail(rs.getString(8));
                    String sql2 = "select promotion_price,promotion_number,promotion_beginTime,promotion_endTime from promotion where goods_id = ? ";
                    java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                    pst2.setInt(1,rs.getInt(1));
                    java.sql.ResultSet rs2 = pst2.executeQuery();
                    bg.setPromotionPrice(bg.getGoods_price());
                    while (rs2.next())
                    {
                        if (rs2.getInt(2) > 0 && rs2.getTimestamp(3).getTime() < System.currentTimeMillis() && rs2.getTimestamp(4).getTime() > System.currentTimeMillis())
                        {
                            bg.setPromotionPrice(rs2.getDouble(1));
                            break;
                        }
                    }
                    sql2 = "select catagory_name from fresh where catagory_id = ?";
                    pst2 = conn.prepareStatement(sql2);
                    pst2.setInt(1,bg.getCategory_id());
                    rs2 = pst2.executeQuery();
                    rs2.next();
                    bg.setCategory_name(rs2.getString(1));


                    sql2 = "select sum(goods_number) from order_detail where goods_id=?";
                    pst2 = conn.prepareStatement(sql2);
                    pst2.setInt(1,bg.getGoods_id());
                    rs2 = pst2.executeQuery();
                    rs2.next();
                    bg.setGoods_sellNumber(rs2.getInt(1));

                    sql2 = "select a.dis_inf_id,a.dis_inf_content,a.dis_beginTime,a.dis_endTime,leastgoods_number,discount from discount a,dis_conn_goods b where a.dis_inf_id = b.dis_inf_id and b.goods_id = ?  ";
                    pst2 = conn.prepareStatement(sql2);
                    pst2.setInt(1,rs.getInt(1));
                    rs2 = pst2.executeQuery();
                    bg.setDiscount(1.0);
                    bg.setDiscountContent("无");
                    bg.setDiscount_least_number(1);


                    while (rs2.next())
                    {
                        if (rs2.getTimestamp(3).getTime()<System.currentTimeMillis() && rs2.getTimestamp(4).getTime() > System.currentTimeMillis())
                        {
                            bg.setDiscountId(rs2.getInt(1));
                            bg.setDiscountContent(rs2.getString(2));
                            bg.setDiscount_least_number(rs2.getInt(5));
                            bg.setDiscount(rs2.getDouble(6));
                            break;
                        }
                    }


                    double SumStar = 5;
                    String sqlStar = "select sum(comment_star) from goods_comment where goods_id = ?";
                    java.sql.PreparedStatement pstStar = conn.prepareStatement(sqlStar);
                    pstStar.setInt(1,bg.getGoods_id());
                    java.sql.ResultSet rsStar = pstStar.executeQuery();
                    rsStar.next();
                    SumStar += rsStar.getInt(1);
                    sqlStar = "select count(*) from goods_comment where goods_id = ?";
                    pstStar = conn.prepareStatement(sqlStar);
                    pstStar.setInt(1,bg.getGoods_id());
                    rsStar = pstStar.executeQuery();
                    rsStar.next();
                    bg.setStar(SumStar / (rsStar.getInt(1)+1));

                    result.add(bg);
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
}
