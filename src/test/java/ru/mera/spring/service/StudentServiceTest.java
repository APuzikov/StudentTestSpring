package ru.mera.spring.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mera.spring.entity.Student;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void createStudent() throws Exception {
        Student student = new Student();
        student.setName("Ivanov");
        student.setEmail("ivanov@mail.ru");
        student.setPassword("qwer1!");
        studentService.createStudent(student);
    }

    @Test(expected = Exception.class)
    public void createStudentNull(){
        Student student = null;
        studentService.createStudent(student);
    }

    @Test(expected = Exception.class)
    public void createStudentNoName(){
        Student student = new Student();
        studentService.createStudent(student);
    }

    @Test(expected = Exception.class)
    public void createStudentNoEmail(){
        Student student = new Student();
        student.setName("Ivanov");
        studentService.createStudent(student);
    }

    @Test(expected = Exception.class)
    public void createStudentNoPassword(){
        Student student = new Student();
        student.setName("Ivanov");
        student.setEmail("ivanov@mail.ru");;
        studentService.createStudent(student);
    }

    @Test(expected = Exception.class)
    public void createStudentWrongName(){
        Student student = new Student();
        student.setName("Ivanov1");
        studentService.createStudent(student);
    }

    @Test(expected = Exception.class)
    public void createStudentWrongEmail(){
        Student student = new Student();
        student.setName("Ivanov");
        student.setEmail("ivanov@mail.ru1");
        studentService.createStudent(student);
    }

    @Test(expected = Exception.class)
    public void createStudentWrongPassword(){
        Student student = new Student();
        student.setName("Ivanov");
        student.setEmail("ivanov@mail.ru");
        student.setPassword("qwer1");
        studentService.createStudent(student);
    }

    @Test
    public void updateStudent() throws Exception {
    }

}