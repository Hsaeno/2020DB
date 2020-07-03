/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/7/3 14:43:28                            */
/*==============================================================*/


drop table if exists address;

drop table if exists admin;

drop table if exists coupon;

drop table if exists dis_conn_goods;

drop table if exists discount_inf;

drop table if exists fresh;

drop table if exists goods;

drop table if exists goods_comment;

drop table if exists menu;

drop table if exists menu_recommand;

drop table if exists order_detail;

drop table if exists orders;

drop table if exists purchase_table;

drop table if exists timeLimitPromotion;

drop table if exists users;

/*==============================================================*/
/* Table: address                                               */
/*==============================================================*/
create table address
(
   address_id           int not null,
   order_id             int,
   user_id              int,
   province             varchar(50) not null,
   city                 varchar(50) not null,
   region               varchar(50) not null,
   contact_person       varchar(50) not null,
   contact_phoneNumber  varchar(50) not null,
   primary key (address_id)
);

/*==============================================================*/
/* Table: admin                                                 */
/*==============================================================*/
create table admin
(
   admin_id             int not null,
   admin_name           varchar(50) not null,
   admin_login_pwd      varchar(50) not null,
   primary key (admin_id)
);

/*==============================================================*/
/* Table: coupon                                                */
/*==============================================================*/
create table coupon
(
   coupon_id            int not null,
   order_id             int,
   coupon_content       varchar(50) not null,
   least_monet          double not null,
   sub_money            double not null,
   cp_beginTime         datetime,
   cp_endTime           datetime,
   primary key (coupon_id)
);

/*==============================================================*/
/* Table: dis_conn_goods                                        */
/*==============================================================*/
create table dis_conn_goods
(
   dis_inf_id           int not null,
   goods_id             int not null,
   begin_time           datetime not null,
   end_time             datetime not null,
   primary key (dis_inf_id, goods_id)
);

/*==============================================================*/
/* Table: discount_inf                                          */
/*==============================================================*/
create table discount_inf
(
   dis_inf_id           int not null,
   dis_inf_content      varchar(50) not null,
   leastgoods_number    int not null,
   discount             double not null,
   dis_beginTime        datetime,
   dis_endTime          datetime,
   primary key (dis_inf_id)
);

/*==============================================================*/
/* Table: fresh                                                 */
/*==============================================================*/
create table fresh
(
   catagory_id          int not null,
   catagory_name        varchar(50) not null,
   description          varchar(50) not null,
   primary key (catagory_id)
);

/*==============================================================*/
/* Table: goods                                                 */
/*==============================================================*/
create table goods
(
   goods_id             int not null,
   purchase_id          int,
   catagory_id          int,
   promotion_id         int,
   goods_name           varchar(50) not null,
   goods_price          double not null,
   vip_price            double not null,
   goods_number         int not null,
   spec                 double not null,
   detail               varchar(50) not null,
   primary key (goods_id)
);

/*==============================================================*/
/* Table: goods_comment                                         */
/*==============================================================*/
create table goods_comment
(
   goods_id             int not null,
   user_id              int not null,
   comment_content      varchar(50) not null,
   comment_date         datetime not null,
   comment_star         datetime not null,
   comment_pic          longblob not null,
   primary key (goods_id, user_id)
);

/*==============================================================*/
/* Table: menu                                                  */
/*==============================================================*/
create table menu
(
   menu_id              int not null,
   menu_name            varchar(50) not null,
   material             varchar(50) not null,
   step                 varchar(50) not null,
   picture              longblob not null,
   primary key (menu_id)
);

/*==============================================================*/
/* Table: menu_recommand                                        */
/*==============================================================*/
create table menu_recommand
(
   goods_id             int not null,
   menu_id              int not null,
   recommend_description varchar(50),
   primary key (goods_id, menu_id)
);

/*==============================================================*/
/* Table: order_detail                                          */
/*==============================================================*/
create table order_detail
(
   order_id             int not null,
   goods_id             int not null,
   dis_inf_id           int not null,
   number               int not null,
   price                double not null,
   discount             double not null,
   primary key (order_id, goods_id, dis_inf_id)
);

/*==============================================================*/
/* Table: orders                                                */
/*==============================================================*/
create table orders
(
   order_id             int not null,
   address_id           int,
   user_id              int,
   coupon_id            int,
   origin_price         double not null,
   settle_price         double not null,
   require_time         datetime not null,
   order_status         varchar(50) not null,
   primary key (order_id)
);

/*==============================================================*/
/* Table: purchase_table                                        */
/*==============================================================*/
create table purchase_table
(
   purchase_id          int not null,
   admin_id             int,
   purchase_number      int not null,
   purchase_status      varchar(50) not null,
   primary key (purchase_id)
);

/*==============================================================*/
/* Table: timeLimitPromotion                                    */
/*==============================================================*/
create table timeLimitPromotion
(
   promotion_id         int not null,
   goods_id             int,
   promotion_price      double not null,
   promotion_number     int not null,
   promotion_beginTime  datetime,
   promotion_endTime    datetime,
   primary key (promotion_id)
);

/*==============================================================*/
/* Table: users                                                 */
/*==============================================================*/
create table users
(
   user_id              int not null,
   user_name            varchar(50) not null,
   user_sex             varchar(10) not null,
   user_pwd             varchar(50) not null,
   user_phoneNumber     varchar(50) not null,
   user_email           varchar(50) not null,
   user_city            varchar(50) not null,
   user_regTime         datetime,
   vip                  bool not null,
   vip_endTime          datetime,
   primary key (user_id)
);

alter table address add constraint FK_设置 foreign key (user_id)
      references users (user_id) on delete restrict on update restrict;

alter table address add constraint FK_配送2 foreign key (order_id)
      references orders (order_id) on delete restrict on update restrict;

alter table coupon add constraint FK_使用2 foreign key (order_id)
      references orders (order_id) on delete restrict on update restrict;

alter table dis_conn_goods add constraint FK_dis_conn_goods foreign key (dis_inf_id)
      references discount_inf (dis_inf_id) on delete restrict on update restrict;

alter table dis_conn_goods add constraint FK_dis_conn_goods2 foreign key (goods_id)
      references goods (goods_id) on delete restrict on update restrict;

alter table goods add constraint FK_Relationship_1 foreign key (catagory_id)
      references fresh (catagory_id) on delete restrict on update restrict;

alter table goods add constraint FK_订购 foreign key (purchase_id)
      references purchase_table (purchase_id) on delete restrict on update restrict;

alter table goods add constraint FK_限时促销 foreign key (promotion_id)
      references timeLimitPromotion (promotion_id) on delete restrict on update restrict;

alter table goods_comment add constraint FK_goods_comment foreign key (goods_id)
      references goods (goods_id) on delete restrict on update restrict;

alter table goods_comment add constraint FK_goods_comment2 foreign key (user_id)
      references users (user_id) on delete restrict on update restrict;

alter table menu_recommand add constraint FK_menu_recommand foreign key (goods_id)
      references goods (goods_id) on delete restrict on update restrict;

alter table menu_recommand add constraint FK_menu_recommand2 foreign key (menu_id)
      references menu (menu_id) on delete restrict on update restrict;

alter table order_detail add constraint FK_order_detail foreign key (order_id)
      references orders (order_id) on delete restrict on update restrict;

alter table order_detail add constraint FK_order_detail2 foreign key (goods_id)
      references goods (goods_id) on delete restrict on update restrict;

alter table order_detail add constraint FK_order_detail3 foreign key (dis_inf_id)
      references discount_inf (dis_inf_id) on delete restrict on update restrict;

alter table orders add constraint FK_使用 foreign key (coupon_id)
      references coupon (coupon_id) on delete restrict on update restrict;

alter table orders add constraint FK_拥有 foreign key (user_id)
      references users (user_id) on delete restrict on update restrict;

alter table orders add constraint FK_配送 foreign key (address_id)
      references address (address_id) on delete restrict on update restrict;

alter table purchase_table add constraint FK_Relationship_7 foreign key (admin_id)
      references admin (admin_id) on delete restrict on update restrict;

alter table timeLimitPromotion add constraint FK_限时促销2 foreign key (goods_id)
      references goods (goods_id) on delete restrict on update restrict;

