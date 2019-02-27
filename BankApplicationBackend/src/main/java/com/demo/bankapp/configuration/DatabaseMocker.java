package com.demo.bankapp.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.demo.bankapp.controller.UserController;
import com.demo.bankapp.repository.UserRepository;
import com.demo.bankapp.request.CreateNewUserRequest;

@Configuration
class DatabaseMocker {

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	CommandLineRunner initDatabase(UserRepository repository, UserController userController) {
		return args -> {
			CreateNewUserRequest cnuRequest = new CreateNewUserRequest();
			cnuRequest.setUsername("Mert");
			cnuRequest.setPassword("mert123");
			cnuRequest.setTcno("21412322112");

			CreateNewUserRequest cnuRequest2 = new CreateNewUserRequest();
			cnuRequest2.setUsername("Mert2");
			cnuRequest2.setPassword("mert1234");
			cnuRequest2.setTcno("23141232212");

			userController.createNewUser(cnuRequest);
			userController.createNewUser(cnuRequest2);
			// repository.save(new User("Bilbo Baggins", "burglar", "128185822"));
			// repository.save(new User("Frodo Baggins", "thief", "21528582"));
		};
	}
}
