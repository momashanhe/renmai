/**
 * 联系人详情
 */

$(document).ready(function () {
  // 页面加载时获取联系人信息
  loadContactInfo();
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
        $('#name').val(contact.name || '');
        $('#phone').val(contact.phone || '');
        $('#email').val(contact.email || '');
        $('#address').val(contact.address || '');
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