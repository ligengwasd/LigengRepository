drop table if exists AD_CALENDAR;

drop table if exists AD_MENU;

drop table if exists AD_MENUGROUP;

drop table if exists AD_ORG;

drop table if exists AD_REFLIST;

drop table if exists AD_REFRENCE;

drop table if exists AD_SHOP;

drop table if exists AD_USER;

drop table if exists AD_USERGROUP;

drop table if exists AD_WAREHOUSE;

drop table if exists D_ORDER;

drop table if exists D_ORDERLINE;

drop table if exists GEO_CITY;

drop table if exists GEO_COUNTRY;

drop table if exists GEO_DISTRICT;

drop table if exists GEO_PROVINCE;

drop table if exists GEO_STREET;

drop table if exists M_BOM;

drop table if exists M_BOMLINE;

drop table if exists M_CARDHANDLER;

drop table if exists M_CARDTEMP;

drop table if exists M_CONVERT;

drop table if exists M_DELIVERPLAN;

drop table if exists M_GROUPMATERIAL;

drop table if exists M_MATERIAL;

drop table if exists M_MATERIALGROUP;

drop table if exists M_MATERIALGROUPSTANDARD;

drop table if exists M_MATERIALIMG;

drop table if exists M_PRICECHANGE;

drop table if exists M_SUPPLIER;

drop table if exists M_VIRTUALCARD;

drop table if exists P_ORDER;

drop table if exists P_ORDERLINE;

drop table if exists S_ACCOUNTLOG;

drop table if exists S_BONUS;

drop table if exists S_BONUSLINE;

drop table if exists S_CART;

drop table if exists S_COLLECTION;

drop table if exists S_COMMENT;

drop table if exists S_COMMENTIMG;

drop table if exists S_COMPLAINT;

drop table if exists S_FINDPASSWORD;

drop table if exists S_FREEBIEPLAN;

drop table if exists S_GIFTWARE;

drop table if exists S_ICONPLAN;

drop table if exists S_ORDER;

drop table if exists S_ORDERACTION;

drop table if exists S_ORDERLINE;

drop table if exists S_ORDERVIRTUALCARD;

drop table if exists S_PAYLINE;

drop table if exists S_POINTGAME;

drop table if exists S_POINTLOG;

drop table if exists S_PROMOTECODE;

drop table if exists S_PROMOTECODELINE;

drop table if exists S_PROMOTIONPLAN;

drop table if exists S_SEARCHLOG;

drop table if exists S_SECKILLPLAN;

drop table if exists S_SECONDHALFPLAN;

drop table if exists S_SHAKEGAME;

drop table if exists S_SHAKELOG;

drop table if exists S_SIGNIN;

drop table if exists S_SIGNINLOG;

drop table if exists S_USER;

drop table if exists S_USERADDRESS;

drop table if exists S_USERRANK;

drop table if exists S_WHOLESALE;

/*==============================================================*/
/* Table: AD_CALENDAR                                           */
/*==============================================================*/
create table AD_CALENDAR
(
   AD_CALENDAR_ID       varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   DELIVERY_START       datetime comment '配送开始',
   DELIVERY_END         datetime comment '配送结束',
   primary key (AD_CALENDAR_ID)
);

alter table AD_CALENDAR comment '销售时间表。主要用于支持预售。';

/*==============================================================*/
/* Table: AD_MENU                                                */
/*==============================================================*/
create table AD_MENU
(
   AD_MENU_ID           varchar(20) not null,
   PARENT_ID            varchar(20),
   MENU_NAME            varchar(200),
   URI                  varchar(200),
   LINE_NO              numeric(20,0),
   primary key (AD_MENU_ID)
);

/*==============================================================*/
/* Table: AD_MENUGROUP                                          */
/*==============================================================*/
create table AD_MENUGROUP
(
   AD_MENUGROUP_ID      varchar(20) not null,
   AD_USERGROUP_ID      varchar(20),
   AD_MENU_ID           varchar(20),
   LINE_NO              numeric(20,0),
   primary key (AD_MENUGROUP_ID)
);

/*==============================================================*/
/* Table: AD_ORG                                                */
/*==============================================================*/
create table AD_ORG
(
   AD_ORG_ID            varchar(20) not null,
   PARENT_ID            varchar(20) comment '父机构',
   AD_SHOP_ID           varchar(20),
   ORG_CODE             varchar(20) comment '机构代码',
   ORG_NAME             varchar(200) comment '机构名',
   LINE_NO              numeric(20,0) comment '行号',
   primary key (AD_ORG_ID)
);

/*==============================================================*/
/* Table: AD_REFLIST                                            */
/*==============================================================*/
create table AD_REFLIST
(
   AD_REFLIST_ID        varchar(20) not null,
   AD_REFRENCE_ID       varchar(20),
   REF_NAME             varchar(60) comment '名称',
   REF_VALUE            varchar(60) comment '值',
   DESCRIPTION          varchar(250) comment '描述',
   primary key (AD_REFLIST_ID)
);

alter table AD_REFLIST comment '列表内容';

/*==============================================================*/
/* Table: AD_REFRENCE                                           */
/*==============================================================*/
create table AD_REFRENCE
(
   AD_REFRENCE_ID       varchar(20) not null,
   REFRENCE_NAME        varchar(250) comment '列表名称',
   DESCRIPTION          varchar(250) comment '描述',
   primary key (AD_REFRENCE_ID)
);

alter table AD_REFRENCE comment '数据列表项';

/*==============================================================*/
/* Table: AD_SHOP                                               */
/*==============================================================*/
create table AD_SHOP
(
   AD_SHOP_ID           varchar(20) not null,
   SHOP_NAME            varchar(200) comment '店铺名称',
   SHOP_LOGO            varchar(200) comment '店铺logo',
   DISCRIPTION          varchar(200) comment '描述',
   primary key (AD_SHOP_ID)
);

alter table AD_SHOP comment '店铺';

/*==============================================================*/
/* Table: AD_USER                                               */
/*==============================================================*/
create table AD_USER
(
   AD_USER_ID           varchar(20) not null,
   AD_USERGROUP_ID      varchar(20),
   AD_ORG_ID            varchar(20),
   AD_SHOP_ID           varchar(20),
   REAL_NAME            varchar(200) comment '真实姓名',
   USER_NAME            varchar(200) comment '用户名',
   PASSWORD             varchar(200) comment '密码',
   EMAIL                varchar(200) comment '邮箱地址',
   LINE_NO              numeric(20,0) comment '行号',
   TEL                  varchar(200) comment '电话号码',
   USER_STATUS          varchar(20) comment '用户状态',
   LAST_LOGIN           datetime comment '最后登录时间',
   primary key (AD_USER_ID)
);

alter table AD_USER comment '管理员用户';

/*==============================================================*/
/* Table: AD_USERGROUP                                          */
/*==============================================================*/
create table AD_USERGROUP
(
   AD_USERGROUP_ID      varchar(20) not null,
   USERGROUP_NAME       varchar(200),
   LINE_NO              numeric(20,0),
   primary key (AD_USERGROUP_ID)
);

/*==============================================================*/
/* Table: AD_WAREHOUSE                                          */
/*==============================================================*/
create table AD_WAREHOUSE
(
   AD_WAREHOUSE_ID      varchar(20) not null,
   AD_SHOP_ID           varchar(20),
   primary key (AD_WAREHOUSE_ID)
);

alter table AD_WAREHOUSE comment '仓库
';

/*==============================================================*/
/* Table: D_ORDER                                               */
/*==============================================================*/
create table D_ORDER
(
   D_ORDER_ID           varchar(20) not null,
   S_ORDER_ID           varchar(20),
   AD_SHOP_ID           varchar(20),
   ORDER_SN             varchar(20) not null comment '订单号',
   ORDER_STATUS         varchar(20)   not null comment '订单状态0，未确认；1，已确认；2，已取消；3，无效；4，退货  5 表示拆单 拆单前的原单',
   CONSIGNEE            varchar(60) not null comment '收货人的姓名，用户页面填写',
   COUNTRY              varchar(20)   not null comment '国家',
   PROVINCE             varchar(20)   not null comment '省份',
   CITY                 varchar(20)   not null comment '城市',
   DISTRICT             varchar(20)   not null comment '区域',
   STREET               varchar(20) comment '街道',
   ADDRESS              varchar(255) not null comment '收货人详细地址',
   ZIPCODE              varchar(60) not null comment '邮编',
   TEL                  varchar(60) not null comment '电话',
   MOBILE               varchar(60) not null comment '手机',
   EMAIL                varchar(60) not null comment '电邮',
   GOODS_AMOUNT         numeric(18,2) not null comment '订单中的商品总价',
   RECEIVABLES          numeric(18,2) comment '应收款',
   ADD_TIME             datetime   not null comment '订单生成时间',
   CONFIRM_TIME         datetime   not null comment '订单确认时间',
   ARRIVE_DATE          datetime comment '送货日期',
   ARRIVE_TIME          varchar(20) comment '送货时间段，0代表全天 1代表早上8点到12点，2代表下午12点到16点，3代表晚上16点到20点 ，4代表8点到13点  5 代表13点到18点  默认是0代表全天',
   PLATE_NUMBER         varchar(20) comment '车牌号码 订单被分配的配送车车号',
   IS_IMPORT            char(1) comment '是否重要订单 1表示是 0表示普通订单 默认0',
   primary key (D_ORDER_ID)
);

alter table D_ORDER comment '配送订单';

/*==============================================================*/
/* Table: D_ORDERLINE                                           */
/*==============================================================*/
create table D_ORDERLINE
(
   D_ORDERLINE_ID       varchar(20) not null,
   D_ORDER_ID           varchar(20),
   M_MATERIAL_ID        varchar(20),
   S_ORDERLINE_ID       varchar(20),
   AD_SHOP_ID           varchar(20),
   primary key (D_ORDERLINE_ID)
);

alter table D_ORDERLINE comment '配送订单商品行';

/*==============================================================*/
/* Table: GEO_CITY                                              */
/*==============================================================*/
create table GEO_CITY
(
   GEO_CITY_ID          varchar(20) not null,
   GEO_PROVINCE_ID      varchar(20),
   CITY_NAME            varchar(60),
   primary key (GEO_CITY_ID)
);

/*==============================================================*/
/* Table: GEO_COUNTRY                                           */
/*==============================================================*/
create table GEO_COUNTRY
(
   GEO_COUNTRY_ID       varchar(20) not null,
   COUNTRY_NAME         varchar(60),
   primary key (GEO_COUNTRY_ID)
);

/*==============================================================*/
/* Table: GEO_DISTRICT                                          */
/*==============================================================*/
create table GEO_DISTRICT
(
   GEO_DISTRICT_ID      varchar(20) not null,
   GEO_CITY_ID          varchar(20),
   DISTRICT_NAME        varchar(60),
   primary key (GEO_DISTRICT_ID)
);

/*==============================================================*/
/* Table: GEO_PROVINCE                                          */
/*==============================================================*/
create table GEO_PROVINCE
(
   GEO_COUNTRY_ID       varchar(20),
   GEO_PROVINCE_ID      varchar(20) not null,
   PROVINCE_NAME        varchar(60),
   primary key (GEO_PROVINCE_ID)
);

/*==============================================================*/
/* Table: GEO_STREET                                            */
/*==============================================================*/
create table GEO_STREET
(
   GEO_STREET_ID        varchar(20) not null,
   GEO_DISTRICT_ID      varchar(20),
   STREET_NAME          varchar(60),
   primary key (GEO_STREET_ID)
);

/*==============================================================*/
/* Table: M_BOM                                                 */
/*==============================================================*/
create table M_BOM
(
   M_BOM_ID             varchar(20) not null,
   M_MATERIAL_ID        varchar(20) comment '父物料',
   AD_SHOP_ID           varchar(20),
   PRODUCT_QTY          numeric(20,0) comment '生产数量',
   BOM_TYPE             varchar(20) comment 'BOM类型',
   IS_ACTIVE            char(1) comment '是否有效',
   BOM_CODE             varchar(20) comment 'BOM单号',
   BOM_NAME             varchar(200) comment 'BOM名称',
   primary key (M_BOM_ID)
);

alter table M_BOM comment 'BOM';

/*==============================================================*/
/* Table: M_BOMLINE                                             */
/*==============================================================*/
create table M_BOMLINE
(
   M_BOMLINE_ID         varchar(20) not null,
   M_BOM_ID             varchar(20),
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   CONSUMETYPE          varchar(20) comment '消耗类型',
   CONSUMEFIXQTY        numeric(20,0) comment '消耗定额',
   LINE_NO              numeric(20,0) comment '行号',
   primary key (M_BOMLINE_ID)
);

alter table M_BOMLINE comment 'BOM组成物料';

/*==============================================================*/
/* Table: M_CARDHANDLER                                         */
/*==============================================================*/
create table M_CARDHANDLER
(
   M_CARDHANDLER_ID     varchar(20) not null,
   M_VIRTUALCARD_ID     varchar(20) comment '卡券',
   AD_SHOP_ID           varchar(20),
   HANDLE_TYPE          varchar(20) comment '处理类型',
   HANDLE_STATUE        varchar(20) comment '状态',
   DISCRIPTION          varchar(200) comment '描述',
   primary key (M_CARDHANDLER_ID)
);

alter table M_CARDHANDLER comment '卡券处理';

/*==============================================================*/
/* Table: M_CARDTEMP                                            */
/*==============================================================*/
create table M_CARDTEMP
(
   M_CARDTEMP_ID        varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   VIRTUALCARD_TYPE     varchar(20) comment '卡券类型',
   VIRTUALCARD_SN       varchar(60) comment '卡号',
   PASSWORD             varchar(20) comment '密码',
   ADD_DATE             datetime comment '添加时间',
   END_DATE             datetime comment '到期时间',
   IS_ACTIVE            char(1) comment '是否激活',
   primary key (M_CARDTEMP_ID)
);

alter table M_CARDTEMP comment '卡号临时表
';

/*==============================================================*/
/* Table: M_CONVERT                                             */
/*==============================================================*/
create table M_CONVERT
(
   M_CONVERT_ID         varchar(20) not null,
   VIRTUALCARD_ID       varchar(20) comment '卡券',
   MATERIAL_ID          varchar(20) comment '礼包',
   AD_SHOP_ID           varchar(20),
   primary key (M_CONVERT_ID)
);

alter table M_CONVERT comment '卡券兑换表';

/*==============================================================*/
/* Table: M_DELIVERPLAN                                         */
/*==============================================================*/
create table M_DELIVERPLAN
(
   M_DELIVERPLAN_PLAN   varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   SORDER_START         datetime comment '下单开始时间',
   SORDER_END           datetime comment '下单结束时间',
   PORDER_DATE          datetime comment '采购日期',
   RECEIVING_DATE       datetime comment '收货日期',
   DELIVER_DATE         datetime comment '配送日期',
   primary key (M_DELIVERPLAN_PLAN)
);

alter table M_DELIVERPLAN comment '配送计划';

/*==============================================================*/
/* Table: M_GROUPMATERIAL                                       */
/*==============================================================*/
create table M_GROUPMATERIAL
(
   M_GROUPMATERIAL_ID   varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   M_MATERIALGROUP_ID   varchar(20),
   AD_SHOP_ID           varchar(20),
   primary key (M_GROUPMATERIAL_ID)
);

alter table M_GROUPMATERIAL comment '物料与物料组的对照';

/*==============================================================*/
/* Table: M_MATERIAL                                            */
/*==============================================================*/
create table M_MATERIAL
(
   M_MATERIAL_ID        varchar(20) not null,
   M_SUPPLIER_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   ITEM_SN              varchar(200) comment '商品编码（货号）',
   MATERIAL_CODE        varchar(200) comment '物料编码',
   FOREIGN_CODE         varchar(200) comment '外部编码',
   MATERIAL_NAME        varchar(200) comment '物料名称',
   SHORT_NAME           varchar(200) comment '简称',
   SUBHEAD              varchar(200) comment '商品副标题',
   KEYWORDS             varchar(255) comment '关键字',
   BRAND                varchar(200) comment '品牌',
   STANDARD             varchar(200) comment '规格型号',
   DESCRIPTION          varchar(200) comment '描述',
   LENGTH               numeric(18,2) comment '长度',
   WIDTH                numeric(18,2) comment '宽度',
   HEIGHT               numeric(18,2) comment '高度',
   VOLUME               numeric(18,2) comment '体积',
   IS_WEIGHTED          char(1) comment '审重件',
   GROSSWEIGHT          numeric(18,2) comment '毛重',
   NETWEIGHT            numeric(18,2) comment '净重',
   STATUS               varchar(20) comment '状态',
   IS_REAL              char(1) comment '是否实物商品',
   WEB_ONSALE           char(1) comment '网站上架',
   WEB_PRICE            numeric(18,2)   comment '本店价格',
   ANDROIDPHONE_ONSALE  char(1) comment '安卓手机上架',
   IOSPHONE_ONSALE      char(1) comment '苹果手机上架',
   PHONE_PRICE          numeric(18,2) comment '手机端价格',
   ANDROIDPAD_ONSALE    char(1) comment '安卓平板上架',
   IOSPAD_ONSALE        char(1) comment '苹果平板上架',
   PAD_PRICE            numeric(18,2) comment '平板电脑端价格',
   WEIXIN_ONSALE        char(1) comment '微信上架',
   WEIXIN_PRICE         numeric(18,2) comment '微信价格',
   ADD_TIME             datetime comment '添加时间',
   LINE_NO              numeric(20,0) comment '行号，排序号',
   CONSUME_POINT        numeric(20,0) comment '购买此商品可使用积分的最高数量',
   GIFT_POINT           numeric(20,0) comment '赠送积分的基础积分',
   RANK_POINT           numeric(20,0) comment '赠送等级积分',
   PACKING              varchar(20) comment '包装材料',
   STORE_DAYS           varchar(20) comment '保质期',
   STORE_TYPE           varchar(20) comment '保存条件',
   IS_SOLDOUT           char(1) comment '是否售罄',
   IS_NORMALDELIVERY    char(1) comment '是否常规配送',
   primary key (M_MATERIAL_ID)
);

alter table M_MATERIAL comment '物料';

/*==============================================================*/
/* Table: M_MATERIALGROUP                                       */
/*==============================================================*/
create table M_MATERIALGROUP
(
   M_MATERIALGROUP_ID   varchar(20) not null,
   M_MATERIALGROUPSTANDARD_ID varchar(20) comment '分类标准',
   PARENT_ID            varchar(20) comment '父节点',
   AD_SHOP_ID           varchar(20),
   GROUP_NAME           varchar(200) comment '分类名称',
   GROUP_CODE           varchar(200) comment '分类编码',
   DISPLAY_NAME         varchar(200) comment '展示名称',
   ISLEAF               char(1) comment '是否叶子节点',
   GROUP_LEVEL          numeric(20,0) comment '分类层级',
   IS_ACTIVED           char(1),
   primary key (M_MATERIALGROUP_ID)
);

alter table M_MATERIALGROUP comment '物料组';

/*==============================================================*/
/* Table: M_MATERIALGROUPSTANDARD                               */
/*==============================================================*/
create table M_MATERIALGROUPSTANDARD
(
   M_MATERIALGROUPSTANDARD_ID varchar(20) not null,
   AD_SHOP_ID           varchar(20),
   MATERIALGROUPSTANDARD_NAME varchar(60) comment '名称',
   primary key (M_MATERIALGROUPSTANDARD_ID)
);

alter table M_MATERIALGROUPSTANDARD comment '物料分组标准';

/*==============================================================*/
/* Table: M_MATERIALIMG                                         */
/*==============================================================*/
create table M_MATERIALIMG
(
   M_MATERIALIMG_ID     varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   LINE_NO              numeric(20,0),
   MASTER_IMG           char(1),
   INF_IMG              char(1),
   URI                  varchar(200),
   THUM_URI             varchar(200),
   primary key (M_MATERIALIMG_ID)
);

alter table M_MATERIALIMG comment '物料图片';

/*==============================================================*/
/* Table: M_PRICECHANGE                                         */
/*==============================================================*/
create table M_PRICECHANGE
(
   M_PRICECHANGE_ID     varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   CHANGEDATE           datetime comment '价格发生变更的日期',
   NEWPRICE             numeric(18,2) comment '变更后的价格',
   primary key (M_PRICECHANGE_ID)
);

alter table M_PRICECHANGE comment '价格变更记录';

/*==============================================================*/
/* Table: M_SUPPLIER                                            */
/*==============================================================*/
create table M_SUPPLIER
(
   M_SUPPLIER_ID        varchar(20) not null,
   AD_SHOP_ID           varchar(20),
   SUPPLIER_CODE        varchar(200) comment '供应商编码',
   SUPPLIER_NAME        varchar(200) comment '供应商名称',
   SHORT_NAME           varchar(200) comment '简称',
   ENTNATURE            varchar(200) comment '企业性质',
   ARTIFICIALPERSON     varchar(200) comment '法人代表',
   TAXREGISTERNO        varchar(200) comment '税务登记号',
   BUSIEXEQUATUR        varchar(200) comment '生产经营许可证',
   BIZREGISTERNO        varchar(200) comment '工商注册号',
   BUSILICENCE          varchar(200) comment '营业执照',
   BIZANALYSISCODE      varchar(200) comment '业务分析码',
   DESCRIPTION          varchar(200) comment '描述',
   TAXDATA              varchar(200) comment '税种',
   TAXRATE              varchar(200) comment '税率',
   OFFICE_ADDRESS       varchar(200) comment '办公地址',
   WAREHOUSE_ADDRESS    varchar(200) comment '仓库地址',
   primary key (M_SUPPLIER_ID)
);

alter table M_SUPPLIER comment '供应商';

/*==============================================================*/
/* Table: M_VIRTUALCARD                                         */
/*==============================================================*/
create table M_VIRTUALCARD
(
   M_VIRTUALCARD_ID     varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   S_USER_ID            varchar(20) comment '充值卡使用者',
   VIRTUALCARD_TYPE     varchar(20) comment '卡券类型',
   VIRTUALCARD_SN       varchar(60) comment '卡号',
   PASSWORD             varchar(20) comment '密码',
   ADD_DATE             datetime comment '添加时间',
   END_DATE             datetime comment '到期时间',
   IS_ACTIVE            char(1) comment '是否激活',
   IS_DELIVERED         char(1) comment '是否已配送',
   primary key (M_VIRTUALCARD_ID)
);

alter table M_VIRTUALCARD comment '卡券';

/*==============================================================*/
/* Table: P_ORDER                                               */
/*==============================================================*/
create table P_ORDER
(
   P_ORDER_ID           varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   M_SUPPLIER_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   ADD_DATE             datetime comment '下单日期
            ',
   ORDERTYPE            varchar(20) comment '业务类型',
   CASHDISCOUNT         varchar(20) comment '现金折扣',
   DESCRIPTION          varchar(200) comment '描述',
   INVOICEDAMOUNT       numeric(18,2) comment '累计开票金额',
   TOTALAMOUNT          numeric(18,2) comment '商品总金额',
   TAXAMOUNT            numeric(18,2) comment '税款总金额',
   TOTALTAXAMOUNT       numeric(18,2) comment '价税合计',
   PAYMENTCONDITION     varchar(20) comment '付款条件',
   PAYMENTTYPE          varchar(20) comment '付款方式',
   SUPPLIERORDERNUMBER  varchar(200) comment '供应商订单号',
   DELIVERYADDRESS      varchar(200) comment '交货地址',
   DELIVERYDATE         datetime comment '交货日期',
   primary key (P_ORDER_ID)
);

/*==============================================================*/
/* Table: P_ORDERLINE                                           */
/*==============================================================*/
create table P_ORDERLINE
(
   P_ORDERLINE_ID       varchar(20) not null,
   P_ORDER_ID           varchar(20),
   AD_SHOP_ID           varchar(20),
   M_MATERIAL_ID        varchar(20),
   QTY                  numeric(18,2) comment '订货数量',
   PRICE                numeric(18,2) comment '商品单价',
   AMOUNT               numeric(18,2) comment '商品总金额',
   TAX                  numeric(18,2) comment '税额',
   TAXRATE              numeric(18,2) comment '税率',
   TAXAMOUNT            numeric(18,2) comment '价税合计',
   DISCOUNTAMOUNT       numeric(18,2) comment '折扣额',
   ISPRESENT            char(1) comment '赠品',
   LINE_NO              numeric(20,0) comment '行号',
   RECEIVEQTY           numeric(18,2) comment '收货数量',
   primary key (P_ORDERLINE_ID)
);

/*==============================================================*/
/* Table: S_ACCOUNTLOG                                          */
/*==============================================================*/
create table S_ACCOUNTLOG
(
   S_ACCOUNTLOG_ID      varchar(20) not null,
   S_PAYLINE_ID         varchar(20) comment '关联支付行',
   S_USER_ID            varchar(20) comment '用户',
   M_VIRTUALCARD_ID     varchar(20) comment '充值对应的卡号',
   AD_SHOP_ID           varchar(20),
   BIZ_TYPE             varchar(20) comment '业务类型【充值，消费，人工添加】',
   AMOUNT               numeric(18,2) comment '金额',
   primary key (S_ACCOUNTLOG_ID)
);

alter table S_ACCOUNTLOG comment '余额账户日志';

/*==============================================================*/
/* Table: S_BONUS                                               */
/*==============================================================*/
create table S_BONUS
(
   S_BONUS_ID           varchar(20) not null,
   AD_SHOP_ID           varchar(20),
   BONUS_NAME           varchar(60) comment '抵用券名称',
   BONUS_STATUE         varchar(20) comment '抵用券状态',
   BONUS_AMOUNT         numeric(18,2) comment '抵用券金额',
   ORDER_AMOUNT         numeric(18,2) comment '使用抵用券的最低限额',
   BONUS_TYPE           varchar(20) comment '抵用券类型',
   BONUS_ROUTE          varchar(20) comment '发放途径',
   SEND_STARTDATE       datetime comment '开始发放日期',
   SEND_ENDDATE         datetime comment '结束发放日期',
   START_DATE           datetime comment '开始使用日期',
   END_DATE             datetime comment '结束使用日期',
   primary key (S_BONUS_ID)
);

alter table S_BONUS comment '抵用券';

/*==============================================================*/
/* Table: S_BONUSLINE                                           */
/*==============================================================*/
create table S_BONUSLINE
(
   S_BONUSLINE_ID       varchar(20) not null,
   S_BONUS_ID           varchar(20) comment '抵用券',
   S_ORDER_ID           varchar(20) comment '使用该抵用券的订单',
   S_USER_ID            varchar(20) comment '抵用券持有者',
   AD_SHOP_ID           varchar(20),
   BOUNS_SN             varchar(20) comment '抵用券号',
   BOUNS_STATUE         varchar(20) comment '抵用券状态',
   primary key (S_BONUSLINE_ID)
);

alter table S_BONUSLINE comment '抵用券号码';

/*==============================================================*/
/* Table: S_CART                                                */
/*==============================================================*/
create table S_CART
(
   S_CART_ID            varchar(20) not null,
   S_USER_ID            varchar(20),
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   primary key (S_CART_ID)
);

alter table S_CART comment '购物车';

/*==============================================================*/
/* Table: S_COLLECTION                                          */
/*==============================================================*/
create table S_COLLECTION
(
   S_COLLECTION_ID      varchar(20) not null,
   S_USER_ID            varchar(20) comment '用户',
   M_MATERIAL_ID        varchar(20) comment '收藏商品',
   AD_SHOP_ID           varchar(20),
   ADD_TIME             datetime comment '收藏时间',
   IS_SUBSCRIBED        char(1) comment '活动信息订阅',
   primary key (S_COLLECTION_ID)
);

alter table S_COLLECTION comment '客户收藏夹';

/*==============================================================*/
/* Table: S_COMMENT                                             */
/*==============================================================*/
create table S_COMMENT
(
   S_COMMENT_ID         varchar(20) not null,
   S_USER_ID            varchar(20) comment '用户',
   M_MATERIAL_ID        varchar(20) comment '物料',
   PARENT_ID            varchar(20) comment '父评论',
   IP_ADDRESS           varchar(20) comment 'ip地址',
   CONTENT              varchar(250) comment '评论内容',
   ADD_TIME             datetime comment '评论时间',
   COMMENT_STATUE       varchar(20) comment '评论状态',
   COMMENT_FROM         varchar(20) comment '评论来自',
   primary key (S_COMMENT_ID)
);

alter table S_COMMENT comment '客户评论';

/*==============================================================*/
/* Table: S_COMMENTIMG                                          */
/*==============================================================*/
create table S_COMMENTIMG
(
   S_COMMENTIMG_ID      varchar(20) not null,
   S_COMMENT_ID         varchar(20),
   URI                  varchar(200) comment '图片地址',
   THUM_URI             varchar(200) comment '缩略图地址',
   primary key (S_COMMENTIMG_ID)
);

alter table S_COMMENTIMG comment '评论图片';

/*==============================================================*/
/* Table: S_COMPLAINT                                           */
/*==============================================================*/
create table S_COMPLAINT
(
   S_COMPLAINT_ID       varchar(20) not null,
   primary key (S_COMPLAINT_ID)
);

alter table S_COMPLAINT comment '投诉直通车';

/*==============================================================*/
/* Table: S_FINDPASSWORD                                        */
/*==============================================================*/
create table S_FINDPASSWORD
(
   S_FINDPASSWORD_ID    varchar(20) not null,
   S_USER_ID            varchar(20),
   EMAIL                varchar(200),
   URI                  varchar(200),
   ADD_TIME             datetime,
   IP_ADDRESS           varchar(20),
   primary key (S_FINDPASSWORD_ID)
);

alter table S_FINDPASSWORD comment '找回密码链接';

/*==============================================================*/
/* Table: S_FREEBIEPLAN                                         */
/*==============================================================*/
create table S_FREEBIEPLAN
(
   S_FREEBIEPLAN_ID     varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   FREEBIE_MATERIAL_ID  varchar(20) comment '赠品',
   AD_SHOP_ID           varchar(20),
   START_TIME           datetime comment '开始时间',
   END_TIME             datetime comment '结束时间',
   IS_ACTIVE            char(1) comment '是否生效',
   FREEBIE_NUM          numeric(20,0) comment '赠送数量',
   primary key (S_FREEBIEPLAN_ID)
);

alter table S_FREEBIEPLAN comment '买赠';

/*==============================================================*/
/* Table: S_GIFTWARE                                            */
/*==============================================================*/
create table S_GIFTWARE
(
   S_GIFTWARE_ID        varchar(20) not null,
   S_USER_ID            varchar(20) comment '用户',
   M_MATERIAL_ID        varchar(20) comment '礼品',
   AD_SHOP_ID           varchar(20),
   QTY                  numeric(20,0) comment '数量',
   END_TIME             datetime comment '到期时间',
   PRICE                numeric(18,2) comment '价格',
   DISCRIPTION          varchar(255) comment '描述',
   GIFT_STATUS          varchar(20) comment '状态',
   primary key (S_GIFTWARE_ID)
);

alter table S_GIFTWARE comment '用户所获得赠品列表';

/*==============================================================*/
/* Table: S_ICONPLAN                                            */
/*==============================================================*/
create table S_ICONPLAN
(
   S_ICONPLAN_ID        varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   START_TIME           datetime comment '开始时间',
   END_TIME             datetime comment '结束时间',
   IS_ACTIVE            char(1) comment '是否生效',
   ICON_TYPE            varchar(20),
   primary key (S_ICONPLAN_ID)
);

alter table S_ICONPLAN comment 'ICON计划';

/*==============================================================*/
/* Table: S_ORDER                                               */
/*==============================================================*/
create table S_ORDER
(
   S_ORDER_ID           varchar(20) not null,
   S_USER_ID            varchar(20),
   AD_SHOP_ID           varchar(20),
   ORDER_SN             varchar(20) not null comment '订单号',
   ORDER_TYPE           varchar(60) not null comment '订单类型 
            兑换、补送、配送单（宅配业务的）、渠道零售、渠道团购
            活动、内部单、调拨、退单、配送退单、冲单、虚拟单',
   ORDER_STATUS         varchar(20)   not null comment '订单状态',
   POSTSCRIPT           varchar(255) not null comment '订单附言',
   INV_TYPE             varchar(60) not null comment '发票类型',
   INV_PAYEE            varchar(120) not null comment '发票抬头，用户页面填写''',
   INV_CONTENT          varchar(120) not null comment '发票内容，用户页面选择',
   GOODS_AMOUNT         numeric(18,2) not null comment '订单中的商品总价',
   SHIP_AMOUNT          numeric(18,2) comment '订单运费总价',
   ORDER_FROM           varchar(60) not null comment '订单的来源',
   ORDER_BENIFIT        varchar(60) comment '业绩部门',
   ADD_TIME             datetime   not null comment '订单生成时间',
   CONFIRM_TIME         datetime   not null comment '订单确认时间',
   IS_IMPORT            char(1) comment '是否重要订单 1表示是 0表示普通订单 默认0',
   primary key (S_ORDER_ID)
);

alter table S_ORDER comment '销售订单';

/*==============================================================*/
/* Table: S_ORDERACTION                                         */
/*==============================================================*/
create table S_ORDERACTION
(
   S_ORDERACTION_ID     varchar(20) not null,
   S_ORDER_ID           varchar(20),
   D_ORDER_ID           varchar(20),
   AD_SHOP_ID           varchar(20),
   ORDER_STATUS         varchar(20),
   ACTION_NOTE          varchar(20),
   ADD_TIME             datetime,
   primary key (S_ORDERACTION_ID)
);

alter table S_ORDERACTION comment '订单操作';

/*==============================================================*/
/* Table: S_ORDERLINE                                           */
/*==============================================================*/
create table S_ORDERLINE
(
   S_ORDERLINE_ID       varchar(20) not null,
   S_ORDER_ID           varchar(20),
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   PRICE                numeric(18,2) not null comment '商品价格',
   QTY                  numeric(20,0) comment '数量',
   DISCOUNT             numeric(18,2) comment '折扣金额',
   GOODS_POINTS         numeric(20,0) comment '可获得的积分',
   PROMOTE_TYPE         varchar(20) comment '促销类型，目前规定1秒杀 ，2特价，3买赠的买的商品，4第二件半价商品，5换购商品，6摇一摇赠品，7大转盘赠品，8活动赠品 9多买优惠 10团购商品 11第二件起特价',
   HAVE_COMMENTED       varchar(20) comment '商品是否晒单评论 1已经晒单评论，0未晒单评论',
   primary key (S_ORDERLINE_ID)
);

alter table S_ORDERLINE comment '销售订单订单行';

/*==============================================================*/
/* Table: S_ORDERVIRTUALCARD                                    */
/*==============================================================*/
create table S_ORDERVIRTUALCARD
(
   S_ORDERVIRTUALCARD_ID varchar(20) not null,
   M_VIRTUALCARD_ID     varchar(20) comment '兑换券',
   S_ORDER_ID           varchar(20) comment '销售订单,兑换订单',
   D_ORDER_ID           varchar(20),
   AD_SHOP_ID           varchar(20),
   REL_TYPE             varchar(20) comment '关联方式（销售关联,兑换关联,配送关联）',
   DELIVERE_STATUS      varchar(20) comment '配送状态',
   primary key (S_ORDERVIRTUALCARD_ID)
);

alter table S_ORDERVIRTUALCARD comment '销售订单中的卡号  配送订单中的卡号  兑换订单中的卡号';

/*==============================================================*/
/* Table: S_PAYLINE                                             */
/*==============================================================*/
create table S_PAYLINE
(
   S_PAYLINE_ID         varchar(20) not null,
   S_ORDER_ID           varchar(20),
   AD_SHOP_ID           varchar(20),
   PAY_TYPE             varchar(20) comment '支付类型【收款，退款】',
   PAY_MODE             varchar(20) comment '支付方式【优惠码，抵用券，账户余额，支付宝，财付通，银行..........】',
   PAY_STATUS           varchar(20) comment '支付状态',
   PAY_AMONT            numeric(20,0) comment '支付金额',
   PAY_TIME             datetime comment '支付时间',
   PAY_SN               varchar(20) comment '在线支付时，支付渠道返回的支付流水号',
   primary key (S_PAYLINE_ID)
);

alter table S_PAYLINE comment '订单支付信息';

/*==============================================================*/
/* Table: S_POINTGAME                                           */
/*==============================================================*/
create table S_POINTGAME
(
   S_POINTGAME_ID       varchar(20) not null,
   primary key (S_POINTGAME_ID)
);

alter table S_POINTGAME comment '积分活动规则';

/*==============================================================*/
/* Table: S_POINTLOG                                            */
/*==============================================================*/
create table S_POINTLOG
(
   S_POINTLOG_ID        varchar(20) not null,
   S_USER_ID            varchar(20),
   S_ORDER_ID           varchar(20),
   S_PAYLINE_ID         varchar(20),
   AD_SHOP_ID           varchar(20),
   BIZ_TYPE             varchar(20) comment '业务类型',
   POINT_QTY            numeric(20,0) comment '积分数量',
   DESCRIPTION          varchar(255) comment '描述',
   primary key (S_POINTLOG_ID)
);

alter table S_POINTLOG comment '积分日志';

/*==============================================================*/
/* Table: S_PROMOTECODE                                         */
/*==============================================================*/
create table S_PROMOTECODE
(
   S_PROMOTECODE_ID     varchar(20) not null,
   AD_SHOP_ID           varchar(20),
   PROMOTECODE_NAME     varchar(200) comment '优惠码名称',
   PROMOTECODE_TYPE     varchar(20) comment '优惠类型',
   START_DATE           datetime comment '开始使用日期',
   END_DATE             datetime comment '结束使用日期',
   ADD_DATE             datetime comment '添加日期',
   primary key (S_PROMOTECODE_ID)
);

alter table S_PROMOTECODE comment '优惠码';

/*==============================================================*/
/* Table: S_PROMOTECODELINE                                     */
/*==============================================================*/
create table S_PROMOTECODELINE
(
   S_PROMOTECODELINE_ID varchar(20) not null,
   S_PROMOTECODE_ID     varchar(20),
   AD_SHOP_ID           varchar(20),
   PROMOTECODE_SN       varchar(200) comment '优惠码号码',
   START_DATE           datetime comment '开始使用日期',
   END_DATE             datetime comment '结束使用日期',
   ADD_DATE             datetime comment '添加日期',
   MIN_ORDERAMT         numeric(18,2) comment '最小订单金额',
   MAX_ORDERAMT         numeric(18,2) comment '最大订单金额',
   PROMOTE_AMT          numeric(18,2) comment '优惠金额',
   M_MATERIAL_ID        varchar(20) comment '赠品',
   primary key (S_PROMOTECODELINE_ID)
);

/*==============================================================*/
/* Table: S_PROMOTIONPLAN                                       */
/*==============================================================*/
create table S_PROMOTIONPLAN
(
   S_PROMOTIONPLAN_ID   varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   START_TIME           datetime comment '开始时间',
   END_TIME             datetime comment '结束时间',
   PROMOTION_PRICE      numeric(18,2) comment '促销价格',
   IS_ACTIVE            char(1) comment '是否生效',
   primary key (S_PROMOTIONPLAN_ID)
);

alter table S_PROMOTIONPLAN comment '促销计划';

/*==============================================================*/
/* Table: S_SEARCHLOG                                           */
/*==============================================================*/
create table S_SEARCHLOG
(
   S_SEARCHLOG_ID       varchar(20) not null,
   primary key (S_SEARCHLOG_ID)
);

alter table S_SEARCHLOG comment '搜索日志
';

/*==============================================================*/
/* Table: S_SECKILLPLAN                                         */
/*==============================================================*/
create table S_SECKILLPLAN
(
   S_SECKILLPLAN_ID     varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   START_TIME           datetime comment '开始时间',
   END_TIME             datetime comment '结束时间',
   SECKILL_PRICE        numeric(18,2) comment '秒杀价格',
   SECKILL_NUM          numeric(20,0) comment '秒杀限量',
   IS_ACTIVE            char(1) comment '是否生效',
   primary key (S_SECKILLPLAN_ID)
);

alter table S_SECKILLPLAN comment '秒杀计划';

/*==============================================================*/
/* Table: S_SECONDHALFPLAN                                      */
/*==============================================================*/
create table S_SECONDHALFPLAN
(
   S_SECONDHALF_ID      varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   START_TIME           datetime comment '开始时间',
   END_TIME             datetime comment '结束时间',
   IS_ACTIVE            char(1) comment '是否生效',
   primary key (S_SECONDHALF_ID)
);

alter table S_SECONDHALFPLAN comment '第二件半价活动计划';

/*==============================================================*/
/* Table: S_SHAKEGAME                                           */
/*==============================================================*/
create table S_SHAKEGAME
(
   S_SHAKEGAME_ID       varchar(20) not null,
   primary key (S_SHAKEGAME_ID)
);

alter table S_SHAKEGAME comment '摇一摇游戏设定';

/*==============================================================*/
/* Table: S_SHAKELOG                                            */
/*==============================================================*/
create table S_SHAKELOG
(
   S_SHAKELOG_ID        varchar(20) not null,
   S_USER_ID            varchar(20),
   ADD_TIME             datetime comment '时间',
   SHAKE_TYPE           varchar(20) comment '摇一摇类型',
   SHAKE_RESULT         varchar(20) comment '结果',
   primary key (S_SHAKELOG_ID)
);

alter table S_SHAKELOG comment '摇一摇日志';

/*==============================================================*/
/* Table: S_SIGNIN                                              */
/*==============================================================*/
create table S_SIGNIN
(
   S_SIGNIN_ID          varchar(20) not null,
   S_USER_ID            varchar(20),
   SIGNIN_NAME          varchar(200) comment '签到活动名称',
   START_DATE           datetime comment '开始使用日期',
   END_DATE             datetime comment '结束使用日期',
   CONTINUITY_QTY       numeric(20,0) comment '连续签到次数',
   QTY                  numeric(20,0) comment '可兑换签到次数',
   primary key (S_SIGNIN_ID)
);

alter table S_SIGNIN comment '签到活动';

/*==============================================================*/
/* Table: S_SIGNINLOG                                           */
/*==============================================================*/
create table S_SIGNINLOG
(
   S_SIGNINLOG_ID       varchar(20) not null,
   S_SIGNIN_ID          varchar(20),
   SIGNIN_TYPE          varchar(20) comment '业务类型（签到,领奖）',
   SIGNIN_TIME          datetime comment '签到时间',
   USED_TIME            datetime comment '领奖时间',
   USED_CONTINUITYQTY   numeric(20,0) comment '使用连续签到次数',
   USED_QTY             numeric(20,0) comment '使用可兑换签到次数',
   SIGNIN_FROM          varchar(20) comment '签到来源',
   primary key (S_SIGNINLOG_ID)
);

alter table S_SIGNINLOG comment '签到日志';

/*==============================================================*/
/* Table: S_USER                                                */
/*==============================================================*/
create table S_USER
(
   S_USER_ID            varchar(20) not null,
   USER_NAME            varchar(60) comment '用户名',
   PASSWORD             varchar(60) comment '用户密码 MD5加密',
   REAL_NAME            varchar(60) comment '真实姓名',
   AUTHORISED_PHONE     varchar(60) comment '授权电话',
   ALIAS                varchar(60) comment '昵称',
   EMAIL                varchar(60) comment '邮件',
   USER_THUMB           varchar(60) comment '用户头像缩略图',
   USER_IMAGE           varchar(60) comment '用户头像',
   GENERAL_IMAGE        varchar(60) comment '用户头衔原图',
   QUESTION             varchar(255) comment '密码提示问题',
   ANSWER               varchar(255) comment '密码提示问题答案',
   SEX                  varchar(20)   comment '性别',
   BIRTHDAY             datetime comment '出生日期',
   USER_MONEY           numeric(18,2) comment '用户现有资金 也就是账户余额，默认值为0 ，2013新版中账户余额是包含alipay_money,card_money,bonus_money三者之和  使用余额扣款先后顺序 支付宝 - 卡券余额-返利金额',
   ONLINE_MONEY         numeric(18,2) comment '支付宝充值进入的账户金额 默认值0 每次充值自加，并同时更新user_money',
   CARD_MONEY           numeric(18,2) comment '菜管家充值卡充值的金额，默认值0，每次充值自加',
   BONUS_MONEY          numeric(18,2) comment '菜管家返利的金额，默认值0，每次返利自加',
   FROZEN_MONEY         numeric(18,2) comment '冻结资金',
   PAY_POINTS           numeric(20,0)   comment '消费积分 可以用来支付订单的积分 100分一块钱 2013年年底开始目前积分存在有效期，默认是次年年底',
   RANK_POINTS          numeric(20,0)   comment '等级积分数',
   REG_TIME             datetime   comment '注册时间',
   REG_FROM             varchar(20) comment '注册来源（官网,app,微信）',
   LAST_LOGIN           datetime   comment '上次登录时间',
   LAST_TIME            datetime comment '最后一次修改信息时间',
   LAST_IP              varchar(20) comment '上次登录ip',
   VISIT_COUNT          numeric(20,0)   comment '访问次数',
   USER_RANK            numeric(20,0)   comment '''会员登记id，取值ecs_user_rank',
   IS_SPECIAL           char(1)   comment '特殊会员 1表示重要会员，该会员下的订单默认为重要订单',
   MSN                  varchar(60) comment 'msn',
   QQ                   varchar(60) comment 'qq',
   WEIXIN               varchar(60) comment '微信openID',
   SINA_WEIBO           varchar(60) comment '微博号(sina微博)',
   QQ_WEIBO             varchar(60) comment '微博号(qq微博)',
   OFFICE_PHONE         varchar(60) comment '办公电话',
   HOME_PHONE           varchar(60) comment '家庭电话',
   MOBILE_PHONE         varchar(60) comment '手机',
   IS_VALIDATED         char(1)   comment '是否有效N',
   CREDIT_LINE          numeric(18,2)   comment '信用额度',
   ALIPAY_USER_ID       varchar(60) comment '支付宝用户id',
   IDENTITY_CARD        varchar(60) comment '身份证号',
   ZIPCODE              varchar(60) comment '邮编',
   primary key (S_USER_ID)
);

alter table S_USER comment '客户';

/*==============================================================*/
/* Table: S_USERADDRESS                                         */
/*==============================================================*/
create table S_USERADDRESS
(
   S_USERADDRESS_ID     varchar(20) not null,
   S_USER_ID            varchar(20),
   CONSIGNEE            varchar(60) comment '收货人',
   EMAIL                varchar(60) comment '邮件',
   COUNTRY              varchar(60) comment '国家',
   PROVINCE             varchar(60) comment '省份',
   CITY                 varchar(60) comment '城市',
   DISTRICT             varchar(60) comment '区域',
   ADDRESS              varchar(120) comment '详细地址',
   ZIPCODE              varchar(60) comment '邮编',
   TEL                  varchar(60) comment '电话',
   MOBILE               varchar(60) comment '手机',
   IS_DEFAULT           char(1) comment '是否默认地址',
   RECEIVE_DM           char(1) comment '是否接受DM',
   primary key (S_USERADDRESS_ID)
);

alter table S_USERADDRESS comment '客户地址';

/*==============================================================*/
/* Table: S_USERRANK                                            */
/*==============================================================*/
create table S_USERRANK
(
   S_USERRANK_ID        varchar(20) not null,
   RANK_NAME            varchar(60) comment '等级名',
   MIN_POINTS           numeric(20,0)   comment '最小分数',
   MAX_POINTS           numeric(20,0)   comment '最大分数',
   DISCOUNT             numeric(20,0)   comment '折扣',
   INTEGRAL_TIMES       numeric(18,2) comment '积分返点倍数',
   primary key (S_USERRANK_ID)
);

alter table S_USERRANK comment '用户等级设定规则';

/*==============================================================*/
/* Table: S_WHOLESALE                                           */
/*==============================================================*/
create table S_WHOLESALE
(
   S_WHOLESALE_ID       varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   START_TIME           datetime comment '开始时间',
   END_TIME             datetime comment '结束时间',
   IS_ACTIVE            char(1) comment '是否生效',
   FIRSTSTEP_NUM        numeric(20,0),
   FIRSTSTEP_PRICE      numeric(18,2),
   SECONDSTEP_NUM       numeric(20,0),
   SECONDSTEP_PRICE     numeric(18,2),
   THRID_NUM            numeric(20,0),
   THRID_PRICE          numeric(18,2),
   primary key (S_WHOLESALE_ID)
);

alter table S_WHOLESALE comment '菜菜团';

alter table AD_CALENDAR add constraint CALENDAR_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table AD_CALENDAR add constraint CALENDAR_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table AD_MENU add constraint MENU_MENU foreign key (PARENT_ID)
      references AD_MENU (AD_MENU_ID) on delete restrict on update restrict;

alter table AD_MENUGROUP add constraint MENUGOUP_USERGOUP foreign key (AD_USERGROUP_ID)
      references AD_USERGROUP (AD_USERGROUP_ID) on delete restrict on update restrict;

alter table AD_MENUGROUP add constraint MENUGOUP_MENU foreign key (AD_MENU_ID)
      references AD_MENU (AD_MENU_ID) on delete restrict on update restrict;

alter table AD_ORG add constraint ORG_ORG foreign key (PARENT_ID)
      references AD_ORG (AD_ORG_ID) on delete restrict on update restrict;

alter table AD_ORG add constraint ORG_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table AD_REFLIST add constraint REFLIST_REFRENCE foreign key (AD_REFRENCE_ID)
      references AD_REFRENCE (AD_REFRENCE_ID) on delete restrict on update restrict;

alter table AD_USER add constraint USER_USERGROUP foreign key (AD_USERGROUP_ID)
      references AD_USERGROUP (AD_USERGROUP_ID) on delete restrict on update restrict;

alter table AD_USER add constraint USER_ORG foreign key (AD_ORG_ID)
      references AD_ORG (AD_ORG_ID) on delete restrict on update restrict;

alter table AD_USER add constraint USER_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table AD_WAREHOUSE add constraint WAREHOUSE_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table D_ORDER add constraint ORDER_S_ORDER foreign key (S_ORDER_ID)
      references S_ORDER (S_ORDER_ID) on delete restrict on update restrict;

alter table D_ORDER add constraint ORDER_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table D_ORDERLINE add constraint DORDERLINE_DORDER foreign key (D_ORDER_ID)
      references D_ORDER (D_ORDER_ID) on delete restrict on update restrict;

alter table D_ORDERLINE add constraint DORDERLINE_SORDERLINE foreign key (S_ORDERLINE_ID)
      references S_ORDERLINE (S_ORDERLINE_ID) on delete restrict on update restrict;

alter table D_ORDERLINE add constraint DORDERLINE_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table D_ORDERLINE add constraint DORDERLINE_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table GEO_CITY add constraint CITY_PROVINCE foreign key (GEO_PROVINCE_ID)
      references GEO_PROVINCE (GEO_PROVINCE_ID) on delete restrict on update restrict;

alter table GEO_DISTRICT add constraint DISTRICT_CITY foreign key (GEO_CITY_ID)
      references GEO_CITY (GEO_CITY_ID) on delete restrict on update restrict;

alter table GEO_PROVINCE add constraint PROVINCE_COUNTRY foreign key (GEO_COUNTRY_ID)
      references GEO_COUNTRY (GEO_COUNTRY_ID) on delete restrict on update restrict;

alter table GEO_STREET add constraint STREET_DISTRICT foreign key (GEO_DISTRICT_ID)
      references GEO_DISTRICT (GEO_DISTRICT_ID) on delete restrict on update restrict;

alter table M_BOM add constraint BOM_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table M_BOM add constraint BOM_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table M_BOMLINE add constraint BOMLINE_BOM foreign key (M_BOM_ID)
      references M_BOM (M_BOM_ID) on delete restrict on update restrict;

alter table M_BOMLINE add constraint BOMLINE_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table M_BOMLINE add constraint BOMLINE_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table M_CARDHANDLER add constraint CARDHANDLER_VIRTUALCARD foreign key (M_VIRTUALCARD_ID)
      references M_VIRTUALCARD (M_VIRTUALCARD_ID) on delete restrict on update restrict;

alter table M_CARDHANDLER add constraint CARDHANDLER_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table M_CARDTEMP add constraint CARDTEMP_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table M_CARDTEMP add constraint CARDTEMP_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table M_CONVERT add constraint CONVERT_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table M_CONVERT add constraint CONVERT_MATERIAL2 foreign key (MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table M_CONVERT add constraint CONVERT_MATERIAL1 foreign key (VIRTUALCARD_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table M_DELIVERPLAN add constraint DELIVERPLAN_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table M_DELIVERPLAN add constraint DELIVERPLAN_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table M_GROUPMATERIAL add constraint GOUPMATERIAL_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table M_GROUPMATERIAL add constraint MATERIAL_MATERIALGROUP foreign key (M_MATERIALGROUP_ID)
      references M_MATERIALGROUP (M_MATERIALGROUP_ID) on delete restrict on update restrict;

alter table M_GROUPMATERIAL add constraint GROUPMATERIAL_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table M_MATERIAL add constraint MATERIAL_SUPPLIER foreign key (M_SUPPLIER_ID)
      references M_SUPPLIER (M_SUPPLIER_ID) on delete restrict on update restrict;

alter table M_MATERIAL add constraint MATERIAL_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table M_MATERIALGROUP add constraint MATERIALGROUP_PARENT foreign key (PARENT_ID)
      references M_MATERIALGROUP (M_MATERIALGROUP_ID) on delete restrict on update restrict;

alter table M_MATERIALGROUP add constraint GROUP_STANDAR foreign key (M_MATERIALGROUPSTANDARD_ID)
      references M_MATERIALGROUPSTANDARD (M_MATERIALGROUPSTANDARD_ID) on delete restrict on update restrict;

alter table M_MATERIALGROUP add constraint MATERIALGROUP_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table M_MATERIALGROUPSTANDARD add constraint MATERIALGROUPSTANDARD_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table M_MATERIALIMG add constraint MATERIALIMG_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table M_MATERIALIMG add constraint MATERIAIMG_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table M_PRICECHANGE add constraint PRICECHANGE_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table M_PRICECHANGE add constraint PRICECHANGE_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table M_SUPPLIER add constraint SUPPLIER_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table M_VIRTUALCARD add constraint VIRTUALCARD_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table M_VIRTUALCARD add constraint VIRTUALCARD_USER foreign key (S_USER_ID)
      references S_USER (S_USER_ID) on delete restrict on update restrict;

alter table P_ORDER add constraint P_ORDER_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table P_ORDER add constraint PORDER_SUPPLIER foreign key (M_SUPPLIER_ID)
      references M_SUPPLIER (M_SUPPLIER_ID) on delete restrict on update restrict;

alter table P_ORDER add constraint PORDER_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table P_ORDERLINE add constraint PORDERLINE_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table P_ORDERLINE add constraint P_ORDERLINE_ORDER foreign key (P_ORDER_ID)
      references P_ORDER (P_ORDER_ID) on delete restrict on update restrict;

alter table P_ORDERLINE add constraint PORDERLINE_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_ACCOUNTLOG add constraint ACCOUNTLOG_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_ACCOUNTLOG add constraint ACCOUNTLOG_PAYLINE foreign key (S_PAYLINE_ID)
      references S_PAYLINE (S_PAYLINE_ID) on delete restrict on update restrict;

alter table S_ACCOUNTLOG add constraint ACCOUNTLOG_USER foreign key (S_USER_ID)
      references S_USER (S_USER_ID) on delete restrict on update restrict;

alter table S_ACCOUNTLOG add constraint ACCOUNTLOG_VIRTUALCARD foreign key (M_VIRTUALCARD_ID)
      references M_VIRTUALCARD (M_VIRTUALCARD_ID) on delete restrict on update restrict;

alter table S_BONUS add constraint BONUS_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_BONUSLINE add constraint BONUSLINE_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_BONUSLINE add constraint BONUSLINE_BONUS foreign key (S_BONUS_ID)
      references S_BONUS (S_BONUS_ID) on delete restrict on update restrict;

alter table S_BONUSLINE add constraint BONUSLINE_ORDER foreign key (S_ORDER_ID)
      references S_ORDER (S_ORDER_ID) on delete restrict on update restrict;

alter table S_BONUSLINE add constraint BONUSLNE_USER foreign key (S_USER_ID)
      references S_USER (S_USER_ID) on delete restrict on update restrict;

alter table S_CART add constraint CART_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_CART add constraint CART_USER foreign key (S_USER_ID)
      references S_USER (S_USER_ID) on delete restrict on update restrict;

alter table S_CART add constraint CART_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table S_COLLECTION add constraint COLLECTION_USER foreign key (S_USER_ID)
      references S_USER (S_USER_ID) on delete restrict on update restrict;

alter table S_COLLECTION add constraint COLLECTION_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table S_COLLECTION add constraint COLLECTION_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_COMMENT add constraint COMMENT_USER foreign key (S_USER_ID)
      references S_USER (S_USER_ID) on delete restrict on update restrict;

alter table S_COMMENT add constraint COMMENT_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table S_COMMENT add constraint COMMENT_BACK foreign key (PARENT_ID)
      references S_COMMENT (S_COMMENT_ID) on delete restrict on update restrict;

alter table S_COMMENTIMG add constraint COMMENT_COMMENTIMAGE foreign key (S_COMMENT_ID)
      references S_COMMENT (S_COMMENT_ID) on delete restrict on update restrict;

alter table S_FINDPASSWORD add constraint FINDPASSWORD_USER foreign key (S_USER_ID)
      references S_USER (S_USER_ID) on delete restrict on update restrict;

alter table S_FREEBIEPLAN add constraint FREEBIE_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table S_FREEBIEPLAN add constraint FK_Reference_61 foreign key (FREEBIE_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table S_FREEBIEPLAN add constraint FREEBIEPLAN_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_GIFTWARE add constraint GIFTWAE_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_GIFTWARE add constraint GIFTWARE_USER foreign key (S_USER_ID)
      references S_USER (S_USER_ID) on delete restrict on update restrict;

alter table S_GIFTWARE add constraint GIFTWARE_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table S_ICONPLAN add constraint ICON_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table S_ICONPLAN add constraint ICONPLAN_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_ORDER add constraint SORDER_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_ORDER add constraint ORDER_USER foreign key (S_USER_ID)
      references S_USER (S_USER_ID) on delete restrict on update restrict;

alter table S_ORDERACTION add constraint ORDERACTION_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_ORDERACTION add constraint ORDERACTION_SORDER foreign key (S_ORDER_ID)
      references S_ORDER (S_ORDER_ID) on delete restrict on update restrict;

alter table S_ORDERACTION add constraint ORDERACTION_DORDER foreign key (D_ORDER_ID)
      references D_ORDER (D_ORDER_ID) on delete restrict on update restrict;

alter table S_ORDERLINE add constraint SORDERLINE_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_ORDERLINE add constraint SORDERLINE_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table S_ORDERLINE add constraint SORDERLINE_ORDER foreign key (S_ORDER_ID)
      references S_ORDER (S_ORDER_ID) on delete restrict on update restrict;

alter table S_ORDERVIRTUALCARD add constraint ORDERVIRTUALCARD_VIRTUALCARD foreign key (M_VIRTUALCARD_ID)
      references M_VIRTUALCARD (M_VIRTUALCARD_ID) on delete restrict on update restrict;

alter table S_ORDERVIRTUALCARD add constraint ORDERVIRTUALCARD_ORDER foreign key (S_ORDER_ID)
      references S_ORDER (S_ORDER_ID) on delete restrict on update restrict;

alter table S_ORDERVIRTUALCARD add constraint ORDERVIRTUALCARD_DORDER foreign key (D_ORDER_ID)
      references D_ORDER (D_ORDER_ID) on delete restrict on update restrict;

alter table S_ORDERVIRTUALCARD add constraint ORDERVIRTUALCARD_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_PAYLINE add constraint PAYLINE_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_PAYLINE add constraint PAYLINE_ORDER foreign key (S_ORDER_ID)
      references S_ORDER (S_ORDER_ID) on delete restrict on update restrict;

alter table S_POINTLOG add constraint POINTLOG_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_POINTLOG add constraint POINTLOG_USER foreign key (S_USER_ID)
      references S_USER (S_USER_ID) on delete restrict on update restrict;

alter table S_POINTLOG add constraint POINTLOG_ORDER foreign key (S_ORDER_ID)
      references S_ORDER (S_ORDER_ID) on delete restrict on update restrict;

alter table S_POINTLOG add constraint POINTLOG_PAYLINE foreign key (S_PAYLINE_ID)
      references S_PAYLINE (S_PAYLINE_ID) on delete restrict on update restrict;

alter table S_PROMOTECODE add constraint PROMOTECODE_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_PROMOTECODELINE add constraint PROMOTECODELINE_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table S_PROMOTECODELINE add constraint PROMOTECODE_PROMOTECODELINE foreign key (S_PROMOTECODE_ID)
      references S_PROMOTECODE (S_PROMOTECODE_ID) on delete restrict on update restrict;

alter table S_PROMOTECODELINE add constraint PROMOTECODELINE_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_PROMOTIONPLAN add constraint PROMOTION_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table S_PROMOTIONPLAN add constraint PROMOTION_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_SECKILLPLAN add constraint SECKILL_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table S_SECKILLPLAN add constraint SECKILLPLAN_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_SECONDHALFPLAN add constraint SECONDHALF_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table S_SECONDHALFPLAN add constraint SECONDHALFPLAN_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;

alter table S_SHAKELOG add constraint SHAKELOG_USER foreign key (S_USER_ID)
      references S_USER (S_USER_ID) on delete restrict on update restrict;

alter table S_SIGNIN add constraint SIGNIN_USER foreign key (S_USER_ID)
      references S_USER (S_USER_ID) on delete restrict on update restrict;

alter table S_SIGNINLOG add constraint SIGNIN_SIGNINLOG foreign key (S_SIGNIN_ID)
      references S_SIGNIN (S_SIGNIN_ID) on delete restrict on update restrict;

alter table S_USERADDRESS add constraint USERADDRESS_USER foreign key (S_USER_ID)
      references S_USER (S_USER_ID) on delete restrict on update restrict;

alter table S_WHOLESALE add constraint WHOLESALE_MATERIAL foreign key (M_MATERIAL_ID)
      references M_MATERIAL (M_MATERIAL_ID) on delete restrict on update restrict;

alter table S_WHOLESALE add constraint WHOLESALE_SHOP foreign key (AD_SHOP_ID)
      references AD_SHOP (AD_SHOP_ID) on delete restrict on update restrict;
