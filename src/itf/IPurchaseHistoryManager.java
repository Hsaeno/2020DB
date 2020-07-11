package itf;

import model.*;
import util.BaseException;

import java.util.List;

public interface IPurchaseHistoryManager {
    public List<BeanOrder> loadAllOrder() throws BaseException;
    public List<BeanOrderDetail>  loadAllOrderDetail(int order_id) throws BaseException;
    public void addComment(int goods_id,String comment,int star) throws BaseException;
    public List<BeanGoodsComment> loadAll(int goods_id) throws BaseException;
    public BeanGoodsComment Search(int goods_id,String user_id) throws BaseException;
    public void modifyComment(int goods_id,String comment,int star) throws BaseException;
    public void MakeSure(int orders)  throws BaseException;
    public void Back(int orders) throws BaseException;
    public void AdminUpdateOrder(int order_id) throws BaseException;
    public List<BeanOrder> AdminLoadAll() throws BaseException;
}

