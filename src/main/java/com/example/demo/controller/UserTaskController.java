package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TaskServiceImpl;
import com.example.demo.service.UserServiceImpl;

@Controller
@RequestMapping("/usertask")
public class UserTaskController {

	private TaskServiceImpl serviceImpl;
	private UserRepository userRepository;

	private UserServiceImpl userServiceImpl;

	public UserTaskController(TaskServiceImpl serviceImpl, UserServiceImpl userServiceImpl,
			UserRepository userRepository) {
		super();
		this.serviceImpl = serviceImpl;
		this.userServiceImpl = userServiceImpl;
		this.userRepository = userRepository;
	}

	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	public String getAll(Model model,Principal principal) {
		String userEmail = principal.getName();
		List<Task> tasks = serviceImpl.findAllTaskByUser(userEmail);

		for (Task task : tasks) {
			if (userEmail != null) {
				User user = userRepository.findByEmail(userEmail);
				if (user != null) {
					task.setAssignedTo(user.getFirstname() + " " + user.getLastname());
				}
			} else {
				task.setAssignedTo("Not Assigned");
			}
		}
		model.addAttribute("tasks", tasks);
		model.addAttribute("task", new Task());
		return "user/user_tasks";
	}

	@RequestMapping(value = "/editsave/{id}", method = RequestMethod.POST)
	public String saveTask(@ModelAttribute Task task, @PathVariable Long id) {
		String status = task.getStatus();
		System.out.println("status : " + status);
		System.out.println("Id : " + id);
		boolean saveStatus = serviceImpl.saveStatus(task, id);
		if (saveStatus) {
			return "redirect:/usertask/getall";
		}
		{
			return "redirect:/user/index";
		}
	}
}
