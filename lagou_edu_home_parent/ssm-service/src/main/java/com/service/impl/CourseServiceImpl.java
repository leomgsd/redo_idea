package com.service.impl;

import com.dao.CourseMapper;
import com.domain.Course;
import com.domain.CourseVO;
import com.domain.Teacher;
import com.service.CourseService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> findCourseByCondition(CourseVO courseVO) {
        List<Course> courseList = courseMapper.findCourseByCondition(courseVO);
        return courseList;
    }

    @Override
    public void saveCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {
        //封装课程信息
        Course course = new Course();
        //虚假的，为了将courseVO属性封装到course
        courseVO.setId(1);

        //将CourseVO对应的属性封装到Course
        BeanUtils.copyProperties(course,courseVO);

        //补全课程信息
        Date date = new Date();
        course.setCreateTime(date);
        course.setUpdateTime(date);

        //保存课程
        courseMapper.saveCourse(course);

        //获取新插入数据的id值
        int id = course.getId();

        //封装讲师信息
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacher,courseVO);

        //补全讲师信息
        teacher.setCreateTime(date);
        teacher.setUpdateTime(date);
        teacher.setIsDel(0);
        teacher.setCourseId(id);

        //保存讲师信息
        courseMapper.saveTeacher(teacher);

    }

    @Override
    public CourseVO findCourseById(int id) {
        CourseVO courseVO = courseMapper.findCourseById(id);
        return courseVO;
    }

    @Override
    public void updateCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {
        Course course = new Course();

        BeanUtils.copyProperties(course,courseVO);

        Date date = new Date();
        course.setUpdateTime(date);

        courseMapper.updateCourse(course);

        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacher,courseVO);

        teacher.setUpdateTime(date);
        teacher.setCourseId(course.getId());

        courseMapper.updateTeacher(teacher);
    }

    @Override
    public void updateCourseStatus(int id, int status) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(status);
        course.setUpdateTime(new Date());

        courseMapper.updateCourseStatus(course);
    }


}
