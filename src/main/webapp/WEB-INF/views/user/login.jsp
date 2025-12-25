<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>用户登录 - 人脉</title>
  <!-- 导航栏 -->
  <jsp:include page="/WEB-INF/common/navbar.jsp">
    <jsp:param name="active" value="login"/>
  </jsp:include>
  <!-- 认证 -->
  <link href="${pageContext.request.contextPath}/static/css/custom/user/auth.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">
  <!-- 主体内容 -->
  <main class="flex-grow-1">
    <div class="container py-5">
      <div class="row justify-content-center">
        <div class="col-md-6 col-lg-5">
          <div class="card shadow-sm">
            <div class="card-header bg-primary text-white text-center">
              <h4 class="mb-0">用户登录</h4>
            </div>
            <div class="card-body">
              <form id="loginForm">
                <div class="mb-3">
                  <label for="username" class="form-label auth-form-label">用户名</label>
                  <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-user"></i></span>
                    <input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名" required>
                  </div>
                </div>
                <div class="mb-3">
                  <label for="password" class="form-label auth-form-label">密码</label>
                  <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-lock"></i></span>
                    <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码" required>
                  </div>
                </div>
                <div class="d-grid">
                  <button type="submit" class="btn btn-primary" id="loginBtn">
                    登录
                  </button>
                </div>
              </form>
              <div class="text-center mt-3">
                <p class="mb-0">还没有账号？<a href="${pageContext.request.contextPath}/view/user/register" class="auth-link">立即注册</a></p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>

  <!-- 页脚 -->
  <jsp:include page="/WEB-INF/common/footer.jsp"/>

  <!-- 认证 -->
  <script src="${pageContext.request.contextPath}/static/js/custom/user/auth.js"></script>
</body>
</html>