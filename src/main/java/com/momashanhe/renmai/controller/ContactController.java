package com.momashanhe.renmai.controller;

import com.momashanhe.renmai.util.BusinessUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 联系人控制器，处理联系人相关页面跳转
 */
@WebServlet("/view/contact/*")
public class ContactController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 检查是否已登录，如果用户未登录，重定向到登录页面
        if (!BusinessUtil.requireLogin(request, response)) {
            return;
        }

        switch (request.getPathInfo()) {
            case "/list":
                request.getRequestDispatcher("/WEB-INF/views/contact/list.jsp").forward(request, response);
                break;
            case "/detail":
                request.getRequestDispatcher("/WEB-INF/views/contact/detail.jsp").forward(request, response);
                break;
            case "/add":
                request.getRequestDispatcher("/WEB-INF/views/contact/add.jsp").forward(request, response);
                break;
            case "/edit":
                request.getRequestDispatcher("/WEB-INF/views/contact/edit.jsp").forward(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }
}