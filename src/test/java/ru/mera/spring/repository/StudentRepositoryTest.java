package ru.mera.spring.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mera.spring.entity.Student;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @org.junit.Test
    public void findByEmail() throws Exception {
//        Student student = studentRepository.findByEmail("ivanov");
//
//        assertNotNull(student);
//
//        System.out.println(student);
//
//         assertEquals("ivanov", student.getName());
    }

}