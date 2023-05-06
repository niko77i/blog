/*
 Navicat Premium Data Transfer

 Source Server         : docker-mysql
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : 121.199.75.2:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 06/05/2023 13:31:27
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
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '提问', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, '2023-03-01 22:36:48', NULL, 0);
INSERT INTO `category` VALUES (2, '分享', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, '2023-03-01 22:36:48', NULL, 0);
INSERT INTO `category` VALUES (3, '讨论', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, '2023-03-01 22:36:48', NULL, 0);
INSERT INTO `category` VALUES (4, '建议', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, '2023-03-01 22:36:48', NULL, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 'asdasd', NULL, 1, 2, 0, 0, 0, NULL, '2023-04-06 19:45:20', '2023-04-06 19:45:20');
INSERT INTO `comment` VALUES (2, 'vzxcv', 1, 1, 3, 0, 0, 0, NULL, '2023-04-06 19:45:20', '2023-04-06 19:45:20');
INSERT INTO `comment` VALUES (3, 'zasdas', NULL, 4, 1, 0, 0, 0, NULL, '2023-04-06 19:45:20', '2023-04-06 19:45:20');
INSERT INTO `comment` VALUES (4, 'asdfaasd', NULL, 3, 4, 0, 0, 0, NULL, '2023-04-06 19:45:20', '2023-04-06 19:45:20');
INSERT INTO `comment` VALUES (5, 'sxcds', 7, 2, 5, 0, 0, 0, NULL, '2023-04-06 19:45:20', '2023-04-06 19:45:20');
INSERT INTO `comment` VALUES (6, 'bvcx', 7, 2, 3, 0, 0, 0, NULL, '2023-04-29 19:57:16', '2023-04-29 13:25:05');
INSERT INTO `comment` VALUES (7, 'fasda', NULL, 2, 2, 0, 0, 0, NULL, '2023-04-09 19:57:16', '2023-04-09 19:57:16');
INSERT INTO `comment` VALUES (8, 'asdasd', 6, 2, 2, 0, 0, 0, NULL, '2023-04-09 19:57:16', '2023-04-09 19:57:16');
INSERT INTO `comment` VALUES (9, 'fdASDSF', 8, 2, 3, 0, 0, 0, NULL, '2023-04-09 19:57:16', '2023-04-09 19:57:16');

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
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES (1, 'Github上最值得学习的100个Java开源项目，涵盖各种技术栈！', ' 你有多久没好好学习一个开源项目了？\n\n你是否经常为找不到好的开源项目而烦恼？\n\n你是否为不知道怎么入手去看一个开源项目？\n\n你是否想看别人的项目学习笔记？\n\n你是否想跟着别人的项目搭建过程一步一步跟着做项目？\n\n为了让更多Java的开发者能更容易找到值得学习的开源项目，我搭建了这个Java开源学习网站，宗旨梳理Java知识，共享开源项目笔记。来瞧一瞧：\n\nimg[//image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/20200414/473f73a3eb6f471e8154620a9c1d5306.png] \n\n网站截图中可以看出，点击筛选条件组合之后，再点击搜索就会搜索出对应的开源项目。\n\n比如打开renren-fast项目，可以看到具体项目的信息，以及模块解析。\n\nimg[//image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/20200414/f74740692dab4d9c937cd56336ead1b4.png]\n\n这样，学习一个开源项目就不再费劲，每天学习一个开源项目，在不知不觉中提升技术水平！目前网站已经收录了近100个开源项目，让Java不再难懂！\n\n直接扫公众号下方的二维码，回复关键字【网站】即可获得网站的域名地址！\n\nimg[//image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/20200414/f58f7c6d038c4dfb99bd9cf40935392e.png]\n\n关注上方的公众号二维码\n\n回复【网站】即可获取项目域名地址。\n', '0', 1, 1, 0, 0, 19, 2, 1, 1, NULL, '2023-05-04 14:55:48', '2023-05-04 14:55:48');
INSERT INTO `post` VALUES (2, '关注公众号：MarkerHub，一起学习Java', '关注学习：\n\nimg[//image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/20200414/f58f7c6d038c4dfb99bd9cf40935392e.png] ', '0', 1, 2, 0, 0, 36, 5, 1, 1, NULL, '2023-05-05 14:55:48', '2023-05-05 14:55:48');
INSERT INTO `post` VALUES (4, 'Github上最值得学习的100个Java开源项目，涵盖各种技术栈！', ' 你有多久没好好学习一个开源项目了？\n\n你是否经常为找不到好的开源项目而烦恼？\n\n你是否为不知道怎么入手去看一个开源项目？\n\n你是否想看别人的项目学习笔记？\n\n你是否想跟着别人的项目搭建过程一步一步跟着做项目？\n\n为了让更多Java的开发者能更容易找到值得学习的开源项目，我搭建了这个Java开源学习网站，宗旨梳理Java知识，共享开源项目笔记。来瞧一瞧：\n\nimg[//image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/20200414/473f73a3eb6f471e8154620a9c1d5306.png] \n\n网站截图中可以看出，点击筛选条件组合之后，再点击搜索就会搜索出对应的开源项目。\n\n比如打开renren-fast项目，可以看到具体项目的信息，以及模块解析。\n\nimg[//image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/20200414/f74740692dab4d9c937cd56336ead1b4.png]\n\n这样，学习一个开源项目就不再费劲，每天学习一个开源项目，在不知不觉中提升技术水平！目前网站已经收录了近100个开源项目，让Java不再难懂！\n\n直接扫公众号下方的二维码，回复关键字【网站】即可获得网站的域名地址！\n\nimg[//image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/20200414/f58f7c6d038c4dfb99bd9cf40935392e.png]\n\n关注上方的公众号二维码\n\n回复【网站】即可获取项目域名地址。\n', '0', 1, 4, 0, 0, 17, 1, 1, 1, NULL, '2023-05-04 14:55:48', '2023-05-04 14:55:48');
INSERT INTO `post` VALUES (7, 'qwerq', 'fasfdsa', '0', 2, NULL, 0, 0, 0, 0, 0, 0, NULL, '2023-05-04 14:55:48', '2023-05-04 14:55:48');
INSERT INTO `post` VALUES (8, '啊实打实', '啊手动阀', '0', 4, NULL, 0, 0, 0, 0, 0, 0, NULL, '2023-05-05 14:55:48', '2023-05-05 14:55:48');
INSERT INTO `post` VALUES (9, '从v仔细擦拭', 'a说得对强强阿斯顿', '0', 3, NULL, 0, 0, 0, 0, 0, 0, NULL, '2023-05-04 14:55:48', '2023-05-04 14:55:48');
INSERT INTO `post` VALUES (10, '阿桑的歌', ' 阿萨官方啊', '0', 4, NULL, 0, 0, 3, 0, 0, 0, NULL, '2023-05-01 14:55:48', '2023-05-01 14:55:48');
INSERT INTO `post` VALUES (11, ' 岁的法国', ' g阿达啊', '0', 1, NULL, 0, 0, 0, 0, 0, 0, NULL, '2023-05-04 14:55:48', '2023-05-04 14:55:48');
INSERT INTO `post` VALUES (12, '大海', 'a啊手动阀从啊实打实的face[哈哈] ', '0', 2, 2, 0, 0, 12, 0, 0, 0, NULL, '2023-04-30 14:55:48', '2023-04-30 14:55:48');
INSERT INTO `post` VALUES (14, '每天喝水', '好处多多', '0', 4, 2, 0, 0, 1, 0, 0, 0, NULL, '2023-05-04 14:55:48', '2023-05-04 14:55:48');

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
  `address` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '状态',
  `lasted` datetime(0) NULL DEFAULT NULL COMMENT '最后的登陆时间',
  `created` datetime(0) NOT NULL COMMENT '创建日期',
  `modified` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'MarkerHub', '96e79218965eb72c92a549dd5a330112', '11111@qq.com', NULL, 0, '关注公众号：MarkerHub，一起学Java！！', '0', NULL, 0, NULL, '/res/images/avatar/default.png', 1, 1, NULL, 0, '2023-05-05 20:25:22', '2020-03-28 14:37:24', '2020-03-28 14:37:24');
INSERT INTO `user` VALUES (2, 'test007', '96e79218965eb72c92a549dd5a330112', '1111@qq.com', NULL, 0, NULL, '0', NULL, 0, NULL, '/upload/avatar/avatar_2.jpg', 2, 3, NULL, 0, '2023-05-05 20:25:43', '2020-03-28 14:37:24', '2020-03-28 14:37:24');
INSERT INTO `user` VALUES (3, 'test004', '96e79218965eb72c92a549dd5a330112', '144d11@qq.com', NULL, 0, NULL, '0', NULL, 0, NULL, '/res/images/avatar/default.png', 0, 3, NULL, 0, NULL, '2020-03-28 14:37:24', '2020-03-28 14:37:24');
INSERT INTO `user` VALUES (4, 'test005', '96e79218965eb72c92a549dd5a330112', '144d15@qq.com', NULL, 0, NULL, '0', NULL, 0, NULL, '/res/images/avatar/default.png', 1, 1, NULL, 0, NULL, '2020-03-28 14:37:24', '2020-03-28 14:37:24');
INSERT INTO `user` VALUES (5, 'test00756', '96e79218965eb72c92a549dd5a330112', '45454541@qq.com', NULL, 0, NULL, '0', NULL, 0, NULL, '/res/images/avatar/default.png', 0, 1, NULL, 0, NULL, '2020-03-28 14:37:24', '2020-03-28 14:37:24');
INSERT INTO `user` VALUES (10, 'test77', '96e79218965eb72c92a549dd5a330112', '111@qq.com', NULL, 0, NULL, NULL, NULL, 0, NULL, '/res/images/avatar/default.png', 0, 0, NULL, 0, NULL, '2020-03-28 14:37:24', '2020-03-28 14:37:24');
INSERT INTO `user` VALUES (11, 'test771', '96e79218965eb72c92a549dd5a330112', '221@qq.com', NULL, 0, NULL, NULL, NULL, 0, NULL, '/res/images/avatar/default.png', 0, 0, NULL, 0, NULL, '2020-03-28 14:37:24', '2020-03-28 14:37:24');
INSERT INTO `user` VALUES (12, 'test777', '96e79218965eb72c92a549dd5a330112', 'ds@qq.com', NULL, 0, NULL, NULL, NULL, 0, NULL, '/res/images/avatar/default.png', 0, 0, NULL, 0, NULL, '2020-03-28 14:37:24', '2020-03-28 14:37:24');
INSERT INTO `user` VALUES (13, 'admin', '96e79218965eb72c92a549dd5a330112', '2222@qq.com', NULL, 0, NULL, '0', NULL, 0, NULL, '/res/images/avatar/default.png', 0, 0, NULL, 0, '2023-04-30 13:18:05', '2020-03-28 14:37:24', '2020-03-28 14:37:24');

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
-- Records of user_action
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_collection
-- ----------------------------
INSERT INTO `user_collection` VALUES (1, 2, 4, 4, '2023-04-22 19:45:59', '2023-04-22 19:45:59');
INSERT INTO `user_collection` VALUES (8, 2, 1, 1, '2023-05-05 20:33:49', '2023-05-05 20:33:49');

-- ----------------------------
-- Table structure for user_message
-- ----------------------------
DROP TABLE IF EXISTS `user_message`;
CREATE TABLE `user_message`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `from_user_id` bigint(0) NULL DEFAULT NULL COMMENT '发送消息的用户ID',
  `to_user_id` bigint(0) NOT NULL COMMENT '接收消息的用户ID',
  `post_id` bigint(0) NULL DEFAULT NULL COMMENT '消息可能关联的帖子',
  `comment_id` bigint(0) NULL DEFAULT NULL COMMENT '消息可能关联的评论',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `type` tinyint(0) NULL DEFAULT NULL COMMENT '消息类型 0 系统消息 1 谁评论了你的文章 2 别人评论了你的评论',
  `created` datetime(0) NOT NULL,
  `modified` datetime(0) NULL DEFAULT NULL,
  `status` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_message
-- ----------------------------
INSERT INTO `user_message` VALUES (1, 3, 2, 2, 6, 'bvcx', 2, '2023-04-29 19:57:16', '2023-04-29 19:57:16', 1);
INSERT INTO `user_message` VALUES (2, 5, 2, 2, 5, 'sxcds', 2, '2023-04-06 19:45:20', '2023-04-06 19:45:20', 1);
INSERT INTO `user_message` VALUES (4, 2, 3, 2, 8, 'asdasd', 2, '2023-04-09 21:26:27', '2023-04-09 23:32:18', 0);

SET FOREIGN_KEY_CHECKS = 1;
