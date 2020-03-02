package io.javabrains;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import io.javabrains.springbootstarter.configuration.PersistenceContext;

@Import(PersistenceContext.class)
@SpringBootApplication
public class CourseApiDataNewApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseApiDataNewApplication.class, args);
	}
}
