
DROP TABLE IF EXISTS `tmall_category`;
CREATE TABLE `tmall_category` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `type` varchar(4) DEFAULT NULL COMMENT '类型',
  `parent_code` varchar(32) DEFAULT NULL COMMENT '父节点',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `pic` varchar(255) DEFAULT NULL COMMENT '图片',
  `order_no` int(11) DEFAULT NULL COMMENT '序号',
  `status` varchar(4) DEFAULT NULL COMMENT '状态',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`) COMMENT '分类'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tmall_product`;
CREATE TABLE `tmall_product` (
  `code` varchar(32) NOT NULL COMMENT '商品编号',
  `store_code` varchar(32) DEFAULT NULL COMMENT '商家编号',
  `kind` varchar(32) DEFAULT NULL COMMENT '类型',
  `category` varchar(32) DEFAULT NULL COMMENT '大类',
  `type` varchar(32) DEFAULT NULL COMMENT '小类',
  `name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `slogan` varchar(255) DEFAULT NULL COMMENT '广告语',
  `adv_pic` varchar(255) DEFAULT NULL COMMENT '广告图',
  `pic` text DEFAULT NULL COMMENT 'pic',
  `description` text COMMENT '图文描述',
  `location` varchar(32) DEFAULT NULL COMMENT '位置(0 普通 1 热门)',
  `order_no` int(11) DEFAULT NULL COMMENT '相对位置编号',
  `status` varchar(4) DEFAULT NULL COMMENT '产品状态',
  `bought_count` int(11) DEFAULT NULL COMMENT '已购买数量',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `company_code` varchar(32) DEFAULT NULL COMMENT '所属公司',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`) COMMENT '产品'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `tmall_product_specs`;
CREATE TABLE `tmall_product_specs` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `product_code` varchar(32) DEFAULT NULL COMMENT '商品编号',
  `original_price` bigint(20) DEFAULT NULL COMMENT '原价',
  `price1` bigint(20) DEFAULT NULL COMMENT '价格1（人民币）',
  `price2` bigint(20) DEFAULT NULL COMMENT '价格2（虚拟币1）',
  `price3` bigint(20) DEFAULT NULL COMMENT '价格3（虚拟币2）',
  `quantity` int(11) DEFAULT NULL COMMENT '库存',
  `province` varchar(255) DEFAULT NULL COMMENT '发货地,精确到省份',
  `weight` DECIMAL(18,8) DEFAULT NULL COMMENT '重量',
  `order_no` int(11) DEFAULT NULL COMMENT '相对位置编号',
  `company_code` varchar(32) DEFAULT NULL COMMENT '所属公司',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tmall_cart`;
CREATE TABLE `tmall_cart` (
  `code` varchar(32) NOT NULL COMMENT '购物车编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `product_code` varchar(32) DEFAULT NULL COMMENT '产品编号',
  `product_specs_code` varchar(32) DEFAULT NULL COMMENT '产品参数编号',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`) COMMENT '购物车'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tmall_express_rule`;
CREATE TABLE `tmall_express_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `start_point` varchar(255) DEFAULT NULL COMMENT '起点',
  `end_point` varchar(255) DEFAULT NULL COMMENT '终点',
  `price` bigint(20) DEFAULT NULL COMMENT '价格',
  
  `updater` varchar(32) NOT NULL COMMENT '更新人',
  `update_datetime` datetime NOT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`id`) COMMENT '快递规则'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tmall_order`;
CREATE TABLE `tmall_order` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `type` varchar(4) DEFAULT NULL COMMENT '类型(邮寄/自提)',
  `to_user` varchar(32) DEFAULT NULL COMMENT '向谁要货',
  `take_address` varchar(255) DEFAULT NULL COMMENT '提货地址',
  `receiver` varchar(255) DEFAULT NULL COMMENT '收件人姓名',
  `re_mobile` varchar(32) DEFAULT NULL COMMENT '收件人电话',
  `re_address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `receipt_type` varchar(4) DEFAULT NULL COMMENT '发票类型(1 个人，2 企业)',
  `receipt_title` varchar(32) DEFAULT NULL COMMENT '发票抬头',
  `apply_user` varchar(32) DEFAULT NULL COMMENT '下单人',
  `apply_note` varchar(255) DEFAULT NULL COMMENT '下单嘱咐',
  `apply_datetime` datetime DEFAULT NULL COMMENT '下单时间',
  `amount1` bigint(20) DEFAULT NULL COMMENT '金额1',
  `amount2` bigint(20) DEFAULT NULL COMMENT '金额2',
  `amount3` bigint(20) DEFAULT NULL COMMENT '金额3',
  `yunfei` bigint(20) DEFAULT NULL COMMENT '运费',
  `status` varchar(4) DEFAULT NULL COMMENT '状态',
  `pay_type` varchar(32) DEFAULT NULL COMMENT '支付方式',
  `pay_group` varchar(32) DEFAULT NULL COMMENT '支付组号',
  `pay_code` varchar(32) DEFAULT NULL COMMENT '支付编号',
  `pay_datetime` datetime DEFAULT NULL COMMENT '支付时间',
  `pay_amount1` bigint(20) DEFAULT NULL COMMENT '支付金额1',
  `pay_amount2` bigint(20) DEFAULT NULL COMMENT '支付金额2',
  `pay_amount3` bigint(20) DEFAULT NULL COMMENT '支付金额3',
  `prompt_times` int(11) DEFAULT NULL COMMENT '催货次数',
  `deliverer` varchar(32) DEFAULT NULL COMMENT '发货人',
  `delivery_datetime` datetime DEFAULT NULL COMMENT '发货时间',
  `logistics_company` varchar(32) DEFAULT NULL COMMENT '物流公司编号',
  `logistics_code` varchar(32) DEFAULT NULL COMMENT '物流单号',
  `pdf` varchar(255) DEFAULT NULL COMMENT '物流单',
  `signer` varchar(32) DEFAULT NULL COMMENT '签收人',
  `sign_datetime` datetime DEFAULT NULL COMMENT '签收时间',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`) COMMENT '订单'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tmall_product_order`;
CREATE TABLE `tmall_product_order` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `order_code` varchar(32) DEFAULT NULL COMMENT '订单编号',
  `product_code` varchar(32) DEFAULT NULL COMMENT '商品编号',
  `product_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `product_type` varchar(32) DEFAULT NULL COMMENT '商品类型',
  `product_specs_code` varchar(32) DEFAULT NULL COMMENT '商品参数编号',
  `product_specs_name` varchar(32) DEFAULT NULL COMMENT '商品参数名称',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  `price1` bigint(20) DEFAULT NULL COMMENT '价格1',
  `price2` bigint(20) DEFAULT NULL COMMENT '价格2',
  `price3` bigint(20) DEFAULT NULL COMMENT '价格3',
  `is_evaluate` boolean DEFAULT NULL COMMENT '是否已评价',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`) COMMENT '订单产品'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tsys_config`;
CREATE TABLE `tsys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `ckey` varchar(32) DEFAULT NULL COMMENT 'key值',
  `cvalue` text COMMENT '值',
  
  `updater` varchar(32) NOT NULL COMMENT '更新人',
  `update_datetime` datetime NOT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`id`) COMMENT '系统参数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tsys_dict`;
CREATE TABLE `tsys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号（自增长）',
  `type` char(1) NOT NULL COMMENT '类型（0=下拉框意义 1=下拉框选项）',
  `parent_key` varchar(32) DEFAULT NULL COMMENT '父key',
  `dkey` varchar(32) NOT NULL COMMENT 'key',
  `dvalue` varchar(64) NOT NULL COMMENT '值',
  
  `updater` varchar(32) NOT NULL COMMENT '更新人',
  `update_datetime` datetime NOT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`id`) COMMENT '数据字典'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
