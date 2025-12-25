-- 创建数据库
CREATE DATABASE IF NOT EXISTS renmai CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE renmai;

-- 创建用户表
CREATE TABLE IF NOT EXISTS tbl_user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建联系人表
CREATE TABLE IF NOT EXISTS tbl_contact (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(10) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100),
    address VARCHAR(200),
    company VARCHAR(50),
    position VARCHAR(50),
    avatar VARCHAR(200),
    remark VARCHAR(200),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 插入示例用户数据
INSERT INTO tbl_user (username, password, email) VALUES
('user', '123456', 'user@example.com');

-- 插入示例联系人数据
INSERT INTO tbl_contact (name, phone, email, address, company, position) VALUES
('张三', '13800138001', 'zhangsan@example.com', '北京市朝阳区', '张三商贸有限公司', 'CEO'),
('李四', '13800138002', 'lisi@example.com', '上海市浦东新区', '李四网络科技有限公司', 'CTO'),
('王五', '13800138003', 'wangwu@example.com', '广州市天河区', '王五有限公司', 'CFO');