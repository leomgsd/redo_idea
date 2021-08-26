package com.dao;

import com.domain.Resource;
import com.domain.ResourceCategory;
import com.domain.ResourceVo;

import java.util.List;

public interface ResourceMapper {
    //分页多条件查询资源信息
    public List<Resource> findAllResourceByPage(ResourceVo resourceVo);

    //查询所有资源分类
    public List<ResourceCategory> findResourceCategory();
}
