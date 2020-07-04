package model;

import java.util.Date;

public class BeanDiscount {
    private int dis_inf_id;
    private String dis_inf_content;
    private int leastGoods_number;
    private double discount;
    private Date dis_beginTime;
    private Date dis_endTime;

    public int getDis_inf_id() {
        return dis_inf_id;
    }

    public void setDis_inf_id(int dis_inf_id) {
        this.dis_inf_id = dis_inf_id;
    }

    public String getDis_inf_content() {
        return dis_inf_content;
    }

    public void setDis_inf_content(String dis_inf_content) {
        this.dis_inf_content = dis_inf_content;
    }

    public int getLeastGoods_number() {
        return leastGoods_number;
    }

    public void setLeastGoods_number(int leastGoods_number) {
        this.leastGoods_number = leastGoods_number;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getDis_beginTime() {
        return dis_beginTime;
    }

    public void setDis_beginTime(Date dis_beginTime) {
        this.dis_beginTime = dis_beginTime;
    }

    public Date getDis_endTime() {
        return dis_endTime;
    }

    public void setDis_endTime(Date dis_endTime) {
        this.dis_endTime = dis_endTime;
    }
}
