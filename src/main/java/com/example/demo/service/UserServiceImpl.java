package com.example.demo.service;

import java.util.Arrays;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.UserDto;
import com.example.demo.model.ERole;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import jakarta.mail.internet.MimeMessage;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender javaMailSender;
	Random random = new Random(100001);

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void save(UserDto dto) {
		User user = new User();
		user.setFirstname(dto.getFirstname());
		user.setLastname(dto.getLastname());
		user.setEmail(dto.getEmail());
		user.setPhnumber(dto.getPhnumber());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));

		String role2 = dto.getRole();
		if (role2 == null) {
			role2 = ERole.ROLE_USER.name();
		}
		Role role = roleRepository.findByName(role2);

		if (role == null) {
			role = checkRoleExist();
		}
		user.setRole(Arrays.asList(role));
		userRepository.save(user);
	}

	private Role checkRoleExist() {
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		return roleRepository.save(role);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User login(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}

	public String generateOtp() {

		 int otpValue = 100000 + random.nextInt(900000);
		return String.valueOf(otpValue);
	}

	public void sendMailTo(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		javaMailSender.send(message);
	}

	@Override
	public void saveUpdatedPassword(String email, String password) {
		User user = userRepository.findByEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
	}

}
