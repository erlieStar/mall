package com.makenv.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.makenv.common.ServerResponse;
import com.makenv.dao.CategoryMapper;
import com.makenv.pojo.Category;
import com.makenv.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName))  {
            return ServerResponse.errorMsg("添加品类参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);

        int result = categoryMapper.insert(category);
        if (result > 0) {
            return ServerResponse.successMsg("添加品类成功");
        }
        return ServerResponse.errorMsg("添加品类失败");
    }

    @Override
    public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {
        if (categoryId == null || StringUtils.isBlank(categoryName))  {
            return ServerResponse.errorMsg("更新品类参数错误");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int result = categoryMapper.updateByPrimaryKeySelective(category);
        if (result > 0) {
            return ServerResponse.successMsg("更新品类成功");
        }
        return ServerResponse.errorMsg("更新品类失败");
    }

    /**
     * 找到当前节点的孩子节点
     */
    @Override
    public ServerResponse<List<Category>> getChildCategory(Integer categoryId) {
        List<Category> categoryList = categoryMapper.selectChildByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            log.info("未找到当前分类的子分类");
        }
        return ServerResponse.successData(categoryList);
    }

    /**
     * 找到当前节点的孩子节点以及孩子节点的孩子节点
     */
    @Override
    public ServerResponse<List<Integer>> getAllChildCategory(Integer categoryId) {

        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null) {
            Set<Category> categorySet = Sets.newHashSet();
            findChild(categorySet, categoryId);
            for (Category categoryItem : categorySet) {
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.successData(categoryIdList);
    }

    private Set<Category> findChild(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }

        List<Category> categoryList = categoryMapper.selectChildByParentId(categoryId);
        for (Category tempCategory : categoryList) {
            findChild(categorySet, tempCategory.getId());
        }
        return categorySet;
    }
}
