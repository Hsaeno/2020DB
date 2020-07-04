package model;

import java.util.Date;

public class BeanOrder {
    private int order_id;
    private int address_id;
    private int user_id;
    private int coupon_id;
    private double origin_price;
    private double settle_price;
    private Date require_time;
    private String order_status;
}
