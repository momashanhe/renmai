/**
 * 用户认证
 */

$(document).ready(function() {
  // 处理登录表单提交
  $('#loginForm').on('submit', function(e) {
    // 阻止表单默认提交
    e.preventDefault();
    
    // 获取表单数据
    var username = $('#username').val().trim();
    var password = $('#password').val().trim();
    
    // 简单验证
    if (username === '') {
      showErrorMessage('请输入用户名');
      return;
    }
    if (password === '') {
      showErrorMessage('请输入密码');
      return;
    }
    
    // 禁用按钮并显示加载状态
    var loginBtn = $('#loginBtn');
    var originalText = loginBtn.html();
    loginBtn.prop('disabled', true).html('<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>');
    
    // 发送请求
    $.ajax({
      url: contextPath + '/api/user/login',
      type: 'POST',
      data: {
        username: username,
        password: password
      },
      dataType: 'json',
      success: function(response) {
        if (response.success) {
          showSuccessMessage(response.message);
          // 登录成功后跳转到联系人列表页面
          setTimeout(function() {
            window.location.href = contextPath + '/view/contact/list';
          }, 1500);
        } else {
          showErrorMessage(response.message);
        }
      },
      error: function(xhr, status, error) {
        showErrorMessage('登录失败，请稍后重试');
      },
      complete: function() {
        // 重新启用按钮
        loginBtn.prop('disabled', false).html(originalText);
      }
    });
  });
  
  // 处理注册表单提交
  $('#registerForm').on('submit', function(e) {
    // 阻止表单默认提交
    e.preventDefault();
    
    // 获取表单数据
    var username = $('#username').val().trim();
    var password = $('#password').val().trim();
    var confirmPassword = $('#confirmPassword').val().trim();
    var email = $('#email').val().trim();
    
    // 简单验证
    if (username === '') {
      showErrorMessage('请输入用户名');
      return;
    }
    if (password === '') {
      showErrorMessage('请输入密码');
      return;
    }
    if (confirmPassword === '') {
      showErrorMessage('请确认密码');
      return;
    }
    if (password !== confirmPassword) {
      showErrorMessage('两次输入的密码不一致');
      return;
    }
    if (email === '') {
      showErrorMessage('请输入邮箱');
      return;
    }
    
    // 禁用按钮并显示加载状态
    var registerBtn = $('#registerBtn');
    var originalText = registerBtn.html();
    registerBtn.prop('disabled', true).html('<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>');
    
    // 发送请求
    $.ajax({
      url: contextPath + '/api/user/register',
      type: 'POST',
      data: {
        username: username,
        password: password,
        confirmPassword: confirmPassword,
        email: email
      },
      dataType: 'json',
      success: function(response) {
        if (response.success) {
          showSuccessMessage(response.message);
          // 注册成功后跳转到登录页面
          setTimeout(function() {
            window.location.href = contextPath + '/view/user/login';
          }, 1500);
        } else {
          showErrorMessage(response.message);
        }
      },
      error: function(xhr, status, error) {
        showErrorMessage('注册失败，请稍后重试');
      },
      complete: function() {
        // 重新启用按钮
        registerBtn.prop('disabled', false).html(originalText);
      }
    });
  });
});