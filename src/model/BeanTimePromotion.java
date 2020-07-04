package model;

import java.util.Date;

public class BeanTimePromotion {
    private int promotion_id;
    private int goods_id;
    private double promotion_price;
    private int promotion_number;
    private Date promotion_beginTime;
    private Date promotion_endTime;

    public int getPromotion_id() {
        return promotion_id;
    }

    public void setPromotion_id(int promotion_id) {
        this.promotion_id = promotion_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public double getPromotion_price() {
        return promotion_price;
    }

    public void setPromotion_price(double promotion_price) {
        this.promotion_price = promotion_price;
    }

    public int getPromotion_number() {
        return promotion_number;
    }

    public void setPromotion_number(int promotion_number) {
        this.promotion_number = promotion_number;
    }

    public Date getPromotion_beginTime() {
        return promotion_beginTime;
    }

    public void setPromotion_beginTime(Date promotion_beginTime) {
        this.promotion_beginTime = promotion_beginTime;
    }

    public Date getPromotion_endTime() {
        return promotion_endTime;
    }

    public void setPromotion_endTime(Date promotion_endTime) {
        this.promotion_endTime = promotion_endTime;
    }
}
