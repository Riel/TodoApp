package com.task.todo.repositories;

import com.task.todo.models.Owner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OwnerRepository extends CrudRepository<Owner, Long> {
  void deleteOwnerByName(String name);
  Optional<Owner> findFirstByName(String name);
  Owner save(Owner owner);
}