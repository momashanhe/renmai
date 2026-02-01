package com.momashanhe.renmai.dao;

import com.momashanhe.renmai.entity.Contact;
import com.momashanhe.renmai.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 联系人数据访问对象
 */
public class ContactDao {

    /**
     * 根据ID查询联系人
     *
     * @param id 联系人ID
     * @return 联系人对象
     */
    public Contact findById(int id) {
        Contact contact = null;
        String sql = "SELECT id, name, phone, email, address, company, position, avatar, remark, create_time, update_time FROM tbl_contact WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            contact = new Contact();

            if (rs.next()) {
                contact.setId(rs.getInt("id"));
                contact.setName(rs.getString("name"));
                contact.setPhone(rs.getString("phone"));
                contact.setEmail(rs.getString("email"));
                contact.setAddress(rs.getString("address"));
                contact.setCompany(rs.getString("company"));
                contact.setPosition(rs.getString("position"));
                contact.setAvatar(rs.getString("avatar"));
                contact.setRemark(rs.getString("remark"));
                contact.setCreateTime(rs.getTimestamp("create_time"));
                contact.setUpdateTime(rs.getTimestamp("update_time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contact;
    }

    /**
     * 查询联系人列表
     *
     * @return 联系人列表
     */
    public List<Contact> listAll() {
        List<Contact> contacts = null;
        String sql = "SELECT id, name, phone, email, address, company, position, avatar, remark, create_time, update_time FROM tbl_contact ORDER BY create_time DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            contacts = new ArrayList<>();

            while (rs.next()) {
                Contact contact = new Contact();
                contact.setId(rs.getInt("id"));
                contact.setName(rs.getString("name"));
                contact.setPhone(rs.getString("phone"));
                contact.setEmail(rs.getString("email"));
                contact.setAddress(rs.getString("address"));
                contact.setCompany(rs.getString("company"));
                contact.setPosition(rs.getString("position"));
                contact.setAvatar(rs.getString("avatar"));
                contact.setRemark(rs.getString("remark"));
                contact.setCreateTime(rs.getTimestamp("create_time"));
                contact.setUpdateTime(rs.getTimestamp("update_time"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    /**
     * 根据用户ID查询联系人列表
     *
     * @param userId 用户ID
     * @return 联系人列表
     */
    public List<Contact> listByUserId(int userId) {
        List<Contact> contacts = null;
        String sql = "SELECT id, name, phone, email, address, company, position, avatar, remark, create_time, update_time FROM tbl_contact WHERE user_id = ? ORDER BY create_time DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            contacts = new ArrayList<>();

            while (rs.next()) {
                Contact contact = new Contact();
                contact.setId(rs.getInt("id"));
                contact.setName(rs.getString("name"));
                contact.setPhone(rs.getString("phone"));
                contact.setEmail(rs.getString("email"));
                contact.setAddress(rs.getString("address"));
                contact.setCompany(rs.getString("company"));
                contact.setPosition(rs.getString("position"));
                contact.setAvatar(rs.getString("avatar"));
                contact.setRemark(rs.getString("remark"));
                contact.setCreateTime(rs.getTimestamp("create_time"));
                contact.setUpdateTime(rs.getTimestamp("update_time"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    /**
     * 根据关键词查询联系人列表
     *
     * @param keyword 关键词
     * @return 联系人列表
     */
    public List<Contact> listByKeyword(String keyword) {
        List<Contact> contacts = null;
        String sql = "SELECT id, name, phone, email, address, company, position, avatar, remark, create_time, update_time FROM tbl_contact " +
                "WHERE name LIKE ? OR phone LIKE ? OR email LIKE ? OR address LIKE ? " +
                "ORDER BY create_time DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";
            ps.setString(1, searchKeyword);
            ps.setString(2, searchKeyword);
            ps.setString(3, searchKeyword);
            ps.setString(4, searchKeyword);

            ResultSet rs = ps.executeQuery();
            contacts = new ArrayList<>();

            while (rs.next()) {
                Contact contact = new Contact();
                contact.setId(rs.getInt("id"));
                contact.setName(rs.getString("name"));
                contact.setPhone(rs.getString("phone"));
                contact.setEmail(rs.getString("email"));
                contact.setAddress(rs.getString("address"));
                contact.setCompany(rs.getString("company"));
                contact.setPosition(rs.getString("position"));
                contact.setAvatar(rs.getString("avatar"));
                contact.setRemark(rs.getString("remark"));
                contact.setCreateTime(rs.getTimestamp("create_time"));
                contact.setUpdateTime(rs.getTimestamp("update_time"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    /**
     * 创建联系人
     *
     * @param contact 联系人对象
     * @return 是否创建成功
     */
    public boolean createContact(Contact contact) {
        String sql = "INSERT INTO tbl_contact (name, phone, email, address, company, position, avatar, remark) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, contact.getName());
            ps.setString(2, contact.getPhone());
            ps.setString(3, contact.getEmail());
            ps.setString(4, contact.getAddress());
            ps.setString(5, contact.getCompany());
            ps.setString(6, contact.getPosition());
            ps.setString(7, contact.getAvatar());
            ps.setString(8, contact.getRemark());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 更新联系人
     *
     * @param contact 联系人对象
     * @return 是否更新成功
     */
    public boolean updateContact(Contact contact) {
        String sql = "UPDATE tbl_contact SET name = ?, phone = ?, email = ?, address = ?, company = ?, position = ?, avatar = ?, remark = ?, update_time = NOW() WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, contact.getName());
            ps.setString(2, contact.getPhone());
            ps.setString(3, contact.getEmail());
            ps.setString(4, contact.getAddress());
            ps.setString(5, contact.getCompany());
            ps.setString(6, contact.getPosition());
            ps.setString(7, contact.getAvatar());
            ps.setString(8, contact.getRemark());
            ps.setInt(9, contact.getId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除联系人
     *
     * @param id 联系人ID
     * @return 是否删除成功
     */
    public boolean deleteContact(int id) {
        String sql = "DELETE FROM tbl_contact WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}