package com.makenv.controller.backend;

import com.makenv.common.ResponseCode;
import com.makenv.common.ServerResponse;
import com.makenv.pojo.User;
import com.makenv.service.CategoryService;
import com.makenv.service.UserService;
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
@RequestMapping("manage/category")
public class CategoryManageController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    //增加节点，测试通过
    @RequestMapping(value = "add_category", method = RequestMethod.POST)
    public ServerResponse addCategory(HttpServletRequest httpServletRequest, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if (org.apache.commons.lang.StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆");
//        }
//        //检验是否是管理员
//        if (userService.checkAdminRole(user).isSuccess()) {
//            return categoryService.addCategory(categoryName, parentId);
//        } else {
//            return ServerResponse.errorMsg("无权操作，需要管理员权限");
//        }
        //通过拦截器验证，是否登录以及权限
        return categoryService.addCategory(categoryName, parentId);
    }

    //修改品类名字，测试通过
    @RequestMapping(value = "set_category_name", method = RequestMethod.POST)
    public ServerResponse setCategoryName(HttpServletRequest httpServletRequest, Integer categoryId, String categoryName) {
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if (org.apache.commons.lang.StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆");
//        }
//        //检验是否是管理员
//        if (userService.checkAdminRole(user).isSuccess()) {
//            return categoryService.updateCategoryName(categoryId, categoryName);
//        } else {
//            return ServerResponse.errorMsg("无权操作，需要管理员权限");
//        }
        return categoryService.updateCategoryName(categoryId, categoryName);
    }

    //获取平级子节点，测试通过
    @RequestMapping(value = "get_category", method = RequestMethod.GET)
    public ServerResponse getChildCategory(HttpServletRequest httpServletRequest, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if (org.apache.commons.lang.StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆");
//        }
//        //检验是否是管理员
//        if (userService.checkAdminRole(user).isSuccess()) {
//            return categoryService.getChildCategory(categoryId);
//        } else {
//            return ServerResponse.errorMsg("无权操作，需要管理员权限");
//        }
        return categoryService.getChildCategory(categoryId);
    }

    //获取当前分类id及递归子节点categoryId，测试通过
    @RequestMapping(value = "get_deep_category", method = RequestMethod.GET)
    public ServerResponse getAllChildCategory(HttpServletRequest httpServletRequest, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if (org.apache.commons.lang.StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.errorMsg("用户未登录，无法获取用户信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆");
//        }
//        //检验是否是管理员
//        if (userService.checkAdminRole(user).isSuccess()) {
//            return categoryService.getAllChildCategory(categoryId);
//        } else {
//            return ServerResponse.errorMsg("无权操作，需要管理员权限");
//        }
        return categoryService.getAllChildCategory(categoryId);
    }
}
