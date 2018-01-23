package com.makenv.service.impl;

import com.google.common.collect.Sets;
import com.makenv.common.ServerResponse;
import com.makenv.dao.CategoryMapper;
import com.makenv.pojo.Category;
import com.makenv.service.CategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isNotBlank(categoryName))  {
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
        if (categoryId == null || StringUtils.isNotBlank(categoryName))  {
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
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.successData(categoryList);
    }

    /**
     * 找到当前节点的孩子节点以及孩子节点的孩子节点
     */
    @Override
    public ServerResponse<Set<Category>> getAllChildCategory(Integer categoryId) {
        Set<Category> categorySet = Sets.newHashSet();
        findChild(categorySet, categoryId);
        return ServerResponse.successData(categorySet);
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
