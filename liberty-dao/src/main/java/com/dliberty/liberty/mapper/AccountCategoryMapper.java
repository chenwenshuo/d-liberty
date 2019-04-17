package com.dliberty.liberty.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dliberty.liberty.entity.AccountCategory;

public interface AccountCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountCategory record);

    AccountCategory selectByPrimaryKey(Integer id);

    List<AccountCategory> selectAll();

    int updateByPrimaryKey(AccountCategory record);
    
    List<AccountCategory> selectByUserId(@Param("userId")Integer userId,@Param("cateType")String cateType);
    
    /**
     * 根据类型名称查询
     * @param userId
     * @param cateType
     * @param cateName
     * @return
     */
    int selectByCateName(@Param("userId")Integer userId,@Param("cateType")String cateType,@Param("cateName")String cateName);
}