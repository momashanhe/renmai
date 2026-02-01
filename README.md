# 人脉管理系统

## 项目简介

基于Java Web技术开发的人脉管理系统，采用原生Servlet架构，实现了登录注册、联系人管理等功能。

## 技术选型

### 后端技术

- Java版本: JDK 1.8
- 构建工具: Maven 3.6.0
- Web框架: Servlet
- 数据库: MySQL 5.7.40
- 数据访问: JDBC
- JSON处理: Jackson 2.9.1

### 前端技术

- 模板引擎: JSP + JSTL
- UI框架: Bootstrap 5.3.0
- JavaScript库: jQuery 3.6.0
- 交互方式: AJAX异步请求

## 项目结构

```code
renmai/
├── src/
│   ├── main/
│   │   ├── java/com/momashanhe/renmai/
│   │   │   ├── business/                     # 业务逻辑层
│   │   │   │   ├── ContactBusiness.java      # 联系人业务逻辑类
│   │   │   │   └── UserBusiness.java         # 用户业务逻辑类
│   │   │   ├── controller/                   # 控制器层
│   │   │   │   ├── ContactController.java    # 联系人控制器
│   │   │   │   └── UserController.java       # 用户控制器
│   │   │   ├── dao/                          # 数据访问层
│   │   │   │   ├── ContactDao.java           # 联系人数据访问类
│   │   │   │   └── UserDao.java              # 用户数据访问类
│   │   │   ├── entity/                       # 实体类
│   │   │   │   ├── Contact.java              # 联系人实体类
│   │   │   │   └── User.java                 # 用户实体类
│   │   │   ├── filter/                       # 过滤器
│   │   │   │   ├── EncodingFilter.java       # 编码过滤器
│   │   │   │   └── LoginFilter.java          # 登录过滤器
│   │   │   └── util/                         # 工具类
│   │   │       ├── BusinessUtil.java         # 业务工具类
│   │   │       └── DBUtil.java               # 数据库工具类
│   │   ├── resources/                        # 资源文件
│   │   │   ├── db.properties                 # 数据库配置文件
│   │   │   └── db.sql                        # 数据库脚本文件
│   │   └── webapp/                           # Web资源
│   │       ├── WEB-INF/
│   │       │   ├── common/                   # 公共组件
│   │       │   │   ├── footer.jsp            # 页脚组件
│   │       │   │   └── navbar.jsp            # 导航栏组件
│   │       │   ├── views/                    # 业务页面
│   │       │   │   ├── contact/              # 联系人页面
│   │       │   │   │   ├── add.jsp           # 添加联系人页面
│   │       │   │   │   ├── detail.jsp        # 联系人详情页面
│   │       │   │   │   ├── edit.jsp          # 编辑联系人页面
│   │       │   │   │   └── list.jsp          # 联系人列表页面
│   │       │   │   └── user/                 # 用户页面
│   │       │   │       ├── login.jsp         # 用户登录页面
│   │       │   │       └── register.jsp      # 用户注册页面
│   │       │   └── web.xml                   # Web配置文件
│   │       ├── error/                        # 错误页面
│   │       │   ├── 404.jsp                   # 404错误页面
│   │       │   └── 500.jsp                   # 500错误页面
│   │       ├── static/                       # 静态资源
│   │       │   ├── css/                      # CSS样式文件
│   │       │   │   ├── common/               # 公共样式
│   │       │   │   │   ├── footer.css        # 页脚样式文件
│   │       │   │   │   └── navbar.css        # 导航栏样式文件
│   │       │   │   ├── custom/               # 自定义样式
│   │       │   │   │   ├── contact/          # 联系人页面样式
│   │       │   │   │   │   ├── add.css       # 添加联系人页面样式
│   │       │   │   │   │   ├── detail.css    # 联系人详情页面样式
│   │       │   │   │   │   ├── edit.css      # 编辑联系人页面样式
│   │       │   │   │   │   └── list.css      # 联系人列表页面样式
│   │       │   │   │   └── user/             # 用户页面样式
│   │       │   │   │       └── auth.css      # 用户认证页面样式
│   │       │   │   └── vendor/               # 第三方样式
│   │       │   │       ├── bootstrap/        # Bootstrap样式
│   │       │   │       └── font-awesome/     # Font Awesome图标
│   │       │   ├── js/                       # JS文件
│   │       │   │   ├── common/               # 公共脚本
│   │       │   │   │   ├── footer.js         # 页脚脚本文件
│   │       │   │   │   └── navbar.js         # 导航栏脚本文件
│   │       │   │   ├── custom/               # 自定义脚本
│   │       │   │   │   ├── contact/          # 联系人页面脚本
│   │       │   │   │   │   ├── add.js        # 添加联系人页面脚本
│   │       │   │   │   │   ├── detail.js     # 联系人详情页面脚本
│   │       │   │   │   │   ├── edit.js       # 编辑联系人页面脚本
│   │       │   │   │   │   └── list.js       # 联系人列表页面脚本
│   │       │   │   │   └── user/             # 用户页面脚本
│   │       │   │   │       └── auth.js       # 用户认证页面脚本
│   │       │   │   └── vendor/               # 第三方脚本
│   │       │   │       ├── bootstrap/        # Bootstrap脚本
│   │       │   │       └── jquery/           # jQuery脚本
│   │       │   └── template/                 # 模板文件
│   │       │       └── 联系人模板.csv          # 联系人导入导出模板
│   │       ├── favicon.ico                   # 网站图标
│   │       └── index.jsp                     # 首页
│   └── test/                                 # 测试代码
├── .gitignore                                # Git忽略文件
├── LICENSE                                   # 许可证文件
├── pom.xml                                   # Maven配置文件
└── README.md                                 # 项目说明文档
```

## 功能模块

### 用户模块

- 用户注册
- 用户登录
- 登录状态验证

### 联系人模块

- 添加联系人
- 编辑联系人
- 删除联系人
- 查看联系人详情
- 查看联系人列表
- 联系人导入导出
- 联系人头像管理

## 接口说明

### 页面路由

- `GET /view/user/login`
- `GET /view/user/register`
- `GET /view/contact/list`
- `GET /view/contact/detail`
- `GET /view/contact/add`
- `GET /view/contact/edit`

### 用户接口

- `POST /user/login`
- `POST /user/register`
- `POST /user/logout`

### 联系人接口

- `GET /contact/list`
- `GET /contact/detail`
- `POST /contact/add`
- `POST /contact/edit`
- `POST /contact/delete`
- `POST /contact/import`
- `GET /contact/export`

## 数据库设计

### 用户表 (tbl_user)

- id: 主键
- username: 用户名
- password: 密码
- email: 邮箱
- create_time: 创建时间
- update_time: 更新时间

### 联系人表 (tbl_contact)

- id: 主键
- name: 姓名
- phone: 电话
- email: 邮箱
- address: 地址
- company: 公司
- position: 职位
- avatar: 头像
- remark: 备注
- create_time: 创建时间
- update_time: 更新时间

## 部署说明

### 环境要求

- JDK 1.8
- Maven 3.6.0
- MySQL 5.7.40
- Tomcat 8.5.50

### 数据库

执行`src/main/resources/db.sql`脚本创建数据库。

修改`src/main/resources/db.properties`中的数据库配置。

### 打包部署

编译打包项目：
```bash
mvn clean package
```

将生成的WAR包部署到Tomcat服务器。

启动Tomcat并访问：
[http://localhost:8080/renmai/](http://localhost:8080/renmai/)

## 测试账号

数据库初始化脚本中包含以下测试账号：

- 用户名：user
- 密码：123456
