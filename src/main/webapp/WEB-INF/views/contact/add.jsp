<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>添加联系人 - 人脉</title>
  <!-- 导航栏 -->
  <jsp:include page="/WEB-INF/common/navbar.jsp">
    <jsp:param name="active" value="add"/>
  </jsp:include>
  <!-- 添加联系人 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/custom/contact/add.css">
</head>
<body class="d-flex flex-column min-vh-100">
  <!-- 主体内容 -->
  <main class="flex-grow-1">
    <div class="container py-5">
      <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
          <div class="card shadow-sm">
            <div class="card-header bg-primary text-white text-center">
              <h4 class="mb-0">添加联系人</h4>
            </div>
            <div class="card-body">
              <form id="addContactForm" enctype="multipart/form-data">
                <div class="mb-3">
                  <label class="form-label fw-medium">头像：</label>
                  <div class="mb-2">
                    <div id="avatarPreview">
                      <i class="fas fa-user fa-2x"></i>
                    </div>
                    <input type="file" class="form-control d-none" id="avatar" name="avatar" accept="image/*">
                  </div>
                </div>
                <div class="mb-3">
                  <label for="name" class="form-label fw-medium">姓名<span class="text-danger">*</span></label>
                  <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-user"></i></span>
                    <input type="text" class="form-control" id="name" name="name" placeholder="请输入联系人姓名" required>
                  </div>
                </div>
                <div class="mb-3">
                  <label for="phone" class="form-label fw-medium">电话</label>
                  <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-phone"></i></span>
                    <input type="tel" class="form-control" id="phone" name="phone" placeholder="请输入联系电话">
                  </div>
                </div>
                <div class="mb-3">
                  <label for="email" class="form-label fw-medium">邮箱</label>
                  <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-envelope"></i></span>
                    <input type="email" class="form-control" id="email" name="email" placeholder="请输入联系邮箱">
                  </div>
                </div>
                <div class="mb-3">
                  <label for="address" class="form-label fw-medium">地址</label>
                  <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-map-marker-alt"></i></span>
                    <input type="text" class="form-control" id="address" name="address" placeholder="请输入联系地址">
                  </div>
                </div>
                <div class="mb-3">
                  <label for="company" class="form-label fw-medium">公司</label>
                  <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-building"></i></span>
                    <input type="text" class="form-control" id="company" name="company" placeholder="请输入公司名称">
                  </div>
                </div>
                <div class="mb-3">
                  <label for="position" class="form-label fw-medium">职位</label>
                  <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-briefcase"></i></span>
                    <input type="text" class="form-control" id="position" name="position" placeholder="请输入职位信息">
                  </div>
                </div>
                <div class="mb-3">
                  <label for="remark" class="form-label fw-medium">备注</label>
                  <textarea class="form-control" id="remark" name="remark" rows="3" placeholder="请输入备注信息"></textarea>
                </div>
                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                  <a href="${pageContext.request.contextPath}/view/contact/list" class="btn btn-secondary">
                    <i class="fas fa-arrow-left me-1"></i>返回
                  </a>
                  <button type="button" class="btn btn-secondary" id="resetBtn">
                    <i class="fas fa-sync-alt me-1"></i>重置
                  </button>
                  <button type="submit" class="btn btn-primary" id="saveBtn">
                    <i class="fas fa-save me-1"></i>保存
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>

  <!-- 页脚 -->
  <jsp:include page="/WEB-INF/common/footer.jsp"/>

  <!-- 添加联系人 -->
  <script src="${pageContext.request.contextPath}/static/js/custom/contact/add.js"></script>
</body>
</html>