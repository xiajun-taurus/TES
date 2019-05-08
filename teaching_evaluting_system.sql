/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost
 Source Database       : teaching_evaluting_system

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : utf-8

 Date: 05/09/2019 00:44:27 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `classinfo`
-- ----------------------------
DROP TABLE IF EXISTS `classinfo`;
CREATE TABLE `classinfo` (
  `oid` varchar(128) NOT NULL,
  `classname` varchar(128) NOT NULL COMMENT '班级名称',
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `classinfo`
-- ----------------------------
BEGIN;
INSERT INTO `classinfo` VALUES ('39fc1ba828ed11e98b74599b0132f949', '计算154');
COMMIT;

-- ----------------------------
--  Table structure for `comment_result`
-- ----------------------------
DROP TABLE IF EXISTS `comment_result`;
CREATE TABLE `comment_result` (
  `oid` varchar(32) NOT NULL,
  `teacher_id` varchar(32) DEFAULT NULL COMMENT '教师id',
  `paper_id` varchar(32) DEFAULT NULL COMMENT '试卷id',
  `aver_score` float DEFAULT NULL COMMENT '平均得分',
  `comment` varchar(128) DEFAULT NULL COMMENT '评价内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评价时间',
  PRIMARY KEY (`oid`),
  KEY `teacher_id` (`teacher_id`),
  KEY `paper_id` (`paper_id`),
  CONSTRAINT `comment_result_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`oid`),
  CONSTRAINT `comment_result_ibfk_2` FOREIGN KEY (`paper_id`) REFERENCES `papers` (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `comment_result`
-- ----------------------------
BEGIN;
INSERT INTO `comment_result` VALUES ('c3374c6a291211e98b74599b0132f949', '03ed90d428e611e98b74599b0132f949', '893d2728291211e98b74599b0132f949', '8.5', '教学质量很高老师为人负责，上课风趣幽默，是个好老师  ', '2019-02-05 16:36:01');
COMMIT;

-- ----------------------------
--  Table structure for `comment_result_item`
-- ----------------------------
DROP TABLE IF EXISTS `comment_result_item`;
CREATE TABLE `comment_result_item` (
  `oid` varchar(32) NOT NULL,
  `score` int(11) DEFAULT NULL,
  `comment` varchar(128) DEFAULT NULL COMMENT '评价内容',
  `result_id` varchar(32) NOT NULL COMMENT '所属评价结果',
  `commenter_id` varchar(32) DEFAULT NULL COMMENT '评价人id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评价时间',
  PRIMARY KEY (`oid`),
  KEY `result_id` (`result_id`),
  KEY `commenter_id` (`commenter_id`),
  CONSTRAINT `comment_result_item_ibfk_1` FOREIGN KEY (`result_id`) REFERENCES `comment_result` (`oid`),
  CONSTRAINT `comment_result_item_ibfk_2` FOREIGN KEY (`commenter_id`) REFERENCES `students` (`oid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `course_info`
-- ----------------------------
DROP TABLE IF EXISTS `course_info`;
CREATE TABLE `course_info` (
  `oid` varchar(32) NOT NULL COMMENT '课程id',
  `course_name` varchar(128) DEFAULT NULL COMMENT '课程名',
  `last_edit_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `course_info`
-- ----------------------------
BEGIN;
INSERT INTO `course_info` VALUES ('42b117260c4611e989b02dec18b133e8', '大学英语', '2018-12-30 23:19:11'), ('76445e360c2d11e989b02dec18b133e8', '概率论', '2018-12-30 20:21:40'), ('a22cd3f20c2d11e989b02dec18b133e8', '高等数学（1）', '2018-12-30 20:22:54'), ('aaec07c40c2d11e989b02dec18b133e8', '计算机网络', '2018-12-30 20:23:09');
COMMIT;

-- ----------------------------
--  Table structure for `major_info`
-- ----------------------------
DROP TABLE IF EXISTS `major_info`;
CREATE TABLE `major_info` (
  `oid` varchar(128) DEFAULT NULL,
  `major_name` varchar(128) DEFAULT NULL COMMENT '专业名称',
  KEY `oid` (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `major_info`
-- ----------------------------
BEGIN;
INSERT INTO `major_info` VALUES ('1dcaded028eb11e98b74599b0132f949', '计算机科学与技术'), ('7d2d11f428eb11e98b74599b0132f949', '电气工程及其自动化'), ('10861d1e28ed11e98b74599b0132f949', '信息管理与信息系统'), ('303001ca28ed11e98b74599b0132f949', '电子信息工程');
COMMIT;

-- ----------------------------
--  Table structure for `papers`
-- ----------------------------
DROP TABLE IF EXISTS `papers`;
CREATE TABLE `papers` (
  `oid` varchar(32) NOT NULL,
  `paper_title` varchar(128) DEFAULT NULL COMMENT '试卷名',
  `questions` varchar(3600) DEFAULT NULL COMMENT '试卷包含的试题，存放多个题目id用逗号隔开',
  `last_edit_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上次修改时间',
  `enable_status` int(11) NOT NULL DEFAULT '1' COMMENT '是否使用',
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `papers`
-- ----------------------------
BEGIN;
INSERT INTO `papers` VALUES ('532f6d86400411e9aca40366f59b27f3', '2313', null, '2019-03-06 19:38:13', '0'), ('893d2728291211e98b74599b0132f949', '问卷！', '16499eea076111e989b02dec18b133e8,8036dd1408dc11e989b02dec18b133e8,1407689e05b611e989b02dec18b133e8', '2019-02-05 14:52:00', '1'), ('b31ecabe400111e9aca40366f59b27f3', '1234', null, '2019-03-06 19:19:25', '0');
COMMIT;

-- ----------------------------
--  Table structure for `question_bank`
-- ----------------------------
DROP TABLE IF EXISTS `question_bank`;
CREATE TABLE `question_bank` (
  `oid` varchar(32) NOT NULL,
  `question` varchar(128) DEFAULT NULL,
  `answerA` varchar(128) DEFAULT NULL,
  `scoreA` int(11) DEFAULT NULL,
  `answerB` varchar(128) DEFAULT NULL,
  `scoreB` int(11) DEFAULT NULL,
  `answerC` varchar(128) DEFAULT NULL,
  `scoreC` int(11) DEFAULT NULL,
  `answerD` varchar(128) DEFAULT NULL,
  `scoreD` int(11) DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `question_bank`
-- ----------------------------
BEGIN;
INSERT INTO `question_bank` VALUES ('1407689e05b611e989b02dec18b133e8', '第一题', '帅的一批', '5', '非常帅', '3', '比较帅', '2', '帅', '1'), ('16499eea076111e989b02dec18b133e8', '第二题', '非常好', '4', '较好', '3', '一般', '1', '差', '0'), ('2e58699c076311e989b02dec18b133e8', '我-_- hah', '必须帅', '4', '非常帅', '3', '无语', '0', '帅', '1'), ('53c5d86609f911e989b02dec18b133e8', '我帅吗', '帅的一批', '4', '非常帅', '3', '比较帅', '2', '帅', '1'), ('8036dd1408dc11e989b02dec18b133e8', '课堂氛围如何', '活跃', '4', '一般', '3', '差', '0', '极差', '-1');
COMMIT;

-- ----------------------------
--  Table structure for `security_question`
-- ----------------------------
DROP TABLE IF EXISTS `security_question`;
CREATE TABLE `security_question` (
  `oid` varchar(128) NOT NULL,
  `question` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `security_question`
-- ----------------------------
BEGIN;
INSERT INTO `security_question` VALUES ('9dcce2a2383d11e9bb566d5d8ca59808', '您母亲的姓名是？'), ('9dd180e6383d11e9bb566d5d8ca59808', '您父亲的姓名是？'), ('9dd4b9fa383d11e9bb566d5d8ca59808', '您配偶的姓名是？'), ('9dd80092383d11e9bb566d5d8ca59808', '您的出生地是？'), ('9dda6b8e383d11e9bb566d5d8ca59808', '您高中班主任的名字是？'), ('9ddda380383d11e9bb566d5d8ca59808', '您初中班主任的名字是？'), ('9de094e6383d11e9bb566d5d8ca59808', '您小学班主任的名字是？'), ('9de3e826383d11e9bb566d5d8ca59808', '您的小学校名是？'), ('9de77928383d11e9bb566d5d8ca59808', '您的学号（或工号）是？'), ('9deab57a383d11e9bb566d5d8ca59808', '您父亲的生日是？'), ('9deec4d0383d11e9bb566d5d8ca59808', '您母亲的生日是？'), ('9df15fa6383d11e9bb566d5d8ca59808', '您配偶的生日是？');
COMMIT;

-- ----------------------------
--  Table structure for `students`
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students` (
  `oid` varchar(128) NOT NULL,
  `major_id` varchar(128) DEFAULT NULL COMMENT '专业id',
  `class_id` varchar(128) DEFAULT NULL COMMENT '班级id',
  PRIMARY KEY (`oid`),
  KEY `major_id` (`major_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `students_ibfk_1` FOREIGN KEY (`oid`) REFERENCES `user` (`oid`) ON DELETE CASCADE,
  CONSTRAINT `students_ibfk_2` FOREIGN KEY (`major_id`) REFERENCES `major_info` (`oid`),
  CONSTRAINT `students_ibfk_3` FOREIGN KEY (`class_id`) REFERENCES `classinfo` (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `students`
-- ----------------------------
BEGIN;
INSERT INTO `students` VALUES ('d0d9fd6028ed11e98b74599b0132f949', '1dcaded028eb11e98b74599b0132f949', '39fc1ba828ed11e98b74599b0132f949');
COMMIT;

-- ----------------------------
--  Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `oid` varchar(32) NOT NULL,
  `course_id` varchar(32) DEFAULT NULL,
  `paper_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `teacher`
-- ----------------------------
BEGIN;
INSERT INTO `teacher` VALUES ('03ed90d428e611e98b74599b0132f949', 'a22cd3f20c2d11e989b02dec18b133e8', '893d2728291211e98b74599b0132f949'), ('aec222f64a0911e99a910b31d4977892', 'aaec07c40c2d11e989b02dec18b133e8', null);
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `oid` varchar(128) NOT NULL,
  `school_no` varchar(128) DEFAULT NULL COMMENT '学号/工号',
  `username` varchar(128) NOT NULL COMMENT '用户名',
  `truename` varchar(128) DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(50) DEFAULT '000000' COMMENT '密码',
  `email` varchar(128) DEFAULT NULL,
  `signature` varchar(128) DEFAULT NULL COMMENT '个性签名',
  `avatar` varchar(128) DEFAULT NULL,
  `role` int(2) NOT NULL COMMENT '0：管理员；1：教师；2：学生',
  `security_question` varchar(128) DEFAULT NULL COMMENT '密保问题',
  `security_answer` varchar(128) DEFAULT NULL COMMENT '密保问题答案',
  `phone` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  KEY `security_question` (`security_question`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`security_question`) REFERENCES `security_question` (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('03ed90d428e611e98b74599b0132f949', '150800001', '150800001', '胡雷', '000000', null, null, null, '1', null, null, null), ('899f5e3428e511e98b74599b0132f949', '000000000', 'admin', '管理员', 'admin', 'admin@sdjtu.com', '做好管理者', null, '0', '9dd4b9fa383d11e9bb566d5d8ca59808', 'q2', null), ('aec222f64a0911e99a910b31d4977892', '10086', '10086', '王大明', '000000', '', '我是隔壁的老王', null, '1', null, null, '10010'), ('d0d9fd6028ed11e98b74599b0132f949', '150811402', '150811402', '华成志', '000000', null, null, null, '2', null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `xuanke`
-- ----------------------------
DROP TABLE IF EXISTS `xuanke`;
CREATE TABLE `xuanke` (
  `oid` varchar(32) NOT NULL,
  `class_id` varchar(32) NOT NULL COMMENT '班级id',
  `course_id` varchar(32) NOT NULL COMMENT '课程id',
  PRIMARY KEY (`oid`),
  KEY `class_id` (`class_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `xuanke_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `classinfo` (`oid`),
  CONSTRAINT `xuanke_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course_info` (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `xuanke`
-- ----------------------------
BEGIN;
INSERT INTO `xuanke` VALUES ('5e3f25fe292011e98b74599b0132f949', '39fc1ba828ed11e98b74599b0132f949', 'a22cd3f20c2d11e989b02dec18b133e8');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
