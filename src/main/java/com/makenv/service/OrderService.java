package com.makenv.service;

import com.github.pagehelper.PageInfo;
import com.makenv.common.ServerResponse;
import com.makenv.vo.OrderVo;

import java.util.Map;

public interface OrderService {

    ServerResponse pay(Long orderNo, Integer userId, String path);
    ServerResponse aliCallback(Map<String, String> params);
    ServerResponse queryOrderPayStatus(Integer userId, Long orderNo);
    ServerResponse createOrder(Integer userId, Integer shippingId);
    ServerResponse cancel(Integer userId, Long orderNO);
    ServerResponse getOrderCartProduct(Integer userId);
    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);
    ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);


    //backend
    ServerResponse<PageInfo> manageList(int pageNum, int pageSize);
    ServerResponse<PageInfo> manageDetail(Long orderNo);
    ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize);
    ServerResponse<String> manageSendGoods(Long orderNo);

}
