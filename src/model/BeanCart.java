package model;

import java.text.SimpleDateFormat;

public class BeanCart {
    String goodsName;
    int goods_id;
    int goods_number;
    String user_id;
    double goods_price;
    double goods_vip_price;
    double goods_promotion_price;

    int dis_inf_id;
    double discount = 1.0;

    public int getDis_inf_id() {
        return dis_inf_id;
    }

    public void setDis_inf_id(int dis_inf_id) {
        this.dis_inf_id = dis_inf_id;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }


    public double getGoods_promotion_price() {
        return goods_promotion_price;
    }

    public void setGoods_promotion_price(double goods_promotion_price) {
        this.goods_promotion_price = goods_promotion_price;
    }

    public double getGoods_vip_price() {
        return goods_vip_price;
    }

    public void setGoods_vip_price(double goods_vip_price) {
        this.goods_vip_price = goods_vip_price;
    }

    public int getCartNumber() {
        return cartNumber;
    }

    public void setCartNumber(int cartNumber) {
        this.cartNumber = cartNumber;
    }

    int cartNumber;
    public static final String[] tableTitles={"商品名称","商品数量","商品单价","会员价","促销价","折扣"};

    public String getGoodsName() {
        return goodsName;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(int goods_number) {
        this.goods_number = goods_number;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCell(int col)
    {
        if (col==0)
            return this.getGoodsName();
        else if (col==1)
            return Integer.toString(this.getGoods_number());
        else if (col==2)
            return Double.toString(this.getGoods_price());
        else if (col==3)
            return Double.toString(this.getGoods_vip_price());
        else if (col==4)
            return Double.toString(this.getGoods_promotion_price());
        else if (col==5)
            return Double.toString(this.getDiscount());
        else
            return "";
    }
}
