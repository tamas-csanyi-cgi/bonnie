package com.cgi.hexagon.h2storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
public class H2StorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(H2StorageApplication.class, args);
	}

}
