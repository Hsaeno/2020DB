package itf;

import model.BeanDisConnGoods;
import model.BeanDiscount;
import util.BaseException;

import java.util.Date;
import java.util.List;

public interface IDisConnGoodsManager {
    public List<BeanDisConnGoods> loadAll() throws BaseException;
    public void add(int dis_inf_id,String goods_name, Date beginTime, Date endTime) throws BaseException;
    public void delete(int id) throws BaseException;
    public void modify(int id,int dis_id,String goods_name, Date beginTime, Date endTime) throws BaseException;
}
