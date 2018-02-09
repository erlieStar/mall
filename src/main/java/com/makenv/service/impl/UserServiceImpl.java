package com.makenv.service.impl;

import com.makenv.common.Const;
import com.makenv.common.ServerResponse;
import com.makenv.common.TokenCache;
import com.makenv.dao.UserMapper;
import com.makenv.pojo.User;
import com.makenv.service.UserService;
import com.makenv.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int result = userMapper.checkUsername(username);
        if (result == 0) {
            return ServerResponse.errorMsg("用户名不存在");
        }

        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            return ServerResponse.errorMsg("密码错误");
        }
        user.setPassword("");
        return ServerResponse.successMsgData("登陆成功", user);
    }

    @Override
    public ServerResponse<String> register(User user) {
        ServerResponse validResponse = checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int result = userMapper.insert(user);
        if (result == 0) {
            return ServerResponse.errorMsg("注册失败");
        }
        return ServerResponse.successMsg("注册成功");
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNoneBlank(type)) {
            if (Const.USERNAME.equals(type)) {
                int result = userMapper.checkUsername(str);
                if (result > 0) {
                    return ServerResponse.errorMsg("用户名已经存在");
                }
            } else if (Const.EMAIL.equals(type)) {
                int result = userMapper.checkEmail(str);
                if (result > 0) {
                    return ServerResponse.errorMsg("email已经存在");
                }
            }
        } else {
            return ServerResponse.errorMsg("参数错误");
        }
        return ServerResponse.successMsg("校验成功");
    }

    @Override
    public ServerResponse<String> selectQuestion(String username) {
        ServerResponse serverResponse = checkValid(username, Const.USERNAME);
        if (serverResponse.isSuccess()) {
            //用户不存在
            return ServerResponse.errorMsg("用户不存在");
        }
        String question = userMapper.selectQuesByUsername(username);
        //不是空白字符返回ture（空白字符包括：空格，tab键，换行符）
        if (StringUtils.isNotBlank(question)) {
            return ServerResponse.successData(question);
        }
        return ServerResponse.errorMsg("找回的密码是空的");
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int result = userMapper.checkAnswer(username, question, answer);
        if (result > 0) {
            String token = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, token);
            return ServerResponse.successData(token);
        }
        return ServerResponse.errorMsg("问题回答错误");
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.errorMsg("参数错误，token需要传递");
        }
        ServerResponse validResponse = checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            return ServerResponse.errorMsg("用户不存在");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)) {
            return ServerResponse.errorMsg("token无效或者过期");
        }
        if (StringUtils.equals(forgetToken, token)) {
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int result = userMapper.updataPassByUsername(username, md5Password);
            if (result > 0) {
                return ServerResponse.errorMsg("修改密码成功");
            }
        } else {
            return ServerResponse.errorMsg("token错误，请重新获取重置密码的token");
        }
        return ServerResponse.errorMsg("修改密码失败");
    }

    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
        int result = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
        if (result == 0) {
            return ServerResponse.errorMsg("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        result = userMapper.updateByPrimaryKeySelective(user);
        if (result > 0) {
            return ServerResponse.successMsg("密码更新成功");
        }
        return ServerResponse.errorMsg("密码更新失败");
    }

    @Override
    public ServerResponse<User> updateInformation(User user) {
        //username不能进行修改
        //email可以修改，如果email已经存在，则不能是当前用户的
        //注册的时候邮箱不能重复，但可以改成重复的邮箱
        int result = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
        if (result > 0) {
            return ServerResponse.errorMsg("email已经存在");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        result = userMapper.updateByPrimaryKeySelective(user);
        if (result > 0) {
            return ServerResponse.successMsgData("更改成功", updateUser);
        }
        return ServerResponse.errorMsg("更改失败");
    }

    @Override
    public ServerResponse<User> getInformation(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return ServerResponse.errorMsg("找不到当前用户");
        }
        user.setPassword("");
        return ServerResponse.successData(user);
    }

    @Override
    public ServerResponse checkAdminRole(User user) {
        if (user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN) {
            return ServerResponse.success();
        }
        return ServerResponse.error();
    }
}
