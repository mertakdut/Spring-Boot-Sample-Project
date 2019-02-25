package com.demo.bankapp.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.demo.bankapp.controller.UserController;
import com.demo.bankapp.repository.UserRepository;
import com.demo.bankapp.request.CreateNewUserRequest;

@Configuration
class DatabaseMocker {

	@Bean
	CommandLineRunner initDatabase(UserRepository repository, UserController userController) {
		return args -> {
			CreateNewUserRequest cnuRequest = new CreateNewUserRequest();
			cnuRequest.setUsername("Mert");
			cnuRequest.setPassword("mert123");
			cnuRequest.setTcno("2141232212");
			
			userController.createNewUser(cnuRequest);
			// repository.save(new User("Bilbo Baggins", "burglar", "128185822"));
			// repository.save(new User("Frodo Baggins", "thief", "21528582"));
		};
	}
}
