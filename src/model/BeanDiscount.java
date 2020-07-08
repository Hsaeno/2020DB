package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BeanDiscount {
    private int dis_inf_id;
    private String dis_inf_content;
    private int leastGoods_number;
    private double discount;
    private Date dis_beginTime;
    private Date dis_endTime;

    public static final String[] tableTitles={"序号","内容","适用数量","折扣信息","开始时间","结束时间"};

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


    public String getCell(int col)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (col==0)
            return Integer.toString(this.getDis_inf_id());
        else if (col==1)
            return this.getDis_inf_content();
        else if (col==2)
            return Integer.toString(this.getLeastGoods_number());
        else if (col==3)
            return Double.toString(this.getDiscount());
        else if (col==4)
            return sdf.format(this.getDis_beginTime());
        else if (col==5)
            return sdf.format(this.getDis_endTime());
        else
            return "";
    }
}
