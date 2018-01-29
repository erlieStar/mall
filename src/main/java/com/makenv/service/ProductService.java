package com.makenv.service;

import com.github.pagehelper.PageInfo;
import com.makenv.common.ServerResponse;
import com.makenv.pojo.Product;
import com.makenv.vo.ProductDetailVo;
import io.swagger.models.auth.In;

public interface ProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse setSaleStatus(Integer productId, Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize);

    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, int categoryId, int pageNum, int pageSize, String orderBy);
}
