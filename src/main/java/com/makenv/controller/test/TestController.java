package com.makenv.controller.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.makenv.common.ServerResponse;
import com.makenv.dao.ProductMapper;
import com.makenv.pojo.Product;
import com.makenv.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author : lilimin
 * @Description : 在本地测试文件上传
 * @Date : Created in 18:36 2018/1/21
 */
@Controller
public class TestController {

    @Autowired
    ProductMapper productMapper;

    @RequestMapping(value = "testupload", method = RequestMethod.GET)
    public String testUpload() {
        return "testload";
    }

    /*
     * 这里用Integer比较合适，可以通过判断是否为null来判断是否传入参数，不至于用int直接报错
     */
    @RequestMapping("testparam")
    @ResponseBody
    public void testParam(Integer productId, Integer status) {
        System.out.println(productId + " " + status);
    }

    @RequestMapping("testparam2")
    @ResponseBody
    public void testParam2(int productId, int status) {
        System.out.println(productId + " " + status);
    }

    /*
     * Mybatis-PageHelper的使用
     */
    @RequestMapping("testhelper")
    @ResponseBody
    public ServerResponse<PageInfo> testhelper(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectList();
        System.out.println("----->" + productList);
        PageInfo pageInfo = new PageInfo(productList);
        return ServerResponse.successData(pageInfo);
    }
}
