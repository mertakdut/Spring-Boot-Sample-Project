package com.demo.bankapp.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.demo.bankapp.model.User;
import com.demo.bankapp.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
class DatabaseMocker {

	@Bean
	CommandLineRunner initDatabase(UserRepository repository) {
		return args -> {
			repository.save(new User("Bilbo Baggins", "burglar", "128185822"));
			repository.save(new User("Frodo Baggins", "thief", "21528582"));
		};
	}
}
