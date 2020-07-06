package model;

import javax.persistence.criteria.CriteriaBuilder;

public class BeanGoods {
    private int goods_id;
    private int category_id;
    private String goods_name;
    private double goods_price;
    private double vip_price;
    private int goods_number;
    private double spec;
    private String detail;

    public static final String[] tableTitles={"序号","类别","名称","价格","规格","会员价","详情","数量"};


    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }


    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }


    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    public double getVip_price() {
        return vip_price;
    }

    public void setVip_price(double vip_price) {
        this.vip_price = vip_price;
    }

    public int getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(int goods_number) {
        this.goods_number = goods_number;
    }

    public double getSpec() {
        return spec;
    }

    public void setSpec(double spec) {
        this.spec = spec;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCell(int col)
    {
        if (col==0)
            return Integer.toString(this.getGoods_id());
        else if(col==1)
            return Integer.toString(this.getCategory_id());
        else if(col==2)
            return this.getGoods_name();
        else if (col==3)
            return Double.toString(this.getGoods_price());
        else if (col==4)
            return Double.toString(this.getSpec());
        else if (col==5)
            return Double.toString(this.getVip_price());
        else if (col==6)
            return this.getDetail();
        else if (col==7)
            return Integer.toString(this.getGoods_number());
        else
            return "";
    }
}
