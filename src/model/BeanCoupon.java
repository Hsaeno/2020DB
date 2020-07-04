package model;

import java.util.Date;

public class BeanCoupon {
    private int coupon_id;
    private int order_id;
    private String coupon_content;
    private double least_monet;
    private double sub_money;
    private Date cp_beginTime;
    private Date cp_endTime;

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getCoupon_content() {
        return coupon_content;
    }

    public void setCoupon_content(String coupon_content) {
        this.coupon_content = coupon_content;
    }

    public double getLeast_monet() {
        return least_monet;
    }

    public void setLeast_monet(double least_monet) {
        this.least_monet = least_monet;
    }

    public double getSub_money() {
        return sub_money;
    }

    public void setSub_money(double sub_money) {
        this.sub_money = sub_money;
    }

    public Date getCp_beginTime() {
        return cp_beginTime;
    }

    public void setCp_beginTime(Date cp_beginTime) {
        this.cp_beginTime = cp_beginTime;
    }

    public Date getCp_endTime() {
        return cp_endTime;
    }

    public void setCp_endTime(Date cp_endTime) {
        this.cp_endTime = cp_endTime;
    }
}
