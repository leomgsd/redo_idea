package com.controller;

import com.domain.PromotionAd;
import com.domain.PromotionAdVO;
import com.domain.ResponseResult;
import com.github.pagehelper.PageInfo;
import com.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/PromotionAd")
public class PromotionAdController {

    @Autowired
    private PromotionAdService promotionAdService;

    @RequestMapping("/findAllPromotionAdByPage")
    public ResponseResult findAllAdByPage(PromotionAdVO promotionAdVO){
        PageInfo<PromotionAd> pageInfo = promotionAdService.finaAllPromotionAdByPage(promotionAdVO);
        ResponseResult responseResult = new ResponseResult(true, 200, "广告分页查询成功", pageInfo);
        return responseResult;
    }

    @RequestMapping("/PromotionAdUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        //1.判断接收到的上传文件是否为空
        if(file.isEmpty()){
            throw  new RuntimeException();
        }

        //2.获取项目部署路径

        // D:\apache-tomcat-8.5.56\webapps\ssm-web\
        String realPath = request.getServletContext().getRealPath("/");
        // D:\apache-tomcat-8.5.56\webapps
        String substring = realPath.substring(0, realPath.indexOf("ssm_web"));



        //3.获取原文件名
        //lagou.jpg
        String originalFilename = file.getOriginalFilename();

        //4.生成新文件名
        //12421321.jpg
        String newFileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));

        //5.文件上传

        String uploadPath = substring + "upload\\";
        File filePath = new File(uploadPath, newFileName);

        // 如果目录不存在就创建目录
        if(!filePath.getParentFile().exists()){
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录：" + filePath);
        }

        // 图片就进行了真正的上传
        file.transferTo(filePath);

        // 6. 将文件名和文件路径返回，进行响应
        Map<String, String> map = new HashMap<>();
        map.put("fileName",newFileName);

        map.put("filePath","http://localhost:8080/upload/" + newFileName);

        ResponseResult responseResult = new ResponseResult(true, 200, "图片上传成功", map);

        return responseResult;

    }

    @RequestMapping("/saveOrUpdatePromotionAd")
    public ResponseResult saveOrUpdatePromotionAd(@RequestBody PromotionAd promotionAd){
        if(promotionAd.getId() == null){
            promotionAdService.savePromotionAd(promotionAd);
            ResponseResult responseResult = new ResponseResult(true, 200, "新增广告成功", null);
            return responseResult;
        }else {
            promotionAdService.updatePromotionAd(promotionAd);
            ResponseResult responseResult = new ResponseResult(true, 200, "修改广告成功", null);
            return responseResult;
        }
    }

    @RequestMapping("/findPromotionAdById")
    public ResponseResult findPromotionAdById(@RequestParam int id){
        PromotionAd promotionAdById = promotionAdService.findPromotionAdById(id);
        ResponseResult responseResult = new ResponseResult(true, 200, "根据id查询广告成功", promotionAdById);
        return  responseResult;
    }

    @RequestMapping("/updatePromotionAdStatus")
    public ResponseResult updateCourseStatus(int id,int status){
        promotionAdService.updatePromotionAdStatus(id,status);
        ResponseResult responseResult = new ResponseResult(true, 200, "广告动态上下线成功", null);
        return responseResult;
    }
}
