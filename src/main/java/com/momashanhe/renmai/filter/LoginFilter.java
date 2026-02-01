package com.momashanhe.renmai.filter;

import com.momashanhe.renmai.entity.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录过滤器，确保用户必须登录才能访问联系人功能
 */
@WebFilter("/contact/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化操作
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 获取会话
        HttpSession session = httpRequest.getSession(false);

        // 检查用户是否已登录
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            // 用户未登录，重定向到登录页面
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/view/user/login");
        } else {
            // 用户已登录，继续处理请求
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // 清理操作
    }
}