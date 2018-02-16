package com.makenv.controller.backend;

import com.github.pagehelper.PageInfo;
import com.makenv.common.Const;
import com.makenv.common.ResponseCode;
import com.makenv.common.ServerResponse;
import com.makenv.pojo.User;
import com.makenv.service.OrderService;
import com.makenv.service.UserService;
import com.makenv.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("manage/order")
public class OrderManageController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    //订单列表,测试通过
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ServerResponse<PageInfo> orderList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆，请登录管理员");
        }
        //检验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()) {
            return orderService.manageList(pageNum, pageSize);
        } else {
            return ServerResponse.errorMsg("无权操作，需要管理员权限");
        }
    }

    //订单详情，测试通过
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public ServerResponse<OrderVo> orderDetail(HttpSession session, Long orderNo) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆，请登录管理员");
        }
        //检验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()) {
            return orderService.manageDetail(orderNo);
        } else {
            return ServerResponse.errorMsg("无权操作，需要管理员权限");
        }
    }


    //按订单号查询，测试通过
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public ServerResponse<PageInfo> orderSearch(HttpSession session, Long orderNo,
                                                @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆，请登录管理员");
        }
        //检验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()) {
            return orderService.manageSearch(orderNo, pageNum, pageSize);
        } else {
            return ServerResponse.errorMsg("无权操作，需要管理员权限");
        }
    }

    //订单发货，状态必须是已经付款，测试通过
    @RequestMapping(value = "send_goods", method = RequestMethod.GET)
    public ServerResponse<String> orderSendGoods(HttpSession session, Long orderNo) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆，请登录管理员");
        }
        //检验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()) {
            return orderService.manageSendGoods(orderNo);
        } else {
            return ServerResponse.errorMsg("无权操作，需要管理员权限");
        }
    }
}
