package ru.mera.spring.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mera.spring.entity.Answer;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest
public class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    public void findById(){

        List<Answer> list = answerRepository.findByQuestionId(5);

        assertNotNull(list);

        list.stream().forEach(answer -> System.out.println(answer));
        list.stream().forEach(answer -> assertEquals(5, answer.getQuestionId()));
    }

}