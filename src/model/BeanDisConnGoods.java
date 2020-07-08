package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BeanDisConnGoods {
    private int dis_id;
    private int goods_id;
    private Date begin_time;
    private Date end_time;
    private String goods_name;
    private String dis_content;
    private int tableId;

    public static final String[] tableTitles={"序号","优惠序号","商品名称","开始时间","结束时间"};

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getDis_content() {
        return dis_content;
    }

    public void setDis_content(String dis_content) {
        this.dis_content = dis_content;
    }

    public int getDis_id() {
        return dis_id;
    }

    public void setDis_id(int dis_id) {
        this.dis_id = dis_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public Date getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(Date begin_time) {
        this.begin_time = begin_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }


    public String getCell(int col)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (col==0)
            return Integer.toString(this.getTableId());
        else if (col==1)
            return Integer.toString(this.getDis_id());
        else if (col==2)
            return this.getGoods_name();
        else if (col==3)
            return sdf.format(this.getBegin_time());
        else if (col==4)
            return sdf.format(this.getEnd_time());
        else
            return "";
    }
}
