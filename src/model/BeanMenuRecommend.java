package model;

public class BeanMenuRecommend {
    private int goods_id;
    private int menu_id;
    private String recommend_description;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public String getRecommend_description() {
        return recommend_description;
    }

    public void setRecommend_description(String recommend_description) {
        this.recommend_description = recommend_description;
    }
}
