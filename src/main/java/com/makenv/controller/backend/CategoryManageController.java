package com.makenv.controller.backend;

import com.makenv.common.Const;
import com.makenv.common.ResponseCode;
import com.makenv.common.ServerResponse;
import com.makenv.pojo.User;
import com.makenv.service.CategoryService;
import com.makenv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("manage/category")
public class CategoryManageController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    //增加节点，测试通过
    @RequestMapping(value = "add_category", method = RequestMethod.POST)
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆");
        }
        //检验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()) {
            return categoryService.addCategory(categoryName, parentId);
        } else {
            return ServerResponse.errorMsg("无权操作，需要管理员权限");
        }
    }

    //修改品类名字，测试通过
    @RequestMapping(value = "set_category_name", method = RequestMethod.POST)
    public ServerResponse setCategoryName(HttpSession session, Integer categoryId, String categoryName) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆");
        }
        //检验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()) {
            return categoryService.updateCategoryName(categoryId, categoryName);
        } else {
            return ServerResponse.errorMsg("无权操作，需要管理员权限");
        }
    }

    //获取平级子节点，测试通过
    @RequestMapping(value = "get_category", method = RequestMethod.GET)
    public ServerResponse getChildCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆");
        }
        //检验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()) {
            return categoryService.getChildCategory(categoryId);
        } else {
            return ServerResponse.errorMsg("无权操作，需要管理员权限");
        }
    }

    //获取当前分类id及递归子节点categoryId，测试通过
    @RequestMapping(value = "get_deep_category", method = RequestMethod.GET)
    public ServerResponse getAllChildCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.errorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆");
        }
        //检验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()) {
            return categoryService.getAllChildCategory(categoryId);
        } else {
            return ServerResponse.errorMsg("无权操作，需要管理员权限");
        }
    }
}
