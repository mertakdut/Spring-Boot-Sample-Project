package com.demo.bankapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.demo.bankapp.model.User;

@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
	
	User findByTcno(String tcno);

}
