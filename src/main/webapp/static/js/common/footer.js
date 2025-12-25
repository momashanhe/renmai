/**
 * 页脚
 */

$(document).ready(function() {
  // 动态更新年份
  const currentYear = new Date().getFullYear();
  const footer = $('footer');
  if (footer.length > 0) {
    const yearElements = footer.find('#currentYear');
    yearElements.each(function() {
      $(this).text(currentYear);
    });
  }
});