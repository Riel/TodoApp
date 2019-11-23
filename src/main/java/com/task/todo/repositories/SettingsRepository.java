package com.task.todo.repositories;

import com.task.todo.models.Setting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends CrudRepository<Setting, Long> {
}
