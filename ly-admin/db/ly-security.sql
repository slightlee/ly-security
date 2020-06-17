/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50646
 Source Host           : 127.0.0.1:3306
 Source Schema         : ly-security

 Target Server Type    : MySQL
 Target Server Version : 50646
 File Encoding         : 65001

 Date: 16/06/2020 15:47:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  0：已删除  1：正常',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='部门管理';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (1, 0, '明天集团', 0, 1);
INSERT INTO `sys_dept` VALUES (2, 1, '北京分公司', 1, 1);
INSERT INTO `sys_dept` VALUES (3, 1, '上海分公司', 2, 1);
INSERT INTO `sys_dept` VALUES (4, 1, '武汉分公司', 3, 1);
INSERT INTO `sys_dept` VALUES (5, 0, '会议中心', 0, 1);
INSERT INTO `sys_dept` VALUES (6, 5, 'A企业', 1, 1);
INSERT INTO `sys_dept` VALUES (7, 5, 'B企业', 2, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
INSERT INTO `sys_log` VALUES (1, 'admin', '修改用户', 'com.ly.lyadmin.modules.sys.controller.SysUserController.update()', '{\"userId\":1,\"username\":\"admin\",\"password\":\"50a1609148ef2812d6871df5aff1d7795c3b2dbdab4429a5ec5248208ab369c5\",\"salt\":\"cH2RU7hkNczXWJtUVPJQ\",\"email\":\"lmm_work@163.com\",\"mobile\":\"17671781620\",\"status\":1,\"roleId\":0}', 768, '127.0.0.1', '2020-06-16 15:11:18');
INSERT INTO `sys_log` VALUES (2, 'admin', '修改用户', 'com.ly.lyadmin.modules.sys.controller.SysUserController.update()', '{\"userId\":2,\"username\":\"ly\",\"password\":\"571e6e4802ad54aedcb82c71021fc0ab5341a5a3c33871a772db7b5438982b08\",\"salt\":\"N1QWD1mGy3zndIhmlvpw\",\"email\":\"1@163.com\",\"mobile\":\"17671781620\",\"status\":1,\"roleId\":0}', 342, '127.0.0.1', '2020-06-16 15:11:39');
INSERT INTO `sys_log` VALUES (3, 'admin', '修改用户', 'com.ly.lyadmin.modules.sys.controller.SysUserController.update()', '{\"userId\":2,\"username\":\"ly\",\"password\":\"fd8168a316159b6030bf25d6fdca5578b53419808f28f2aea7fb8f6b59c64b19\",\"salt\":\"mOSvCyx59hr5yMUFtqqM\",\"email\":\"1@163.com\",\"mobile\":\"17671781620\",\"status\":1,\"roleId\":0}', 620, '127.0.0.1', '2020-06-16 15:13:06');
INSERT INTO `sys_log` VALUES (4, 'admin', '修改用户', 'com.ly.lyadmin.modules.sys.controller.SysUserController.update()', '{\"userId\":2,\"username\":\"ly\",\"password\":\"54bc4f4739a98d3e9b502000511e19093efedcdbee324e962e546d975d618899\",\"salt\":\"jOfkQasqy352ffFIY2Ed\",\"email\":\"1@163.com\",\"mobile\":\"17671781620\",\"status\":1,\"roleId\":0}', 528, '127.0.0.1', '2020-06-16 15:15:05');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', NULL, NULL, 0, '#icon-xitong', 0);
INSERT INTO `sys_menu` VALUES (2, 1, '管理员管理', 'sys/user/userlist.html', NULL, 1, '#icon-guanliyuan', 1);
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', 'sys/role/rolelist.html', NULL, 1, '#icon-jiaose', 2);
INSERT INTO `sys_menu` VALUES (4, 1, '菜单管理', 'sys/menu/menulist.html', NULL, 1, '#icon-iconset0194', 3);
INSERT INTO `sys_menu` VALUES (5, 1, 'SQL监控', 'druid/sql.html', NULL, 1, '#icon-xitongjiance', 4);
INSERT INTO `sys_menu` VALUES (6, 1, '定时任务', 'modules/job/schedule.html', NULL, 1, '#icon-dingshirenwu', 5);
INSERT INTO `sys_menu` VALUES (7, 6, '查看', NULL, 'sys:schedule:list,sys:schedule:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (8, 6, '新增', NULL, 'sys:schedule:save', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (9, 6, '修改', NULL, 'sys:schedule:update', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (10, 6, '删除', NULL, 'sys:schedule:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (11, 6, '暂停', NULL, 'sys:schedule:pause', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (12, 6, '恢复', NULL, 'sys:schedule:resume', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (13, 6, '立即执行', NULL, 'sys:schedule:run', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (14, 6, '日志列表', NULL, 'sys:schedule:log', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (20, 3, '新增', NULL, 'sys:role:save,sys:menu:perms', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (21, 3, '修改', NULL, 'sys:role:update,sys:menu:perms', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (27, 1, '参数管理', 'sys/config/configlist.html', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, '#icon-canshu', 6);
INSERT INTO `sys_menu` VALUES (29, 1, '系统日志', 'sys/log/loglist.html', 'sys:log:list', 1, '#icon-rizhi', 7);
INSERT INTO `sys_menu` VALUES (30, 1, '文件上传', 'modules/oss/oss.html', 'sys:oss:all', 1, '#icon-wenjianshangchuan', 8);
INSERT INTO `sys_menu` VALUES (31, 1, '部门管理', 'sys/dept/deptlist.html', NULL, 1, '#icon-bumenguanli1', 9);
INSERT INTO `sys_menu` VALUES (32, 31, '查看', NULL, 'sys:dept:list,sys:dept:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (33, 31, '新增', NULL, 'sys:dept:save,sys:dept:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (34, 31, '修改', NULL, 'sys:dept:update,sys:dept:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (35, 31, '删除', NULL, 'sys:dept:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (36, 1, '字典管理', 'sys/dict/dict.html', NULL, 1, '#icon-zidian', 6);
INSERT INTO `sys_menu` VALUES (37, 36, '查看', NULL, 'sys:dict:list,sys:dict:info', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (38, 36, '新增', NULL, 'sys:dict:save', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (39, 36, '修改', NULL, 'sys:dict:update', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (40, 36, '删除', NULL, 'sys:dict:delete', 2, NULL, 6);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (0, '平台管理员', '平台管理员', NULL, '2019-12-11 10:56:02', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与部门对应关系';

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=326 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (287, 0, 1);
INSERT INTO `sys_role_menu` VALUES (288, 0, 2);
INSERT INTO `sys_role_menu` VALUES (289, 0, 15);
INSERT INTO `sys_role_menu` VALUES (290, 0, 16);
INSERT INTO `sys_role_menu` VALUES (291, 0, 17);
INSERT INTO `sys_role_menu` VALUES (292, 0, 18);
INSERT INTO `sys_role_menu` VALUES (293, 0, 3);
INSERT INTO `sys_role_menu` VALUES (294, 0, 19);
INSERT INTO `sys_role_menu` VALUES (295, 0, 20);
INSERT INTO `sys_role_menu` VALUES (296, 0, 21);
INSERT INTO `sys_role_menu` VALUES (297, 0, 22);
INSERT INTO `sys_role_menu` VALUES (298, 0, 4);
INSERT INTO `sys_role_menu` VALUES (299, 0, 23);
INSERT INTO `sys_role_menu` VALUES (300, 0, 24);
INSERT INTO `sys_role_menu` VALUES (301, 0, 25);
INSERT INTO `sys_role_menu` VALUES (302, 0, 26);
INSERT INTO `sys_role_menu` VALUES (303, 0, 5);
INSERT INTO `sys_role_menu` VALUES (304, 0, 6);
INSERT INTO `sys_role_menu` VALUES (305, 0, 7);
INSERT INTO `sys_role_menu` VALUES (306, 0, 8);
INSERT INTO `sys_role_menu` VALUES (307, 0, 9);
INSERT INTO `sys_role_menu` VALUES (308, 0, 10);
INSERT INTO `sys_role_menu` VALUES (309, 0, 11);
INSERT INTO `sys_role_menu` VALUES (310, 0, 12);
INSERT INTO `sys_role_menu` VALUES (311, 0, 13);
INSERT INTO `sys_role_menu` VALUES (312, 0, 14);
INSERT INTO `sys_role_menu` VALUES (313, 0, 27);
INSERT INTO `sys_role_menu` VALUES (314, 0, 29);
INSERT INTO `sys_role_menu` VALUES (315, 0, 30);
INSERT INTO `sys_role_menu` VALUES (316, 0, 31);
INSERT INTO `sys_role_menu` VALUES (317, 0, 32);
INSERT INTO `sys_role_menu` VALUES (318, 0, 33);
INSERT INTO `sys_role_menu` VALUES (319, 0, 34);
INSERT INTO `sys_role_menu` VALUES (320, 0, 35);
INSERT INTO `sys_role_menu` VALUES (321, 0, 36);
INSERT INTO `sys_role_menu` VALUES (322, 0, 37);
INSERT INTO `sys_role_menu` VALUES (323, 0, 38);
INSERT INTO `sys_role_menu` VALUES (324, 0, 39);
INSERT INTO `sys_role_menu` VALUES (325, 0, 40);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人编号',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'admin', '50a1609148ef2812d6871df5aff1d7795c3b2dbdab4429a5ec5248208ab369c5', 'cH2RU7hkNczXWJtUVPJQ', 'lmm_work@163.com', '17671781620', 1, 1, '2019-11-11 11:11:11', NULL, NULL);
INSERT INTO `sys_user` VALUES (2, 'ly', '54bc4f4739a98d3e9b502000511e19093efedcdbee324e962e546d975d618899', 'jOfkQasqy352ffFIY2Ed', '1@163.com', '17671781620', 1, NULL, '2019-11-13 11:18:14', 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (10, 1, 0);
INSERT INTO `sys_user_role` VALUES (13, 2, 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
