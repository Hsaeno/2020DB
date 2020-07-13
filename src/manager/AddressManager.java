package manager;

import itf.IAddressManager;
import model.BeanAddress;
import model.BeanDiscount;
import model.BeanGoods;
import model.BeanUsers;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressManager implements IAddressManager {
    @Override
    public List<BeanAddress> loadAll() throws BaseException {
        Connection conn = null;
        List<BeanAddress> result=new ArrayList<BeanAddress>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from address where user_id = ? order by address_id ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, BeanUsers.currentLoginUser.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                BeanAddress ba = new BeanAddress();
                ba.setAddress_id(rs.getInt(1));
                ba.setUser_id(rs.getString(2));
                ba.setProvince(rs.getString(3));
                ba.setCity(rs.getString(4));
                ba.setRegion(rs.getString(5));
                ba.setDetail_address(rs.getString(6));
                ba.setContact_person(rs.getString(7));
                ba.setContact_phoneNumber(rs.getString(8));
                result.add(ba);
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
    public void add(String user_id, String province, String city, String region, String detail_address,String contact_person, String contact_phoneNumber) throws BaseException {
        Connection conn = null;
        if(contact_phoneNumber.length()!=11)
        {
            throw new BusinessException("请输入11位格式手机号码");
        }
        for(int i = 0;i<contact_phoneNumber.length();i++)
        {
            if (!Character.isDigit(contact_phoneNumber.charAt(i)))
            {
                throw new BusinessException("手机号码应为纯数字");
            }
        }
        if ( "".equals(contact_person) || "".equals(detail_address) || contact_person == null || detail_address == null )
        {
            throw new BusinessException("相关信息不能为空");
        }
        if (region.length() >10 || detail_address.length() > 20)
        {
            throw new BusinessException("地址信息过长,请精简");
        }
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into address(user_id,province,city,region,detail_address,contact_person,contact_phoneNumber) values (?,?,?,?,?,?,?)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,user_id);
            pst.setString(2,province);
            pst.setString(3,city);
            pst.setString(4,region);
            pst.setString(5,detail_address);
            pst.setString(6,contact_person);
            pst.setString(7,contact_phoneNumber);
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
            String sql = "delete from address where address_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            try {
                pst.executeUpdate();
            } catch (Exception e)
            {
                throw new BusinessException("该地址有绑定信息,不支持删除");
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
    public void modify(int id, String user_id, String province, String city, String region, String detail_address, String contact_person, String contact_phoneNumber) throws BaseException {
        Connection conn = null;
        if(contact_phoneNumber.length()!=11)
        {
            throw new BusinessException("请输入11位格式手机号码");
        }
        for(int i = 0;i<contact_phoneNumber.length();i++)
        {
            if (!Character.isDigit(contact_phoneNumber.charAt(i)))
            {
                throw new BusinessException("手机号码应为纯数字");
            }
        }
        if ( "".equals(contact_person) || "".equals(detail_address) || contact_person == null || detail_address == null )
        {
            throw new BusinessException("相关信息不能为空");
        }
        if (region.length() >10 || detail_address.length() > 20)
        {
            throw new BusinessException("地址信息过长,请精简");
        }
        try {
            conn = DBUtil.getConnection();
            String sql = "update address set user_id = ?,province = ?,city = ?, region = ?,detail_address = ?,contact_person = ?,contact_person = ? where address_id = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,user_id);
            pst.setString(2,province);
            pst.setString(3,city);
            pst.setString(4,region);
            pst.setString(5,detail_address);
            pst.setString(6,contact_person);
            pst.setString(7,contact_phoneNumber);
            pst.setInt(8,id);
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
    public List<BeanAddress> loadUserAddress(BeanUsers bu) throws BaseException {
        Connection conn = null;
        List<BeanAddress> result=new ArrayList<BeanAddress>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from address  where user_id = ? order by address_id";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,bu.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                BeanAddress ba = new BeanAddress();
                ba.setAddress_id(rs.getInt(1));
                ba.setUser_id(rs.getString(2));
                ba.setProvince(rs.getString(3));
                ba.setCity(rs.getString(4));
                ba.setRegion(rs.getString(5));
                ba.setDetail_address(rs.getString(6));
                ba.setContact_person(rs.getString(7));
                ba.setContact_phoneNumber(rs.getString(8));
                result.add(ba);
            }
            if (result == null)
            {
                throw new BusinessException("还没有地址,先去添加地址吧！");
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
