package com.task.todo.repositories;

import com.task.todo.models.Owner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OwnerRepository extends CrudRepository<Owner, Long> {
  void deleteOwnerByName(String name);
  Owner save(Owner owner);
}