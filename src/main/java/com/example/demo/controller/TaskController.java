package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.dto.TaskDto;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TaskServiceImpl;

@Controller
@RequestMapping("/task")
public class TaskController {

	private TaskServiceImpl taskServiceImpl;
	private UserRepository userRepository;

	public TaskController(TaskServiceImpl taskServiceImpl,UserRepository userRepository) {
		super();
		this.taskServiceImpl = taskServiceImpl;
		this.userRepository = userRepository;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveTask(@ModelAttribute("taskDto") TaskDto taskDto) {
		taskServiceImpl.save(taskDto);
		return "redirect:/task/getall";
	}

	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	public String getAllTasks(Model model) {
	    List<Task> allTasks = taskServiceImpl.findAll();
	    List<User> users = new ArrayList<>();

	    for (Task task : allTasks) {
	        String email = task.getAssignedTo();
	        if (email != null) {
	            User user = userRepository.findByEmail(email);
	            if (user != null) {
	                task.setAssignedTo(user.getFirstname()+ " "+user.getLastname());
	            }
	        } else {
	            task.setAssignedTo("Not Assigned");
	        }
	    }

	    model.addAttribute("tasks", allTasks);
	    return "all_tasks";
	}


	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable Long id, Model model) {
		Task task = taskServiceImpl.findById(id);
		String email = task.getAssignedTo();
		User user = userRepository.findByEmail(email);
		if (user != null){
			String firstname = user.getFirstname();
			String lastname = user.getLastname();
			task.setAssignedTo(firstname+ " "+lastname);
		}
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		model.addAttribute("task", task);
		return "edit_task";
	}
	
	@RequestMapping(value = "/editsave", method = RequestMethod.POST)
	public String saveEdit(@ModelAttribute("task") TaskDto taskDto) {
		taskServiceImpl.editTask(taskDto);
		return "redirect:/task/getall";
	}

	@RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
	public String delete(@PathVariable Long id) {
		if(taskServiceImpl.delete(id)) 
			return "redirect:/task/getall";
		return null;
	}
}
