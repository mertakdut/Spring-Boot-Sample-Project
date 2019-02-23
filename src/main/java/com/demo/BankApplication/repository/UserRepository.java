package com.demo.BankApplication.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.BankApplication.model.User;

// In fact, you don’t even have to annotate this if it’s top-level and visible.
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
