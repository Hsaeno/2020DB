package itf;

import model.BeanMenu;
import model.BeanMenuRecommend;
import util.BaseException;

import java.util.List;

public interface IMenuRecManager {
    public List<BeanMenuRecommend> loadAll() throws BaseException;
    public void add(String goods_name,int menu_id,String description) throws BaseException;
    public void delete(int id) throws BaseException;
    public void modify(int id,String goods_name,int menu_id,String description) throws BaseException;
}
