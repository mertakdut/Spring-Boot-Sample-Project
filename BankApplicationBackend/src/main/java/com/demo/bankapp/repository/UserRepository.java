package com.demo.bankapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.demo.bankapp.model.User;

// In fact, we don’t even have to annotate this if it’s top-level and visible.
@RepositoryRestResource(exported = false)
@CrossOrigin(origins = "*")
public interface UserRepository extends JpaRepository<User, Long> {

}
