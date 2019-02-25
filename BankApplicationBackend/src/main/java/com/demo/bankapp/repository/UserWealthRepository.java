package com.demo.bankapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.demo.bankapp.model.UserWealth;

@RepositoryRestResource(exported = false)
public interface UserWealthRepository extends JpaRepository<UserWealth, Long> {

}
