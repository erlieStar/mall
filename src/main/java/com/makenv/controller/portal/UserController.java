package com.makenv.controller.portal;

import com.makenv.common.Const;
import com.makenv.common.ResponseCode;
import com.makenv.common.ServerResponse;
import com.makenv.pojo.User;
import com.makenv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //登录，测试通过
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String passwrod, HttpSession session) {
        ServerResponse<User> response = userService.login(username, passwrod);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    //退出登录，测试通过
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ServerResponse<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.success();
    }

    //注册，只需要传入username,password,email,phone,question,answer字段即可,测试通过
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ServerResponse<String> register(User user) {
        return userService.register(user);
    }

    //检查用户名和密码是否存在,type为username时测试用户名，type为email时测试email，测试通过
    @RequestMapping(value = "check_vaild", method = RequestMethod.POST)
    public ServerResponse<String> checkVaild(String str, String type) {
        return userService.checkValid(str, type);
    }

    //获取用户信息，测试通过
    @RequestMapping(value = "get_user_info", method = RequestMethod.POST)
    public ServerResponse<String> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return ServerResponse.successData(user);
        }
        return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
    }

    //获取问题，测试通过
    @RequestMapping(value = "forget_get_question", method = RequestMethod.POST)
    public ServerResponse<String> forgetGetQuestion(String username) {
        return userService.selectQuestion(username);
    }

    //提交问题答案，返回修改密码用的token，测试通过
    @RequestMapping(value = "forget_check_answer", method = RequestMethod.POST)
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return userService.checkAnswer(username, question, answer);
    }

    //忘记密码的重设密码，测试通过
    @RequestMapping(value = "forget_reset_password", method = RequestMethod.POST)
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        return userService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    //登录状态中的重置密码，测试通过
    @RequestMapping(value = "reset_password", method = RequestMethod.POST)
    public ServerResponse<String> resetPassword(HttpSession session, String passwordOld, String passwordNew) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.errorMsg("用户未登录");
        }
        return userService.resetPassword(passwordOld, passwordNew, user);
    }

    //登录状态更新个人信息，只需传入email,phone,question,answer,测试通过
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

    //获取当前登录用户的详细信息，并强制登录（当status等于10时，前端页面自动跳转到登录页面），测试通过
    @RequestMapping(value = "get_information", method = RequestMethod.POST)
    public ServerResponse<User> getInformation(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,需要强制登录");
        }
        return userService.getInformation(user.getId());
    }
}