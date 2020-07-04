package model;

public class BeanPurchaseTable {
    private int purchase_id;
    private int admin_id;
    private int purchase_number;
    private String purchase_status;

    public int getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(int purchase_id) {
        this.purchase_id = purchase_id;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
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
