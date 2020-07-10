package itf;

import model.BeanAddress;
import model.BeanCoupon;
import model.BeanUsers;
import util.BaseException;

import java.util.Date;
import java.util.List;

public interface IAddressManager {
    public List<BeanAddress> loadAll() throws BaseException;
    public void add(String user_id,String province,String city,String region,String detail_address,String contact_person,String contact_phoneNumber) throws BaseException;
    public void delete(int id) throws BaseException;
    public void modify(int id,String user_id,String province,String city,String region,String detail_address,String contact_person,String contact_phoneNumber) throws BaseException;
    public List<BeanAddress> loadUserAddress(BeanUsers bu) throws BaseException;
}
