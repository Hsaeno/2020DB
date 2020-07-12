package itf;

import model.BeanAddress;
import model.BeanCart;
import model.BeanCoupon;
import model.BeanGoods;
import util.BaseException;

import java.util.Date;
import java.util.List;

public interface ICartManager {
    public List<BeanCart> loadAll(String  user_id) throws BaseException;
    public void add(BeanGoods bg,int goods_number,String user_id) throws BaseException;
    public void delete(int id) throws BaseException;
    public void deleteAll(String  user_id) throws BaseException;
    public void modify(int id,int goods_number) throws BaseException;
    public double CalculateOrgPrice() throws BaseException;
    public double CalculateSetPrice(int coupon_id,double org_price) throws BaseException;
    public int MakeOrder(BeanAddress ba, int coupon_id, double origin_price, double settle_price, Date requireTime) throws BaseException;
    public void MakeOrderDetail(int order_id) throws BaseException;
}
