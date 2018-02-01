package com.makenv.service;

import com.makenv.common.ServerResponse;
import com.makenv.pojo.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);
    ServerResponse updateCategoryName(Integer categoryId, String categoryName);
    ServerResponse <List<Category>> getChildCategory(Integer categoryId);
    ServerResponse <List<Integer>> getAllChildCategory(Integer categoryId);
}
