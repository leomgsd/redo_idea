package com.service;

import com.domain.Course;
import com.domain.CourseLesson;
import com.domain.CourseSection;

import java.util.List;

public interface CourseContentService {
    public List<CourseSection> findSectionAndLessonByCourseId(int id);

    public Course findCourseByCourseId(int courseId);

    public void saveSection(CourseSection courseSection);

    public void updateSection(CourseSection courseSection);

    public void updateSectionStatus(int id,int status);

    public void saveLesson(CourseLesson courseLesson);
}
