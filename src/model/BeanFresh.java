package model;

import java.text.SimpleDateFormat;

public class BeanFresh {
    private int category_id;
    private String category_name;
    private String description;
    public static final String[] tableTitles={"序号","名称","描述"};
    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCell(int col)
    {
        if (col==0)
            return Integer.toString(this.getCategory_id());
        else if (col==1)
            return this.getCategory_name();
        else if (col==2)
            return this.getDescription();
        else
            return "";
    }
}
