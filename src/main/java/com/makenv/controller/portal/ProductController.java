package com.makenv.controller.portal;

import com.github.pagehelper.PageInfo;
import com.makenv.common.ServerResponse;
import com.makenv.service.ProductService;
import com.makenv.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    //产品详情，测试通过
    @RequestMapping("detail")
    public ServerResponse<ProductDetailVo> detail(int productId) {
        return productService.getProductDetail(productId);
    }

    //获取产品，测试通过
    @RequestMapping("list")
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        return productService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize);
    }

}
