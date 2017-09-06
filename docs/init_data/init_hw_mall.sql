
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('O','handler_order','18767101909','',now(),'用户催单平台短信通知人','CD-CHW000015','CD-CHW000015');

INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('J01','1','0','积分商品','积分@2x_1504086524929.png',1,'1','CD-CHW000015','CD-CHW000015');

/*
-- Query: SELECT `type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code` FROM tsys_dict where system_code = 'CD-CHW000015'
LIMIT 0, 10000

-- Date: 2017-03-28 22:03
*/
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'category_status','类别状态','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','category_status','0','待上架','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','category_status','1','已上架','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','category_status','2','已下架','admin',now(),'','CD-CHW000015','CD-CHW000015');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'product_location','产品位置','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','product_location','0','普通','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','product_location','1','首页推荐','admin',now(),'','CD-CHW000015','CD-CHW000015');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'product_status','产品状态','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','product_status','1','待上架','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','product_status','3','已上架','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','product_status','4','已下架','admin',now(),'','CD-CHW000015','CD-CHW000015');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'order_status','订单状态','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','order_status','1','待支付','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','order_status','2','待发货','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','order_status','3','待收货','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','order_status','4','待评价','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','order_status','5','已完成','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','order_status','91','用户异常','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','order_status','92','商户异常','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','order_status','93','快递异常','admin',now(),'','CD-CHW000015','CD-CHW000015');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'pay_type','支付类型','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','pay_type','1','余额支付','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','pay_type','2','微信APP','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','pay_type','3','支付宝','admin',now(),'','CD-CHW000015','CD-CHW000015');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'kd_company','物流公司','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','kd_company','EMS','邮政EMS','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','kd_company','STO','申通快递','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','kd_company','ZTO','中通快递','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','kd_company','YTO','圆通快递','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','kd_company','HTKY','汇通快递','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','kd_company','SF','顺丰快递','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','kd_company','TTKD','天天快递','admin',now(),'','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','kd_company','ZJS','宅急送','admin',now(),'','CD-CHW000015','CD-CHW000015');
