package model;

public class BeanPurchaseTable {
    private int purchase_id;
    private String admin_id;
    private int purchase_number;
    private int goods_id;
    private int purchase_orderId;
    private String goods_name;
    private String purchase_status;
    private int catagory_id;

    public int getGoods_id() {
        return goods_id;
    }

    public int getCatagory_id() {
        return catagory_id;
    }

    public void setCatagory_id(int catagory_id) {
        this.catagory_id = catagory_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getPurchase_orderId() {
        return purchase_orderId;
    }

    public void setPurchase_orderId(int purchase_orderId) {
        this.purchase_orderId = purchase_orderId;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(int purchase_id) {
        this.purchase_id = purchase_id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public int getPurchase_number() {
        return purchase_number;
    }

    public void setPurchase_number(int purchase_number) {
        this.purchase_number = purchase_number;
    }

    public String getPurchase_status() {
        return purchase_status;
    }

    public void setPurchase_status(String purchase_status) {
        this.purchase_status = purchase_status;
    }
}
