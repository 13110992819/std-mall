DROP TABLE IF EXISTS `tmall_vproduct`;
CREATE TABLE `tmall_vproduct` (
  `code` varchar(32) NOT NULL COMMENT '商品编号',
  `type` varchar(4) DEFAULT NULL COMMENT '类型',
  `name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `slogan` varchar(255) DEFAULT NULL COMMENT '广告语',
  `adv_pic` varchar(255) DEFAULT NULL COMMENT '广告图',
  
  `pic` text DEFAULT NULL COMMENT 'pic',
  `description` text COMMENT '图文描述',
  `price` varchar(255) DEFAULT NULL COMMENT '价格档（逗号隔开）',
  `rate` decimal(18,8) DEFAULT NULL COMMENT '1个产品价格币种，兑换rate个支付币种',
  `location` varchar(32) DEFAULT NULL COMMENT '位置(0 普通)',
  
  `order_no` int(11) DEFAULT NULL COMMENT '相对位置编号',
  `status` varchar(4) DEFAULT NULL COMMENT '产品状态',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  
  `company_code` varchar(32) DEFAULT NULL COMMENT '所属公司',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`) COMMENT '虚拟产品'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tmall_vorder`;
CREATE TABLE `tmall_vorder` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `product_code` varchar(32) DEFAULT NULL COMMENT '产品编号',
  `re_cardno` varchar(255) DEFAULT NULL COMMENT '收件人卡号',
  `re_name` varchar(255) DEFAULT NULL COMMENT '收件人姓名',
  `re_mobile` varchar(32) DEFAULT NULL COMMENT '收件人电话',
  
  `apply_user` varchar(32) DEFAULT NULL COMMENT '下单人',
  `apply_datetime` datetime DEFAULT NULL COMMENT '下单时间',
  `amount` bigint(20) DEFAULT NULL COMMENT '金额',
  `pay_amount` bigint(20) DEFAULT NULL COMMENT '支付金额',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（待支付/已支付/已取消/已发货）',
  
  `pay_type` varchar(4) DEFAULT NULL COMMENT '支付方式',
  `pay_group` varchar(32) DEFAULT NULL COMMENT '支付组号',
  `pay_code` varchar(32) DEFAULT NULL COMMENT '支付编号',
  `pay_datetime` datetime DEFAULT NULL COMMENT '支付时间',
  `handle_user` varchar(32) DEFAULT NULL COMMENT '处理人编号',
  
  `handle_datetime` datetime DEFAULT NULL COMMENT '处理时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`) COMMENT '虚拟产品订单'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

