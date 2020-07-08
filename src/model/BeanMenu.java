package model;

import java.sql.Blob;
import java.text.SimpleDateFormat;

public class BeanMenu {
    private int menu_id;
    private String menu_name;
    private String material;
    private String step;
    private Blob menu_picture;

    // todo 图片
    public static final String[] tableTitles={"序号","菜谱名","原材料","步骤"};


    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public Blob getMenu_picture() {
        return menu_picture;
    }

    public void setMenu_picture(Blob menu_picture) {
        this.menu_picture = menu_picture;
    }


    public String getCell(int col)
    {
        if (col==0)
            return Integer.toString(this.getMenu_id());
        else if (col==1)
            return this.menu_name;
        else if (col==2)
            return this.material;
        else if (col==3)
            return this.step;
        else
            return "";
    }
}
