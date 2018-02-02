package ru.mera.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mera.spring.entity.Student;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {

    Student findByEmail(String email);
    //List<Student> findByEmail(String email);
}
