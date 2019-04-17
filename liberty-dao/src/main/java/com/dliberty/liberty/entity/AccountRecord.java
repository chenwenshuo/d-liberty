package com.dliberty.liberty.entity;

import java.util.Date;

public class AccountRecord {
    private Integer id;

    private Integer cateId;

    private String cateType;

    private String cateName;
    
    private Integer userId;

    private Integer accountMoney;

    private Date accountTime;

    private String accountRemarks;

    private Date createTime;

    private Date updateTime;
    
    private String deleted;
    
    private String formId;
    
    private String formIdExpire;

    private String firstName;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public String getCateType() {
        return cateType;
    }

    public void setCateType(String cateType) {
        this.cateType = cateType;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(Integer accountMoney) {
        this.accountMoney = accountMoney;
    }

    public Date getAccountTime() {
        return accountTime;
    }

    public void setAccountTime(Date accountTime) {
        this.accountTime = accountTime;
    }

    public String getAccountRemarks() {
        return accountRemarks;
    }

    public void setAccountRemarks(String accountRemarks) {
        this.accountRemarks = accountRemarks;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getFormIdExpire() {
		return formIdExpire;
	}

	public void setFormIdExpire(String formIdExpire) {
		this.formIdExpire = formIdExpire;
	}

	
    
	
    
}