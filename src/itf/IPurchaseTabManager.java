package itf;

import model.BeanAdmin;
import model.BeanGoods;
import model.BeanPurchaseTable;
import util.BaseException;

import java.util.ArrayList;
import java.util.List;

public interface IPurchaseTabManager {
    public int returnNextOderId(BeanAdmin ba) throws BaseException;
    public List<BeanPurchaseTable> returnOrderGoods(BeanAdmin ba, int orederId) throws BaseException;
    public void add(String admin_id,String goods_name,String purchase_number,int purchase_orderid) throws BaseException;
    public List<BeanPurchaseTable> loadSimpleTable(BeanAdmin ba) throws BaseException;
    public void updateStatus(int orderId, String status) throws BaseException;

}
