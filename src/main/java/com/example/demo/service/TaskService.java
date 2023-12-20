package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.TaskDto;
import com.example.demo.model.Task;

public interface TaskService {

	void save(TaskDto taskDto);
	
	Task findById(Long id);
	
	Task findByTitle(String title);
	
	String assignTo(Long id, Long user_id);
	
	String editTask(TaskDto taskDto);
	
	List<Task> findAll();
	
	boolean delete(Long id);
	
	List<Task> findAllTaskByUser(String email);
	
	String saveTask(Long id,String status);
	
	boolean saveStatus(Task task,Long id);
}
