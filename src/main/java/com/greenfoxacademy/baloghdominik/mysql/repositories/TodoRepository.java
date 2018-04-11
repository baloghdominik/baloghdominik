package com.greenfoxacademy.baloghdominik.mysql.repositories;

import com.greenfoxacademy.baloghdominik.mysql.models.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TodoRepository extends CrudRepository<Todo, Long> {

    public List<Todo> findBydone(Boolean bool);
}

