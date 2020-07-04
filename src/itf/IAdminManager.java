package itf;

import model.BeanAdmin;
import util.BaseException;

public interface IAdminManager {
    public BeanAdmin login(String userid,String pwd)throws BaseException;
}
