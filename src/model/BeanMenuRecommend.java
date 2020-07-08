package model;

public class BeanMenuRecommend {
    private int goods_id;
    private int menu_id;
    private String recommend_description;
    private int tabid;
    private String goods_name;

    public static final String[] tableTitles={"推荐序号","商品名称","菜单序号","介绍"};

    public int getTabid() {
        return tabid;
    }

    public void setTabid(int tabid) {
        this.tabid = tabid;
    }

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

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public void setRecommend_description(String recommend_description) {
        this.recommend_description = recommend_description;
    }
    public String getCell(int col)
    {
        if (col==0)
            return Integer.toString(this.getTabid());
        else if (col==1)
            return this.getGoods_name();
        else if (col==2)
            return Integer.toString(this.getMenu_id());
        else if (col==3)
            return this.getRecommend_description();
        else
            return "";
    }
}
