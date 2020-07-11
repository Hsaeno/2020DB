package itf;

import model.BeanCoupon;
import util.BaseException;

import java.util.Date;
import java.util.List;

public interface ICouponManager {
    public List<BeanCoupon> loadAll() throws BaseException;
    public void add(String content, double least_money, double sub_money, Date beginTime,Date endTime) throws BaseException;
    public void delete(int id) throws BaseException;
    public void modify(int id,String content, double least_money, double sub_money, Date beginTime,Date endTime) throws BaseException;
    public BeanCoupon loadByPrice(int price) throws BaseException;
    public List<BeanCoupon> AdminLoadAll() throws BaseException;
}
