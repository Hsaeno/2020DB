package model;

import java.util.Date;

public class BeanUsers {
    private int user_id;
    private String user_name;
    private String user_sex;
    private String user_pwd;
    private String user_phoneNumber;
    private String user_email;
    private String user_city;
    private String user_regTime;
    private Boolean vip;
    private Date vip_endTime;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
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

    public String getUser_regTime() {
        return user_regTime;
    }

    public void setUser_regTime(String user_regTime) {
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
}
