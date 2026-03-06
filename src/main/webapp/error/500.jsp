<%--
  500错误页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>500 - 服务器内部错误</title>
  <!-- 导航栏 -->
  <jsp:include page="/WEB-INF/common/navbar.jsp">
    <jsp:param name="active" value="error"/>
  </jsp:include>
</head>
<body>
  <div class="container py-5">
    <div class="row justify-content-center">
      <div class="col-12 col-md-8 col-lg-6">
        <div class="card shadow-sm">
          <div class="card-body text-center p-5">
            <h1 class="display-1 text-danger fw-bold mb-4">500</h1>
            <h2 class="mb-4">抱歉，服务器出现内部错误</h2>
            <p class="lead mb-4">服务器遇到了一个意外的情况，导致无法完成您的请求。</p>
            <p class="lead mb-4">我们的技术团队已经被通知此问题，我们会尽快解决。</p>
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary btn-lg">返回首页</a>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 页脚 -->
  <jsp:include page="/WEB-INF/common/footer.jsp"/>
</body>
</html>