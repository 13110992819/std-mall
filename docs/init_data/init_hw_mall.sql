INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('O','handler_order','18767101909','',now(),'用户催单平台短信通知人','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('mall','back_jf_rate','0.1','',now(),'商城订单人民币支付返现积分比例','CD-CHW000015','CD-CHW000015');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('mall','back_oneref_xjk_rate','0.02','',now(),'商城订单人民币支付推荐返现小金库比例','CD-CHW000015','CD-CHW000015');

INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('J01','1','0','积分商品','积分@2x_1504086524929.png',1,'1','CD-CHW000015','CD-CHW000015');

INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('O','CONFIRM_ORDER_DAYS','7','admin',now(),'超过天数，系统确认收货','CD-CHW000015','CD-CHW000015');