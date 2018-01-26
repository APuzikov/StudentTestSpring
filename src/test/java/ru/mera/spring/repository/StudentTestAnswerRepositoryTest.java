package ru.mera.spring.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mera.spring.entity.StudentTestAnswers;
import ru.mera.spring.entity.StudentTestQuestion;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest

public class StudentTestAnswerRepositoryTest {

    @Autowired
    private StudentTestAnswerRepository studentTestAnswerRepository;

    @Test
    public void findByStudentTestQuestionId() throws Exception {

        List<StudentTestAnswers> list = studentTestAnswerRepository.findByStudentTestQuestionId(47);

        assertNotNull(list);

        list.stream().forEach(sta -> System.out.println(sta));

    }

}