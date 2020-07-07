package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BeanCoupon {
    private int coupon_id;
    private String coupon_content;
    private double least_monet;
    private double sub_money;
    private Date cp_beginTime;
    private Date cp_endTime;

    public static final String[] tableTitles={"序号","内存","最低金额","减免金额","开始时间","结束时间"};

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
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


    public String getCell(int col)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (col==0)
            return Integer.toString(this.getCoupon_id());
        else if (col==1)
            return this.getCoupon_content();
        else if (col==2)
            return Double.toString(this.getLeast_monet());
        else if (col==3)
            return Double.toString(this.getSub_money());
        else if (col==4)
            return sdf.format(this.getCp_beginTime());
        else if (col==5)
            return sdf.format(this.getCp_endTime());
        else
            return "";
    }
}
