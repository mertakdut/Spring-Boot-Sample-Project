package com.demo.bankapp.response;

import java.util.List;

import com.demo.bankapp.model.User;

import lombok.Data;

@Data
public class FindAllUsersResponse {
	List<User> userList;
}
