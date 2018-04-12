package com.greenfoxacademy.baloghdominik.mysql.repositories;

import com.greenfoxacademy.baloghdominik.mysql.models.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.persistence.OrderBy;
import javax.persistence.criteria.Order;
import java.util.List;

@Component
public interface TodoRepository extends CrudRepository<Todo, Long> {

    public List<Todo> findBydone(Boolean bool);

    public List<Todo> findByurgent(Boolean bool);
}

