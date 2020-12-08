/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : oauth-server

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 04/12/2020 17:12:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `created_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES (1, '2020-10-16 16:31:37', '2020-10-20 10:04:15', 'ROLE_SUPERADMIN');
INSERT INTO `authority` VALUES (2, '2020-10-16 16:31:37', '2020-10-20 10:04:18', 'ROLE_ADMIN');
INSERT INTO `authority` VALUES (3, '2020-10-16 16:35:13', '2020-10-20 10:04:21', 'ROLE_USER');

-- ----------------------------
-- Table structure for email_msg_log
-- ----------------------------
DROP TABLE IF EXISTS `email_msg_log`;
CREATE TABLE `email_msg_log`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '消息唯一标识',
  `created_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '邮件标题',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '邮件名',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '消息内容',
  `email_enum` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮件格式',
  `exchange` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '交换机',
  `routing_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '路由键',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '状态: 0投递中 1投递成功 2投递失败 3已消费',
  `try_count` int(0) NOT NULL DEFAULT 0 COMMENT '重试次数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 196 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '消息投递日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of email_msg_log
-- ----------------------------
INSERT INTO `email_msg_log` VALUES (164, '2020-11-23 16:31:34', '2020-11-25 10:36:24', '南风落尽', 'wzp9215@qq.com', '光与影错落\n于墙上斑驳\n岁月中蹉跎\n印时光你我', '普通邮件', 'emailExchange', 'oauth', 1, 0);
INSERT INTO `email_msg_log` VALUES (165, '2020-11-25 09:53:30', '2020-11-25 10:36:22', '南风落尽', 'wzp9215@qq.com', '光与影错落\n于墙上斑驳\n岁月中蹉跎\n印时光你我', '普通邮件', 'emailExchange', 'oauth', 1, 0);
INSERT INTO `email_msg_log` VALUES (166, '2020-11-25 09:54:05', '2020-11-25 10:36:22', '南风落尽', 'wzp9215@qq.com', '光与影错落\n于墙上斑驳\n岁月中蹉跎\n印时光你我', '普通邮件', 'emailExchange', 'oauth', 1, 0);
INSERT INTO `email_msg_log` VALUES (167, '2020-11-25 10:10:16', '2020-11-25 10:36:21', '南风落尽', 'wzp9215@qq.com', '光与影错落\n于墙上斑驳\n岁月中蹉跎\n印时光你我', '普通邮件', 'emailExchange', 'oauth', 1, 0);
INSERT INTO `email_msg_log` VALUES (168, '2020-11-25 10:13:31', '2020-11-25 10:36:20', '南风落尽', 'wzp9215@qq.com', '光与影错落\n于墙上斑驳\n岁月中蹉跎\n印时光你我', '普通邮件', 'emailExchange', 'oauth', 1, 0);
INSERT INTO `email_msg_log` VALUES (169, '2020-11-25 10:33:54', '2020-11-25 10:33:54', '南风落尽', 'wzp9215@qq.com', '光与影错落\n于墙上斑驳\n岁月中蹉跎\n印时光你我', '普通邮件', 'emailExchange', 'oauth', 1, 0);
INSERT INTO `email_msg_log` VALUES (170, '2020-11-25 10:34:26', '2020-11-25 10:34:26', '南风落尽', 'wzp9215@qq.com', '光与影错落\n于墙上斑驳\n岁月中蹉跎\n印时光你我', '附件邮件', 'emailExchange', 'oauth', 1, 0);
INSERT INTO `email_msg_log` VALUES (171, '2020-11-25 10:36:38', '2020-11-25 10:36:38', '南风落尽', 'wzp9215@qq.com', '光与影错落\n于墙上斑驳\n岁月中蹉跎\n印时光你我', '附件邮件', 'emailExchange', 'oauth', 1, 0);
INSERT INTO `email_msg_log` VALUES (172, '2020-11-25 10:39:53', '2020-11-25 10:39:53', '南风落尽', 'wzp9215@qq.com', '光与影错落\n于墙上斑驳\n岁月中蹉跎\n印时光你我', '附件邮件', 'emailExchange', 'oauth', 1, 0);
INSERT INTO `email_msg_log` VALUES (173, '2020-11-25 11:11:01', '2020-11-25 11:11:01', '南风落尽', 'wzp9215@qq.com', '光与影错落\n于墙上斑驳\n岁月中蹉跎\n印时光你我', '附件邮件', 'emailExchange', 'oauth', 1, 0);
INSERT INTO `email_msg_log` VALUES (174, '2020-11-25 11:11:37', '2020-11-25 11:11:37', '南风落尽', 'wzp9215@qq.com', '光与影错落\n于墙上斑驳\n岁月中蹉跎\n印时光你我', '附件邮件', 'emailExchange', 'oauth', 1, 0);
INSERT INTO `email_msg_log` VALUES (175, '2020-11-25 11:13:58', '2020-11-25 11:13:58', '南风落尽', 'wzp9215@qq.com', '光与影错落\n于墙上斑驳\n岁月中蹉跎\n印时光你我', '附件邮件', 'emailExchange', 'oauth', 1, 0);
INSERT INTO `email_msg_log` VALUES (176, '2020-11-25 11:14:07', '2020-11-25 11:14:07', '南风落尽', 'wzp9215@qq.com', '光与影错落\n于墙上斑驳\n岁月中蹉跎\n印时光你我', '附件邮件', 'emailExchange', 'oauth', 1, 0);
INSERT INTO `email_msg_log` VALUES (190, '2020-11-25 11:38:19', '2020-11-25 11:38:19', '南风落尽', 'wzp9215@qq.com', '测试一下附件的发送', '附件邮件', 'emailExchange', 'oauth', 1, 0);
INSERT INTO `email_msg_log` VALUES (191, '2020-11-26 13:31:33', '2020-11-26 13:31:33', '南风落尽', 'wzp9215@qq.com', '测试一下附件的发送', '附件邮件', 'emailExchange', 'oauth', 1, 0);
INSERT INTO `email_msg_log` VALUES (192, '2020-11-26 13:35:02', '2020-11-26 13:35:02', '南风落尽', 'wzp9215@qq.com', '测试一下附件的发送', '附件邮件', 'emailExchange', 'oauth', 1, 0);
INSERT INTO `email_msg_log` VALUES (193, '2020-11-26 14:41:21', '2020-11-26 14:41:21', '南风落尽', 'wzp9215@qq.com', '测试一下附件的发送', '附件邮件', 'emailExchange', 'oauth', 1, 0);
INSERT INTO `email_msg_log` VALUES (194, '2020-11-26 14:44:56', '2020-11-26 14:44:56', '南风落尽', 'wzp9215@qq.com', '测试一下附件的发送', '附件邮件', 'emailExchange', 'oauth', 1, 0);
INSERT INTO `email_msg_log` VALUES (195, '2020-11-27 10:25:08', '2020-11-27 10:25:08', '南风落尽', 'wzp9215@qq.com', '测试一下附件的发送', '附件邮件', 'emailExchange', 'oauth', 1, 0);

-- ----------------------------
-- Table structure for file_list
-- ----------------------------
DROP TABLE IF EXISTS `file_list`;
CREATE TABLE `file_list`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID自增',
  `created_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `file_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '文件名',
  `file_size` bigint(0) NOT NULL COMMENT '文件大小',
  `removed` bit(1) NOT NULL DEFAULT b'0' COMMENT '0：未删除 1：已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_list
-- ----------------------------
INSERT INTO `file_list` VALUES (3, '2020-10-16 17:37:45', '2020-10-16 17:37:45', '南风', 100001, b'0');
INSERT INTO `file_list` VALUES (4, '2020-10-16 17:49:45', '2020-10-19 10:49:33', '31', 100001, b'0');
INSERT INTO `file_list` VALUES (5, '2020-10-19 10:38:34', '2020-10-19 10:38:34', '南风', 100001, b'0');
INSERT INTO `file_list` VALUES (6, '2020-10-19 10:39:40', '2020-10-19 10:39:40', '南风', 100001, b'0');
INSERT INTO `file_list` VALUES (7, '2020-10-28 16:00:03', '2020-10-28 16:00:03', '南风', 100001, b'0');

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `created_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `user_id` bigint(0) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `login_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `login_log_enum` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of login_log
-- ----------------------------
INSERT INTO `login_log` VALUES (1, '2020-11-16 16:33:52', '2020-11-16 17:26:24', 1, '超级管理员', '用户超级管理员登录', '10.0.30.60', '登录');
INSERT INTO `login_log` VALUES (2, '2020-11-16 16:33:52', '2020-11-16 17:26:22', 1, '超级管理员', '用户超级管理员登录', '10.0.30.60', '登录');
INSERT INTO `login_log` VALUES (3, '2020-11-16 16:40:32', '2020-11-16 17:26:20', 1, '超级管理员', '用户超级管理员登录', '10.0.30.60', '登录');
INSERT INTO `login_log` VALUES (4, '2020-11-16 16:41:02', '2020-11-16 17:26:18', 1, '超级管理员', '用户超级管理员登录', '10.0.30.60', '登录');
INSERT INTO `login_log` VALUES (5, '2020-11-16 16:42:35', '2020-11-16 17:26:17', 1, '超级管理员', '用户超级管理员登录', '10.0.30.60', '登录');
INSERT INTO `login_log` VALUES (6, '2020-11-16 16:44:24', '2020-11-16 17:26:15', 1, '超级管理员', '用户超级管理员登录', '10.0.30.60', '登录');
INSERT INTO `login_log` VALUES (7, '2020-11-16 16:48:54', '2020-11-16 17:26:14', 1, '超级管理员', '用户超级管理员登录', '10.0.30.60', '登录');
INSERT INTO `login_log` VALUES (8, '2020-11-16 17:19:00', '2020-11-16 17:26:13', 1, '超级管理员', '用户超级管理员登录', '10.0.30.60', '登录');
INSERT INTO `login_log` VALUES (9, '2020-11-16 17:22:33', '2020-11-16 17:29:18', 1, '超级管理员', '用户超级管理员退出登录', '10.0.30.60', '退出');
INSERT INTO `login_log` VALUES (10, '2020-11-16 18:05:39', '2020-11-16 18:05:39', 1, '超级管理员', '用户超级管理员登录', '10.0.30.60', '登录');
INSERT INTO `login_log` VALUES (11, '2020-11-16 18:05:45', '2020-11-16 18:05:45', 1, '超级管理员', '用户超级管理员退出登录', '10.0.30.60', '退出');
INSERT INTO `login_log` VALUES (12, '2020-11-17 09:39:15', '2020-11-17 09:39:15', 1, '超级管理员', '用户超级管理员登录', '10.0.30.60', '登录');
INSERT INTO `login_log` VALUES (14, '2020-11-25 15:14:07', '2020-11-25 15:14:07', 1, '超级管理员', '用户超级管理员登录', '10.0.30.60', '登录');
INSERT INTO `login_log` VALUES (15, '2020-11-25 15:14:26', '2020-11-25 15:14:26', 1, '超级管理员', '用户超级管理员登录', '10.0.30.60', '登录');
INSERT INTO `login_log` VALUES (16, '2020-11-27 10:21:39', '2020-11-27 10:21:39', 1, '超级管理员', '用户超级管理员登录', '10.0.30.60', '登录');
INSERT INTO `login_log` VALUES (17, '2020-11-27 10:22:20', '2020-11-27 10:22:20', 1, '超级管理员', '用户超级管理员登录', '10.0.30.60', '登录');
INSERT INTO `login_log` VALUES (18, '2020-11-27 10:22:34', '2020-11-27 10:22:34', 1, '超级管理员', '用户超级管理员登录', '10.0.30.60', '登录');
INSERT INTO `login_log` VALUES (19, '2020-11-27 10:22:35', '2020-11-27 10:22:35', 1, '超级管理员', '用户超级管理员登录', '10.0.30.60', '登录');
INSERT INTO `login_log` VALUES (20, '2020-11-27 10:22:35', '2020-11-27 10:22:35', 1, '超级管理员', '用户超级管理员登录', '10.0.30.60', '登录');
INSERT INTO `login_log` VALUES (21, '2020-11-27 10:23:33', '2020-11-27 10:23:33', 1, '超级管理员', '用户超级管理员登录', '10.0.30.60', '登录');
INSERT INTO `login_log` VALUES (22, '2020-11-27 10:24:00', '2020-11-27 10:24:00', 1, '超级管理员', '用户超级管理员登录', '10.0.30.60', '登录');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `created_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '2020-10-16 16:15:46', '2020-10-16 16:16:08', '超级管理员');
INSERT INTO `role` VALUES (2, '2020-10-16 16:15:46', '2020-10-20 10:04:48', '普通管理员');
INSERT INTO `role` VALUES (3, '2020-10-16 16:15:46', '2020-10-20 10:04:55', '普通用户');
INSERT INTO `role` VALUES (4, '2020-10-16 16:16:15', '2020-10-21 10:18:53', '四级管理员');

-- ----------------------------
-- Table structure for role_authority
-- ----------------------------
DROP TABLE IF EXISTS `role_authority`;
CREATE TABLE `role_authority`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(0) NULL DEFAULT NULL,
  `authority_id` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKqbri833f7xop13bvdje3xxtnw`(`authority_id`) USING BTREE,
  INDEX `FK2052966dco7y9f97s1a824bj1`(`role_id`) USING BTREE,
  CONSTRAINT `role_authority_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_authority_ibfk_2` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_authority
-- ----------------------------
INSERT INTO `role_authority` VALUES (1, 1, 1);
INSERT INTO `role_authority` VALUES (2, 1, 2);
INSERT INTO `role_authority` VALUES (3, 1, 3);
INSERT INTO `role_authority` VALUES (4, 2, 2);
INSERT INTO `role_authority` VALUES (5, 2, 3);
INSERT INTO `role_authority` VALUES (6, 3, 3);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `created_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `last_login_time` timestamp(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后登录ip',
  `register_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账号注册ip',
  `enable` bit(1) NULL DEFAULT NULL COMMENT '是否激活',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话号码',
  `birthday` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生日',
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '2020-10-16 15:44:30', '2020-11-13 16:45:38', '2020-11-27 18:14:25', '10.0.30.60', NULL, b'1', '超级管理员', '$2a$10$.gGC3VK/ZwbQGLL54erVlOwipig.rWYkSsvl/tN2v7p4B1rbJT9T2', '南风落尽', '男', '18966648791', '1995-11-30', '四川', '成都');
INSERT INTO `user` VALUES (2, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', 'user', '$2a$10$NRFMnFY3Qr5abrBeF/4dYOZraEw36yHHgYMyr0/NUxK1H3o4N0IWe', '普通用户', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (3, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', 'user1', '$2a$10$X.9XRCnI2vX3NQYNIFAqWugws0xYlU.TZ1uJ1RbuLlUX/qMRnpCGy', '普通用户1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (4, '2020-10-16 15:44:30', '2020-10-16 15:44:13', '2020-11-13 16:05:14', '10.0.30.60', NULL, b'1', 'user2', '$2a$10$X.9XRCnI2vX3NQYNIFAqWugws0xYlU.TZ1uJ1RbuLlUX/qMRnpCGy', '普通用户2', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (5, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', 'user3', '$2a$10$X.9XRCnI2vX3NQYNIFAqWugws0xYlU.TZ1uJ1RbuLlUX/qMRnpCGy', '普通用户3', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (6, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', 'user4', '$2a$10$X.9XRCnI2vX3NQYNIFAqWugws0xYlU.TZ1uJ1RbuLlUX/qMRnpCGy', '普通用户4', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (7, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', 'user5', '$2a$10$X.9XRCnI2vX3NQYNIFAqWugws0xYlU.TZ1uJ1RbuLlUX/qMRnpCGy', '普通用户5', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (8, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', 'user6', '$2a$10$X.9XRCnI2vX3NQYNIFAqWugws0xYlU.TZ1uJ1RbuLlUX/qMRnpCGy', '普通用户6', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (9, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', 'user7', '$2a$10$X.9XRCnI2vX3NQYNIFAqWugws0xYlU.TZ1uJ1RbuLlUX/qMRnpCGy', '普通用户7', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (10, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', 'user8', '$2a$10$X.9XRCnI2vX3NQYNIFAqWugws0xYlU.TZ1uJ1RbuLlUX/qMRnpCGy', '普通用户8', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (11, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', 'user9', '$2a$10$X.9XRCnI2vX3NQYNIFAqWugws0xYlU.TZ1uJ1RbuLlUX/qMRnpCGy', '普通用户9', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (12, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', 'user10', '$2a$10$X.9XRCnI2vX3NQYNIFAqWugws0xYlU.TZ1uJ1RbuLlUX/qMRnpCGy', '普通用户10', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (13, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', 'user11', '$2a$10$X.9XRCnI2vX3NQYNIFAqWugws0xYlU.TZ1uJ1RbuLlUX/qMRnpCGy', '普通用户11', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (14, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', '南风1', '$2a$10$L.Qyqehj2wLFE3D7aAnrpuuljMdHrQgGGXmDU1rT1uj8k6zMjKLXW', 'wzp1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (15, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', 'kuye', '$2a$10$zPwdsOme7qWEHaF0oNL.yuWfCO7EKtp5sMh9G0nyOuPK10gX.bFNy', 'wzp', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (18, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', '南风2', '$2a$10$6pjElsV1p93G.PFxj8ZWiecVINsk7bXEAJ5CCcpP/gc.LQ.gNVFeO', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (19, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', '南风3', '$2a$10$GCWo4GsOWFysw5rFnjdTZ.lPfEsDRTkr62leUYKnWxUXwmMgYv2Ge', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (20, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', '南风4', '$2a$10$EwB4.nbWUGADpoXT1hWhVeQE2y0RuSVs/1Og/TSqlwFxzLZgp8/ty', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (21, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', '南风5', '$2a$10$xpj9jNjQWU4ilg.NavXxrOOD65./1PIgjySrPjXinDb1QKPhi86ha', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (22, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', '南风6', '$2a$10$FwZNkzyBNPK5vb1k.Yl5SOl6uEDyAbLrTI.J4GY0tb6R/k5cMOg8G', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (23, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', '南风7', '$2a$10$8M099kQyjOkxjkXll9O2dOcZ8PziJA.N3kEJLmAv5kDzLbg0FGUHS', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (24, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', '南风8', '$2a$10$3wUdiJPHthfgrqlGeEA4du1NVjSHP4skAtK/owHuw594EBE1AqOrK', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (25, '2020-10-16 15:44:30', '2020-10-29 11:30:51', NULL, NULL, NULL, b'1', NULL, '$2a$10$VrPwblhDaAMMtsiBCBJxAumlj8iSDlDrjqqQcel8DXC4Df8AQw1wq', '阿达11', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (26, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', '南风10', '$2a$10$QTLnRpXGqMbiwdYFDu3fg.W7amCCLZJQXghkb1RP2pYEAVvCQhewa', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (27, '2020-10-16 15:44:30', '2020-10-16 15:44:13', NULL, NULL, NULL, b'1', '南风11', '$2a$10$LDvLKreE.dLw4MearWCVMOTL.J6PGrSPOUkhuXzuIahYVtI7gNvkm', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (29, '2020-10-16 16:37:40', '2020-10-16 16:37:40', NULL, NULL, NULL, b'1', '南风15', '$2a$10$9cCWjZB7m6Uq35NJoiDet.G/nfdpMLaAXsWWlQnQw/4CNnVscmY.2', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (30, '2020-10-16 17:45:19', '2020-10-16 17:45:19', NULL, NULL, NULL, b'1', 'admin1', '$2a$10$2slQumzM4n0OcV3WQZMkFOZDs3Olccyvmi9mcqBnDUeMAAtQG5iH2', '南风15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (31, '2020-10-19 09:31:02', '2020-10-19 09:31:02', NULL, NULL, NULL, b'1', 'admin11', '$2a$10$PoTjzE3oSYFZP9WnUmOW7uWx9O8iawpjSEhy289e99mg7q33atUty', '南风16', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (32, '2020-10-19 10:12:57', '2020-10-19 10:25:10', NULL, NULL, NULL, b'1', 'admin111', '$2a$10$kDeEUTdlWGXZwz88v8hZBeyPdQeLNne5oveuk/j3PuBSicinNZWkW', '南风180', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (33, '2020-10-19 10:37:40', '2020-10-19 10:42:28', NULL, NULL, NULL, b'1', 'admin112', '$2a$10$Y1LdtYDAbT3XHFheIYYi.uJsvfustkfus9XrzhmN1lJkzVfbGeEVC', '南风181', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (34, '2020-10-19 10:42:46', '2020-10-22 10:38:42', NULL, NULL, NULL, b'1', 'admin1123', '123456', '南风15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (35, '2020-10-19 10:43:58', '2020-10-22 10:38:45', NULL, NULL, NULL, b'1', 'admin11223', '$2a$10$UJfmm4DjiVZB6GfSjYZcJuvwoT8Gb6Oukex6ektImaFWszVgpMAsC', '南风1812345', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (36, '2020-10-19 13:33:30', '2020-10-22 10:38:40', NULL, NULL, NULL, b'1', 'admin112233', '$2a$10$Haqdeqw.XSyp0Exy3eNDuu46WlMly9tL1TyYr/.8Z2Qo03BBCxFjy', '南风18123456', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (37, '2020-10-22 10:36:40', '2020-10-22 10:38:40', NULL, NULL, NULL, b'1', '1', '$2a$10$t7n0t9My4T2btRFIN5JssOXtD7wNR.5RiQPvTAWz.0zZP2RCSvMtG', '张三', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (38, '2020-10-22 10:36:40', '2020-10-22 10:38:47', NULL, NULL, NULL, b'1', '2', '$2a$10$hwSB5FK7aU2RsrMG5zIXD.DRZHVT9ya5uM0qEXjG68G/TLphjs1Wa', '李四', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (39, '2020-10-22 10:37:53', '2020-10-22 10:38:49', NULL, NULL, NULL, b'1', '3', '$2a$10$WOe.2SBJsJuPpCj6DGe/W.wM5XkEC41IQpPcplqPgTgu8xSSD/J1O', '张三', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (40, '2020-10-22 10:37:57', '2020-10-22 10:38:52', NULL, NULL, NULL, b'1', '4', '$2a$10$ArU4E3B9gG7IxwHRKk3O1eySFUOKvz5JEKvutq1aIoU08M40YPhUK', '李四', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (41, '2020-10-22 10:38:32', '2020-10-22 10:38:32', NULL, NULL, NULL, b'1', '5', '$2a$10$JIZV2sl9MfU5TkPRuotSRugae8BbloAzj9DMhv5QztQLF.UJmy3WO', '张三', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (42, '2020-10-22 10:38:32', '2020-10-22 10:38:32', NULL, NULL, NULL, b'1', '6', '$2a$10$s8YMvwJ6CFYIX2/8GbCQDe1RxZh071UDWkZWlabo/dIIIQMf2oVuO', '李四', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (43, '2020-10-22 10:43:17', '2020-10-22 10:43:17', NULL, NULL, NULL, b'1', '7', '$2a$10$uUIHYRucTmybw.bPak.coOqYlML1ssKq4Z0SxD6zyTF7OKb.D8xwS', '张三', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (44, '2020-10-22 10:43:17', '2020-10-22 10:43:17', NULL, NULL, NULL, b'1', '8', '$2a$10$6aETEXxMA2zIkkZpUWMJSOstrbFw5d0XGldvsZR98uqVCESQsR6E2', '李四', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (48, '2020-10-29 13:56:06', '2020-11-26 14:46:33', NULL, NULL, '10.0.30.60', b'1', 'test南风test1111', '$2a$10$J2h/tMMlQierIa/nq0WbHe9NlkcIVXf9ggXtlwcJEhiNhvpV//WeC', '南风落尽', '男', '18966648791', '1995-10-30', '四川', '成都');
INSERT INTO `user` VALUES (49, '2020-11-02 10:49:54', '2020-11-02 10:49:54', NULL, NULL, NULL, b'1', 'user111', '$2a$10$VapUHn8.cKuJs90V6Sp8v.ypvECwsJEcYl8P0P4UizkASoFLMYIbC', '普通用户1', '', '', NULL, '', '');
INSERT INTO `user` VALUES (50, '2020-11-02 10:49:54', '2020-11-02 10:49:54', NULL, NULL, NULL, b'1', 'user21', '$2a$10$PBnVZ.GHJjV/JabLduIl8u/Ta3o6ynsJ.sObOfwJCflut38r7uhRW', '普通用户2', '', '', NULL, '', '');
INSERT INTO `user` VALUES (51, '2020-11-02 10:49:54', '2020-11-02 10:49:54', NULL, NULL, NULL, b'1', 'user31', '$2a$10$hPFHqa/8l6eUy3nWQzTpAuEyxwhU0kiBDnThgEqvk/2SkvJpmoJ56', '普通用户3', '', '', NULL, '', '');
INSERT INTO `user` VALUES (52, '2020-11-02 10:49:54', '2020-11-02 10:49:54', NULL, NULL, NULL, b'1', 'user41', '$2a$10$bGQCwpeYvlZRoFOXnkVssOl6BHdfwPveRE7Q6cdSlhHnKOf9XpgDG', '普通用户4', '', '', NULL, '', '');
INSERT INTO `user` VALUES (53, '2020-11-02 10:49:54', '2020-11-02 10:49:54', NULL, NULL, NULL, b'1', 'user51', '$2a$10$KsLS7qQbVQv9WLXSE3GFj.H0BgQhlqAegh9Lq4Z2D3z3GvSrTcIa6', '普通用户5', '', '', NULL, '', '');
INSERT INTO `user` VALUES (54, '2020-11-02 10:49:54', '2020-11-02 10:49:54', NULL, NULL, NULL, b'1', 'user61', '$2a$10$z0pbhUWww2/2wJxAlvRPjezcktUTy7nQ9VxCjxXiRq8U3Y8wgcI8G', '普通用户6', '', '', NULL, '', '');
INSERT INTO `user` VALUES (55, '2020-11-02 10:49:54', '2020-11-02 10:49:54', NULL, NULL, NULL, b'1', 'user71', '$2a$10$WOStisr8./VskXMunQTkKu/h0QBOzCM5Hg8bUNuSf503PmPdbDZQK', '普通用户7', '', '', NULL, '', '');
INSERT INTO `user` VALUES (56, '2020-11-02 10:49:54', '2020-11-02 10:49:54', NULL, NULL, NULL, b'1', 'user81', '$2a$10$Br9A1lPW//J4vm.BmAVW5OF9/i225FApNeJv7WJPt5XY0aTFoNzPS', '普通用户8', '', '', NULL, '', '');
INSERT INTO `user` VALUES (57, '2020-11-02 10:49:54', '2020-11-02 10:49:54', NULL, NULL, NULL, b'1', 'user91', '$2a$10$qWBgaljnT.cvVKM3usF1p.ukfl10ZkiW1fFUObsLe6FaNLkpjX9k.', '普通用户9', '', '', NULL, '', '');
INSERT INTO `user` VALUES (58, '2020-11-02 10:49:55', '2020-11-02 10:49:55', NULL, NULL, NULL, b'1', 'user101', '$2a$10$p6lZ.g.dGnEViMBjfTnosucKsG6AiNos2hpPvt/LD.OUeZeLnSgQC', '普通用户10', '', '', NULL, '', '');
INSERT INTO `user` VALUES (59, '2020-11-02 10:49:55', '2020-11-02 10:49:55', NULL, NULL, NULL, b'1', 'kuye1', '$2a$10$2e12jo0w5UZMkRKl6gu4FOuqm5g6R86ct6vvIFC.Qlk4lwwv433PS', 'wzp', '', '', NULL, '', '');
INSERT INTO `user` VALUES (60, '2020-11-02 10:49:55', '2020-11-02 10:49:55', NULL, NULL, NULL, b'1', '南风21', '$2a$10$zOUYaYelHKhUO5gY9G0Kye.GDMfNNXz1c8DCqq5.mu6N7ws9QX1Va', '', '', '', NULL, '', '');
INSERT INTO `user` VALUES (61, '2020-11-02 10:49:55', '2020-11-02 10:49:55', NULL, NULL, NULL, b'1', '南风31', '$2a$10$4mm/ASY.tn2uWnti4n2./ehU.PNUBsKl/XErIyjG7fMi2E6RSGeYm', '', '', '', NULL, '', '');
INSERT INTO `user` VALUES (62, '2020-11-02 10:49:55', '2020-11-02 10:49:55', NULL, NULL, NULL, b'1', '南风41', '$2a$10$63B.ofGkL3DoNY3tU6K8NeFbB58rV1OJSEcfj/Fc//wJBjcneVK/y', '', '', '', NULL, '', '');
INSERT INTO `user` VALUES (63, '2020-11-02 10:49:55', '2020-11-02 10:49:55', NULL, NULL, NULL, b'1', '南风51', '$2a$10$IQKgQx.xLsMZkdu0eP5ZgeIy.n2PKLzPQnP1XIsG5oFDwwzz0c0Ke', '', '', '', NULL, '', '');
INSERT INTO `user` VALUES (64, '2020-11-02 10:49:55', '2020-11-02 10:49:55', NULL, NULL, NULL, b'1', '南风61', '$2a$10$NNC61QG/O4J0m6AACX1nV.hLEg5rIhvNx3784H.Na75zEnkEqLcmm', '', '', '', NULL, '', '');
INSERT INTO `user` VALUES (65, '2020-11-02 10:49:55', '2020-11-02 10:49:55', NULL, NULL, NULL, b'1', '南风71', '$2a$10$ifZoT8Jpmfg9H2sjDnf7OOOwfWo4.AcpNmRROYFqkH.j8z9fPRlgu', '', '', '', NULL, '', '');
INSERT INTO `user` VALUES (66, '2020-11-02 10:49:55', '2020-11-02 10:49:55', NULL, NULL, NULL, b'1', '南风81', '$2a$10$N1vr7KtvDUeNW4rB7LjWcOVeX/iygtYCB/BTa6/rklgzl0xhlDaQy', '', '', '', NULL, '', '');
INSERT INTO `user` VALUES (67, '2020-11-02 10:49:56', '2020-11-02 10:49:56', NULL, NULL, NULL, b'1', '', '$2a$10$NdNF7u6QbH0xm2bSmG7o9.PEfz4sQ.xBxTlpcu0Rt7Ve4xFpqxIIi', '阿达11', '', '', NULL, '', '');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(0) NULL DEFAULT NULL,
  `user_id` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKa68196081fvovjhkek5m97n3y`(`role_id`) USING BTREE,
  INDEX `FK859n2jvi8ivhui0rl0esws6o`(`user_id`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 2, 2);
INSERT INTO `user_role` VALUES (3, 1, 14);
INSERT INTO `user_role` VALUES (4, 2, 14);
INSERT INTO `user_role` VALUES (5, 1, 15);

SET FOREIGN_KEY_CHECKS = 1;
