package model;

import java.text.SimpleDateFormat;

public class BeanAddress {
    private int address_id;
    private int order_id;
    private String user_id;
    private String province;
    private String city;
    private String region;
    private String contact_person;
    private String  contact_phoneNumber;
    private String detail_address;

    public static final String[] tableTitles={"序号","用户","省","市","区","详细地址","联系人","联系方式"};

    public String getDetail_address() {
        return detail_address;
    }

    public void setDetail_address(String detail_address) {
        this.detail_address = detail_address;
    }

    public int getAddress_id() { return address_id; }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getContact_phoneNumber() {
        return contact_phoneNumber;
    }

    public void setContact_phoneNumber(String contact_phoneNumber) {
        this.contact_phoneNumber = contact_phoneNumber;
    }

    public String getCell(int col)
    {
        if (col==0)
            return Integer.toString(this.getAddress_id());
        else if (col==1)
            return this.getUser_id();
        else if (col==2)
            return this.getProvince();
        else if (col==3)
            return this.getCity();
        else if (col==4)
            return this.getRegion();
        else if (col==5)
            return this.getDetail_address();
        else if (col==6)
            return this.getContact_person();
        else if (col==7)
            return this.getContact_phoneNumber();
        else
            return "";
    }
}

