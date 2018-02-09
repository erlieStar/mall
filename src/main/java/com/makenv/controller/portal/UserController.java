package com.makenv.controller.portal;

import com.makenv.common.Const;
import com.makenv.common.ResponseCode;
import com.makenv.common.ServerResponse;
import com.makenv.dao.UserMapper;
import com.makenv.pojo.User;
import com.makenv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String passwrod, HttpSession session) {
        ServerResponse<User> response = userService.login(username, passwrod);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ServerResponse<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.success();
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ServerResponse<String> register(User user) {
        return userService.register(user);
    }

    @RequestMapping(value = "check_vaild", method = RequestMethod.POST)
    public ServerResponse<String> checkVaild(String str, String type) {
        return userService.checkValid(str, type);
    }

    @RequestMapping(value = "get_user_info", method = RequestMethod.POST)
    public ServerResponse<String> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return ServerResponse.successData(user);
        }
        return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
    }

    @RequestMapping(value = "forget_get_question", method = RequestMethod.POST)
    public ServerResponse<String> forgetGetQuestion(String username) {
        return userService.selectQuestion(username);
    }

    @RequestMapping(value = "forget_check_answer", method = RequestMethod.POST)
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return userService.checkAnswer(username, question, answer);
    }

    @RequestMapping(value = "forget_reset_password", method = RequestMethod.POST)
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        return userService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    @RequestMapping(value = "reset_password", method = RequestMethod.POST)
    public ServerResponse<String> resetPassword(HttpSession session, String passwordOld, String passwordNew) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.errorMsg("用户未登录");
        }
        return userService.resetPassword(passwordOld, passwordNew, user);
    }


    @RequestMapping(value = "update_information", method = RequestMethod.POST)
    public ServerResponse<User> updateInformation(HttpSession session, User user) {
        //防止横向越权，用session中的userId
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.errorMsg("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = userService.updateInformation(user);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @RequestMapping(value = "get_information", method = RequestMethod.POST)
    public ServerResponse<User> getInformation(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        return userService.getInformation(user.getId());
    }
}