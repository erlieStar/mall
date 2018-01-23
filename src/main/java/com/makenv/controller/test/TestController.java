package com.makenv.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author : lilimin
 * @Description : 在本地测试文件上传
 * @Date : Created in 18:36 2018/1/21
 */
@Controller
public class TestController {

    @RequestMapping(value = "testupload", method = RequestMethod.GET)
    public String testUpload() {
        return "testload";
    }
}
