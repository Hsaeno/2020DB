package itf;

import model.BeanFresh;
import model.BeanGoods;
import util.BaseException;

import java.util.List;

public interface IGoodsManager {
    public List<BeanGoods> loadAll(int catagory_id) throws BaseException;
    public BeanGoods add(int catagory_id,String name,double price,double vip_price,int goods_number,double Spec,String detail) throws BaseException;
    public void update(int goods_id,int catagory_id,String name,double price,double vip_price,int goods_number,double Spec,String detail) throws BaseException;
    public void delete(int goods_id) throws BaseException;
}
