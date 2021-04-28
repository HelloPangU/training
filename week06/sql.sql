CREATE TABLE `t_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(50) DEFAULT NULL,
  `use_nick` varchar(255) DEFAULT NULL COMMENT '昵称',
  `balance` decimal(11,5) DEFAULT NULL COMMENT '余额',
  `user_level` varchar(255) DEFAULT NULL COMMENT '用户等级',
  `user_gender` char(1) DEFAULT NULL COMMENT 'm 男 w 女',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '修改人id',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `t_order` (
  `order_id` int(11) NOT NULL,
  `buyer_id` int(11) DEFAULT NULL COMMENT '购买人Id',
  `merchant_id` int(11) DEFAULT NULL COMMENT '商家id',
  `order_status` varchar(10) DEFAULT NULL COMMENT '订单状态',
  `money` decimal(11,5) DEFAULT NULL COMMENT '支付金额',
  `product_id` int(11) DEFAULT NULL COMMENT '产品id',
  `pay_status` varchar(10) DEFAULT NULL COMMENT '支付状态',
  `express_id` varchar(50) DEFAULT NULL COMMENT '快递单号',
  `buyer_remark` varchar(255) DEFAULT NULL COMMENT '客户备注',
  `merchant_remark` varchar(255) DEFAULT NULL COMMENT '商家备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_product` (
  `product_id` int(11) NOT NULL COMMENT '产品id',
  `merchant_id` int(11) DEFAULT NULL COMMENT '商家id',
  `money` decimal(11,5) DEFAULT NULL COMMENT '单价',
  `product_status` varchar(255) DEFAULT NULL COMMENT '商品状态',
  `product_remark` varchar(255) DEFAULT NULL COMMENT '商品备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
