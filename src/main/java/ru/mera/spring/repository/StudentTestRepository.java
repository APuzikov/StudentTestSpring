package ru.mera.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mera.spring.entity.StudentTest;

import java.util.List;

@Repository
public interface StudentTestRepository extends CrudRepository<StudentTest, Integer> {

    List<StudentTest> findByStudentId(int studentId);
}
