package itf;
import model.BeanUsers;
import util.BaseException;
public interface IUserManager {
    /**
     * 注册：
     * 要求用户名不能重复，不能为空
     * 两次输入的密码必须一致，密码不能为空
     * 如果注册失败，则抛出异常
     * @param userid
     * @param pwd  密码
     * @param pwd2 重复输入的密码
     * @return
     * @throws BaseException
     */
    public BeanUsers reg(String userid, String pwd, String pwd2,String name,String sex,String emain,String phoneNumber,String city) throws BaseException;
    /**
     * 登陆
     * 1、如果用户不存在或者密码错误，抛出一个异常
     * 2、如果认证成功，则返回当前用户信息
     * @param userid
     * @param pwd
     * @return
     * @throws BaseException
     */
    public BeanUsers login(String userid,String pwd)throws BaseException;
    /**
     * 修改密码
     * 如果没有成功修改，则抛出异常
     * @param user    当前用户
     * @param oldPwd  原密码
     * @param newPwd  新密码
     * @param newPwd2 重复输入的新密码
     */
    public void changePwd(BeanUsers user, String oldPwd,String newPwd, String newPwd2)throws BaseException;

    public void changeInf(BeanUsers user, String name,String email,String phone,String city,String passwd) throws BaseException;

    public void ManageVip(BeanUsers user,int mode) throws BaseException;
}
