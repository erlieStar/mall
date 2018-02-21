package com.makenv.controller.portal;

import com.makenv.common.Const;
import com.makenv.common.ResponseCode;
import com.makenv.common.ServerResponse;
import com.makenv.pojo.User;
import com.makenv.service.CartService;
import com.makenv.util.CookieUtil;
import com.makenv.util.JsonUtil;
import com.makenv.util.RedisShardedPoolUtil;
import com.makenv.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    CartService cartService;

    //购物车列表,测试通过
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ServerResponse<CartVo> list(HttpServletRequest httpServletRequest) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (org.apache.commons.lang.StringUtils.isEmpty(loginToken)) {
            return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.list(user.getId());
    }

    //购物车添加商品,测试通过
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ServerResponse<CartVo> add(HttpServletRequest httpServletRequest, Integer productId, Integer count) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (org.apache.commons.lang.StringUtils.isEmpty(loginToken)) {
            return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.add(user.getId(), productId, count);
    }

    //更新购物车商品，测试通过
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ServerResponse<CartVo> update(HttpServletRequest httpServletRequest, Integer productId, Integer count) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (org.apache.commons.lang.StringUtils.isEmpty(loginToken)) {
            return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.update(user.getId(), productId, count);
    }

    //删除商品，测试通过
    @RequestMapping(value = "delete_product", method = RequestMethod.POST)
    public ServerResponse<CartVo> deleteProduct(HttpServletRequest httpServletRequest, String productIds) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (org.apache.commons.lang.StringUtils.isEmpty(loginToken)) {
            return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.deleteProduct(user.getId(), productIds);
    }

    //全选,测试通过
    @RequestMapping(value = "select_all", method = RequestMethod.POST)
    public ServerResponse<CartVo> selectAll(HttpServletRequest httpServletRequest) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (org.apache.commons.lang.StringUtils.isEmpty(loginToken)) {
            return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelect(user.getId(), null, Const.Cart.CHECKED);
    }

    //取消全选,测试通过
    @RequestMapping(value = "un_select_all", method = RequestMethod.POST)
    public ServerResponse<CartVo> unSelectAll(HttpServletRequest httpServletRequest) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (org.apache.commons.lang.StringUtils.isEmpty(loginToken)) {
            return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelect(user.getId(), null, Const.Cart.UN_CHECKED);
    }

    //选中商品,测试通过
    @RequestMapping(value = "select", method = RequestMethod.POST)
    public ServerResponse<CartVo> select(HttpServletRequest httpServletRequest, Integer productId) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (org.apache.commons.lang.StringUtils.isEmpty(loginToken)) {
            return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelect(user.getId(), productId, Const.Cart.CHECKED);
    }

    //取消选中商品,测试通过
    @RequestMapping(value = "un_select", method = RequestMethod.POST)
    public ServerResponse<CartVo> unSelect(HttpServletRequest httpServletRequest, Integer productId) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (org.apache.commons.lang.StringUtils.isEmpty(loginToken)) {
            return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelect(user.getId(), productId, Const.Cart.UN_CHECKED);
    }

    //获取购物车中商品的总数量，测试通过
    @RequestMapping(value = "get_cart_product_count", method = RequestMethod.GET)
    public ServerResponse<Integer> getCartProductCount(HttpServletRequest httpServletRequest) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (org.apache.commons.lang.StringUtils.isEmpty(loginToken)) {
            return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            //没有登录，返回0
            return ServerResponse.successData(0);
        }
        return cartService.getCartProductCount(user.getId());
    }
}
