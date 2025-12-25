<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Favicon -->
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
<link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
<!-- 导航栏 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common/navbar.css">
<!-- Bootstrap CSS -->
<link href="${pageContext.request.contextPath}/static/css/vendor/bootstrap/bootstrap.min.css" rel="stylesheet">
<!-- Font Awesome -->
<link href="${pageContext.request.contextPath}/static/css/vendor/font-awesome/css/all.min.css" rel="stylesheet">

<nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
  <div class="container-fluid">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/view/contact/list">
      <i class="fas fa-address-book me-2"></i>人脉
    </a>
    
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>
    
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto">
        <li class="nav-item">
          <a class="nav-link ${param.active == 'contacts' ? 'active' : ''}" 
             href="${pageContext.request.contextPath}/view/contact/list">
            <i class="fas fa-users me-1"></i>联系人列表
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link ${param.active == 'add' ? 'active' : ''}" 
             href="${pageContext.request.contextPath}/view/contact/add">
            <i class="fas fa-user-plus me-1"></i>添加联系人
          </a>
        </li>
      </ul>
      
      <ul class="navbar-nav">
        <c:if test="${not empty sessionScope.user}">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
              <i class="fas fa-user me-1"></i>${sessionScope.user.username}
            </a>
            <ul class="dropdown-menu dropdown-menu-end">
              <li>
                <a class="dropdown-item" href="#" id="logoutLink">
                  <i class="fas fa-sign-out-alt me-1"></i>退出登录
                </a>
              </li>
            </ul>
          </li>
        </c:if>
      </ul>
    </div>
  </div>
</nav>

<!-- 通用消息提示组件 -->
<div id="messageContainer" class="position-fixed top-0 start-50 translate-middle-x p-3 message-container">
  <!-- 消息将通过JavaScript动态插入到这里 -->
</div>

<!-- 通用确认模态框 -->
<div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="confirmModalLabel">确认操作</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <span id="confirmMessage">确定要执行此操作吗？</span>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" id="confirmOkBtn">确定</button>
      </div>
    </div>
  </div>
</div>

<!-- 设置上下文路径全局变量 -->
<script>
  contextPath = '${pageContext.request.contextPath}';
</script>

<!-- jQuery -->
<script src="${pageContext.request.contextPath}/static/js/vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap JS -->
<script src="${pageContext.request.contextPath}/static/js/vendor/bootstrap/bootstrap.bundle.min.js"></script>

<!-- 导航栏 -->
<script src="${pageContext.request.contextPath}/static/js/common/navbar.js"></script>