<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>联系人列表 - 人脉</title>
  <!-- 导航栏 -->
  <jsp:include page="/WEB-INF/common/navbar.jsp">
    <jsp:param name="active" value="contacts"/>
  </jsp:include>
  <!-- 联系人列表 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/custom/contact/list.css">
</head>
<body class="d-flex flex-column min-vh-100">
  <!-- 主体内容 -->
  <main class="flex-grow-1">
    <div class="container py-4">
      <div class="row">
        <div class="col-12">
          <div class="d-flex justify-content-between align-items-center mb-4">
            <h2><i class="fas fa-users me-2"></i>联系人列表</h2>
            <div class="d-flex gap-2">
              <button class="btn btn-secondary" id="exportBtn">
                <i class="fas fa-file-export me-1"></i>导出联系人
              </button>
              <button class="btn btn-secondary" id="importBtn">
                <i class="fas fa-file-import me-1"></i>导入联系人
              </button>
              <a href="${pageContext.request.contextPath}/view/contact/add" class="btn btn-primary">
                <i class="fas fa-user-plus me-1"></i>添加联系人
              </a>
            </div>
          </div>
          
          <!-- 搜索区域 -->
          <div class="bg-light rounded p-3 mb-4">
            <div class="row align-items-center">
              <div class="col-md-6">
                <div class="input-group search-input-max-width">
                  <span class="input-group-text"><i class="fas fa-search"></i></span>
                  <input type="text" class="form-control" id="searchInput" placeholder="搜索联系人...">
                </div>
              </div>
              <div class="col-md-6 text-md-end mt-2 mt-md-0">
                <button class="btn btn-outline-secondary" id="resetBtn">
                  <i class="fas fa-sync-alt me-1"></i>重置
                </button>
              </div>
            </div>
          </div>
          
          <!-- 联系人列表容器 -->
          <div id="contactListContainer">
            <div class="d-flex justify-content-center align-items-center loading-container">
              <div class="spinner-border text-primary" role="status">
                <span class="visually-hidden">加载中...</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>

  <!-- 页脚 -->
  <jsp:include page="/WEB-INF/common/footer.jsp"/>

  <!-- 联系人列表 -->
  <script src="${pageContext.request.contextPath}/static/js/custom/contact/list.js"></script>
</body>
</html>