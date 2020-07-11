package itf;

import model.BeanCoupon;
import model.BeanPromotion;
import util.BaseException;

import java.util.Date;
import java.util.List;

public interface IPromotionManager {
    public List<BeanPromotion> loadAll() throws BaseException;
    public void add(String goods_name, double promotion_price, int number, Date beginTime, Date endTime) throws BaseException;
    public void delete(int id) throws BaseException;
    public void modify(int id,String goods_name, double promotion_price, int number, Date beginTime, Date endTime) throws BaseException;
    public List<BeanPromotion> AdminLoadAll() throws BaseException;
}
