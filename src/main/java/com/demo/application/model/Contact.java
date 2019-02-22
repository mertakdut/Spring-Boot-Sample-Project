package com.demo.application.model;

public class Contact {
	private int id;
	private String name;
	private String email;
	private String address;
	private String telephone;

	public Contact() {
	}

	public Contact(String name, String email, String address, String telephone) {
		this.setName(name);
		this.setEmail(email);
		this.setAddress(address);
		this.setTelephone(telephone);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	// getters and setters
}
