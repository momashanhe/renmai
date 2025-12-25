package com.momashanhe.renmai.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库连接工具类
 */
public class DBUtil {
    private static String url;
    private static String username;
    private static String password;
    private static String driver;

    static {
        try {
            // 加载配置文件
            Properties props = new Properties();
            InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            props.load(inputStream);

            // 获取数据库配置信息
            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
            driver = props.getProperty("db.driver");

            // 加载数据库驱动
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     *
     * @return Connection对象
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * 关闭数据库连接
     *
     * @param connection Connection对象
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}