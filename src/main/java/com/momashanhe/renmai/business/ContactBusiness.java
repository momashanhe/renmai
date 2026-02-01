package com.momashanhe.renmai.business;

import com.momashanhe.renmai.dao.ContactDao;
import com.momashanhe.renmai.entity.Contact;
import com.momashanhe.renmai.util.BusinessUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * 联系人控制器，处理联系人相关操作
 */
@WebServlet("/api/contact/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class ContactBusiness extends HttpServlet {
    private ContactDao contactDao = new ContactDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 检查是否已登录，如果用户未登录，重定向到登录页面
        if (!BusinessUtil.requireLogin(request, response)) {
            return;
        }

        switch (request.getPathInfo()) {
            case "/list":
                contactList(request, response);
                break;
            case "/detail":
                contactDetail(request, response);
                break;
            case "/export":
                exportContacts(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 检查是否已登录，如果用户未登录，重定向到登录页面
        if (!BusinessUtil.requireLogin(request, response)) {
            return;
        }

        switch (request.getPathInfo()) {
            case "/add":
                addContact(request, response);
                break;
            case "/edit":
                editContact(request, response);
                break;
            case "/delete":
                deleteContact(request, response);
                break;
            case "/import":
                importContacts(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    /**
     * 联系人列表
     */
    private void contactList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取参数
        String keyword = request.getParameter("keyword");

        // 查询联系人列表
        List<Contact> contacts;
        if (keyword == null || keyword.isEmpty()) {
            contacts = contactDao.listAll();
        } else {
            contacts = contactDao.listByKeyword(keyword);
        }

        // 查询失败
        if (contacts == null) {
            BusinessUtil.sendErrorResponse(response, "查询失败，请稍后重试");
            return;
        }

        // 查询成功
        BusinessUtil.sendSuccessResponse(response, "查询成功", contacts);
    }

    /**
     * 联系人详情
     */
    private void contactDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取参数
        String idStr = request.getParameter("id");

        // 验证参数
        if (idStr == null || idStr.isEmpty()) {
            BusinessUtil.sendErrorResponse(response, "查询失败，联系人ID不能为空");
            return;
        }
        if (!idStr.matches("[0-9]+")) {
            BusinessUtil.sendErrorResponse(response, "查询失败，联系人ID必须是数字");
            return;
        }

        // 查询联系人
        int id = Integer.parseInt(idStr);
        Contact contact = contactDao.findById(id);

        if (contact == null) {
            BusinessUtil.sendErrorResponse(response, "查询失败，联系人不存在");
            return;
        }

        // 查询成功
        BusinessUtil.sendSuccessResponse(response, "查询成功", contact);
    }

    /**
     * 添加联系人
     */
    private void addContact(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 获取参数
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String company = request.getParameter("company");
        String position = request.getParameter("position");
        String remark = request.getParameter("remark");

        // 验证参数
        if (name == null || name.isEmpty()) {
            BusinessUtil.sendErrorResponse(response, "添加失败，姓名不能为空");
            return;
        }
        if (phone == null || phone.isEmpty()) {
            BusinessUtil.sendErrorResponse(response, "添加失败，电话不能为空");
            return;
        }

        // 处理头像上传
        String avatarPath = null;
        Part avatarPart = request.getPart("avatarFile");
        if (avatarPart != null && avatarPart.getSize() > 0) {
            // 获取上传文件的真实文件名
            String fileName = Paths.get(avatarPart.getSubmittedFileName()).getFileName().toString();
            if (!fileName.isEmpty()) {
                // 生成唯一的文件名
                String uniqueFileName = UUID.randomUUID() + "_" + fileName;

                // 确保上传目录存在
                String uploadDir = getServletContext().getRealPath("/upload/avatar");
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }

                // 保存文件
                String filePath = uploadDir + File.separator + uniqueFileName;
                avatarPart.write(filePath);

                // 保存相对路径到数据库
                avatarPath = "upload/avatar/" + uniqueFileName;
            }
        }

        // 创建联系人
        Contact contact = new Contact(name, phone, email, address, company, position, avatarPath, remark);
        boolean createResult = contactDao.createContact(contact);
        if (createResult) {
            BusinessUtil.sendSuccessResponse(response, "添加成功");
        } else {
            BusinessUtil.sendErrorResponse(response, "添加失败，请稍后重试");
        }
    }

    /**
     * 更新联系人
     */
    private void editContact(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 获取参数
        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String company = request.getParameter("company");
        String position = request.getParameter("position");
        String remark = request.getParameter("remark");

        // 验证参数
        if (idStr == null || idStr.isEmpty()) {
            BusinessUtil.sendErrorResponse(response, "更新失败，联系人ID不能为空");
            return;
        }
        if (!idStr.matches("[0-9]+")) {
            BusinessUtil.sendErrorResponse(response, "更新失败，联系人ID必须是数字");
            return;
        }
        if (name == null || name.isEmpty()) {
            BusinessUtil.sendErrorResponse(response, "更新失败，姓名不能为空");
            return;
        }
        if (phone == null || phone.isEmpty()) {
            BusinessUtil.sendErrorResponse(response, "更新失败，电话不能为空");
            return;
        }

        // 查询联系人
        int id = Integer.parseInt(idStr);
        Contact contact = contactDao.findById(id);

        if (contact == null) {
            BusinessUtil.sendErrorResponse(response, "更新失败，联系人不存在");
            return;
        }

        // 处理头像上传
        String avatarPath = contact.getAvatar();
        Part avatarPart = request.getPart("avatarFile");
        if (avatarPart != null && avatarPart.getSize() > 0) {
            // 获取上传文件的真实文件名
            String fileName = Paths.get(avatarPart.getSubmittedFileName()).getFileName().toString();
            if (!fileName.isEmpty()) {
                // 生成唯一的文件名
                String uniqueFileName = UUID.randomUUID() + "_" + fileName;

                // 确保上传目录存在
                String uploadDir = getServletContext().getRealPath("/upload/avatar");
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }

                // 保存文件
                String filePath = uploadDir + File.separator + uniqueFileName;
                avatarPart.write(filePath);

                // 保存相对路径到数据库
                avatarPath = "upload/avatar/" + uniqueFileName;
            }
        }

        // 更新联系人
        contact.setName(name);
        contact.setPhone(phone);
        contact.setEmail(email);
        contact.setAddress(address);
        contact.setCompany(company);
        contact.setPosition(position);
        contact.setAvatar(avatarPath);
        contact.setRemark(remark);
        boolean updateResult = contactDao.updateContact(contact);
        if (updateResult) {
            BusinessUtil.sendSuccessResponse(response, "更新成功");
        } else {
            BusinessUtil.sendErrorResponse(response, "更新失败，请稍后重试");
        }
    }

    /**
     * 删除联系人
     */
    private void deleteContact(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取参数
        String idStr = request.getParameter("id");

        // 验证参数
        if (idStr == null || idStr.isEmpty()) {
            BusinessUtil.sendErrorResponse(response, "删除失败，联系人ID不能为空");
            return;
        }
        if (!idStr.matches("[0-9]+")) {
            BusinessUtil.sendErrorResponse(response, "删除失败，联系人ID必须是数字");
            return;
        }

        // 查询联系人
        int id = Integer.parseInt(idStr);
        Contact contact = contactDao.findById(id);

        if (contact == null) {
            BusinessUtil.sendErrorResponse(response, "删除失败，联系人不存在");
            return;
        }

        // 删除联系人
        boolean deleteResult = contactDao.deleteContact(id);
        if (deleteResult) {
            BusinessUtil.sendSuccessResponse(response, "删除成功");
        } else {
            BusinessUtil.sendErrorResponse(response, "删除失败，请稍后重试");
        }
    }

    /**
     * 导出联系人
     */
    private void exportContacts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 查询所有联系人
        List<Contact> contacts = contactDao.listAll();

        if (contacts == null) {
            BusinessUtil.sendErrorResponse(response, "导出失败，无法获取联系人数据");
            return;
        }

        // 设置响应头
        response.setContentType("text/csv;charset=GBK");
        response.setHeader("Content-Disposition", "attachment; filename=联系人列表.csv");

        try (PrintWriter out = response.getWriter();
             BufferedWriter writer = new BufferedWriter(out)) {

            // 写入头部
            writer.write("姓名,电话,邮箱,地址,公司,职位,备注\n");

            // 写入数据
            for (Contact contact : contacts) {
                writer.write(escapeField(contact.getName(), false) + "," +
                        escapeField(contact.getPhone(), false) + "," +
                        escapeField(contact.getEmail(), false) + "," +
                        escapeField(contact.getAddress(), false) + "," +
                        escapeField(contact.getCompany(), false) + "," +
                        escapeField(contact.getPosition(), false) + "," +
                        escapeField(contact.getRemark(), false) + "\n");
            }

            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
            BusinessUtil.sendErrorResponse(response, "导出失败，请稍后重试");
        }
    }

    /**
     * 导入联系人
     */
    private void importContacts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // 获取上传的文件
            Part filePart = request.getPart("file");
            if (filePart == null || filePart.getSize() == 0) {
                BusinessUtil.sendErrorResponse(response, "请选择要导入的文件");
                return;
            }

            // 读取内容
            InputStream inputStream = filePart.getInputStream();
            Scanner scanner = new Scanner(inputStream, "GBK");

            // 读取并跳过头部
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            int importedCount = 0;
            int errorCount = 0;

            // 逐行处理数据
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = parseLine(line);

                if (fields.length >= 6) {
                    String name = fields[0].trim();
                    String phone = fields[1].trim();
                    String email = fields[2].trim();
                    String address = fields[3].trim();
                    String company = fields[4].trim();
                    String position = fields[5].trim();
                    String remark = fields[6].trim();

                    // 验证必要字段
                    if (!name.isEmpty() && !phone.isEmpty()) {
                        Contact contact = new Contact(name, phone, email, address, company, position, remark);
                        boolean result = contactDao.createContact(contact);
                        if (result) {
                            importedCount++;
                        } else {
                            errorCount++;
                        }
                    } else {
                        errorCount++;
                    }
                } else {
                    errorCount++;
                }
            }

            scanner.close();

            String message = String.format("导入完成", importedCount, errorCount);
            BusinessUtil.sendSuccessResponse(response, message);

        } catch (Exception e) {
            e.printStackTrace();
            BusinessUtil.sendErrorResponse(response, "导入失败，请检查格式是否正确");
        }
    }

    /**
     * 转义字段
     */
    private String escapeField(String field, Boolean isNumber) {
        if (field == null) {
            return "";
        }

        // 如果字段包含特殊符号，则用双引号包围，并将双引号转义
        if (field.contains(",") || field.contains("\"") || field.contains("\n") || field.contains("\r")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }

        if (isNumber) {
            return field;
        }

        return "\t" + field;
    }

    /**
     * 解析行，处理转义字段
     */
    private String[] parseLine(String line) {
        java.util.List<String> fields = new java.util.ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                // 检查是否是转义的引号
                if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    currentField.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                fields.add(currentField.toString());
                currentField.setLength(0);
            } else {
                currentField.append(c);
            }
        }

        // 添加最后一个字段
        fields.add(currentField.toString());

        return fields.toArray(new String[0]);
    }
}