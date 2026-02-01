/**
 * 编辑联系人
 */

$(document).ready(function () {
  // 页面加载时获取联系人信息
  loadContactInfo();

  // 点击头像预览区域触发文件选择
  $('#avatarPreview').on('click', function () {
    $('#avatar').click();
  });

  // 文件选择变化时更新预览
  $('#avatar').on('change', function () {
    var file = this.files[0];
    if (file) {
      var reader = new FileReader();
      reader.onload = function (e) {
        var $avatarPreview = $('#avatarPreview');
        var $img = $avatarPreview.find('img');

        if ($img.length > 0) {
          $img.attr('src', e.target.result);
        } else {
          $avatarPreview.html(`<img src="${e.target.result}" alt="头像预览" class="rounded-circle avatar-preview-img object-fit-cover">`);
        }
      };
      reader.readAsDataURL(file);
    }
  });

  // 处理表单提交
  $('#editContactForm').on('submit', function (e) {
    // 阻止表单默认提交
    e.preventDefault();

    // 获取表单数据
    var formData = new FormData();
    formData.append('id', $('#id').val());
    formData.append('name', $('#name').val().trim());
    formData.append('phone', $('#phone').val().trim());
    formData.append('email', $('#email').val().trim());
    formData.append('address', $('#address').val().trim());
    formData.append('company', $('#company').val().trim());
    formData.append('position', $('#position').val().trim());
    formData.append('remark', $('#remark').val().trim());

    // 获取头像文件
    var avatarFile = $('#avatar')[0].files[0];
    if (avatarFile) {
      formData.append('avatarFile', avatarFile);
    }

    // 简单验证
    if (!$('#name').val().trim()) {
      showErrorMessage('请输入联系人姓名');
      return;
    }
    if (!$('#phone').val().trim()) {
      showErrorMessage('请输入联系电话');
      return;
    }

    // 禁用按钮并显示加载状态
    var saveBtn = $('#saveBtn');
    var originalHtml = saveBtn.html();
    saveBtn.prop('disabled', true).html('<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> 保存中...');

    // 发送请求
    $.ajax({
      url: contextPath + '/api/contact/edit',
      type: 'POST',
      data: formData,
      processData: false,  // 不处理数据
      contentType: false,  // 不设置内容类型
      dataType: 'json',
      success: function (response) {
        if (response.success) {
          showSuccessMessage(response.message);
          // 编辑成功后延迟跳转到联系人列表页面
          setTimeout(function () {
            window.location.href = contextPath + '/view/contact/list';
          }, 1500);
        } else {
          showErrorMessage(response.message);
        }
      },
      error: function (xhr, status, error) {
        showErrorMessage('编辑联系人失败，请稍后重试');
      },
      complete: function () {
        // 重新启用按钮
        saveBtn.prop('disabled', false).html(originalHtml);
      }
    });
  });

  // 重置按钮点击事件
  $('#resetBtn').on('click', function () {
    loadContactInfo();
  });
});

// 加载联系人信息
function loadContactInfo() {
  // 从URL获取联系人ID
  const urlParams = new URLSearchParams(window.location.search);
  const contactId = urlParams.get('id');

  if (!contactId) {
    showErrorMessage('无效的联系人ID');
    return;
  }

  // 发送请求获取联系人信息
  $.ajax({
    url: contextPath + '/api/contact/detail',
    type: 'GET',
    data: {id: contactId},
    dataType: 'json',
    success: function (response) {
      if (response.success) {
        const contact = response.data;
        // 填充表单数据
        $('#id').val(contact.id);
        $('#name').val(contact.name);
        $('#phone').val(contact.phone);
        $('#email').val(contact.email);
        $('#address').val(contact.address);
        $('#company').val(contact.company || '');
        $('#position').val(contact.position || '');
        $('#remark').val(contact.remark || '');

        // 显示头像
        if (contact.avatar) {
          var $avatarPreview = $('#avatarPreview');
          var $img = $avatarPreview.find('img');

          if ($img.length > 0) {
            // 如果已存在img元素，则更新其属性
            $img.attr('src', `${contextPath}/${contact.avatar}`);
            $img.attr('alt', contact.name);
          } else {
            // 如果不存在img元素，则插入新的img标签
            $avatarPreview.html(`<img src="${contextPath}/${contact.avatar}" alt="${contact.name}" class="rounded-circle avatar-preview-img object-fit-cover">`);
          }
        }
      } else {
        showErrorMessage(response.message || '获取联系人信息失败');
      }
    },
    error: function (xhr, status, error) {
      showErrorMessage('网络请求失败，请稍后重试');
    }
  });
}