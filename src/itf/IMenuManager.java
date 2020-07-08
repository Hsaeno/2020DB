package itf;

import model.BeanGoods;
import model.BeanMenu;
import util.BaseException;

import java.util.Date;
import java.util.List;

public interface IMenuManager {
    public List<BeanMenu> loadAll() throws BaseException;
    public void add(String name, String material,String step) throws BaseException;
    public void delete(int id) throws BaseException;
    public void modify(int id,String name,String material,String step) throws BaseException;
}
