package com.controller;

import com.domain.Resource;
import com.domain.ResourceCategory;
import com.domain.ResourceVo;
import com.domain.ResponseResult;
import com.github.pagehelper.PageInfo;
import com.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping("/findAllResource")
    public ResponseResult findAllResourceByPage(@RequestBody ResourceVo resourceVo){
        PageInfo<Resource> pageInfo = resourceService.findAllResourceByPage(resourceVo);
        ResponseResult responseResult = new ResponseResult(true, 200, "资源信息分页多条件查询成功", pageInfo);
        return responseResult;
    }


    @RequestMapping("/findResourceCategory")
    public ResponseResult findResourceCategory(){
        List<ResourceCategory> categoryList = resourceService.findResourceCategory();
        ResponseResult responseResult = new ResponseResult(true, 200, "查询资源分类信息成功", categoryList);
        return responseResult;
    }
}
