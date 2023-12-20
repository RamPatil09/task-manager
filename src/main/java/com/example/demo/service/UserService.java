package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;

public interface UserService {

	void save(UserDto dto);

	User findByEmail(String email);

	User login(String email);
	
	void saveUpdatedPassword(String email, String password);
}
