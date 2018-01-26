package ru.mera.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mera.spring.entity.Answer;
import ru.mera.spring.entity.StudentTestAnswers;
import ru.mera.spring.entity.StudentTestQuestion;

import java.util.List;

@Repository
public interface StudentTestAnswerRepository extends CrudRepository<StudentTestAnswers, Integer> {

    List<StudentTestAnswers> findByStudentTestQuestionId(int studentTestQuestionId);

}
