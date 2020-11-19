package com.carleton.comp5104.cms.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Assert;

@SpringBootTest
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Test
    public void testRegisterCourse() {
        boolean registerCourse = courseService.registerCourse(123, 1);
        Assert.assertSame(false, registerCourse);
    }

    @Test
    public void testDropCourse() {
        boolean registerCourse = courseService.dropCourse(123, 1);
        Assert.assertSame(false, registerCourse);
    }
}
