package com.makenv.service;

import com.makenv.common.ServerResponse;

import java.util.Map;

public interface OrderService {

    ServerResponse pay(Long orderNo, Integer userId, String path);
    ServerResponse aliCallback(Map<String, String> params);
    ServerResponse queryOrderPayStatus(Integer userId, Long orderNo);
    ServerResponse createOrder(Integer userId, Integer shippingId);
    ServerResponse cancel(Integer userId, Long orderNO);
}
