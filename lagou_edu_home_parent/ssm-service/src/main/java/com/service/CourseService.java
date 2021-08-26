package com.service;

import com.domain.Course;
import com.domain.CourseVO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface CourseService {
    public List<Course> findCourseByCondition(CourseVO courseVO);

    public void saveCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException;

    public CourseVO findCourseById(int id);

    public void updateCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException;

    public void updateCourseStatus(int id,int status);
}
