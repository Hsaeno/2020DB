package itf;

import model.BeanAddress;
import model.BeanCart;
import model.BeanGoods;
import util.BaseException;

import java.util.List;

public interface ICartManager {
    public List<BeanCart> loadAll(String  user_id) throws BaseException;
    public void add(BeanGoods bg,int goods_number,String user_id) throws BaseException;
    public void delete(int id) throws BaseException;
    public void deleteAll(String  user_id) throws BaseException;
    public void modify(int id,int goods_number) throws BaseException;
}
