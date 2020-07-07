package manager;

import itf.IGoodsManager;
import model.BeanFresh;
import model.BeanGoods;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            bg.setSpec(rs.getDouble(5));
            bg.setVip_price(rs.getDouble(6));
            bg.setDetail(rs.getString(7));
            bg.setGoods_number(rs.getInt(8));
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
}
