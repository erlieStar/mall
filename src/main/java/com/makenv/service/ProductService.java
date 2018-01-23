package com.makenv.service;

import com.github.pagehelper.PageInfo;
import com.makenv.common.ServerResponse;
import com.makenv.pojo.Product;
import com.makenv.vo.ProductDetailVo;

public interface ProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse setSaleStatus(int productId, int status);

    ServerResponse<ProductDetailVo> manageProductDetail(int productId);

    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    ServerResponse<PageInfo> searchProduct(String productName, int productId, int pageNum, int pageSize);

    ServerResponse<ProductDetailVo> getProductDetail(int productId);

    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, int categoryId, int pageNum, int pageSize, String orderBy);
}
