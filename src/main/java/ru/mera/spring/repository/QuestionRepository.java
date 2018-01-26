package ru.mera.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mera.spring.entity.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer> {
}
