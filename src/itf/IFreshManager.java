package itf;

import model.BeanFresh;
import util.BaseException;

import java.util.List;

public interface IFreshManager {

    public List<BeanFresh> loadAll() throws BaseException;
    public BeanFresh add(String name,String description) throws BaseException;
    public void update(int id,String name,String description,String oldName) throws BaseException;
    public void delete(int id) throws BaseException;
}
