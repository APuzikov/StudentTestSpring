package ru.mera.spring.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mera.spring.entity.Student;
import ru.mera.spring.entity.StudentTest;
import ru.mera.spring.entity.StudentTestQuestion;
import ru.mera.spring.repository.StudentTestQuestionRepository;
import ru.mera.spring.repository.StudentTestRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest
public class StudentTestServiceTest {

    @Autowired
    private StudentTestService studentTestService;

    @Autowired
    private StudentTestRepository studentTestRepository;

    @Autowired
    private StudentTestQuestionRepository studentTestQuestionRepository;

    @Test
    public void createStudentTest() throws Exception {

        Student student = new Student();
        student.setName("Petrov");
        student.setEmail("petrov@mail.ru");
        student.setPassword("qwer!");
        student.setId(3);

        studentTestService.createStudentTest(student, 6);
    }

    @Test(expected = Exception.class)
    public void createStudentTestNullStudent(){
        Student student = null;
        studentTestService.createStudentTest(student, 6);
    }

    @Test(expected = Exception.class)
    public void createStudentTestNoStudent(){
        Student student = new Student();
        student.setId(100);
        studentTestService.createStudentTest(student, 6);
    }

    @Test(expected = Exception.class)
    public void createStudentTestInvalidNumbers(){
        Student student = new Student();
        student.setId(3);
        studentTestService.createStudentTest(student, 100);
    }


    @Test
    public void updateStudentTest(){

        StudentTest studentTest = studentTestRepository.findOne(19);

        Date date = new Date();
        Timestamp testDate = new Timestamp(date.getTime());
        studentTest.setTestDate(testDate);

        Date date1 = new Date();
        Timestamp endDate = new Timestamp(date1.getTime());
        studentTest.setEndDate(endDate);

        studentTestRepository.save(studentTest);
    }

    @Test(expected = Exception.class)
    public void updateStudentTestNull(){
        StudentTest studentTest = null;
        studentTestService.updateStudentTest(studentTest);
    }

    @Test(expected = Exception.class)
    public void updateStudentTestNoStudent() {
        StudentTest studentTest = new StudentTest();
        studentTest.setStudentId(100);
        studentTestService.updateStudentTest(studentTest);
    }

    @Test
    public void updateStudentTestNoCreateDate() {
        StudentTest studentTest = new StudentTest();
        studentTest.setId(19);
        studentTest.setStudentId(3);
        studentTestService.updateStudentTest(studentTest);
    }

    @Test
    public void updateStudentTestNoTestDate() {
        StudentTest studentTest = new StudentTest();
        studentTest.setStudentId(3);
        studentTest.setId(19);

        Date date = new Date();
        Timestamp createDate = new Timestamp(date.getTime());
        studentTest.setCreateDate(createDate);
        studentTestService.updateStudentTest(studentTest);

        Timestamp dbDate = studentTestRepository.findOne(studentTest.getId()).getCreateDate();

        assertNotEquals(createDate, dbDate);
    }

    @Test
    public void createStudentTestAnswers(){
        StudentTestQuestion studentTestQuestion = studentTestQuestionRepository.findOne(82);
        List<Integer> answerIds = new ArrayList<>();
        answerIds.add(5);
        answerIds.add(6);

        studentTestService.createStudentTestAnswers(answerIds, studentTestQuestion);
    }

    @Test(expected = Exception.class)
    public void createStudentTestAnswersNull(){
        StudentTestQuestion studentTestQuestion = null;
        List<Integer> answerIds = new ArrayList<>();

        studentTestService.createStudentTestAnswers(answerIds, studentTestQuestion);
    }

    @Test(expected = Exception.class)
    public void createStudentTestAnswersEmptyList(){
        StudentTestQuestion studentTestQuestion = new StudentTestQuestion();
        List<Integer> answerIds = new ArrayList<>();

        studentTestService.createStudentTestAnswers(answerIds, studentTestQuestion);
    }

    @Test(expected = Exception.class)
    public void createStudentTestAnswersNoAnswer(){
        StudentTestQuestion studentTestQuestion = new StudentTestQuestion();
        List<Integer> answerIds = new ArrayList<>();
        answerIds.add(1000);
        answerIds.add(2000);

        studentTestService.createStudentTestAnswers(answerIds, studentTestQuestion);
    }

    @Test(expected = Exception.class)
    public void createStudentTestAnswersNoQuestion(){
        StudentTestQuestion studentTestQuestion = new StudentTestQuestion();
        studentTestQuestion.setQuestionId(100);
        List<Integer> answerIds = new ArrayList<>();
        answerIds.add(1);
        answerIds.add(2);

        studentTestService.createStudentTestAnswers(answerIds, studentTestQuestion);
    }

    @Test(expected = Exception.class)
    public void createStudentTestAnswersNoTest(){
        StudentTestQuestion studentTestQuestion = new StudentTestQuestion();
        studentTestQuestion.setQuestionId(12);
        studentTestQuestion.setId(1000);
        List<Integer> answerIds = new ArrayList<>();
        answerIds.add(1);
        answerIds.add(2);

        studentTestService.createStudentTestAnswers(answerIds, studentTestQuestion);
    }

}