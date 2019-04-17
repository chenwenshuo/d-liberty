package com.dliberty.liberty.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dliberty.liberty.entity.AccountRecord;
import com.dliberty.liberty.vo.AccountVo;
import com.dliberty.liberty.vo.JsonBean;
import com.dliberty.liberty.vo.ReportVo;

public interface AccountRecordService {

	/**
	 * 记录
	 * @param userId
	 * @param cateType
	 * @param cateId
	 * @param accountMoney
	 * @param accountTime
	 * @param accountRemarks
	 * @return
	 */
	JsonBean save(Integer userId,Integer recordId,String cateType,Integer cateId,Integer accountMoney,Date accountTime,String accountRemarks,String formId);
	
	/**
	 * 修改
	 * @param record
	 * @return
	 */
	AccountRecord update(AccountRecord record);
	
	/**
	 * 当月消费情况
	 * @param userId
	 * @return
	 */
	AccountVo accountIndex(Integer userId,Integer year,Integer month);
	
	/**
	 * 
	 * @param userId
	 * @param cateType 类别 0 消费 1收入
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	Integer findMoneyByDay(Integer userId,String cateType, Integer year, Integer month, Integer day) ;
	
	/**
	 * 查询每个月的记账日期
	 * @param userId
	 * @param year
	 * @param month
	 * @return
	 */
	List<Date> findAccountDate(Integer userId,Integer year,Integer month);
	
	/**
	 * 根据记录日期查询记录情况
	 * @param userId
	 * @param accountTime
	 * @return
	 */
	List<AccountRecord> findByDate(Integer userId,Date accountTime);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	AccountRecord findById(Integer id);
	
	/**
	 * 删除
	 * @param userId
	 * @param recordId
	 */
	void deleteAccount(Integer userId,Integer recordId);
	
	/**
	 * 统计报表
	 * @param userId
	 * @param year
	 * @param month
	 * @return
	 */
	ReportVo selectReport(Integer userId,Integer year,Integer month,String cateType);
	
	/**
	 * 统计报表
	 * @param userId
	 * @param year
	 * @param month
	 * @return
	 */
	ReportVo selectReport(Integer userId,Date beginTime,Date endTime,String cateType);
	
	/**
	 * 类别详情
	 * @param cateName
	 * @param cateType
	 * @param year
	 * @param month
	 * @return
	 */
	List<AccountRecord> reportList(Integer userId,String cateName,String cateType,Integer year,Integer month) ;
	
	/**
	 * 类别详情
	 * @param cateName
	 * @param cateType
	 * @param year
	 * @param month
	 * @return
	 */
	List<AccountRecord> reportListDate(Integer userId,String cateName,String cateType,Date beginTime,Date endTime) ;
	
	/**
	 * 定时更新过期的formId
	 */
	void syncFormIdExpire();
	
	/**
	 * 查询一条有效的FormId
	 * @param userId
	 * @return
	 */
	AccountRecord findFormId(Integer userId);
	
	/**
	 * 查询最后一条记录
	 * @param userId
	 * @return
	 */
	AccountRecord findLastRecord(@Param("userId")Integer userId);
	
	/**
	 * 
	 * @param userId
	 * @param cateType 类别 0 消费 1收入
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	Integer findMoneyByDate(Integer userId,String cateType, Date beginTime,Date endTime) ;
	
}
