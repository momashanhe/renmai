/**
 * 导航栏
 */

$(document).ready(function() {
  // 激活下拉菜单
  var dropdownElementList = $('.dropdown-toggle').get();
  var dropdownList = dropdownElementList.map(function(dropdownToggleEl) {
    return new bootstrap.Dropdown(dropdownToggleEl);
  });
  
  // 交互效果
  const navLinks = $('.nav-link');
  navLinks.each(function() {
    $(this).on('click', function() {
      // 移除所有活动状态
      navLinks.removeClass('active');
      // 添加当前链接的活动状态
      $(this).addClass('active');
    });
  });
  
  // 处理退出登录链接点击事件
  $('#logoutLink').on('click', function(e) {
    // 阻止默认链接行为
    e.preventDefault();
    
    // 显示确认对话框
    showConfirm('确定要退出登录吗？', function() {
      // 发送退出登录请求
      $.ajax({
        url: contextPath + '/api/user/logout',
        type: 'GET',
        dataType: 'json',
        success: function(response) {
          if (response.success) {
            // 退出成功，显示消息并跳转到登录页面
            showSuccessMessage(response.message);
            setTimeout(function() {
              window.location.href = contextPath + '/view/user/login';
            }, 1500);
          } else {
            // 退出失败，显示错误消息
            showErrorMessage(response.message || '退出登录失败，请稍后重试');
          }
        },
        error: function(xhr, status, error) {
          // 请求失败，显示错误消息
          showErrorMessage('退出登录失败，请稍后重试');
        }
      });
    });
  });
});

/**
 * 显示Bootstrap风格的消息提示
 * @param {string} message - 要显示的消息内容
 * @param {string} type - 消息类型: success, danger, warning, info (默认为info)
 * @param {number} duration - 显示持续时间(毫秒)，默认为3000ms
 */
function showMessage(message, type = 'info', duration = 3000) {
  // 创建消息元素
  const alertDiv = $('<div>')
    .addClass('alert alert-' + type + ' fade show')
    .attr('role', 'alert')
    .html(message);
  
  // 添加到容器
  const container = $('#messageContainer');
  container.append(alertDiv);
  
  // 设置自动隐藏
  if (duration > 0) {
    setTimeout(() => {
      alertDiv.remove();
    }, duration);
  }
}

/**
 * 显示成功消息
 * @param {string} message - 消息内容
 * @param {number} duration - 显示持续时间(毫秒)
 */
function showSuccessMessage(message, duration = 3000) {
  showMessage(message, 'success', duration);
}

/**
 * 显示错误消息
 * @param {string} message - 消息内容
 * @param {number} duration - 显示持续时间(毫秒)
 */
function showErrorMessage(message, duration = 3000) {
  showMessage(message, 'danger', duration);
}

/**
 * 显示警告消息
 * @param {string} message - 消息内容
 * @param {number} duration - 显示持续时间(毫秒)
 */
function showWarningMessage(message, duration = 3000) {
  showMessage(message, 'warning', duration);
}

/**
 * 显示信息消息
 * @param {string} message - 消息内容
 * @param {number} duration - 显示持续时间(毫秒)
 */
function showInfoMessage(message, duration = 3000) {
  showMessage(message, 'info', duration);
}

/**
 * 显示确认模态框
 * @param {string} message - 确认消息内容
 * @param {function} callback - 点击确定后的回调函数
 * @param {string} title - 模态框标题（可选）
 */
function showConfirm(message, callback, title = '确认操作') {
  // 设置模态框内容
  $('#confirmMessage').text(message);
  $('#confirmModalLabel').text(title);
  
  // 绑定确定按钮事件
  const confirmOkBtn = $('#confirmOkBtn');
  const modalElement = $('#confirmModal');
  
  // 清除之前的事件监听器
  const newConfirmOkBtn = confirmOkBtn.clone();
  confirmOkBtn.replaceWith(newConfirmOkBtn);
  
  // 添加新的事件监听器
  newConfirmOkBtn.on('click', function() {
    // 关闭模态框
    const modal = bootstrap.Modal.getInstance(modalElement[0]);
    if (modal) {
      modal.hide();
    }
    
    // 执行回调函数
    if (typeof callback === 'function') {
      callback();
    }
  });
  
  // 显示模态框
  const modal = new bootstrap.Modal(modalElement[0]);
  modal.show();
}