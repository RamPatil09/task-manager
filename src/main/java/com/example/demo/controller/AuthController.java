package com.example.demo.controller;

import java.util.Random;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.service.UserServiceImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AuthController {

	private UserServiceImpl serviceImpl;
	Random random = new Random(1001);

	public AuthController(UserServiceImpl serviceImpl) {
		super();
		this.serviceImpl = serviceImpl;
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String home() {

		return "index";
	}

	@PreAuthorize(value = "ADMIN")
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("userDto", userDto);
		return "auth/registration";
	}

	@RequestMapping(value = "/register/save", method = RequestMethod.POST)
	public String saveRegister(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult result) {

		User existingUser = serviceImpl.findByEmail(userDto.getEmail());

		if (existingUser != null) {
			// User is already registered, add a custom error to the result
			result.rejectValue("email", "userDto.email",
					"This email is already registered. Please use a different email address.");
		}

		if (result.hasErrors()) {
			return "auth/registration";
		}

		serviceImpl.save(userDto);

		return "redirect:/index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpSession session) {
		session.removeAttribute("success");
		return "auth/login";
	}

	@RequestMapping(value = "/forgot_password", method = RequestMethod.GET)
	public String forgotPassword() {
		return "auth/forgot_password";
	}

	@RequestMapping(value = "/send_otp", method = RequestMethod.POST)
	public String sendOtp(@RequestParam("email") String email, HttpSession session) {

		User user = serviceImpl.findByEmail(email);

		if (user == null) {
			session.setAttribute("error", "User not found. Please check the email address and try again.");
			return "auth/forgot_password";
		} else {
			session.setAttribute("email", email);
			String otp = serviceImpl.generateOtp();
			session.setAttribute("otp", otp);
			String subject = "Password Reset OTP";
			String body = "Dear User,\n\n"
					+ "You have requested to reset your password. Your One-Time Password (OTP) for password reset is:\n\n"
					+ "**** " + otp + " ****\n\n"
					+ "Please use this OTP to complete the password reset process. If you did not request this change, please ignore this email.\n\n"
					+ "Thank you,\n" + "Our Company";
			serviceImpl.sendMailTo(email, subject, body);
			session.setAttribute("success", "OTP sent successfully!!");
			session.removeAttribute("error"); // Clear any previous error messages
			return "auth/verify_otp";
		}
	}

	@RequestMapping(value = "/verify_otp", method = RequestMethod.POST)
	public String verifyOTP(@RequestParam("otp") String enteredOtp, HttpSession session, Model model) {
		String generatedOtp = (String) session.getAttribute("otp");

		if (generatedOtp == null || !generatedOtp.equals(enteredOtp)) {
			model.addAttribute("error", "Incorrect OTP. Please enter the correct OTP.");
			session.removeAttribute("success"); // Clear any previous success messages
			return "auth/verify_otp";
		}
		session.removeAttribute("error"); // Clear any previous error messages
		return "auth/new_password";
	}

	@RequestMapping(value = "/reset_password", method = RequestMethod.POST)
	public String resetPassword(@RequestParam("password") String password, HttpSession session) {
		String email = (String) session.getAttribute("email");
		serviceImpl.saveUpdatedPassword(email, password);
		session.removeAttribute("success");
		session.setAttribute("success", "Password changed successfully!");
		return "auth/login";
	}
}
