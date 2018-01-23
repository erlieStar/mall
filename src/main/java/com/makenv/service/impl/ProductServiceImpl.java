package com.makenv.service.impl;

import com.github.pagehelper.PageInfo;
import com.makenv.common.ServerResponse;
import com.makenv.dao.CategoryMapper;
import com.makenv.dao.ProductMapper;
import com.makenv.pojo.Product;
import com.makenv.service.CategoryService;
import com.makenv.service.ProductService;
import com.makenv.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;

    @Override
    public ServerResponse saveOrUpdateProduct(Product product) {
        return null;
    }

    @Override
    public ServerResponse setSaleStatus(int productId, int status) {
        return null;
    }

    @Override
    public ServerResponse<ProductDetailVo> manageProductDetail(int productId) {
        return null;
    }

    @Override
    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public ServerResponse<PageInfo> searchProduct(String productName, int productId, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(int productId) {
        return null;
    }

    @Override
    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, int categoryId, int pageNum, int pageSize, String orderBy) {
        return null;
    }
}
