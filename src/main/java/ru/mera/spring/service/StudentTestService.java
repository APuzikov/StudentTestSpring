package ru.mera.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.mera.spring.entity.*;
import ru.mera.spring.repository.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Service
public class StudentTestService {

    @Autowired
    private StudentTestRepository studentTestRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentTestQuestionRepository studentTestQuestionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private StudentTestAnswerRepository studentTestAnswerRepository;

    public void createStudentTest(Student student, int countOfQuestions){

        Assert.notNull(student, "Student not found!");

        Student studentFromDB = studentRepository.findOne(student.getId());
        Assert.notNull(studentFromDB, "Student not found!");

        Assert.isTrue(countOfQuestions > 0 && countOfQuestions < questionRepository.count(), "Invalid number of questions");

        StudentTest studentTest = new StudentTest();

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        studentTest.setCreateDate(timestamp);
        studentTest.setStudentId(student.getId());

        studentTest = studentTestRepository.save(studentTest);

        //массив с номерами вопросов
            int[] numbersOfQuestions = new int[countOfQuestions];
            for (int i = 0; i < countOfQuestions; i++) {
                while (true) {
                    int value = getRandomNumber();
                    if (checkNumber(value, numbersOfQuestions)) {
                        numbersOfQuestions[i] = value;
                        break;
                    }
                }
            }

        for (int number : numbersOfQuestions){
            StudentTestQuestion studentTestQuestion = new StudentTestQuestion();
            studentTestQuestion.setQuestionId(number);
            studentTestQuestion.setStudentTestId(studentTest.getId());

            studentTestQuestionRepository.save(studentTestQuestion);
        }
    }

    public void updateStudentTest(StudentTest studentTest){

        Assert.notNull(studentTest, "StudentTest can't be null");

        Student student = studentRepository.findOne(studentTest.getStudentId());
        Assert.notNull(student, "Student not found!");

        StudentTest studentTest1 = studentTestRepository.findOne(studentTest.getId());
        Assert.notNull(studentTest1, "Test not found!");

        studentTest.setCreateDate(studentTest1.getCreateDate());

        studentTestRepository.save(studentTest);

    }

    public void createStudentTestAnswers(List<Integer> answerIds, StudentTestQuestion studentTestQuestion){

        Assert.notNull(studentTestQuestion, "StudentTestQuestion can't be null!");
        
        Assert.notEmpty(answerIds, "List of answers is empty!");

        for (Integer answerId : answerIds){
            Answer answer = answerRepository.findOne(answerId);
            Assert.notNull(answer, "Answer not found!");
        }

        int questionId = studentTestQuestion.getQuestionId();
        Question question = questionRepository.findOne(questionId);
        Assert.notNull(question, "Question not found!");

        int testId = studentTestQuestion.getStudentTestId();
        StudentTest studentTest = studentTestRepository.findOne(testId);
        Assert.notNull(studentTest, "Test not found!");

        for (Integer answerId : answerIds){
            StudentTestAnswers studentTestAnswers = new StudentTestAnswers();
            studentTestAnswers.setAnswerId(answerId);
            studentTestAnswers.setStudentTestQuestionId(studentTestQuestion.getId());
            studentTestAnswerRepository.save(studentTestAnswers);
        }

    }

    private int getRandomNumber() {
        return (int)Math.round(Math.random() * (questionRepository.count() - 1)) + 1;
    }

    private boolean checkNumber(int number, int[] numbersOfQuestion){
        boolean check = true;
        for (int i : numbersOfQuestion){
            if (i == number){
                check = false;
            }
        }
        return check;
    }

}
