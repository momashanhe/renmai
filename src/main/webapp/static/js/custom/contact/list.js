/**
 * 联系人列表
 */

$(document).ready(function () {
  // 加载联系人列表
  loadContactList();

  // 搜索框输入事件
  $('#searchInput').on('input', function () {
    debounce(loadContactList, 300)();
  });

  // 重置按钮点击事件
  $('#resetBtn').on('click', function () {
    $('#searchInput').val('');
    loadContactList();
  });

  // 删除按钮点击事件
  $(document).on('click', '.delete-contact-btn', function () {
    const contactId = $(this).data('id');
    const contactName = $(this).data('name');

    showConfirm(`确定要删除联系人 "${contactName}" 吗？此操作不可恢复。`, function () {
      deleteContact(contactId);
    }, '删除联系人');
  });

  // 导出按钮点击事件
  $('#exportBtn').on('click', function () {
    exportContacts();
  });

  // 导入按钮点击事件
  $('#importBtn').on('click', function () {
    showImportModal();
  });
});

// 防抖函数
function debounce(func, wait) {
  let timeout;
  return function executedFunction(...args) {
    const later = () => {
      clearTimeout(timeout);
      func(...args);
    };
    clearTimeout(timeout);
    timeout = setTimeout(later, wait);
  };
}

// 加载联系人列表
function loadContactList() {
  // 显示加载状态
  $('#contactListContainer').html(`
    <div class="d-flex justify-content-center align-items-center loading-container">
      <div class="spinner-border text-primary" role="status">
        <span class="visually-hidden">加载中...</span>
      </div>
    </div>
  `);

  // 构造请求参数
  const params = {};
  const keyword = $('#searchInput').val().trim();
  if (keyword) {
    params.keyword = keyword;
  }

  // 发送请求
  $.ajax({
    url: contextPath + '/api/contact/list',
    type: 'GET',
    data: params,
    dataType: 'json',
    success: function (response) {
      if (response.success) {
        renderContactList(response.data);
      } else {
        showErrorMessage(response.message || '获取联系人列表失败');
        $('#contactListContainer').html(`
          <div class="empty-state">
            <i class="fas fa-exclamation-triangle"></i>
            <h4>加载失败</h4>
            <p>${response.message || '获取联系人列表时发生错误'}</p>
          </div>
        `);
      }
    },
    error: function (xhr, status, error) {
      showErrorMessage('网络请求失败，请稍后重试');
      $('#contactListContainer').html(`
        <div class="empty-state">
          <i class="fas fa-exclamation-triangle"></i>
          <h4>网络错误</h4>
          <p>无法连接到服务器，请检查网络连接</p>
        </div>
      `);
    }
  });
}

// 渲染联系人列表
function renderContactList(contacts) {
  if (!contacts || contacts.length === 0) {
    $('#contactListContainer').html(`
      <div class="text-center py-5 text-muted">
        <i class="fas fa-address-book fa-3x mb-3"></i>
        <h4>暂无联系人</h4>
        <p class="mb-4">还没有添加任何联系人信息</p>
        <a href="${contextPath}/view/contact/add" class="btn btn-primary">
          <i class="fas fa-user-plus me-1"></i>添加联系人
        </a>
      </div>
    `);
    return;
  }

  let html = '';
  contacts.forEach(contact => {
    let avatarHtml;
    if (contact.avatar) {
      avatarHtml = `<img src="${contextPath}/${contact.avatar}" alt="${contact.name}" class="rounded-circle border contact-avatar-img">`;
    } else {
      avatarHtml = `<div class="rounded-circle bg-primary text-white d-flex align-items-center justify-content-center border contact-avatar-text">${contact.name.charAt(0).toUpperCase()}</div>`;
    }
    html += `
      <div class="card shadow-sm mb-3">
        <div class="card-body">
          <div class="d-flex align-items-center">
            <div class="me-3">${avatarHtml}</div>
            <div class="flex-grow-1">
              <div class="fw-bold mb-1">${contact.name}</div>
              <div class="text-muted small mb-1">
                <i class="fas fa-phone me-1"></i>${contact.phone || '未填写'}
              </div>
              <div class="text-muted small mb-1">
                <i class="fas fa-envelope me-1"></i>${contact.email || '未填写'}
              </div>
            </div>
            <div class="d-flex gap-2">
              <a href="${contextPath}/view/contact/detail?id=${contact.id}" 
                 class="btn btn-outline-primary btn-sm" 
                 title="查看">
                <i class="fas fa-eye"></i>
              </a>
              <a href="${contextPath}/view/contact/edit?id=${contact.id}" 
                 class="btn btn-outline-secondary btn-sm" 
                 title="编辑">
                <i class="fas fa-edit"></i>
              </a>
              <button class="btn btn-outline-danger btn-sm delete-contact-btn" 
                  data-id="${contact.id}" 
                  data-name="${contact.name}" 
                  title="删除">
                <i class="fas fa-trash"></i>
              </button>
            </div>
          </div>
        </div>
      </div>
    `;
  });

  $('#contactListContainer').html(html);
}

// 删除联系人
function deleteContact(contactId) {
  // 发送请求
  $.ajax({
    url: contextPath + '/api/contact/delete',
    type: 'POST',
    data: {id: contactId},
    dataType: 'json',
    success: function (response) {
      if (response.success) {
        showSuccessMessage(response.message);
        // 加载联系人列表
        loadContactList();
      } else {
        showErrorMessage(response.message || '删除联系人失败');
      }
    },
    error: function (xhr, status, error) {
      showErrorMessage('网络请求失败，请稍后重试');
    }
  });
}

// 导出联系人
function exportContacts() {
  // 显示加载状态
  const exportBtn = $('#exportBtn');
  const originalText = exportBtn.html();
  exportBtn.prop('disabled', true).html('<span class="spinner-border spinner-border-sm me-1" role="status" aria-hidden="true"></span>导出中...');

  // 发送导出请求
  $.ajax({
    url: contextPath + '/api/contact/export',
    type: 'GET',
    xhrFields: {
      responseType: 'blob'
    },
    success: function (blob, status, xhr) {
      // 创建下载链接
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = '联系人列表.csv';
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
      window.URL.revokeObjectURL(url);

      showSuccessMessage('导出成功');
    },
    error: function (xhr, status, error) {
      showErrorMessage('导出失败，请稍后重试');
    },
    complete: function () {
      // 恢复按钮状态
      exportBtn.prop('disabled', false).html(originalText);
    }
  });
}

// 显示导入模态框
function showImportModal() {
  // 创建导入模态框HTML
  const modalHtml = `
    <div class="modal fade" id="importModal" tabindex="-1" aria-labelledby="importModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="importModalLabel">导入联系人</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label for="csvFile" class="form-label">选择文件</label>
              <div class="input-group">
                <input type="file" class="form-control" id="file" accept=".csv">
                <a href="${contextPath}/static/template/联系人模板.csv" class="btn btn-outline-primary" download>
                  <i class="fas fa-download me-1"></i>下载模板
                </a>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" id="importSubmitBtn">导入</button>
          </div>
        </div>
      </div>
    </div>
  `;

  // 检查模态框是否已存在
  if ($('#importModal').length === 0) {
    $('body').append(modalHtml);
  }

  // 显示模态框
  const modal = new bootstrap.Modal(document.getElementById('importModal'));
  modal.show();

  // 绑定导入提交事件
  $('#importSubmitBtn').off('click').on('click', function () {
    importContacts();
  });
}

// 导入联系人
function importContacts() {
  const fileInput = $('#file')[0];

  if (!fileInput.files[0]) {
    showErrorMessage('请选择要导入的文件');
    return;
  }

  const file = fileInput.files[0];

  // 检查文件类型
  if (!file.name.toLowerCase().endsWith('.csv')) {
    showErrorMessage('请选择CSV格式的文件');
    return;
  }

  // 创建FormData对象
  const formData = new FormData();
  formData.append('file', file);

  // 显示加载状态
  const importSubmitBtn = $('#importSubmitBtn');
  const originalText = importSubmitBtn.text();
  importSubmitBtn.prop('disabled', true).text('导入中...');

  // 发送导入请求
  $.ajax({
    url: contextPath + '/api/contact/import',
    type: 'POST',
    data: formData,
    processData: false,
    contentType: false,
    success: function (response) {
      if (response.success) {
        showSuccessMessage(response.message || '导入成功');

        // 关闭模态框
        const modal = bootstrap.Modal.getInstance(document.getElementById('importModal'));
        modal.hide();

        // 重新加载联系人列表
        loadContactList();
      } else {
        showErrorMessage(response.message || '导入失败');
      }
    },
    error: function (xhr, status, error) {
      showErrorMessage('导入失败，请稍后重试');
    },
    complete: function () {
      // 恢复按钮状态
      importSubmitBtn.prop('disabled', false).text(originalText);
    }
  });
}