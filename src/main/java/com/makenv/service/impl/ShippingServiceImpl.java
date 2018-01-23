package com.makenv.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.makenv.common.ServerResponse;
import com.makenv.dao.ShippingMapper;
import com.makenv.pojo.Shipping;
import com.makenv.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShippingServiceImpl implements ShippingService{

    @Autowired
    ShippingMapper shippingMapper;

    @Override
    public ServerResponse add(int userId, Shipping shipping) {
        shipping.setUserId(userId);
        int result = shippingMapper.insert(shipping);
        if (result > 0) {
            Map map = Maps.newHashMap();
            map.put("shippingId", shipping.getId());
            return ServerResponse.successMsgData("新建地址成功", result);
        }
        return ServerResponse.errorMsg("新建地址失败");
    }

    @Override
    public ServerResponse del(int userId, int shippingId) {
        int result = shippingMapper.deleteByShippingIdUserId(userId, shippingId);
        if (result > 0) {
            return ServerResponse.successMsg("删除地址成功");
        }
        return ServerResponse.errorMsg("删除地址失败");
    }

    @Override
    public ServerResponse update(int userId, Shipping shipping) {
        shipping.setUserId(userId);
        int result = shippingMapper.updateByShipping(shipping);
        if (result > 0) {
            return ServerResponse.successMsg("更新地址成功");
        }
        return ServerResponse.errorMsg("更新地址失败");
    }

    @Override
    public ServerResponse<Shipping> select(int userId, int shippingId) {
        Shipping shipping = shippingMapper.selectByShippingIdUserId(userId, shippingId);
        if (shipping == null) {
            return ServerResponse.errorMsg("无法查询到该地址");
        }
        return ServerResponse.successMsgData("查询地址成功", shipping);
    }

    @Override
    public ServerResponse<PageInfo> list(int userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.successData(pageInfo);
    }
}
