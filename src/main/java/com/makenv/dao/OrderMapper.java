package com.makenv.dao;

import com.makenv.pojo.Order;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByUserIdAndOrderNo(@Param("userId") int userId, @Param("orderNo") long orderNo);

    Order selectByOrderNo(long orderNo);

    List<Order> selectByUserId(int userId);

    List<Order> selectAllOrder();

    //二期新增定时关单
    List<Order> selectOrderStatusByCreateTime(@Param("status") Integer status, @Param("date") String date);

    int closeOrderByOrderId(Integer id);
}