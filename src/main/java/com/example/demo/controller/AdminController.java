package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.dto.TaskDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final UserRepository userRepository;

	public AdminController(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String home() {
		return "admin/admin";
	}

	@RequestMapping(value = "/addtask", method = RequestMethod.GET)
	public String addTask(Model model) {
		TaskDto taskDto = new TaskDto();
		List<User> users = userRepository.findAll();
		model.addAttribute("taskDto", taskDto);
		model.addAttribute("users", users);
		return "admin/task_add";
	}
}
