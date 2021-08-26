package com.service;

import com.domain.Resource;
import com.domain.ResourceCategory;
import com.domain.ResourceVo;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface ResourceService {
    public PageInfo<Resource> findAllResourceByPage(ResourceVo resourceVo);

    public List<ResourceCategory> findResourceCategory();
}
