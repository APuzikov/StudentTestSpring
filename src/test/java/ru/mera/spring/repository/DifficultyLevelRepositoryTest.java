package ru.mera.spring.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mera.spring.entity.DifficultyLevel;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest

public class DifficultyLevelRepositoryTest {

    @Autowired
    private DifficultyLevelRepository difficultyLevelRepository;

    @Test
    public void check(){

        DifficultyLevel difficultyLevel = difficultyLevelRepository.findOne(2);

        assertEquals("NORMAL", difficultyLevel.getSystemName());

        System.out.println(difficultyLevel);
    }

}