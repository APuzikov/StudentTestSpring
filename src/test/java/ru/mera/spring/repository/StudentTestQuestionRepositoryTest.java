package ru.mera.spring.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mera.spring.entity.StudentTestQuestion;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest
public class StudentTestQuestionRepositoryTest {

    @Autowired
    private StudentTestQuestionRepository studentTestQuestionRepository;

    @Test
    public void findByStudentTestId() throws Exception {

        List<StudentTestQuestion> list = studentTestQuestionRepository.findByStudentTestId(11);

        assertNotNull(list);

        list.stream().forEach(stq -> System.out.println(stq));
    }

}