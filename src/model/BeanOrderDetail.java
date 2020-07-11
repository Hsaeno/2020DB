package model;

public class BeanOrderDetail {
    private int order_id;
    private int goods_id;
    private int dis_inf_id;
    private int number;
    private double price;
    private double discount;
    private String goods_name;
    private String dis_content;
    public static final String[] tableTitles={"商品名称","折扣内容","商品数量","价格","享受折扣"};
    public String getDis_content() {
        return dis_content;
    }

    public void setDis_content(String dis_content) {
        this.dis_content = dis_content;
    }

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

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
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
    public String getCell(int col)
    {
        if (col==0)
            return this.getGoods_name();
        else if (col==1)
            return this.getDis_content();
        else if (col==2)
            return Integer.toString(this.getNumber());
        else if (col==3)
            return Double.toString(this.getPrice());
        else if (col==4)
            return Double.toString(this.getDiscount());
        else
            return "";
    }
}
