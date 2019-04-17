package com.dliberty.liberty.entity;

import java.util.Date;

public class AccountCategory {
    private Integer id;

    private String cateCode;

    private String cateName;

    private Integer cateUserId;

    private Date createTime;

    private Date updateTime;

    private String isDeleted;

    private String cateType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCateCode() {
        return cateCode;
    }

    public void setCateCode(String cateCode) {
        this.cateCode = cateCode;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public Integer getCateUserId() {
        return cateUserId;
    }

    public void setCateUserId(Integer cateUserId) {
        this.cateUserId = cateUserId;
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

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCateType() {
        return cateType;
    }

    public void setCateType(String cateType) {
        this.cateType = cateType;
    }
}