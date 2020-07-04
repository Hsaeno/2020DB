package model;

import java.sql.Blob;
import java.util.Date;

public class BeanGoodsComment {
    private int goods_id;
    private int user_id;
    private String comment_content;
    private Date comment_date;
    private Date comment_star;
    private Blob comment_pic;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public Date getComment_date() {
        return comment_date;
    }

    public void setComment_date(Date comment_date) {
        this.comment_date = comment_date;
    }

    public Date getComment_star() {
        return comment_star;
    }

    public void setComment_star(Date comment_star) {
        this.comment_star = comment_star;
    }

    public Blob getComment_pic() {
        return comment_pic;
    }

    public void setComment_pic(Blob comment_pic) {
        this.comment_pic = comment_pic;
    }
}
