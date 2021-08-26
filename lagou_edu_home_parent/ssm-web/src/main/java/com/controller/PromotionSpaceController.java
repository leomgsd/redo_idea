package com.controller;

import com.domain.PromotionSpace;
import com.domain.ResponseResult;
import com.service.PromotionSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/PromotionSpace")
public class PromotionSpaceController {
    @Autowired
    private PromotionSpaceService promotionSpaceService;

    @RequestMapping("/findAllPromotionSpace")
    public ResponseResult findAllPromotionSpace(){
        List<PromotionSpace> list = promotionSpaceService.findAllPromotionSpace();
        ResponseResult responseResult = new ResponseResult(true,200,"查询所有广告位成功",list);
        return responseResult;
    }


    @RequestMapping("/saveOrUpdatePromotionSpace")
    public ResponseResult saveOrUpdatePromotionSpace(@RequestBody PromotionSpace promotionSpace){
        if(promotionSpace.getId() == null){
            promotionSpaceService.savePromotionSpace(promotionSpace);
            ResponseResult responseResult = new ResponseResult(true,200,"新增广告位成功",null);
            return responseResult;
        }else {
            promotionSpaceService.updatePromotionSpace(promotionSpace);
            ResponseResult responseResult = new ResponseResult(true, 200, "修改广告位成功", null);
            return responseResult;
        }

    }


    @RequestMapping("/findPromotionSpaceById")
    public ResponseResult findPromotionSpaceById(int id){
        PromotionSpace promotionSpace = promotionSpaceService.findPromotionSpaceById(id);
        ResponseResult responseResult = new ResponseResult(true, 200, "查询具体广告位成功", promotionSpace);
        return responseResult;
    }

}
