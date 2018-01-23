package com.makenv.service;

import com.makenv.common.ServerResponse;

import java.util.Map;

public interface OrderService {

    ServerResponse pay(long orderNo, int userId, String path);
    ServerResponse aliCallback(Map<String, String> params);
    ServerResponse queryOrderPayStatus(int userId, long orderNo);
    ServerResponse createOrder(int userId, int shippingId);
    ServerResponse cancel(int userId, int orderNO);
}
