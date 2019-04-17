package com.dliberty.liberty.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Discount {
    private Integer id;

    private String sourceCode;

    private Integer discountType;

    private String discountId;

    private String discountCode;

    private Integer discountMoney;

    private Integer expire;

    private Date beginTime;

    private Date endTime;

    private Integer status;

    private String descRemark;

    private String discountRangeDesc;

    private String useUrl;

    private Integer discountUseType;

    private Integer distributionStatus;

    private BigDecimal percentOff;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Integer getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Integer discountType) {
        this.discountType = discountType;
    }

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public Integer getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Integer discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(Integer expire) {
        this.expire = expire;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescRemark() {
        return descRemark;
    }

    public void setDescRemark(String descRemark) {
        this.descRemark = descRemark;
    }

    public String getDiscountRangeDesc() {
        return discountRangeDesc;
    }

    public void setDiscountRangeDesc(String discountRangeDesc) {
        this.discountRangeDesc = discountRangeDesc;
    }

    public String getUseUrl() {
        return useUrl;
    }

    public void setUseUrl(String useUrl) {
        this.useUrl = useUrl;
    }

    public Integer getDiscountUseType() {
        return discountUseType;
    }

    public void setDiscountUseType(Integer discountUseType) {
        this.discountUseType = discountUseType;
    }

    public Integer getDistributionStatus() {
        return distributionStatus;
    }

    public void setDistributionStatus(Integer distributionStatus) {
        this.distributionStatus = distributionStatus;
    }

    public BigDecimal getPercentOff() {
        return percentOff;
    }

    public void setPercentOff(BigDecimal percentOff) {
        this.percentOff = percentOff;
    }
}