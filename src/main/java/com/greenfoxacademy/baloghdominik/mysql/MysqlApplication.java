package com.greenfoxacademy.baloghdominik.mysql;

import com.greenfoxacademy.baloghdominik.mysql.models.Todo;
import com.greenfoxacademy.baloghdominik.mysql.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class MysqlApplication implements CommandLineRunner{

	@Autowired
	TodoRepository todoRepository;

	public static void main(String[] args) {
		SpringApplication.run(MysqlApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}