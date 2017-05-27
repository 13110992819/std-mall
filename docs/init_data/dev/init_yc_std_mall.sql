
/*
-- Query: SELECT * FROM yc_std_mall.tmall_category
-- Date: 2017-05-27 12:21
*/
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('FL201705252032092274355','1','0','笋干','220ecc2636b6e6a4fb4df328a8d6d7bc_1495794540785.jpg',3,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('FL201705252032314584899','1','0','茶叶','8df90f63a2ab71a2ecfccbad78c92be9_1495794120990.jpg',2,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('FL201705252032466426656','1','FL201705252032092274355','笋干','timg_1495715594191.jpg',2,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('FL201705252034020378197','1','FL201705252032092274355','套装','timg_1495715670939.jpg',2,'2','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('FL201705252035198748638','1','FL201705252032314584899','余姚茶叶','茶叶@3x_1495768985568.png',1,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('FL201705252109099874007','1','0','杨梅','ea92aa9220fbc49590d5633230201676_1495794439436.jpg',1,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('FL201705252109517559017','1','FL201705252109099874007','余姚杨梅','timg (14)_1495717792695.jpeg',1,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('FL201705252118462154226','1','0','原木钢笔','钢笔@3x_1495768854951.png',4,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('FL201705252119526804080','1','FL201705252118462154226','钢笔','343794091859278521_1495718392534.jpg',1,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC1','2','0','餐饮','1_1495367277295.png',3,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC10','2','0','旅游','10_1495367141677.png',1,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC11','2','0','教育培训','jiaoyupeix_1495367320827.jpg',9,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC2','2','0','书店','书@3x_1495857563587.png',6,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC3','2','0','电影','dianying_1495367330986.png',9,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC4','2','0','酒店','jiudian_1495367106339.png',1,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC5','2','0','休闲娱乐','xxyl_1495367306525.png',7,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC6','2','0','汽车','qiche_1495367093662.png',0,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC8','2','0','实地采摘','实地采摘@3x_1495857510041.png',3,'1','CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_category` (`code`,`type`,`parent_code`,`name`,`pic`,`order_no`,`status`,`company_code`,`system_code`) VALUES ('YC9','2','0','建材家居','jiajuss_1495367186736.jpg',2,'1','CD-CYC000009','CD-CYC000009');

/*
-- Query: SELECT * FROM yc_std_mall.tmall_product
-- Date: 2017-05-27 12:22
*/
INSERT INTO `tmall_product` (`code`,`category`,`type`,`name`,`slogan`,`adv_pic`,`pic`,`description`,`original_price`,`price1`,`price2`,`price3`,`location`,`order_no`,`status`,`updater`,`update_datetime`,`remark`,`bought_count`,`company_code`,`system_code`) VALUES ('CP201705262042471979305','FL201705252032092274355','FL201705252032466426656','笋干菜','好吃的农家笋干菜，天然','3副本_1495854240462.png','7副本_1495854230193.png||9副本_1495854234500.png','<p>自己笋干，自己晒干，自家种植</p><p><img src=\"http://oq4vi26fi.bkt.clouddn.com/1_1495853373159.png\" style=\"max-width:100%;\"><br></p><p><img src=\"http://oq4vi26fi.bkt.clouddn.com/5_1495853389002.png\" style=\"max-width:100%;\"><br></p><p><br></p>',199000,100000,102000,0,'1',4,'3','yaocheng','2017-05-27 11:07:52',NULL,0,'CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_product` (`code`,`category`,`type`,`name`,`slogan`,`adv_pic`,`pic`,`description`,`original_price`,`price1`,`price2`,`price3`,`location`,`order_no`,`status`,`updater`,`update_datetime`,`remark`,`bought_count`,`company_code`,`system_code`) VALUES ('CP201705262052343966143','FL201705252032314584899','FL201705252035198748638','余姚茶叶','好喝','882dffcd549e93f08d3c7225b2db94ef_1495803068654.jpg','cd03c5be871eed62fe6eb7a4cdca771f_1495857775305.jpg','<p><img src=\"http://oq4vi26fi.bkt.clouddn.com/摄图网-美丽的大自然风景_1495857857344.jpg\" style=\"max-width:100%;\"><br></p><p><img src=\"http://oq4vi26fi.bkt.clouddn.com/12_1495857870190.jpeg\" style=\"max-width:100%;\"><br></p><p><img src=\"http://oq4vi26fi.bkt.clouddn.com/13_1495857879513.jpeg\" style=\"max-width:100%;\"><br></p><p><br></p>',699000,499000,510000,0,'1',2,'3','yaocheng','2017-05-27 12:05:31',NULL,0,'CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_product` (`code`,`category`,`type`,`name`,`slogan`,`adv_pic`,`pic`,`description`,`original_price`,`price1`,`price2`,`price3`,`location`,`order_no`,`status`,`updater`,`update_datetime`,`remark`,`bought_count`,`company_code`,`system_code`) VALUES ('YcProductA','FL201705252118462154226','FL201705252119526804080','原木手工定制','高端定制 专属唯一','343794091859278521_1495853896250.jpg','311939146760115026_1495854076838.jpg||601579705763491731_1495854098493.jpg||19789791300603021_1495854162252.jpg||796451372108979497_1495854173982.jpg||31193914676011_1495854180395.jpg','<p style=\"text-align: center;\"><b><font size=\"3\">乡愫</font></b>--对陌生家乡，有种若即若离的情愫</p><p style=\"text-align: center;\"><font size=\"3\"><b>乡愫</b></font>--是原木手工笔的私人订制品牌，由独立设计师设计并制作。</p><p style=\"text-align: center;\">创始人是一名自由景观设计师，却对“木”情有独钟。</p><p><img src=\"http://121.40.165.180:8901/2016111423/1479136351360094560.jpeg\" alt=\"WechatIMG43.jpeg\"></p><p style=\"text-align: center;\">当代人，不可抗拒的依赖着手机电脑打字，</p><p style=\"text-align: center;\"><font size=\"3\"><b>书写</b></font>渐渐变成一种回忆。</p><p style=\"text-align: center;\">快节奏的生活，冷漠的情感表达，人变得麻木。</p><p style=\"text-align: center;\">希望可以唤起人们的书写<font size=\"3\"><b>情怀</b></font>，</p><p style=\"text-align: center;\">寻找，温柔的一面，独一无二的存在。</p><p style=\"text-align: center; \">想做手作人，这样一个用双手来表达<font size=\"3\"><b>生活态度</b></font>的人。</p><p style=\"text-align: center; \"><u><font color=\"#ff0000\">乡愫接受个人及企业线上私人订制，同时开设线下制作体验课程。</font></u></p><p><img src=\"http://oq4vi26fi.bkt.clouddn.com/WechatIMG91_1495854352262.jpeg\" style=\"font-size: 9pt; max-width: 100%;\" class=\"\"><br></p><p align=\"center\" style=\"text-align: center;\"><img src=\"http://oq4vi26fi.bkt.clouddn.com/WechatIMG64_1495854726012.jpeg\" style=\"max-width:100%;\" class=\"\"><br></p><p><br></p>',780000,780000,795600,0,'1',6,'3','yaocheng','2017-05-27 12:20:41',NULL,0,'CD-CYC000009','CD-CYC000009');
INSERT INTO `tmall_product` (`code`,`category`,`type`,`name`,`slogan`,`adv_pic`,`pic`,`description`,`original_price`,`price1`,`price2`,`price3`,`location`,`order_no`,`status`,`updater`,`update_datetime`,`remark`,`bought_count`,`company_code`,`system_code`) VALUES ('YcProductB','FL201705252109099874007','FL201705252109517559017','杨梅采摘','新鲜','9b427697de67e5ec92efa203312068ba_1495793622395.jpg','9461d820526894fd1392e98eacdf8716_1495793633544.png||419b3b16cd02679ff326d30748ffd40e_1495793718938.jpg','<p><img src=\"http://oq4vi26fi.bkt.clouddn.com/timg (10)_1495717861064.jpeg\" style=\"max-width:100%;\"><img src=\"http://oq4vi26fi.bkt.clouddn.com/timg (11)_1495717861538.jpeg\" style=\"font-size: 9pt; max-width: 100%;\"><img src=\"http://oq4vi26fi.bkt.clouddn.com/timg (12)_1495717861760.jpeg\" style=\"font-size: 9pt; max-width: 100%;\"><img src=\"http://oq4vi26fi.bkt.clouddn.com/timg (13)_1495717861844.jpeg\" style=\"font-size: 9pt; max-width: 100%;\"><img src=\"http://oq4vi26fi.bkt.clouddn.com/timg (14)_1495717861923.jpeg\" style=\"font-size: 9pt; max-width: 100%;\"><br></p><p><br></p>',599000,199000,200000,0,'1',1,'3','yaocheng','2017-05-27 09:40:44',NULL,0,'CD-CYC000009','CD-CYC000009');


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

