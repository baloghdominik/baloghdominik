package com.greenfoxacademy.baloghdominik.mysql.repositories;

import com.greenfoxacademy.baloghdominik.mysql.models.Todo;
import com.greenfoxacademy.baloghdominik.mysql.models.UserModels;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserModelsRepository extends CrudRepository<UserModels, Long> {

    public UserModels findByUsername(String username);
}
