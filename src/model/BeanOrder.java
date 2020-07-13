package model;

import util.DBUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BeanOrder {
    private int order_id;
    private int address_id;
    private int user_id;
    private int coupon_id;
    private double origin_price;
    private double settle_price;
    private Date require_time;
    private String order_status;

    public static final String[] tableTitles={"订单号","地址相关","优惠券信息","结算价","优惠价","要求送达时间","订单状态"};

    public String getAddress_content() {
        return address_content;
    }

    public void setAddress_content(String address_content) {
        this.address_content = address_content;
    }

    private String goods_name;
    private String coupon_content;
    private String address_content;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public double getOrigin_price() {
        return origin_price;
    }

    public void setOrigin_price(double origin_price) {
        this.origin_price = origin_price;
    }

    public double getSettle_price() {
        return settle_price;
    }

    public void setSettle_price(double settle_price) {
        this.settle_price = settle_price;
    }

    public Date getRequire_time() {
        return require_time;
    }

    public void setRequire_time(Date require_time) {
        this.require_time = require_time;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getCoupon_content() {
        return coupon_content;
    }

    public void setCoupon_content(String coupon_content) {
        this.coupon_content = coupon_content;
    }
    public String getCell(int col)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (col==0)
            return Integer.toString(this.getOrder_id());
        else if (col==1)
            return this.getAddress_content();
        else if (col==2)
            return this.getCoupon_content();
        else if (col==3)
            return Double.toString(this.getSettle_price());
        else if (col==4)
            return Double.toString(this.getOrigin_price()-this.getSettle_price());
        else if (col==5)
        {
            if (this.require_time == null)
                return "无";
            return sdf.format(this.getRequire_time());
        }

        else if (col==6)
            return this.getOrder_status();
        else
            return "";
    }

}
