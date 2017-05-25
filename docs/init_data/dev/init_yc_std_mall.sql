/*
-- Query: SELECT * FROM std_mall_200.tmall_category where system_code = 'CD-CYC000009'
LIMIT 0, 400

-- Date: 2017-04-19 19:18
*/
/*
-- Query: SELECT * FROM yc_std_mall.tmall_category where type='2'
LIMIT 0, 50

-- Date: 2017-05-22 15:23
*/
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC1','2','0','餐饮','1_1495367277295.png',3,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC10','2','0','旅游','10_1495367141677.png',1,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC2','2','0','生活服务','shfw_1495367295026.jpg',6,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC3','2','0','电影','dianying_1495367330986.png',9,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC4','2','0','酒店','jiudian_1495367106339.png',1,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC5','2','0','休闲娱乐','xxyl_1495367306525.png',7,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC6','2','0','汽车','qiche_1495367093662.png',0,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC8','2','0','甜点水果','tiandiansg_1495367256370.jpg',3,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC9','2','0','建材家居','jiajuss_1495367186736.jpg',2,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC11','2','0','教育培训','jiaoyupeix_1495367320827.jpg',9,'1','CD-CYC000009','CD-CYC000009');


INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('S','STORE_XFFX','0.2','',now(),' C端消费橙币返现人民币比例','CD-CYC000009','CD-CYC000009');
/*
-- Query: SELECT `type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code` FROM tsys_dict where system_code = 'CD-CYC000009'
LIMIT 0, 10000

-- Date: 2017-03-28 22:03
*/
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'category_status','类别状态','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','category_status','0','待上架','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','category_status','1','已上架','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','category_status','2','已下架','admin',now(),'','CD-CYC000009','CD-CYC000009');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'product_location','产品位置','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','product_location','0','普通','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','product_location','1','首页推荐','admin',now(),'','CD-CYC000009','CD-CYC000009');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'product_status','产品状态','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','product_status','0','待审核','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','product_status','1','审批通过待上架','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','product_status','91','审批不通过','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','product_status','3','已上架','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','product_status','4','已下架','admin',now(),'','CD-CYC000009','CD-CYC000009');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'order_status','订单状态','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','order_status','1','待支付','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','order_status','2','已支付','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','order_status','3','已发货','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','order_status','4','已收货','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','order_status','91','用户异常','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','order_status','92','商户异常','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','order_status','93','快递异常','admin',now(),'','CD-CYC000009','CD-CYC000009');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'kd_company','物流公司','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','kd_company','EMS','邮政EMS','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','kd_company','STO','申通快递','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','kd_company','ZTO','中通快递','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','kd_company','YTO','圆通快递','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','kd_company','HTKY','汇通快递','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','kd_company','SF','顺丰快递','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','kd_company','TTKD','天天快递','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','kd_company','ZJS','宅急送','admin',now(),'','CD-CYC000009','CD-CYC000009');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'store_level','店铺等级','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','store_level','1','普通商家','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','store_level','2','理财型商家','admin',now(),'','CD-CYC000009','CD-CYC000009');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'store_status','店铺状态','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','store_status','0','待审核','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','store_status','91','审核不通过','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','store_status','1','审核通过待上架','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','store_status','2','已上架，开店','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','store_status','3','已上架，关店','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','store_status','4','已下架','admin',now(),'','CD-CYC000009','CD-CYC000009');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'action_type','互动类型','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','action_type','1','点赞','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','action_type','2','收藏','admin',now(),'','CD-CYC000009','CD-CYC000009');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'store_purchase_status','店铺消费状态','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','store_purchase_status','0','待支付','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','store_purchase_status','1','已支付','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','store_purchase_status','2','已取消','admin',now(),'','CD-CYC000009','CD-CYC000009');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'store_pay_type','店铺支付类型','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','store_pay_type','1','人民币余额','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','store_pay_type','5','微信H5支付','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','store_pay_type','50','姚橙020橙币支付','admin',now(),'','CD-CYC000009','CD-CYC000009');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'vproduct_type','虚拟产品类型','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','vproduct_type','1','中石化充值','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','vproduct_type','2','中石油充值','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','vproduct_type','3','手机充值','admin',now(),'','CD-CYC000009','CD-CYC000009');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'vproduct_status','虚拟产品状态','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','vproduct_status','0','待上架','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','vproduct_status','1','已上架','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','vproduct_status','2','已下架','admin',now(),'','CD-CYC000009','CD-CYC000009');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'vorder_status','虚拟订单状态','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','vorder_status','0','待支付','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','vorder_status','1','已支付','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','vorder_status','2','已兑换','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','vorder_status','3','已取消','admin',now(),'','CD-CYC000009','CD-CYC000009');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'vproduct_price','商品价格','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','vproduct_price','2000','2000','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','vproduct_price','1000','1000','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','vproduct_price','500','500','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','vproduct_price','200','200','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','vproduct_price','100','100','admin',now(),'','CD-CYC000009','CD-CYC000009');


/*
-- Query: SELECT * FROM std_mall_200.tmall_vproduct
LIMIT 0, 400

-- Date: 2017-04-23 14:06
*/
INSERT INTO `tmall_vproduct` (`code`,`type`,`name`,`slogan`,`adv_pic`,`pic`,`description`,`price`,`rate`,`location`,`order_no`,`status`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('VCP201700000000000001','1','中石化充值','安全 快捷 方便','adv_zshyk_2x.png','zshyk_2x.png','安全 快捷 方便','100',1,'0',1,'1','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_vproduct` (`code`,`type`,`name`,`slogan`,`adv_pic`,`pic`,`description`,`price`,`rate`,`location`,`order_no`,`status`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('VCP201700000000000002','2','中石油充值','安全 快捷 方便','adv_zsyyk_2x.png','zsyyk_2x.png','安全 快捷 方便','100',1,'0',1,'1','admin',now(),'','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_vproduct` (`code`,`type`,`name`,`slogan`,`adv_pic`,`pic`,`description`,`price`,`rate`,`location`,`order_no`,`status`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('VCP201700000000000003','3','手机充值','安全 快捷 方便','adv_mobile_2x.png','mobile_2x.png','安全 快捷 方便','100',1,'0',1,'1','admin',now(),'','CD-CYC000009','CD-CYC000009');

