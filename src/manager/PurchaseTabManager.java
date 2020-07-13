package manager;

import itf.IPurchaseTabManager;
import model.BeanAdmin;
import model.BeanGoods;
import model.BeanPurchaseTable;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class PurchaseTabManager implements IPurchaseTabManager {
    @Override
    public int returnNextOderId(BeanAdmin ba) throws BaseException {
        Connection conn = null;
        try{
            conn = DBUtil.getConnection();
            String adminId = ba.getAdmin_id();
            String sql = "select max(purchase_orderId) from purchase_table where admin_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,adminId);
            java.sql.ResultSet rs = pst.executeQuery();
            int number = 0;
            if (rs.next()){
                number = rs.getInt(1);
            }
            return number + 1;
        }catch (SQLException e) {
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
    public List<BeanPurchaseTable> returnOrderGoods(BeanAdmin ba, int orederId) throws BaseException {
        Connection conn = null;
        try {
            List<BeanPurchaseTable> result = new ArrayList<>();
            conn = DBUtil.getConnection();
            String adminId = ba.getAdmin_id();
            String sql = "select * from purchase_table where admin_id = ? and purchase_orderid = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,adminId);
            pst.setInt(2,orederId);
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                BeanPurchaseTable bpt = new BeanPurchaseTable();
                String sql2 = "select goods_name,catagory_id from goods where goods_id = ?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,rs.getInt(3));
                java.sql.ResultSet rs2 = pst2.executeQuery();
                rs2.next();
                bpt.setGoods_name(rs2.getString(1));
                bpt.setCatagory_id(rs2.getInt(2));
                bpt.setPurchase_id(rs.getInt(1));
                bpt.setAdmin_id(rs.getString(2));
                bpt.setGoods_id(rs.getInt(3));
                bpt.setPurchase_number(rs.getInt(4));
                bpt.setPurchase_status(rs.getString(5));
                bpt.setPurchase_orderId(rs.getInt(6));
                result.add(bpt);
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
    public void add(String admin_id, String goods_name, String purchase_number, int purchase_orderid) throws BaseException {
        Connection conn = null;
        try{
            conn = DBUtil.getConnection();
            String sql = "select goods_id from goods where goods_name = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,goods_name);
            java.sql.ResultSet rs = pst.executeQuery();
            int goods_id;
            int number;
            if (rs.next())
            {
                goods_id = rs.getInt(1);
            }
            else {
                throw new BusinessException("该商品不存在");
            }
            try{
                number = Integer.parseInt(purchase_number);
            }
            catch (Exception e)
            {
                throw new BusinessException("数量格式出错");
            }
            sql = "insert into purchase_table(admin_id,goods_id,purchase_number,purchase_status,purchase_orderid) values (?,?,?,'下单',?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1,admin_id);
            pst.setInt(2,goods_id);
            pst.setInt(3,number);
            pst.setInt(4,purchase_orderid);
            pst.executeUpdate();
            pst.close();
            rs.close();
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
    public List<BeanPurchaseTable> loadSimpleTable(BeanAdmin ba) throws BaseException {
        Connection conn = null;
        try {
            List<BeanPurchaseTable> result = new ArrayList<>();
            conn = DBUtil.getConnection();
            String adminId = ba.getAdmin_id();
            String sql = "select purchase_status,purchase_orderid from purchase_table where admin_id = ? ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,adminId);
            java.sql.ResultSet rs = pst.executeQuery();
            Set<Integer> set = new HashSet<Integer>();
            while(rs.next())
            {
                int orderId = rs.getInt(2);
                //noinspection ConstantConditions
                if (set.contains(orderId))
                    continue;
                BeanPurchaseTable tbl = new BeanPurchaseTable();
                tbl.setPurchase_status(rs.getString(1));
                tbl.setPurchase_orderId(rs.getInt(2));
                result.add(tbl);
                set.add(rs.getInt(2));
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
    public void updateStatus(int orderId, String status) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select purchase_status from purchase_table where purchase_orderId = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,orderId);
            java.sql.ResultSet rs = pst.executeQuery();
            rs.next();
            HashMap<String, Integer> priority = new HashMap<>(){
                {
                put("下单",1);
                put("在途",2);
                put("入库",3);
            }
            };
            if ((priority.get(status)-priority.get(rs.getString(1))!=1))
            {
                throw new BusinessException("状态更新异常,请选择正确的更新状态");
            }
            sql = "update purchase_table set purchase_status = ? where purchase_orderId = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,status);
            pst.setInt(2,orderId);
            pst.executeUpdate();
            if (status == "入库")
            {
                sql = "select purchase_number,goods_id from purchase_table where admin_id = ? and purchase_orderid = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1,BeanAdmin.currentLoginAdmin.getAdmin_id());
                pst.setInt(2,orderId);
                rs = pst.executeQuery();
                while (rs.next())
                {
                    String sql2 = "update goods set goods_number = goods_number + ? where goods_id = ?";
                    java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                    pst2.setInt(1,rs.getInt(1));
                    pst2.setInt(2,rs.getInt(2));
                    pst2.executeUpdate();
                }
                rs.close();
                pst.close();
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
}
