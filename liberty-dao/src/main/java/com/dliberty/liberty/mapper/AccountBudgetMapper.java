package com.dliberty.liberty.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dliberty.liberty.entity.AccountBudget;

public interface AccountBudgetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountBudget record);

    AccountBudget selectByPrimaryKey(Integer id);

    List<AccountBudget> selectAll();

    int updateByPrimaryKey(AccountBudget record);
    
    /**
     * 查询预算
     * @param year
     * @param month
     * @param userId
     * @return
     */
    AccountBudget selectBudget(@Param("year")Integer year,@Param("month") Integer month, @Param("userId")Integer userId) ;
}