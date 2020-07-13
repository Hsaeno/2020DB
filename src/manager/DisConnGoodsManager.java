package manager;

import control.MainControl;
import itf.IDisConnGoodsManager;
import itf.IGoodsManager;
import model.BeanDisConnGoods;
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

public class DisConnGoodsManager implements IDisConnGoodsManager {
    @Override
    public List<BeanDisConnGoods> loadAll() throws BaseException {
        Connection conn = null;
        List<BeanDisConnGoods> result=new ArrayList<BeanDisConnGoods>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from dis_conn_goods order by tableid";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                BeanDisConnGoods bdcg = new BeanDisConnGoods();
                bdcg.setTableId(rs.getInt(1));
                bdcg.setDis_id(rs.getInt(2));
                String sql2 = "select goods_name from goods where goods_id = ?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,rs.getInt(3));
                java.sql.ResultSet rs2 = pst2.executeQuery();
                rs2.next();
                bdcg.setGoods_name(rs2.getString(1));
                bdcg.setBegin_time(rs.getTimestamp(4));
                bdcg.setEnd_time(rs.getTimestamp(5));
                sql2 = "select dis_inf_content from discount where dis_inf_id = ?";
                pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,bdcg.getDis_id());
                rs2 = pst2.executeQuery();
                rs2.next();
                bdcg.setDis_content(rs2.getString(1));
                if (bdcg.getEnd_time().getTime()>System.currentTimeMillis() && bdcg.getBegin_time().getTime() < System.currentTimeMillis() )
                {
                    result.add(bdcg);
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
    public void add(int dis_inf_id, String goods_name, Date beginTime, Date endTime) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            if (beginTime.getTime() > endTime.getTime())
            {
                throw new BusinessException("结束时间不能早于开始时间");
            }
            String sql = "select goods_id from goods where goods_name = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,goods_name);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BusinessException("该商品不存在");
            }
            int goods_id = rs.getInt(1);
            sql = "select * from discount where dis_inf_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,dis_inf_id);
            rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BusinessException("满折不存在");
            }
            Date dis_begin_time = rs.getTimestamp(5);
            Date dis_end_Time = rs.getTimestamp(6);
            if (beginTime.getTime() < dis_begin_time.getTime()  || endTime.getTime() > dis_end_Time.getTime())
            {
                throw new BusinessException("满折商品时间段应在满折信息时间段内");
            }
            sql = "select end_time,begin_time from dis_conn_goods where goods_id = ?";
            pst =conn.prepareStatement(sql);
            pst.setInt(1,goods_id);
            rs = pst.executeQuery();
            while (rs.next())
            {
                if (rs.getTimestamp(1).getTime() > beginTime.getTime() && rs.getTimestamp(2).getTime() < endTime.getTime())
                {
                    throw new BusinessException("同一件商品在相同时间内不得进行两次满折活动");
                }
            }
            sql = "insert into dis_conn_goods(dis_inf_id,goods_id,begin_time,end_time) values (?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,dis_inf_id);
            pst.setInt(2,goods_id);
            pst.setTimestamp(3,new java.sql.Timestamp(beginTime.getTime()));
            pst.setTimestamp(4,new java.sql.Timestamp(endTime.getTime()));
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
            String sql = "delete from dis_conn_goods where tableid = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            try{
                pst.executeUpdate();
                MainControl.cartManager.resetCart();
            }
            catch(Exception e)
            {
                throw new BusinessException("该满折商品存在绑定信息");
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
    public void modify(int id, int dis_id, String goods_name, Date beginTime, Date endTime) throws BaseException {
        Connection conn = null;
        if (beginTime.getTime() > endTime.getTime())
        {
            throw new BusinessException("结束时间不能早于开始时间");
        }
        try{
            conn = DBUtil.getConnection();
            String sql = "select goods_id from goods where goods_name = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,goods_name);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BusinessException("商品不存在");
            }
            int goods_id = rs.getInt(1);
            sql = "select * from discount where dis_inf_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,dis_id);
            rs = pst.executeQuery();
            if (!rs.next())
            {
                throw new BusinessException("满折不存在");
            }
            sql = "select end_time,begin_time from dis_conn_goods where goods_id = ?";
            pst =conn.prepareStatement(sql);
            pst.setInt(1,goods_id);
            rs = pst.executeQuery();
            while (rs.next())
            {
                if (rs.getTimestamp(1).getTime() > beginTime.getTime() && rs.getTimestamp(2).getTime() < endTime.getTime())
                {
                    throw new BusinessException("同一件商品在相同时间内不得进行两次满折活动");
                }
            }
            sql = "update dis_conn_goods set dis_inf_id = ?,goods_id = ?, begin_time = ?,end_time = ? where tableid = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,dis_id);
            pst.setInt(2,goods_id);
            pst.setTimestamp(3,new java.sql.Timestamp(beginTime.getTime()));
            pst.setTimestamp(4,new java.sql.Timestamp(endTime.getTime()));
            pst.setInt(5,id);
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
    public List<BeanDisConnGoods> AdminLoadAll() throws BaseException {
        Connection conn = null;
        List<BeanDisConnGoods> result=new ArrayList<BeanDisConnGoods>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from dis_conn_goods order by tableid";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                BeanDisConnGoods bdcg = new BeanDisConnGoods();
                bdcg.setTableId(rs.getInt(1));
                bdcg.setDis_id(rs.getInt(2));
                String sql2 = "select goods_name from goods where goods_id = ?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,rs.getInt(3));
                java.sql.ResultSet rs2 = pst2.executeQuery();
                rs2.next();
                bdcg.setGoods_name(rs2.getString(1));
                bdcg.setBegin_time(rs.getTimestamp(4));
                bdcg.setEnd_time(rs.getTimestamp(5));
                sql2 = "select dis_inf_content from discount where dis_inf_id = ?";
                pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,bdcg.getDis_id());
                rs2 = pst2.executeQuery();
                rs2.next();
                bdcg.setDis_content(rs2.getString(1));
                result.add(bdcg);
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

