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
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("detail")
    public ServerResponse<ProductDetailVo> detail(int productId) {
        return productService.getProductDetail(productId);
    }

    @RequestMapping("list")
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "categoryId", required = false) int categoryId,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy", defaultValue = "") String orderBy) {

        return productService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }

}
