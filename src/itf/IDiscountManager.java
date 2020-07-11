package itf;

import model.BeanCoupon;
import model.BeanDiscount;
import util.BaseException;

import java.util.Date;
import java.util.List;

public interface IDiscountManager {
    public List<BeanDiscount> loadAll() throws BaseException;
    public void add(String content, int least_goodsNumber, double discount, Date beginTime, Date endTime) throws BaseException;
    public void delete(int id) throws BaseException;
    public void modify(int id,String content, int least_goodsNumber, double discount, Date beginTime, Date endTime) throws BaseException;
    public List<BeanDiscount> AdminLoadAll() throws BaseException;
}
