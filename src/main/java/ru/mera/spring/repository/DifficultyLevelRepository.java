package ru.mera.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mera.spring.entity.DifficultyLevel;

@Repository
public interface DifficultyLevelRepository extends CrudRepository<DifficultyLevel, Integer> {
}
