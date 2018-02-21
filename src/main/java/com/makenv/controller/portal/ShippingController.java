package com.makenv.controller.portal;

import com.github.pagehelper.PageInfo;
import com.makenv.common.ResponseCode;
import com.makenv.common.ServerResponse;
import com.makenv.pojo.Shipping;
import com.makenv.pojo.User;
import com.makenv.service.ShippingService;
import com.makenv.util.CookieUtil;
import com.makenv.util.JsonUtil;
import com.makenv.util.RedisShardedPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("shipping")
public class ShippingController {

    @Autowired
    ShippingService shippingService;

    //添加地址，测试通过
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ServerResponse add(HttpServletRequest httpServletRequest, Shipping shipping) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (org.apache.commons.lang.StringUtils.isEmpty(loginToken)) {
            return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.add(user.getId(), shipping);
    }

    //删除地址，测试通过
    @RequestMapping(value = "del", method = RequestMethod.POST)
    public ServerResponse del(HttpServletRequest httpServletRequest, int shippingId) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (org.apache.commons.lang.StringUtils.isEmpty(loginToken)) {
            return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.del(user.getId(), shippingId);
    }

    //登录状态更新地址，测试通过
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ServerResponse update(HttpServletRequest httpServletRequest, Shipping shipping) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (org.apache.commons.lang.StringUtils.isEmpty(loginToken)) {
            return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.update(user.getId(), shipping);
    }

    //选中查看具体地址，测试通过
    @RequestMapping(value = "select", method = RequestMethod.GET)
    public ServerResponse select(HttpServletRequest httpServletRequest, int shippingId) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (org.apache.commons.lang.StringUtils.isEmpty(loginToken)) {
            return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.select(user.getId(), shippingId);
    }

    //地址列表，测试通过
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         HttpServletRequest httpServletRequest) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (org.apache.commons.lang.StringUtils.isEmpty(loginToken)) {
            return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.list(user.getId(), pageNum, pageSize);
    }
}
