package ru.mera.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mera.spring.entity.StudentTestQuestion;

import java.util.List;

@Repository
public interface StudentTestQuestionRepository extends CrudRepository<StudentTestQuestion, Integer> {

    List<StudentTestQuestion> findByStudentTestId(int studentTestId);
}
