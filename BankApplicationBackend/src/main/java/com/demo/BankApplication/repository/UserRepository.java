package com.demo.BankApplication.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.demo.BankApplication.model.User;

// In fact, you don’t even have to annotate this if it’s top-level and visible.
//@Repository
@RepositoryRestResource
@CrossOrigin(origins = "*")
public interface UserRepository extends CrudRepository<User, Long> {

}
