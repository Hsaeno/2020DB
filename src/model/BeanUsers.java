package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BeanUsers {
    public static final String[] tableTitles={"账号","名称","性别","密码","手机","邮箱","城市","注册时间","VIP","VIP到期时间"};
    public static BeanUsers currentLoginUser = null;
    private String  user_id;
    private String user_name;
    private String user_sex;



    private String user_pwd;
    private String user_phoneNumber;
    private String user_email;
    private String user_city;
    private Date user_regTime;
    private Boolean vip;
    private Date vip_endTime;

    public String  getUser_id() {
        return user_id;
    }

    public void setUser_id(String  user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_phoneNumber() {
        return user_phoneNumber;
    }

    public void setUser_phoneNumber(String user_phoneNumber) {
        this.user_phoneNumber = user_phoneNumber;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public Date getUser_regTime() {
        return user_regTime;
    }

    public void setUser_regTime(Date user_regTime) {
        this.user_regTime = user_regTime;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }

    public Date getVip_endTime() {
        return vip_endTime;
    }

    public void setVip_endTime(Date vip_endTime) {
        this.vip_endTime = vip_endTime;
    }
    public static BeanUsers getCurrentLoginUser() {
        return currentLoginUser;
    }

    public static void setCurrentLoginUser(BeanUsers currentLoginUser) {
        BeanUsers.currentLoginUser = currentLoginUser;
    }
    public String getCell(int col)
    {
        SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
        if (col == 0)
            return this.getUser_id();
        else if(col==1)
            return this.getUser_name();
        else if(col==2)
            return this.getUser_sex();
        else if (col==3)
            return this.getUser_pwd();
        else if (col==4)
            return this.getUser_phoneNumber();
        else if (col==5)
            return this.getUser_email();
        else if (col==6)
            return this.getUser_city();
        else if (col==7)
            return sdt.format(this.getUser_regTime());
        else if (col==8)
        { if (this.getVip())
                return "是";
            else
                return "否";}
        else if (col==9)
            if (this.getVip_endTime() == null)
                return "null";
            else
                return sdt.format(this.getVip_endTime());
        else
            return "";
    }
}
