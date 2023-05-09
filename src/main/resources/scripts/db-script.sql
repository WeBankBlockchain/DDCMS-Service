
CREATE TABLE `t_account_info` (
  `pk_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL DEFAULT '' COMMENT '账号',
  `did` varchar(255) NOT NULL COMMENT 'DID',
  `account_type` int(4) NOT NULL DEFAULT '0' COMMENT '用户类型',
  `private_key` varchar(66) NOT NULL DEFAULT '' COMMENT '私钥',
  `password` varchar(255) NOT NULL DEFAULT '',
  `status` int(4) NOT NULL DEFAULT '0' COMMENT '账户状态',
  `review_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审核时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_id`),
  UNIQUE KEY `did` (`did`),
  UNIQUE KEY `username` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;


CREATE TABLE `t_person_info` (
  `pk_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `account_id` bigint(20) unsigned NOT NULL COMMENT '账户外键ID',
  `person_name` varchar(32) NOT NULL DEFAULT '' COMMENT '姓名',
  `person_contact` varchar(32) NOT NULL DEFAULT '' COMMENT '个人联系方式',
  `person_email` varchar(128) NOT NULL DEFAULT '' COMMENT '个人邮箱',
  `person_cert_type` varchar(32) NOT NULL DEFAULT '0' COMMENT '个人证件类型',
  `person_cert_no` varchar(128) NOT NULL DEFAULT '' COMMENT '个人证件号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_id`),
  UNIQUE KEY `account_id` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


CREATE TABLE `t_company_info` (
  `pk_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `account_id` bigint(20) unsigned NOT NULL COMMENT '账户外键ID',
  `company_name` varchar(255) NOT NULL COMMENT '公司名称',
  `company_desc` text COMMENT '公司详情',
  `company_cert_type` varchar(32) NOT NULL DEFAULT '0' COMMENT '法人证件类型',
  `company_cert_no` varchar(128) NOT NULL DEFAULT '' COMMENT '法人证件号',
  `company_cert_file_uri` varchar(255) DEFAULT NULL COMMENT '公司证件图片链接',
  `company_contact` varchar(32) NOT NULL DEFAULT '' COMMENT '公司联系方式',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_id`),
  UNIQUE KEY `account_id` (`account_id`),
  UNIQUE KEY `company_name` (`company_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

create table t_product_info
(
    pk_id bigint unsigned auto_increment
        primary key,
    product_bid varchar(255) not null comment '产品链上id',
    product_name varchar(255) default '' not null comment '产品名称',
    provider_id bigint unsigned not null comment '提供方外键ID',
    product_desc text null comment '产品详情',
    status int(4) default 0 not null comment '审核状态',
    review_time timestamp default CURRENT_TIMESTAMP not null comment '审核时间',
    create_time timestamp default CURRENT_TIMESTAMP not null,
    update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint product_gid
        unique (product_bid)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;


 CREATE TABLE `t_tag_info` (
   `pk_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
   `tag_name` varchar(64) NOT NULL DEFAULT '' COMMENT '标签名',
   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`pk_id`),
   UNIQUE KEY (`tag_name`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table t_data_schema_info
(
    pk_id bigint unsigned auto_increment
        primary key,
    data_schema_bid varchar(255) default '' not null comment '数据目录链上id',
    data_schema_name varchar(255) default '' not null comment '数据目录名称',
    provider_id bigint default 0 not null comment '提供方外键id',
    product_id bigint unsigned not null comment '产品外键ID',
    version int(4) default 0 not null comment '版本号',
    visible int(4) default 0 not null comment '是否可见',
    data_schema_desc text null comment '描述',
    data_schema_usage text comment '用途',
    price int(32) default 0 not null comment '价格',
    create_time timestamp default CURRENT_TIMESTAMP not null,
    update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    status int(4) default 0 not null comment '审核状态',
    review_time timestamp default CURRENT_TIMESTAMP not null comment '审核时间',
    constraint data_schema_gid
        unique (data_schema_bid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




 CREATE TABLE `t_data_schema_tags` (
   `pk_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
   `data_schema_id`  bigint(20) unsigned NOT NULL COMMENT '数据目录外键id',
   `tag_id`  bigint(20) unsigned NOT NULL COMMENT '标签外键id',
   PRIMARY KEY (`pk_id`),
   KEY (`data_schema_id`),
   KEY (`tag_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 CREATE TABLE `t_data_schema_access_info` (
    `pk_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `data_schema_id` bigint(20) unsigned NOT NULL COMMENT '数据目录外键id',
    `data_format` int(4) NOT NULL DEFAULT 0 COMMENT '类型，json-0，xml-1，doc-2，pic-3...',
    `data_protocol` int(4) NOT NULL DEFAULT 0 COMMENT '类型，HTTP-0，HTTPS-1，SFTP-2...',
	`content_schema` text  COMMENT '内容格式',
    `access_condition` text  COMMENT '数据的查询条件定义',
    `uri` varchar(64) NOT NULL DEFAULT '' COMMENT '数据访问连接',
    `effect_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生效时间',
    `expire_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '失效时间',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`pk_id`),
    UNIQUE KEY (`data_schema_id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

   CREATE TABLE `t_menu_info` (
     `pk_id` bigint unsigned NOT NULL AUTO_INCREMENT,
     `menu_id` int DEFAULT NULL,
     `menu_name` varchar(64) NOT NULL DEFAULT '' COMMENT '菜单名',
     `parent_id` bigint unsigned NOT NULL COMMENT '父菜单id',
     `menu_url` varchar(255) NOT NULL DEFAULT '' COMMENT '菜单URL',
     `menu_role` int NOT NULL COMMENT '菜单角色',
     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     PRIMARY KEY (`pk_id`)
   ) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;


CREATE TABLE `t_schema_favorite_info` (
  `pk_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
  `account_id` bigint(20) unsigned NOT NULL COMMENT '账户外键ID',
  `schema_id` bigint(20) unsigned NOT NULL COMMENT '产品ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_id`),
  UNIQUE KEY `account_schema` (`account_id`,`schema_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `t_review_record_info` (
  `pk_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `item_id` bigint(20) unsigned NOT NULL COMMENT '外键ID，产品或目录ID',
  `item_type` int(4) NOT NULL DEFAULT 0 COMMENT '类型，产品-1，目录-2',
  `review_state` int(4) NOT NULL DEFAULT 0 COMMENT '审核状态,审核中-0，已通过-1，已拒绝-2',
  `agree_count` int(4) NOT NULL DEFAULT 0 COMMENT '赞同人数',
  `deny_count` int(4) NOT NULL DEFAULT 0 COMMENT '反对人数',
  `witness_count` int(4) NOT NULL DEFAULT 0 COMMENT '投票总人数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_id`),
  UNIQUE KEY `item_id_type` (`item_id`,`item_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO t_menu_info (pk_id, menu_id, menu_name, parent_id, menu_url, menu_role, create_time, update_time) VALUES (1, 1, '用户管理', 0, '', 3, '2023-03-20 15:46:40', '2023-03-20 16:09:20');
INSERT INTO t_menu_info (pk_id, menu_id, menu_name, parent_id, menu_url, menu_role, create_time, update_time) VALUES (2, 1, '用户管理', 0, '', 2, '2023-03-20 15:48:21', '2023-03-20 16:09:24');
INSERT INTO t_menu_info (pk_id, menu_id, menu_name, parent_id, menu_url, menu_role, create_time, update_time) VALUES (3, 2, '业务管理', 0, '', 3, '2023-03-20 15:48:41', '2023-03-20 16:09:27');
INSERT INTO t_menu_info (pk_id, menu_id, menu_name, parent_id, menu_url, menu_role, create_time, update_time) VALUES (4, 2, '业务管理', 0, '', 2, '2023-03-20 15:49:10', '2023-03-20 16:09:28');
INSERT INTO t_menu_info (pk_id, menu_id, menu_name, parent_id, menu_url, menu_role, create_time, update_time) VALUES (5, 2, '业务管理', 0, '', 1, '2023-03-20 15:49:19', '2023-03-20 16:09:31');
INSERT INTO t_menu_info (pk_id, menu_id, menu_name, parent_id, menu_url, menu_role, create_time, update_time) VALUES (6, 3, '数据目录管理', 0, '', 3, '2023-03-20 15:49:37', '2023-03-20 16:09:33');
INSERT INTO t_menu_info (pk_id, menu_id, menu_name, parent_id, menu_url, menu_role, create_time, update_time) VALUES (7, 3, '数据目录管理', 0, '', 2, '2023-03-20 15:49:46', '2023-03-20 16:09:34');
INSERT INTO t_menu_info (pk_id, menu_id, menu_name, parent_id, menu_url, menu_role, create_time, update_time) VALUES (8, 3, '数据目录管理', 0, '', 1, '2023-03-20 15:49:51', '2023-03-20 16:09:35');
INSERT INTO t_menu_info (pk_id, menu_id, menu_name, parent_id, menu_url, menu_role, create_time, update_time) VALUES (9, 4, '所有业务', 2, 'product/all', 3, '2023-03-20 16:10:36', '2023-03-22 14:14:00');
INSERT INTO t_menu_info (pk_id, menu_id, menu_name, parent_id, menu_url, menu_role, create_time, update_time) VALUES (10, 4, '所有业务', 2, 'product/all', 2, '2023-03-20 16:11:32', '2023-03-22 14:14:00');
INSERT INTO t_menu_info (pk_id, menu_id, menu_name, parent_id, menu_url, menu_role, create_time, update_time) VALUES (12, 5, '我的业务', 2, 'product/my', 2, '2023-03-20 16:12:16', '2023-03-22 14:15:28');
INSERT INTO t_menu_info (pk_id, menu_id, menu_name, parent_id, menu_url, menu_role, create_time, update_time) VALUES (13, 5, '我的业务', 2, 'product/my', 1, '2023-03-20 16:12:58', '2023-03-22 14:15:28');
INSERT INTO t_menu_info (pk_id, menu_id, menu_name, parent_id, menu_url, menu_role, create_time, update_time) VALUES (14, 6, '所有数据目录', 3, 'schema/all', 3, '2023-03-20 16:13:39', '2023-03-21 15:14:43');
INSERT INTO t_menu_info (pk_id, menu_id, menu_name, parent_id, menu_url, menu_role, create_time, update_time) VALUES (15, 6, '所有数据目录', 3, 'schema/all', 2, '2023-03-20 16:14:44', '2023-03-21 15:14:43');
INSERT INTO t_menu_info (pk_id, menu_id, menu_name, parent_id, menu_url, menu_role, create_time, update_time) VALUES (16, 7, '我的数据目录', 3, 'schema/my', 2, '2023-03-20 16:15:57', '2023-03-21 16:07:52');
INSERT INTO t_menu_info (pk_id, menu_id, menu_name, parent_id, menu_url, menu_role, create_time, update_time) VALUES (17, 7, '我的数据目录', 3, 'schema/my', 1, '2023-03-20 16:16:30', '2023-03-21 16:07:52');
INSERT INTO t_menu_info (pk_id, menu_id, menu_name, parent_id, menu_url, menu_role, create_time, update_time) VALUES (18, 8, '收藏数据目录', 3, 'schema/fav', 1, '2023-03-20 16:16:53', '2023-03-21 19:04:12');
INSERT INTO t_menu_info (pk_id, menu_id, menu_name, parent_id, menu_url, menu_role, create_time, update_time) VALUES (19, 11, '用户审核', 1, 'account/list', 3, '2023-03-21 16:34:15', '2023-03-21 18:43:27');

