package com.demo.BankApplication.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
@SuppressWarnings("unused")
/* The IDE is tricked here.
 * We actually use all these data without having dull accessor methods thanks to lombok's 'DATA' annotation.
 * That is why warnings are suppressed.
 */
public class User {

	private @Id @GeneratedValue Long id;
	private String username;
	private String password;
	private String tcno;

	private User() {
	}

	public User(String username, String password, String tcno) {
		this.username = username;
		this.password = password;
		this.tcno = tcno;
	}
}
