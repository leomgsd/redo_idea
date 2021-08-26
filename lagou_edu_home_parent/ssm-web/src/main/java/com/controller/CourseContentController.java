package com.controller;

import com.domain.Course;
import com.domain.CourseLesson;
import com.domain.CourseSection;
import com.domain.ResponseResult;
import com.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courseContent")
public class CourseContentController {

    @Autowired
    private CourseContentService courseContentService;

    @RequestMapping("/findSectionAndLesson")
    public ResponseResult findSectionAndLessonByCourseId(int courseId){
        List<CourseSection> list = courseContentService.findSectionAndLessonByCourseId(courseId);
        ResponseResult result = new ResponseResult(true, 200, "章节及课时内容查询成功", list);
        return result;
    }

    @RequestMapping("/findCourseByCourseId")
    public ResponseResult findCourseByCourseId(int courseId){
        Course course = courseContentService.findCourseByCourseId(courseId);
        ResponseResult result = new ResponseResult(true, 200, "查询课程信息成功", course);
        return result;
    }

    @RequestMapping("/saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection courseSection){
        if(courseSection.getId() == null){
            courseContentService.saveSection(courseSection);
            ResponseResult result = new ResponseResult(true,200,"新增章节成功",null);
            return result;
        }else {
            courseContentService.updateSection(courseSection);
            ResponseResult result = new ResponseResult(true,200,"修改章节成功",null);
            return result;
        }
    }

    @RequestMapping("/updateSectionStatus")
    public ResponseResult updateSectionStatus(int id,int status){
        courseContentService.updateSectionStatus(id,status);

        //数据响应
        Map<String,Integer> map = new HashMap<>();
        map.put("status",status);

        ResponseResult result = new ResponseResult(true,200,"修改状态成功",null);
        return result;
    }

    @RequestMapping("/saveLesson")
    public ResponseResult saveLesson(@RequestBody CourseLesson courseLesson){
        courseContentService.saveLesson(courseLesson);
        ResponseResult result = new ResponseResult(true,200,"新增课时成功",null);
        return result;
    }
}
