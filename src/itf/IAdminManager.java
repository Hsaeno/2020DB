package itf;

import model.BeanAdmin;
import model.BeanUsers;
import util.BaseException;

public interface IAdminManager {
    public BeanAdmin login(String userid,String pwd)throws BaseException;
    public BeanAdmin reg(String admin_id,String pwd1,String pwd2,String admin_name) throws BaseException;
    public void changePwd(BeanAdmin user, String oldPwd, String newPwd, String newPwd2)throws BaseException;
}
