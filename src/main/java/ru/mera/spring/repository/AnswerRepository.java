package ru.mera.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mera.spring.entity.Answer;

import java.util.List;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Integer>{

    List<Answer> findByQuestionId(Integer questionId);
}
