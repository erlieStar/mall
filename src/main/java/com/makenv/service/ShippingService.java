package com.makenv.service;

import com.github.pagehelper.PageInfo;
import com.makenv.common.ServerResponse;
import com.makenv.pojo.Shipping;

public interface ShippingService {

    ServerResponse add(int userId, Shipping shipping);
    ServerResponse del(int userId, int shippingId);
    ServerResponse update(int userId, Shipping shipping);
    ServerResponse<Shipping> select(int userId, int shippingId);
    ServerResponse<PageInfo> list(int userId, int pageNum, int pageSize);

}
