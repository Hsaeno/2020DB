package model;

import javax.persistence.criteria.CriteriaBuilder;

public class BeanGoods {
    private int goods_id;
    private int category_id;
    private String category_name;
    private String goods_name;
    private double goods_price;
    private double vip_price;
    private int goods_number;
    private int goods_sellNumber;
    private double star;

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public String getCategory_name() {
        return category_name;
    }

    public int getGoods_sellNumber() {
        return goods_sellNumber;
    }

    public void setGoods_sellNumber(int goods_sellNumber) {
        this.goods_sellNumber = goods_sellNumber;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    private double spec;
    private String detail;
    private double promotionPrice;
    private int promotionId;

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    private String discountContent;
    private int discountId;
    private double discount;
    private int discount_least_number;

    public int getDiscount_least_number() {
        return discount_least_number;
    }

    public void setDiscount_least_number(int discount_least_number) {
        this.discount_least_number = discount_least_number;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDiscountContent() {
        return discountContent;
    }

    public void setDiscountContent(String discountContent) {
        this.discountContent = discountContent;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }


    public double getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public static final String[] tableTitles = {"序号","类别","名称","价格","会员价","数量","规格","详情","销量","星级"};
    public static final String[] tableTitles2 = {"名称","价格","会员价","折扣价","满折情况","规格","详情","库存","销量","星级"};
    public static final String[] tableTitles3 = {"类别","名称","价格","会员价","折扣价","满折情况","规格","详情","库存","销量","星级"};

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
            return Double.toString(this.getVip_price());
        else if (col==5)
            return Integer.toString(this.getGoods_number());
        else if (col==6)
            return Double.toString(this.getSpec());
        else if (col==7)
            return this.getDetail();
        else if (col==8)
            return Integer.toString(this.getGoods_sellNumber());
        else if (col==9)
            return String.format("%.1f",this.getStar());
        else
            return "";
    }

    public String getCell2(int col)
    {
        if(col==0)
            return this.getGoods_name();
        else if (col==1)
            return Double.toString(this.getGoods_price());
        else if (col==2)
            return Double.toString(this.getVip_price());
        else if (col==3)
            if (this.getPromotionPrice() == this.getGoods_price())
                return "无";
            else
                return Double.toString(this.getPromotionPrice());
        else if(col==4)
            return this.getDiscountContent();
        else if (col==5)
            return Double.toString(this.getSpec());
        else if (col==6)
            return this.getDetail();
        else if (col==7)
            return Integer.toString(this.getGoods_number());
        else if (col==8)
            return Integer.toString(this.getGoods_sellNumber());
        else if (col==9)
            return String.format("%.1f",this.getStar());
        else
            return "";
    }
    public String getCell3(int col)
    {
        if (col==0)
            return this.getCategory_name();
        else if(col==1)
            return this.getGoods_name();
        else if (col==2)
            return Double.toString(this.getGoods_price());
        else if (col==3)
            return Double.toString(this.getVip_price());
        else if (col==4)
            if (this.getPromotionPrice() == this.getGoods_price())
                return "无";
            else
                return Double.toString(this.getPromotionPrice());
        else if(col==5)
            return this.getDiscountContent();
        else if (col==6)
            return Double.toString(this.getSpec());
        else if (col==7)
            return this.getDetail();
        else if (col==8)
            return Integer.toString(this.getGoods_number());
        else if (col==9)
            return Integer.toString(this.getGoods_sellNumber());
        else if (col==10)
            return String.format("%.1f",this.getStar());
        else
            return "";
    }
}
