package model;

import java.sql.Blob;

public class BeanMenu {
    private int menu_id;
    private String menu_name;
    private String material;
    private String step;
    private Blob menu_picture;

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
}
