package ru.mera.spring.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mera.spring.entity.StudentTest;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest

public class StudentTestRepositoryTest {

    @Autowired
    private StudentTestRepository studentTestRepository;

    @Test
    public void findByStudentId(){

        List<StudentTest> list = studentTestRepository.findByStudentId(1);

        assertNotNull(list);

        list.stream().forEach(studentTest -> assertEquals(0, studentTest.getTestResult()));

        list.stream().forEach(studentTest -> System.out.println(studentTest));

    }

}