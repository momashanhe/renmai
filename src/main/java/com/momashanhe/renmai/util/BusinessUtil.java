package com.momashanhe.renmai.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.momashanhe.renmai.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器通用方法
 */
public class BusinessUtil {
    // 用于JSON处理
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 发送JSON响应
     *
     * @param response HttpServletResponse对象
     * @param result 要发送的数据
     * @throws IOException
     */
    public static void sendJsonResponse(HttpServletResponse response, Object result) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    /**
     * 发送错误响应
     *
     * @param response HttpServletResponse对象
     * @param message 返回的消息
     * @param data 返回的数据
     * @throws IOException
     */
    public static void sendErrorResponse(HttpServletResponse response, String message, Object data) throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("message", message);
        result.put("data", data);
        sendJsonResponse(response, result);
    }

    /**
     * 发送错误响应
     *
     * @param response HttpServletResponse对象
     * @param message 返回的消息
     * @throws IOException
     */
    public static void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("message", message);
        sendJsonResponse(response, result);
    }

    /**
     * 发送成功响应
     *
     * @param response HttpServletResponse对象
     * @param message 返回的消息
     * @param data 返回的数据
     * @throws IOException
     */
    public static void sendSuccessResponse(HttpServletResponse response, String message, Object data) throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", message);
        result.put("data", data);
        sendJsonResponse(response, result);
    }

    /**
     * 发送成功响应
     *
     * @param response HttpServletResponse对象
     * @param message 返回的消息
     * @throws IOException
     */
    public static void sendSuccessResponse(HttpServletResponse response, String message) throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", message);
        sendJsonResponse(response, result);
    }

    /**
     * 检查用户是否已登录，如果用户未登录，重定向到登录页面
     *
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return 是否已登录
     * @throws IOException
     */
    public static boolean requireLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取当前登录用户
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            // 用户未登录，重定向到登录页面
            response.sendRedirect(request.getContextPath() + "/view/user/login");
            return false;
        }
        return true;
    }
}