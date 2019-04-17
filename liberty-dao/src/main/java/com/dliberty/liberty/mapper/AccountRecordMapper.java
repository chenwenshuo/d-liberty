package com.dliberty.liberty.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dliberty.liberty.entity.AccountRecord;
import com.dliberty.liberty.entity.ext.ReportExtVo;

public interface AccountRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountRecord record);

    AccountRecord selectByPrimaryKey(Integer id);

    List<AccountRecord> selectAll();

    int updateByPrimaryKey(AccountRecord record);
    
    /**
     * 查询用户记录
     * @param userId
     * @param cateType
     * @return
     */
    List<AccountRecord> selectByUserId(@Param("userId")Integer userId,@Param("cateType") String cateType);
    
	/**
	 * 根据条件查询消费金额
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	int findMoneyByDay(@Param("userId")Integer userId,@Param("cateType") String cateType,@Param("year")Integer year,@Param("month")Integer month,@Param("day")Integer day);
	
	/**
	 * 查询每月的记账天数
	 * @param userId
	 * @param year
	 * @param month
	 * @return
	 */
	List<Date> findAccountDate(@Param("userId")Integer userId,@Param("year")Integer year,@Param("month")Integer month);
	
	/**
	 * 根据记录日期查询记录情况
	 * @param userId
	 * @param accountTime
	 * @return
	 */
	List<AccountRecord> findByDate(@Param("userId")Integer userId,@Param("accountTime")Date accountTime);
	
	/**
	 * 统计报表
	 * @param userId
	 * @param cateType
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	List<ReportExtVo> findReportExt(@Param("userId")Integer userId,@Param("cateType") String cateType,@Param("year")Integer year,@Param("month")Integer month,@Param("day")Integer day);
	
	/**
	 * 查询用户金额最高
	 * @param userId
	 * @param year
	 * @param month
	 * @return
	 */
	List<AccountRecord> findAccountMax(@Param("userId")Integer userId,@Param("cateType") String cateType,@Param("num") Integer num,@Param("year")Integer year,@Param("month")Integer month);
	
	/**
	 * 类别详情
	 * @param userId
	 * @param cateName
	 * @param cateType
	 * @param year
	 * @param month
	 * @return
	 */
	List<AccountRecord> reportList(@Param("userId")Integer userId,@Param("cateName")String cateName,@Param("cateType")String cateType,@Param("year")Integer year,@Param("month")Integer month) ;
	
	/**
	 * 类别详情
	 * @param userId
	 * @param cateName
	 * @param cateType
	 * @param year
	 * @param month
	 * @return
	 */
	List<AccountRecord> reportListDate(@Param("userId")Integer userId,@Param("cateName")String cateName,@Param("cateType")String cateType,@Param("beginTime")Date beginTime,@Param("endTime")Date endTime) ;
	/**
	 * 查询所有有效的formId
	 * @return
	 */
	List<AccountRecord> findFormIdExpire();
	
	/**
	 * 查询一条有效的FormId
	 * @param userId
	 * @return
	 */
	AccountRecord findFormId(@Param("userId")Integer userId);
	
	/**
	 * 查询最后一条记录
	 * @param userId
	 * @return
	 */
	AccountRecord findLastRecord(@Param("userId")Integer userId);
	
	/**
	 * 根据条件查询消费金额
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	int findMoneyByDate(@Param("userId")Integer userId,@Param("cateType") String cateType,@Param("beginTime") Date beginTime,@Param("endTime") Date endTime);
	
	/**
	 * 统计报表
	 * @param userId
	 * @param cateType
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	List<ReportExtVo> findReportExtByDate(@Param("userId")Integer userId,@Param("cateType") String cateType,@Param("beginTime") Date beginTime,@Param("endTime") Date endTime);
	
}