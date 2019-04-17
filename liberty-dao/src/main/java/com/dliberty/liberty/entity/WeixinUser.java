package com.dliberty.liberty.entity;

import java.util.Date;

public class WeixinUser {
    private Integer id;

    private String userName;

    private String headImg;

    private String openId;

    private String unionId;

    private Date createTime;

    private Date updateTime;
    
    private String email;
    
    private Integer remindHour;
    
    private Integer remindMinute;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRemindHour() {
		return remindHour;
	}

	public void setRemindHour(Integer remindHour) {
		this.remindHour = remindHour;
	}

	public Integer getRemindMinute() {
		return remindMinute;
	}

	public void setRemindMinute(Integer remindMinute) {
		this.remindMinute = remindMinute;
	}
    
    
}