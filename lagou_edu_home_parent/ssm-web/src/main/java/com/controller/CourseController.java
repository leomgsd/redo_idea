package com.controller;

import com.domain.Course;
import com.domain.CourseVO;
import com.domain.ResponseResult;
import com.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping("/findCourseByCondition")
    //@RequestBody 获取请求体中的内容进行封装
    public ResponseResult findCourseByCondition(@RequestBody CourseVO courseVO){
        List<Course> list = courseService.findCourseByCondition(courseVO);
        ResponseResult responseResult =new ResponseResult(true,200,"响应成功",list);
        return responseResult;
    }


    @RequestMapping("/courseUpload")
    public ResponseResult fileUpload(@RequestParam("file")MultipartFile file, HttpServletRequest request) throws IOException {

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

    @RequestMapping("/saveOrUpdateCourse")
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {
        if(courseVO.getId() == null){
            courseService.saveCourseOrTeacher(courseVO);
            ResponseResult responseResult = new ResponseResult(true,200,"新增成功",null);
            return responseResult;
        }else {
            courseService.updateCourseOrTeacher(courseVO);
            ResponseResult result = new ResponseResult(true,200,"修改成功",null);
            return result;
        }

    }

    @RequestMapping("/findCourseById")
    public ResponseResult findCourseById(int id){
        CourseVO courseVO = courseService.findCourseById(id);
        ResponseResult result = new ResponseResult(true,200,"根据id查询响应信息成功",courseVO);
        return result;
    }

    @RequestMapping("/updateCourseStatus")
    public ResponseResult updateCourseStatus(int id,int status){
        courseService.updateCourseStatus(id,status);
        //响应前端信息
        Map<String,Integer> map=new HashMap<>();
        map.put("status",status);
        ResponseResult result = new ResponseResult(true, 200, "课程状态变更成功", map);
        return result;
    }


}
