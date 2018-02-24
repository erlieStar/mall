package com.makenv.controller.test;

import com.makenv.common.Const;
import com.makenv.common.ResponseCode;
import com.makenv.common.ServerResponse;
import com.makenv.pojo.User;
import com.makenv.service.UserService;
import com.makenv.util.CookieUtil;
import com.makenv.util.JsonUtil;
import com.makenv.util.RedisShardedPoolUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user/springsession")
public class UserSpringSessioinController {

    @Autowired
    private UserService userService;

    //登录，测试通过
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String passwrod, HttpSession session) {
        ServerResponse<User> response = userService.login(username, passwrod);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
//            CookieUtil.writeLoginToken(httpServletResponse, session.getId());
//            RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()), Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        return response;
    }

    //退出登录，测试通过
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ServerResponse<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        CookieUtil.delLoginToken(httpServletRequest, httpServletResponse);
//        RedisShardedPoolUtil.del(loginToken);
        return ServerResponse.success();
    }

    //获取用户信息，测试通过
    @RequestMapping(value = "get_user_info", method = RequestMethod.POST)
    public ServerResponse<String> getUserInfo(HttpSession session) {
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return ServerResponse.successData(user);
        }
        return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
    }

}