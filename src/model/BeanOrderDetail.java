package model;

public class BeanOrderDetail {
    private int order_id;
    private int goods_id;
    private int dis_inf_id;
    private int number;
    private double price;
    private double discount;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getDis_inf_id() {
        return dis_inf_id;
    }

    public void setDis_inf_id(int dis_inf_id) {
        this.dis_inf_id = dis_inf_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
