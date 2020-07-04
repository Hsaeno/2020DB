package model;

public class BeanAdmin {
    private int admin_id;
    private String admin_name;
    private String admin_loginPwd;

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getAdmin_loginPwd() {
        return admin_loginPwd;
    }

    public void setAdmin_loginPwd(String admin_loginPwd) {
        this.admin_loginPwd = admin_loginPwd;
    }
}
