/*
 Navicat Premium Data Transfer

 Source Server         : niko
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : 121.199.75.2:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 05/01/2023 13:04:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容描述',
  `summary` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `icon` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图标',
  `post_count` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '该分类的内容数量',
  `order_num` int(0) NULL DEFAULT NULL COMMENT '排序编码',
  `parent_id` bigint(0) UNSIGNED NULL DEFAULT NULL COMMENT '父级分类的ID',
  `meta_keywords` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'SEO关键字',
  `meta_description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'SEO描述内容',
  `created` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `modified` datetime(0) NULL DEFAULT NULL,
  `status` tinyint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论的内容',
  `parent_id` bigint(0) NULL DEFAULT NULL COMMENT '回复的评论ID',
  `post_id` bigint(0) NOT NULL COMMENT '评论的内容ID',
  `user_id` bigint(0) NOT NULL COMMENT '评论的用户ID',
  `vote_up` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '“顶”的数量',
  `vote_down` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '“踩”的数量',
  `level` tinyint(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '置顶等级',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '评论的状态',
  `created` datetime(0) NOT NULL COMMENT '评论的时间',
  `modified` datetime(0) NULL DEFAULT NULL COMMENT '评论的更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `edit_mode` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '编辑模式：html可视化，markdown ..',
  `category_id` bigint(0) NULL DEFAULT NULL,
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户ID',
  `vote_up` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '支持人数',
  `vote_down` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '反对人数',
  `view_count` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问量',
  `comment_count` int(0) NOT NULL DEFAULT 0 COMMENT '评论数量',
  `recommend` tinyint(1) NULL DEFAULT NULL COMMENT '是否为精华',
  `level` tinyint(0) NOT NULL DEFAULT 0 COMMENT '置顶等级',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '状态',
  `created` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `modified` datetime(0) NULL DEFAULT NULL COMMENT '最后更新日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '昵称',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮件',
  `mobile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机电话',
  `point` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '积分',
  `sign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '个性签名',
  `gender` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `wechat` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '微信号',
  `vip_level` int(0) NULL DEFAULT NULL COMMENT 'vip等级',
  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '生日',
  `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '头像',
  `post_count` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '内容数量',
  `comment_count` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '评论数量',
  `status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '状态',
  `lasted` datetime(0) NULL DEFAULT NULL COMMENT '最后的登陆时间',
  `created` datetime(0) NOT NULL COMMENT '创建日期',
  `modified` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_action
-- ----------------------------
DROP TABLE IF EXISTS `user_action`;
CREATE TABLE `user_action`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户ID',
  `action` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '动作类型',
  `point` int(0) NULL DEFAULT NULL COMMENT '得分',
  `post_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '关联的帖子ID',
  `comment_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '关联的评论ID',
  `created` datetime(0) NULL DEFAULT NULL,
  `modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_collection
-- ----------------------------
DROP TABLE IF EXISTS `user_collection`;
CREATE TABLE `user_collection`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NOT NULL,
  `post_id` bigint(0) NOT NULL,
  `post_user_id` bigint(0) NOT NULL,
  `created` datetime(0) NOT NULL,
  `modified` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_message
-- ----------------------------
DROP TABLE IF EXISTS `user_message`;
CREATE TABLE `user_message`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `from_user_id` bigint(0) NOT NULL COMMENT '发送消息的用户ID',
  `to_user_id` bigint(0) NOT NULL COMMENT '接收消息的用户ID',
  `post_id` bigint(0) NULL DEFAULT NULL COMMENT '消息可能关联的帖子',
  `comment_id` bigint(0) NULL DEFAULT NULL COMMENT '消息可能关联的评论',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `type` tinyint(0) NULL DEFAULT NULL COMMENT '消息类型',
  `created` datetime(0) NOT NULL,
  `modified` datetime(0) NULL DEFAULT NULL,
  `status` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
