package com.yuan.study;

import com.yuan.study.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudyApplicationTests {

    @Autowired
    TestService testService;

    @Test
    void contextLoads() {
        System.out.println(testService.list());
    }

}
