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
   DELIVERY_START       datetime comment '���Ϳ�ʼ',
   DELIVERY_END         datetime comment '���ͽ���',
   primary key (AD_CALENDAR_ID)
);

alter table AD_CALENDAR comment '����ʱ�����Ҫ����֧��Ԥ�ۡ�';

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
   PARENT_ID            varchar(20) comment '������',
   AD_SHOP_ID           varchar(20),
   ORG_CODE             varchar(20) comment '��������',
   ORG_NAME             varchar(200) comment '������',
   LINE_NO              numeric(20,0) comment '�к�',
   primary key (AD_ORG_ID)
);

/*==============================================================*/
/* Table: AD_REFLIST                                            */
/*==============================================================*/
create table AD_REFLIST
(
   AD_REFLIST_ID        varchar(20) not null,
   AD_REFRENCE_ID       varchar(20),
   REF_NAME             varchar(60) comment '����',
   REF_VALUE            varchar(60) comment 'ֵ',
   DESCRIPTION          varchar(250) comment '����',
   primary key (AD_REFLIST_ID)
);

alter table AD_REFLIST comment '�б�����';

/*==============================================================*/
/* Table: AD_REFRENCE                                           */
/*==============================================================*/
create table AD_REFRENCE
(
   AD_REFRENCE_ID       varchar(20) not null,
   REFRENCE_NAME        varchar(250) comment '�б�����',
   DESCRIPTION          varchar(250) comment '����',
   primary key (AD_REFRENCE_ID)
);

alter table AD_REFRENCE comment '�����б���';

/*==============================================================*/
/* Table: AD_SHOP                                               */
/*==============================================================*/
create table AD_SHOP
(
   AD_SHOP_ID           varchar(20) not null,
   SHOP_NAME            varchar(200) comment '��������',
   SHOP_LOGO            varchar(200) comment '����logo',
   DISCRIPTION          varchar(200) comment '����',
   primary key (AD_SHOP_ID)
);

alter table AD_SHOP comment '����';

/*==============================================================*/
/* Table: AD_USER                                               */
/*==============================================================*/
create table AD_USER
(
   AD_USER_ID           varchar(20) not null,
   AD_USERGROUP_ID      varchar(20),
   AD_ORG_ID            varchar(20),
   AD_SHOP_ID           varchar(20),
   REAL_NAME            varchar(200) comment '��ʵ����',
   USER_NAME            varchar(200) comment '�û���',
   PASSWORD             varchar(200) comment '����',
   EMAIL                varchar(200) comment '�����ַ',
   LINE_NO              numeric(20,0) comment '�к�',
   TEL                  varchar(200) comment '�绰����',
   USER_STATUS          varchar(20) comment '�û�״̬',
   LAST_LOGIN           datetime comment '����¼ʱ��',
   primary key (AD_USER_ID)
);

alter table AD_USER comment '����Ա�û�';

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

alter table AD_WAREHOUSE comment '�ֿ�
';

/*==============================================================*/
/* Table: D_ORDER                                               */
/*==============================================================*/
create table D_ORDER
(
   D_ORDER_ID           varchar(20) not null,
   S_ORDER_ID           varchar(20),
   AD_SHOP_ID           varchar(20),
   ORDER_SN             varchar(20) not null comment '������',
   ORDER_STATUS         varchar(20)   not null comment '����״̬0��δȷ�ϣ�1����ȷ�ϣ�2����ȡ����3����Ч��4���˻�  5 ��ʾ�� ��ǰ��ԭ��',
   CONSIGNEE            varchar(60) not null comment '�ջ��˵��������û�ҳ����д',
   COUNTRY              varchar(20)   not null comment '����',
   PROVINCE             varchar(20)   not null comment 'ʡ��',
   CITY                 varchar(20)   not null comment '����',
   DISTRICT             varchar(20)   not null comment '����',
   STREET               varchar(20) comment '�ֵ�',
   ADDRESS              varchar(255) not null comment '�ջ�����ϸ��ַ',
   ZIPCODE              varchar(60) not null comment '�ʱ�',
   TEL                  varchar(60) not null comment '�绰',
   MOBILE               varchar(60) not null comment '�ֻ�',
   EMAIL                varchar(60) not null comment '����',
   GOODS_AMOUNT         numeric(18,2) not null comment '�����е���Ʒ�ܼ�',
   RECEIVABLES          numeric(18,2) comment 'Ӧ�տ�',
   ADD_TIME             datetime   not null comment '��������ʱ��',
   CONFIRM_TIME         datetime   not null comment '����ȷ��ʱ��',
   ARRIVE_DATE          datetime comment '�ͻ�����',
   ARRIVE_TIME          varchar(20) comment '�ͻ�ʱ��Σ�0����ȫ�� 1��������8�㵽12�㣬2��������12�㵽16�㣬3��������16�㵽20�� ��4����8�㵽13��  5 ����13�㵽18��  Ĭ����0����ȫ��',
   PLATE_NUMBER         varchar(20) comment '���ƺ��� ��������������ͳ�����',
   IS_IMPORT            char(1) comment '�Ƿ���Ҫ���� 1��ʾ�� 0��ʾ��ͨ���� Ĭ��0',
   primary key (D_ORDER_ID)
);

alter table D_ORDER comment '���Ͷ���';

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

alter table D_ORDERLINE comment '���Ͷ�����Ʒ��';

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
   M_MATERIAL_ID        varchar(20) comment '������',
   AD_SHOP_ID           varchar(20),
   PRODUCT_QTY          numeric(20,0) comment '��������',
   BOM_TYPE             varchar(20) comment 'BOM����',
   IS_ACTIVE            char(1) comment '�Ƿ���Ч',
   BOM_CODE             varchar(20) comment 'BOM����',
   BOM_NAME             varchar(200) comment 'BOM����',
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
   CONSUMETYPE          varchar(20) comment '��������',
   CONSUMEFIXQTY        numeric(20,0) comment '���Ķ���',
   LINE_NO              numeric(20,0) comment '�к�',
   primary key (M_BOMLINE_ID)
);

alter table M_BOMLINE comment 'BOM�������';

/*==============================================================*/
/* Table: M_CARDHANDLER                                         */
/*==============================================================*/
create table M_CARDHANDLER
(
   M_CARDHANDLER_ID     varchar(20) not null,
   M_VIRTUALCARD_ID     varchar(20) comment '��ȯ',
   AD_SHOP_ID           varchar(20),
   HANDLE_TYPE          varchar(20) comment '��������',
   HANDLE_STATUE        varchar(20) comment '״̬',
   DISCRIPTION          varchar(200) comment '����',
   primary key (M_CARDHANDLER_ID)
);

alter table M_CARDHANDLER comment '��ȯ����';

/*==============================================================*/
/* Table: M_CARDTEMP                                            */
/*==============================================================*/
create table M_CARDTEMP
(
   M_CARDTEMP_ID        varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   VIRTUALCARD_TYPE     varchar(20) comment '��ȯ����',
   VIRTUALCARD_SN       varchar(60) comment '����',
   PASSWORD             varchar(20) comment '����',
   ADD_DATE             datetime comment '���ʱ��',
   END_DATE             datetime comment '����ʱ��',
   IS_ACTIVE            char(1) comment '�Ƿ񼤻�',
   primary key (M_CARDTEMP_ID)
);

alter table M_CARDTEMP comment '������ʱ��
';

/*==============================================================*/
/* Table: M_CONVERT                                             */
/*==============================================================*/
create table M_CONVERT
(
   M_CONVERT_ID         varchar(20) not null,
   VIRTUALCARD_ID       varchar(20) comment '��ȯ',
   MATERIAL_ID          varchar(20) comment '���',
   AD_SHOP_ID           varchar(20),
   primary key (M_CONVERT_ID)
);

alter table M_CONVERT comment '��ȯ�һ���';

/*==============================================================*/
/* Table: M_DELIVERPLAN                                         */
/*==============================================================*/
create table M_DELIVERPLAN
(
   M_DELIVERPLAN_PLAN   varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   SORDER_START         datetime comment '�µ���ʼʱ��',
   SORDER_END           datetime comment '�µ�����ʱ��',
   PORDER_DATE          datetime comment '�ɹ�����',
   RECEIVING_DATE       datetime comment '�ջ�����',
   DELIVER_DATE         datetime comment '��������',
   primary key (M_DELIVERPLAN_PLAN)
);

alter table M_DELIVERPLAN comment '���ͼƻ�';

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

alter table M_GROUPMATERIAL comment '������������Ķ���';

/*==============================================================*/
/* Table: M_MATERIAL                                            */
/*==============================================================*/
create table M_MATERIAL
(
   M_MATERIAL_ID        varchar(20) not null,
   M_SUPPLIER_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   ITEM_SN              varchar(200) comment '��Ʒ���루���ţ�',
   MATERIAL_CODE        varchar(200) comment '���ϱ���',
   FOREIGN_CODE         varchar(200) comment '�ⲿ����',
   MATERIAL_NAME        varchar(200) comment '��������',
   SHORT_NAME           varchar(200) comment '���',
   SUBHEAD              varchar(200) comment '��Ʒ������',
   KEYWORDS             varchar(255) comment '�ؼ���',
   BRAND                varchar(200) comment 'Ʒ��',
   STANDARD             varchar(200) comment '����ͺ�',
   DESCRIPTION          varchar(200) comment '����',
   LENGTH               numeric(18,2) comment '����',
   WIDTH                numeric(18,2) comment '���',
   HEIGHT               numeric(18,2) comment '�߶�',
   VOLUME               numeric(18,2) comment '���',
   IS_WEIGHTED          char(1) comment '���ؼ�',
   GROSSWEIGHT          numeric(18,2) comment 'ë��',
   NETWEIGHT            numeric(18,2) comment '����',
   STATUS               varchar(20) comment '״̬',
   IS_REAL              char(1) comment '�Ƿ�ʵ����Ʒ',
   WEB_ONSALE           char(1) comment '��վ�ϼ�',
   WEB_PRICE            numeric(18,2)   comment '����۸�',
   ANDROIDPHONE_ONSALE  char(1) comment '��׿�ֻ��ϼ�',
   IOSPHONE_ONSALE      char(1) comment 'ƻ���ֻ��ϼ�',
   PHONE_PRICE          numeric(18,2) comment '�ֻ��˼۸�',
   ANDROIDPAD_ONSALE    char(1) comment '��׿ƽ���ϼ�',
   IOSPAD_ONSALE        char(1) comment 'ƻ��ƽ���ϼ�',
   PAD_PRICE            numeric(18,2) comment 'ƽ����Զ˼۸�',
   WEIXIN_ONSALE        char(1) comment '΢���ϼ�',
   WEIXIN_PRICE         numeric(18,2) comment '΢�ż۸�',
   ADD_TIME             datetime comment '���ʱ��',
   LINE_NO              numeric(20,0) comment '�кţ������',
   CONSUME_POINT        numeric(20,0) comment '�������Ʒ��ʹ�û��ֵ��������',
   GIFT_POINT           numeric(20,0) comment '���ͻ��ֵĻ�������',
   RANK_POINT           numeric(20,0) comment '���͵ȼ�����',
   PACKING              varchar(20) comment '��װ����',
   STORE_DAYS           varchar(20) comment '������',
   STORE_TYPE           varchar(20) comment '��������',
   IS_SOLDOUT           char(1) comment '�Ƿ�����',
   IS_NORMALDELIVERY    char(1) comment '�Ƿ񳣹�����',
   primary key (M_MATERIAL_ID)
);

alter table M_MATERIAL comment '����';

/*==============================================================*/
/* Table: M_MATERIALGROUP                                       */
/*==============================================================*/
create table M_MATERIALGROUP
(
   M_MATERIALGROUP_ID   varchar(20) not null,
   M_MATERIALGROUPSTANDARD_ID varchar(20) comment '�����׼',
   PARENT_ID            varchar(20) comment '���ڵ�',
   AD_SHOP_ID           varchar(20),
   GROUP_NAME           varchar(200) comment '��������',
   GROUP_CODE           varchar(200) comment '�������',
   DISPLAY_NAME         varchar(200) comment 'չʾ����',
   ISLEAF               char(1) comment '�Ƿ�Ҷ�ӽڵ�',
   GROUP_LEVEL          numeric(20,0) comment '����㼶',
   IS_ACTIVED           char(1),
   primary key (M_MATERIALGROUP_ID)
);

alter table M_MATERIALGROUP comment '������';

/*==============================================================*/
/* Table: M_MATERIALGROUPSTANDARD                               */
/*==============================================================*/
create table M_MATERIALGROUPSTANDARD
(
   M_MATERIALGROUPSTANDARD_ID varchar(20) not null,
   AD_SHOP_ID           varchar(20),
   MATERIALGROUPSTANDARD_NAME varchar(60) comment '����',
   primary key (M_MATERIALGROUPSTANDARD_ID)
);

alter table M_MATERIALGROUPSTANDARD comment '���Ϸ����׼';

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

alter table M_MATERIALIMG comment '����ͼƬ';

/*==============================================================*/
/* Table: M_PRICECHANGE                                         */
/*==============================================================*/
create table M_PRICECHANGE
(
   M_PRICECHANGE_ID     varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   CHANGEDATE           datetime comment '�۸������������',
   NEWPRICE             numeric(18,2) comment '�����ļ۸�',
   primary key (M_PRICECHANGE_ID)
);

alter table M_PRICECHANGE comment '�۸�����¼';

/*==============================================================*/
/* Table: M_SUPPLIER                                            */
/*==============================================================*/
create table M_SUPPLIER
(
   M_SUPPLIER_ID        varchar(20) not null,
   AD_SHOP_ID           varchar(20),
   SUPPLIER_CODE        varchar(200) comment '��Ӧ�̱���',
   SUPPLIER_NAME        varchar(200) comment '��Ӧ������',
   SHORT_NAME           varchar(200) comment '���',
   ENTNATURE            varchar(200) comment '��ҵ����',
   ARTIFICIALPERSON     varchar(200) comment '���˴���',
   TAXREGISTERNO        varchar(200) comment '˰��ǼǺ�',
   BUSIEXEQUATUR        varchar(200) comment '������Ӫ���֤',
   BIZREGISTERNO        varchar(200) comment '����ע���',
   BUSILICENCE          varchar(200) comment 'Ӫҵִ��',
   BIZANALYSISCODE      varchar(200) comment 'ҵ�������',
   DESCRIPTION          varchar(200) comment '����',
   TAXDATA              varchar(200) comment '˰��',
   TAXRATE              varchar(200) comment '˰��',
   OFFICE_ADDRESS       varchar(200) comment '�칫��ַ',
   WAREHOUSE_ADDRESS    varchar(200) comment '�ֿ��ַ',
   primary key (M_SUPPLIER_ID)
);

alter table M_SUPPLIER comment '��Ӧ��';

/*==============================================================*/
/* Table: M_VIRTUALCARD                                         */
/*==============================================================*/
create table M_VIRTUALCARD
(
   M_VIRTUALCARD_ID     varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   S_USER_ID            varchar(20) comment '��ֵ��ʹ����',
   VIRTUALCARD_TYPE     varchar(20) comment '��ȯ����',
   VIRTUALCARD_SN       varchar(60) comment '����',
   PASSWORD             varchar(20) comment '����',
   ADD_DATE             datetime comment '���ʱ��',
   END_DATE             datetime comment '����ʱ��',
   IS_ACTIVE            char(1) comment '�Ƿ񼤻�',
   IS_DELIVERED         char(1) comment '�Ƿ�������',
   primary key (M_VIRTUALCARD_ID)
);

alter table M_VIRTUALCARD comment '��ȯ';

/*==============================================================*/
/* Table: P_ORDER                                               */
/*==============================================================*/
create table P_ORDER
(
   P_ORDER_ID           varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   M_SUPPLIER_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   ADD_DATE             datetime comment '�µ�����
            ',
   ORDERTYPE            varchar(20) comment 'ҵ������',
   CASHDISCOUNT         varchar(20) comment '�ֽ��ۿ�',
   DESCRIPTION          varchar(200) comment '����',
   INVOICEDAMOUNT       numeric(18,2) comment '�ۼƿ�Ʊ���',
   TOTALAMOUNT          numeric(18,2) comment '��Ʒ�ܽ��',
   TAXAMOUNT            numeric(18,2) comment '˰���ܽ��',
   TOTALTAXAMOUNT       numeric(18,2) comment '��˰�ϼ�',
   PAYMENTCONDITION     varchar(20) comment '��������',
   PAYMENTTYPE          varchar(20) comment '���ʽ',
   SUPPLIERORDERNUMBER  varchar(200) comment '��Ӧ�̶�����',
   DELIVERYADDRESS      varchar(200) comment '������ַ',
   DELIVERYDATE         datetime comment '��������',
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
   QTY                  numeric(18,2) comment '��������',
   PRICE                numeric(18,2) comment '��Ʒ����',
   AMOUNT               numeric(18,2) comment '��Ʒ�ܽ��',
   TAX                  numeric(18,2) comment '˰��',
   TAXRATE              numeric(18,2) comment '˰��',
   TAXAMOUNT            numeric(18,2) comment '��˰�ϼ�',
   DISCOUNTAMOUNT       numeric(18,2) comment '�ۿ۶�',
   ISPRESENT            char(1) comment '��Ʒ',
   LINE_NO              numeric(20,0) comment '�к�',
   RECEIVEQTY           numeric(18,2) comment '�ջ�����',
   primary key (P_ORDERLINE_ID)
);

/*==============================================================*/
/* Table: S_ACCOUNTLOG                                          */
/*==============================================================*/
create table S_ACCOUNTLOG
(
   S_ACCOUNTLOG_ID      varchar(20) not null,
   S_PAYLINE_ID         varchar(20) comment '����֧����',
   S_USER_ID            varchar(20) comment '�û�',
   M_VIRTUALCARD_ID     varchar(20) comment '��ֵ��Ӧ�Ŀ���',
   AD_SHOP_ID           varchar(20),
   BIZ_TYPE             varchar(20) comment 'ҵ�����͡���ֵ�����ѣ��˹���ӡ�',
   AMOUNT               numeric(18,2) comment '���',
   primary key (S_ACCOUNTLOG_ID)
);

alter table S_ACCOUNTLOG comment '����˻���־';

/*==============================================================*/
/* Table: S_BONUS                                               */
/*==============================================================*/
create table S_BONUS
(
   S_BONUS_ID           varchar(20) not null,
   AD_SHOP_ID           varchar(20),
   BONUS_NAME           varchar(60) comment '����ȯ����',
   BONUS_STATUE         varchar(20) comment '����ȯ״̬',
   BONUS_AMOUNT         numeric(18,2) comment '����ȯ���',
   ORDER_AMOUNT         numeric(18,2) comment 'ʹ�õ���ȯ������޶�',
   BONUS_TYPE           varchar(20) comment '����ȯ����',
   BONUS_ROUTE          varchar(20) comment '����;��',
   SEND_STARTDATE       datetime comment '��ʼ��������',
   SEND_ENDDATE         datetime comment '������������',
   START_DATE           datetime comment '��ʼʹ������',
   END_DATE             datetime comment '����ʹ������',
   primary key (S_BONUS_ID)
);

alter table S_BONUS comment '����ȯ';

/*==============================================================*/
/* Table: S_BONUSLINE                                           */
/*==============================================================*/
create table S_BONUSLINE
(
   S_BONUSLINE_ID       varchar(20) not null,
   S_BONUS_ID           varchar(20) comment '����ȯ',
   S_ORDER_ID           varchar(20) comment 'ʹ�øõ���ȯ�Ķ���',
   S_USER_ID            varchar(20) comment '����ȯ������',
   AD_SHOP_ID           varchar(20),
   BOUNS_SN             varchar(20) comment '����ȯ��',
   BOUNS_STATUE         varchar(20) comment '����ȯ״̬',
   primary key (S_BONUSLINE_ID)
);

alter table S_BONUSLINE comment '����ȯ����';

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

alter table S_CART comment '���ﳵ';

/*==============================================================*/
/* Table: S_COLLECTION                                          */
/*==============================================================*/
create table S_COLLECTION
(
   S_COLLECTION_ID      varchar(20) not null,
   S_USER_ID            varchar(20) comment '�û�',
   M_MATERIAL_ID        varchar(20) comment '�ղ���Ʒ',
   AD_SHOP_ID           varchar(20),
   ADD_TIME             datetime comment '�ղ�ʱ��',
   IS_SUBSCRIBED        char(1) comment '���Ϣ����',
   primary key (S_COLLECTION_ID)
);

alter table S_COLLECTION comment '�ͻ��ղؼ�';

/*==============================================================*/
/* Table: S_COMMENT                                             */
/*==============================================================*/
create table S_COMMENT
(
   S_COMMENT_ID         varchar(20) not null,
   S_USER_ID            varchar(20) comment '�û�',
   M_MATERIAL_ID        varchar(20) comment '����',
   PARENT_ID            varchar(20) comment '������',
   IP_ADDRESS           varchar(20) comment 'ip��ַ',
   CONTENT              varchar(250) comment '��������',
   ADD_TIME             datetime comment '����ʱ��',
   COMMENT_STATUE       varchar(20) comment '����״̬',
   COMMENT_FROM         varchar(20) comment '��������',
   primary key (S_COMMENT_ID)
);

alter table S_COMMENT comment '�ͻ�����';

/*==============================================================*/
/* Table: S_COMMENTIMG                                          */
/*==============================================================*/
create table S_COMMENTIMG
(
   S_COMMENTIMG_ID      varchar(20) not null,
   S_COMMENT_ID         varchar(20),
   URI                  varchar(200) comment 'ͼƬ��ַ',
   THUM_URI             varchar(200) comment '����ͼ��ַ',
   primary key (S_COMMENTIMG_ID)
);

alter table S_COMMENTIMG comment '����ͼƬ';

/*==============================================================*/
/* Table: S_COMPLAINT                                           */
/*==============================================================*/
create table S_COMPLAINT
(
   S_COMPLAINT_ID       varchar(20) not null,
   primary key (S_COMPLAINT_ID)
);

alter table S_COMPLAINT comment 'Ͷ��ֱͨ��';

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

alter table S_FINDPASSWORD comment '�һ���������';

/*==============================================================*/
/* Table: S_FREEBIEPLAN                                         */
/*==============================================================*/
create table S_FREEBIEPLAN
(
   S_FREEBIEPLAN_ID     varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   FREEBIE_MATERIAL_ID  varchar(20) comment '��Ʒ',
   AD_SHOP_ID           varchar(20),
   START_TIME           datetime comment '��ʼʱ��',
   END_TIME             datetime comment '����ʱ��',
   IS_ACTIVE            char(1) comment '�Ƿ���Ч',
   FREEBIE_NUM          numeric(20,0) comment '��������',
   primary key (S_FREEBIEPLAN_ID)
);

alter table S_FREEBIEPLAN comment '����';

/*==============================================================*/
/* Table: S_GIFTWARE                                            */
/*==============================================================*/
create table S_GIFTWARE
(
   S_GIFTWARE_ID        varchar(20) not null,
   S_USER_ID            varchar(20) comment '�û�',
   M_MATERIAL_ID        varchar(20) comment '��Ʒ',
   AD_SHOP_ID           varchar(20),
   QTY                  numeric(20,0) comment '����',
   END_TIME             datetime comment '����ʱ��',
   PRICE                numeric(18,2) comment '�۸�',
   DISCRIPTION          varchar(255) comment '����',
   GIFT_STATUS          varchar(20) comment '״̬',
   primary key (S_GIFTWARE_ID)
);

alter table S_GIFTWARE comment '�û��������Ʒ�б�';

/*==============================================================*/
/* Table: S_ICONPLAN                                            */
/*==============================================================*/
create table S_ICONPLAN
(
   S_ICONPLAN_ID        varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   START_TIME           datetime comment '��ʼʱ��',
   END_TIME             datetime comment '����ʱ��',
   IS_ACTIVE            char(1) comment '�Ƿ���Ч',
   ICON_TYPE            varchar(20),
   primary key (S_ICONPLAN_ID)
);

alter table S_ICONPLAN comment 'ICON�ƻ�';

/*==============================================================*/
/* Table: S_ORDER                                               */
/*==============================================================*/
create table S_ORDER
(
   S_ORDER_ID           varchar(20) not null,
   S_USER_ID            varchar(20),
   AD_SHOP_ID           varchar(20),
   ORDER_SN             varchar(20) not null comment '������',
   ORDER_TYPE           varchar(60) not null comment '�������� 
            �һ������͡����͵���լ��ҵ��ģ����������ۡ������Ź�
            ����ڲ������������˵��������˵����嵥�����ⵥ',
   ORDER_STATUS         varchar(20)   not null comment '����״̬',
   POSTSCRIPT           varchar(255) not null comment '��������',
   INV_TYPE             varchar(60) not null comment '��Ʊ����',
   INV_PAYEE            varchar(120) not null comment '��Ʊ̧ͷ���û�ҳ����д''',
   INV_CONTENT          varchar(120) not null comment '��Ʊ���ݣ��û�ҳ��ѡ��',
   GOODS_AMOUNT         numeric(18,2) not null comment '�����е���Ʒ�ܼ�',
   SHIP_AMOUNT          numeric(18,2) comment '�����˷��ܼ�',
   ORDER_FROM           varchar(60) not null comment '��������Դ',
   ORDER_BENIFIT        varchar(60) comment 'ҵ������',
   ADD_TIME             datetime   not null comment '��������ʱ��',
   CONFIRM_TIME         datetime   not null comment '����ȷ��ʱ��',
   IS_IMPORT            char(1) comment '�Ƿ���Ҫ���� 1��ʾ�� 0��ʾ��ͨ���� Ĭ��0',
   primary key (S_ORDER_ID)
);

alter table S_ORDER comment '���۶���';

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

alter table S_ORDERACTION comment '��������';

/*==============================================================*/
/* Table: S_ORDERLINE                                           */
/*==============================================================*/
create table S_ORDERLINE
(
   S_ORDERLINE_ID       varchar(20) not null,
   S_ORDER_ID           varchar(20),
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   PRICE                numeric(18,2) not null comment '��Ʒ�۸�',
   QTY                  numeric(20,0) comment '����',
   DISCOUNT             numeric(18,2) comment '�ۿ۽��',
   GOODS_POINTS         numeric(20,0) comment '�ɻ�õĻ���',
   PROMOTE_TYPE         varchar(20) comment '�������ͣ�Ŀǰ�涨1��ɱ ��2�ؼۣ�3�����������Ʒ��4�ڶ��������Ʒ��5������Ʒ��6ҡһҡ��Ʒ��7��ת����Ʒ��8���Ʒ 9�����Ż� 10�Ź���Ʒ 11�ڶ������ؼ�',
   HAVE_COMMENTED       varchar(20) comment '��Ʒ�Ƿ�ɹ������ 1�Ѿ�ɹ�����ۣ�0δɹ������',
   primary key (S_ORDERLINE_ID)
);

alter table S_ORDERLINE comment '���۶���������';

/*==============================================================*/
/* Table: S_ORDERVIRTUALCARD                                    */
/*==============================================================*/
create table S_ORDERVIRTUALCARD
(
   S_ORDERVIRTUALCARD_ID varchar(20) not null,
   M_VIRTUALCARD_ID     varchar(20) comment '�һ�ȯ',
   S_ORDER_ID           varchar(20) comment '���۶���,�һ�����',
   D_ORDER_ID           varchar(20),
   AD_SHOP_ID           varchar(20),
   REL_TYPE             varchar(20) comment '������ʽ�����۹���,�һ�����,���͹�����',
   DELIVERE_STATUS      varchar(20) comment '����״̬',
   primary key (S_ORDERVIRTUALCARD_ID)
);

alter table S_ORDERVIRTUALCARD comment '���۶����еĿ���  ���Ͷ����еĿ���  �һ������еĿ���';

/*==============================================================*/
/* Table: S_PAYLINE                                             */
/*==============================================================*/
create table S_PAYLINE
(
   S_PAYLINE_ID         varchar(20) not null,
   S_ORDER_ID           varchar(20),
   AD_SHOP_ID           varchar(20),
   PAY_TYPE             varchar(20) comment '֧�����͡��տ�˿',
   PAY_MODE             varchar(20) comment '֧����ʽ���Ż��룬����ȯ���˻���֧�������Ƹ�ͨ������..........��',
   PAY_STATUS           varchar(20) comment '֧��״̬',
   PAY_AMONT            numeric(20,0) comment '֧�����',
   PAY_TIME             datetime comment '֧��ʱ��',
   PAY_SN               varchar(20) comment '����֧��ʱ��֧���������ص�֧����ˮ��',
   primary key (S_PAYLINE_ID)
);

alter table S_PAYLINE comment '����֧����Ϣ';

/*==============================================================*/
/* Table: S_POINTGAME                                           */
/*==============================================================*/
create table S_POINTGAME
(
   S_POINTGAME_ID       varchar(20) not null,
   primary key (S_POINTGAME_ID)
);

alter table S_POINTGAME comment '���ֻ����';

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
   BIZ_TYPE             varchar(20) comment 'ҵ������',
   POINT_QTY            numeric(20,0) comment '��������',
   DESCRIPTION          varchar(255) comment '����',
   primary key (S_POINTLOG_ID)
);

alter table S_POINTLOG comment '������־';

/*==============================================================*/
/* Table: S_PROMOTECODE                                         */
/*==============================================================*/
create table S_PROMOTECODE
(
   S_PROMOTECODE_ID     varchar(20) not null,
   AD_SHOP_ID           varchar(20),
   PROMOTECODE_NAME     varchar(200) comment '�Ż�������',
   PROMOTECODE_TYPE     varchar(20) comment '�Ż�����',
   START_DATE           datetime comment '��ʼʹ������',
   END_DATE             datetime comment '����ʹ������',
   ADD_DATE             datetime comment '�������',
   primary key (S_PROMOTECODE_ID)
);

alter table S_PROMOTECODE comment '�Ż���';

/*==============================================================*/
/* Table: S_PROMOTECODELINE                                     */
/*==============================================================*/
create table S_PROMOTECODELINE
(
   S_PROMOTECODELINE_ID varchar(20) not null,
   S_PROMOTECODE_ID     varchar(20),
   AD_SHOP_ID           varchar(20),
   PROMOTECODE_SN       varchar(200) comment '�Ż������',
   START_DATE           datetime comment '��ʼʹ������',
   END_DATE             datetime comment '����ʹ������',
   ADD_DATE             datetime comment '�������',
   MIN_ORDERAMT         numeric(18,2) comment '��С�������',
   MAX_ORDERAMT         numeric(18,2) comment '��󶩵����',
   PROMOTE_AMT          numeric(18,2) comment '�Żݽ��',
   M_MATERIAL_ID        varchar(20) comment '��Ʒ',
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
   START_TIME           datetime comment '��ʼʱ��',
   END_TIME             datetime comment '����ʱ��',
   PROMOTION_PRICE      numeric(18,2) comment '�����۸�',
   IS_ACTIVE            char(1) comment '�Ƿ���Ч',
   primary key (S_PROMOTIONPLAN_ID)
);

alter table S_PROMOTIONPLAN comment '�����ƻ�';

/*==============================================================*/
/* Table: S_SEARCHLOG                                           */
/*==============================================================*/
create table S_SEARCHLOG
(
   S_SEARCHLOG_ID       varchar(20) not null,
   primary key (S_SEARCHLOG_ID)
);

alter table S_SEARCHLOG comment '������־
';

/*==============================================================*/
/* Table: S_SECKILLPLAN                                         */
/*==============================================================*/
create table S_SECKILLPLAN
(
   S_SECKILLPLAN_ID     varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   START_TIME           datetime comment '��ʼʱ��',
   END_TIME             datetime comment '����ʱ��',
   SECKILL_PRICE        numeric(18,2) comment '��ɱ�۸�',
   SECKILL_NUM          numeric(20,0) comment '��ɱ����',
   IS_ACTIVE            char(1) comment '�Ƿ���Ч',
   primary key (S_SECKILLPLAN_ID)
);

alter table S_SECKILLPLAN comment '��ɱ�ƻ�';

/*==============================================================*/
/* Table: S_SECONDHALFPLAN                                      */
/*==============================================================*/
create table S_SECONDHALFPLAN
(
   S_SECONDHALF_ID      varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   START_TIME           datetime comment '��ʼʱ��',
   END_TIME             datetime comment '����ʱ��',
   IS_ACTIVE            char(1) comment '�Ƿ���Ч',
   primary key (S_SECONDHALF_ID)
);

alter table S_SECONDHALFPLAN comment '�ڶ�����ۻ�ƻ�';

/*==============================================================*/
/* Table: S_SHAKEGAME                                           */
/*==============================================================*/
create table S_SHAKEGAME
(
   S_SHAKEGAME_ID       varchar(20) not null,
   primary key (S_SHAKEGAME_ID)
);

alter table S_SHAKEGAME comment 'ҡһҡ��Ϸ�趨';

/*==============================================================*/
/* Table: S_SHAKELOG                                            */
/*==============================================================*/
create table S_SHAKELOG
(
   S_SHAKELOG_ID        varchar(20) not null,
   S_USER_ID            varchar(20),
   ADD_TIME             datetime comment 'ʱ��',
   SHAKE_TYPE           varchar(20) comment 'ҡһҡ����',
   SHAKE_RESULT         varchar(20) comment '���',
   primary key (S_SHAKELOG_ID)
);

alter table S_SHAKELOG comment 'ҡһҡ��־';

/*==============================================================*/
/* Table: S_SIGNIN                                              */
/*==============================================================*/
create table S_SIGNIN
(
   S_SIGNIN_ID          varchar(20) not null,
   S_USER_ID            varchar(20),
   SIGNIN_NAME          varchar(200) comment 'ǩ�������',
   START_DATE           datetime comment '��ʼʹ������',
   END_DATE             datetime comment '����ʹ������',
   CONTINUITY_QTY       numeric(20,0) comment '����ǩ������',
   QTY                  numeric(20,0) comment '�ɶһ�ǩ������',
   primary key (S_SIGNIN_ID)
);

alter table S_SIGNIN comment 'ǩ���';

/*==============================================================*/
/* Table: S_SIGNINLOG                                           */
/*==============================================================*/
create table S_SIGNINLOG
(
   S_SIGNINLOG_ID       varchar(20) not null,
   S_SIGNIN_ID          varchar(20),
   SIGNIN_TYPE          varchar(20) comment 'ҵ�����ͣ�ǩ��,�콱��',
   SIGNIN_TIME          datetime comment 'ǩ��ʱ��',
   USED_TIME            datetime comment '�콱ʱ��',
   USED_CONTINUITYQTY   numeric(20,0) comment 'ʹ������ǩ������',
   USED_QTY             numeric(20,0) comment 'ʹ�ÿɶһ�ǩ������',
   SIGNIN_FROM          varchar(20) comment 'ǩ����Դ',
   primary key (S_SIGNINLOG_ID)
);

alter table S_SIGNINLOG comment 'ǩ����־';

/*==============================================================*/
/* Table: S_USER                                                */
/*==============================================================*/
create table S_USER
(
   S_USER_ID            varchar(20) not null,
   USER_NAME            varchar(60) comment '�û���',
   PASSWORD             varchar(60) comment '�û����� MD5����',
   REAL_NAME            varchar(60) comment '��ʵ����',
   AUTHORISED_PHONE     varchar(60) comment '��Ȩ�绰',
   ALIAS                varchar(60) comment '�ǳ�',
   EMAIL                varchar(60) comment '�ʼ�',
   USER_THUMB           varchar(60) comment '�û�ͷ������ͼ',
   USER_IMAGE           varchar(60) comment '�û�ͷ��',
   GENERAL_IMAGE        varchar(60) comment '�û�ͷ��ԭͼ',
   QUESTION             varchar(255) comment '������ʾ����',
   ANSWER               varchar(255) comment '������ʾ�����',
   SEX                  varchar(20)   comment '�Ա�',
   BIRTHDAY             datetime comment '��������',
   USER_MONEY           numeric(18,2) comment '�û������ʽ� Ҳ�����˻���Ĭ��ֵΪ0 ��2013�°����˻�����ǰ���alipay_money,card_money,bonus_money����֮��  ʹ�����ۿ��Ⱥ�˳�� ֧���� - ��ȯ���-�������',
   ONLINE_MONEY         numeric(18,2) comment '֧������ֵ������˻���� Ĭ��ֵ0 ÿ�γ�ֵ�Լӣ���ͬʱ����user_money',
   CARD_MONEY           numeric(18,2) comment '�˹ܼҳ�ֵ����ֵ�Ľ�Ĭ��ֵ0��ÿ�γ�ֵ�Լ�',
   BONUS_MONEY          numeric(18,2) comment '�˹ܼҷ����Ľ�Ĭ��ֵ0��ÿ�η����Լ�',
   FROZEN_MONEY         numeric(18,2) comment '�����ʽ�',
   PAY_POINTS           numeric(20,0)   comment '���ѻ��� ��������֧�������Ļ��� 100��һ��Ǯ 2013����׿�ʼĿǰ���ִ�����Ч�ڣ�Ĭ���Ǵ������',
   RANK_POINTS          numeric(20,0)   comment '�ȼ�������',
   REG_TIME             datetime   comment 'ע��ʱ��',
   REG_FROM             varchar(20) comment 'ע����Դ������,app,΢�ţ�',
   LAST_LOGIN           datetime   comment '�ϴε�¼ʱ��',
   LAST_TIME            datetime comment '���һ���޸���Ϣʱ��',
   LAST_IP              varchar(20) comment '�ϴε�¼ip',
   VISIT_COUNT          numeric(20,0)   comment '���ʴ���',
   USER_RANK            numeric(20,0)   comment '''��Ա�Ǽ�id��ȡֵecs_user_rank',
   IS_SPECIAL           char(1)   comment '�����Ա 1��ʾ��Ҫ��Ա���û�Ա�µĶ���Ĭ��Ϊ��Ҫ����',
   MSN                  varchar(60) comment 'msn',
   QQ                   varchar(60) comment 'qq',
   WEIXIN               varchar(60) comment '΢��openID',
   SINA_WEIBO           varchar(60) comment '΢����(sina΢��)',
   QQ_WEIBO             varchar(60) comment '΢����(qq΢��)',
   OFFICE_PHONE         varchar(60) comment '�칫�绰',
   HOME_PHONE           varchar(60) comment '��ͥ�绰',
   MOBILE_PHONE         varchar(60) comment '�ֻ�',
   IS_VALIDATED         char(1)   comment '�Ƿ���ЧN',
   CREDIT_LINE          numeric(18,2)   comment '���ö��',
   ALIPAY_USER_ID       varchar(60) comment '֧�����û�id',
   IDENTITY_CARD        varchar(60) comment '���֤��',
   ZIPCODE              varchar(60) comment '�ʱ�',
   primary key (S_USER_ID)
);

alter table S_USER comment '�ͻ�';

/*==============================================================*/
/* Table: S_USERADDRESS                                         */
/*==============================================================*/
create table S_USERADDRESS
(
   S_USERADDRESS_ID     varchar(20) not null,
   S_USER_ID            varchar(20),
   CONSIGNEE            varchar(60) comment '�ջ���',
   EMAIL                varchar(60) comment '�ʼ�',
   COUNTRY              varchar(60) comment '����',
   PROVINCE             varchar(60) comment 'ʡ��',
   CITY                 varchar(60) comment '����',
   DISTRICT             varchar(60) comment '����',
   ADDRESS              varchar(120) comment '��ϸ��ַ',
   ZIPCODE              varchar(60) comment '�ʱ�',
   TEL                  varchar(60) comment '�绰',
   MOBILE               varchar(60) comment '�ֻ�',
   IS_DEFAULT           char(1) comment '�Ƿ�Ĭ�ϵ�ַ',
   RECEIVE_DM           char(1) comment '�Ƿ����DM',
   primary key (S_USERADDRESS_ID)
);

alter table S_USERADDRESS comment '�ͻ���ַ';

/*==============================================================*/
/* Table: S_USERRANK                                            */
/*==============================================================*/
create table S_USERRANK
(
   S_USERRANK_ID        varchar(20) not null,
   RANK_NAME            varchar(60) comment '�ȼ���',
   MIN_POINTS           numeric(20,0)   comment '��С����',
   MAX_POINTS           numeric(20,0)   comment '������',
   DISCOUNT             numeric(20,0)   comment '�ۿ�',
   INTEGRAL_TIMES       numeric(18,2) comment '���ַ��㱶��',
   primary key (S_USERRANK_ID)
);

alter table S_USERRANK comment '�û��ȼ��趨����';

/*==============================================================*/
/* Table: S_WHOLESALE                                           */
/*==============================================================*/
create table S_WHOLESALE
(
   S_WHOLESALE_ID       varchar(20) not null,
   M_MATERIAL_ID        varchar(20),
   AD_SHOP_ID           varchar(20),
   START_TIME           datetime comment '��ʼʱ��',
   END_TIME             datetime comment '����ʱ��',
   IS_ACTIVE            char(1) comment '�Ƿ���Ч',
   FIRSTSTEP_NUM        numeric(20,0),
   FIRSTSTEP_PRICE      numeric(18,2),
   SECONDSTEP_NUM       numeric(20,0),
   SECONDSTEP_PRICE     numeric(18,2),
   THRID_NUM            numeric(20,0),
   THRID_PRICE          numeric(18,2),
   primary key (S_WHOLESALE_ID)
);

alter table S_WHOLESALE comment '�˲���';

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
