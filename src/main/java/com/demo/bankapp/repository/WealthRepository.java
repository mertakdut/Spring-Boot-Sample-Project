package com.demo.bankapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.demo.bankapp.model.Wealth;

@RepositoryRestResource(exported = false)
public interface WealthRepository extends JpaRepository<Wealth, Long> {

}
