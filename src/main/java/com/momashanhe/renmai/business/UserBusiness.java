package com.momashanhe.renmai.business;

import com.momashanhe.renmai.dao.UserDao;
import com.momashanhe.renmai.entity.User;
import com.momashanhe.renmai.util.BusinessUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用户控制器，处理用户相关操作
 */
@WebServlet("/api/user/*")
public class UserBusiness extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        switch (request.getPathInfo()) {
            case "/logout":
                logout(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        switch (request.getPathInfo()) {
            case "/login":
                login(request, response);
                break;
            case "/register":
                register(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    /**
     * 处理用户登录
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 验证参数
        if (username == null || username.isEmpty()) {
            BusinessUtil.sendErrorResponse(response, "登录失败，用户名不能为空");
            return;
        }
        if (password == null || password.isEmpty()) {
            BusinessUtil.sendErrorResponse(response, "登录失败，密码不能为空");
            return;
        }

        // 根据用户名查询用户
        User user = userDao.findByUsername(username);

        // 验证用户名
        if (user == null) {
            BusinessUtil.sendErrorResponse(response, "登录失败，用户名不存在");
            return;
        }

        // 验证密码
        if (!user.getPassword().equals(password)) {
            BusinessUtil.sendErrorResponse(response, "登录失败，密码错误");
            return;
        }

        // 登录成功，将用户信息存储在会话中
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        BusinessUtil.sendSuccessResponse(response, "登录成功");
    }

    /**
     * 处理用户注册
     */
    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");

        // 验证参数
        if (username == null || username.isEmpty()) {
            BusinessUtil.sendErrorResponse(response, "注册失败，用户名不能为空");
            return;
        }
        if (password == null || password.isEmpty()) {
            BusinessUtil.sendErrorResponse(response, "注册失败，密码不能为空");
            return;
        }
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            BusinessUtil.sendErrorResponse(response, "注册失败，确认密码不能为空");
            return;
        }
        if (email == null || email.isEmpty()) {
            BusinessUtil.sendErrorResponse(response, "注册失败，邮箱不能为空");
            return;
        }

        // 验证密码确认
        if (!password.equals(confirmPassword)) {
            BusinessUtil.sendErrorResponse(response, "注册失败，两次输入密码不一致");
            return;
        }

        // 根据用户名查询用户
        User user = userDao.findByUsername(username);

        // 验证用户名
        if (user != null) {
            BusinessUtil.sendErrorResponse(response, "注册失败，用户名已存在");
            return;
        }

        // 创建用户
        User newUser = new User(username, password, email);
        boolean createResult = userDao.createUser(newUser);
        if (createResult) {
            BusinessUtil.sendSuccessResponse(response, "注册成功，请登录");
        } else {
            BusinessUtil.sendErrorResponse(response, "注册失败，请稍后重试");
        }
    }

    /**
     * 处理用户退出
     */
    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 会话失效
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // 退出成功
        BusinessUtil.sendSuccessResponse(response, "退出成功");
    }
}