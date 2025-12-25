package com.momashanhe.renmai.entity;

import java.util.Date;

/**
 * 联系人实体类
 */
public class Contact {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String company;
    private String position;
    private String avatar;
    private String remark;
    private Date createTime;
    private Date updateTime;

    // 构造函数
    public Contact() {}

    public Contact(String name, String phone, String email, String address, String company, String position, String avatar, String remark) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.company = company;
        this.position = position;
        this.avatar = avatar;
        this.remark = remark;
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    public Contact(String name, String phone, String email, String address, String company, String position, String remark) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.company = company;
        this.position = position;
        this.remark = remark;
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    // Getter和Setter方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}