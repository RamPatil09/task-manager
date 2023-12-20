package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TaskDto;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    private UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        super();
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void save(TaskDto taskDto) {
        Task task = new Task();
        try {
            if (taskDto != null) {
                task.setTitle(taskDto.getTitle());
                task.setDescription(taskDto.getDescription());
                task.setAssignedTo(taskDto.getAssignedTo());
                task.setDueDate(taskDto.getDueDate());
                task.setCompletedDate(taskDto.getCompletedDate());
                task.setStatus(taskDto.getStatus());
                task.setPriority(taskDto.getPriority());
                taskRepository.save(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Task findByTitle(String title) {
        try {
            if (title != null) {
                return taskRepository.findByTitle(title).orElseThrow(NotFoundException::new);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String assignTo(Long id, Long user_id) {
        try {
            if (id != 0 && user_id != null) {
                Optional<Task> optionalTask = taskRepository.findById(id);
                Optional<User> optionaluser = userRepository.findById(user_id);

                if (optionalTask.isPresent() && optionaluser != null) {
                    Task task = optionalTask.get();
                    User user = optionaluser.get();
                    task.setAssignedTo(user.getFirstname() + " " + user.getLastname());
                    taskRepository.save(task); // Save the changes to the task
                } else {
                    return "Task or user not found!!";
                }
            } else {
                return "Invalid id or email";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error assigning task";
        }

        return "Task assigned successfully";
    }

    @Override
    public String editTask(TaskDto taskDto) {
        try {
            if (taskDto != null) {
               Task task = taskRepository.findById(taskDto.getId()).get();

                    task.setTitle(taskDto.getTitle());
                    task.setDescription(taskDto.getDescription());
                    task.setAssignedTo(taskDto.getAssignedTo());
                    task.setDueDate(taskDto.getDueDate());
                    task.setCompletedDate(taskDto.getCompletedDate());
                    task.setStatus(taskDto.getStatus());
                    task.setPriority(taskDto.getPriority());
                    taskRepository.save(task); // Save the changes to the task
                    return "Saved";
                } else {
                    return "Task not found!!";
                }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error Editing task";
        }

    }

    @Override
    public List<Task> findAll() {
        List<Task> list = taskRepository.findAll();
        return list;
    }

    @Override
    public Task findById(Long id) {
        Task task = taskRepository.findById(id).get();
        return task;
    }

    @Override
    public boolean delete(Long id) {
        taskRepository.deleteById(id);
        return true;
    }

    public List<Task> getTasksForUser(String userEmail) {
        List<Task> tasks = taskRepository.findByAssignedTo(userEmail);
        return tasks;
    }

    @Override
    public List<Task> findAllTaskByUser(String email) {
        List<Task> listOfTask = taskRepository.findByAssignedTo(email);
        System.out.println(listOfTask);
        return listOfTask;
    }

    @Override
    public String saveTask(Long id, String status) {
        Task task = taskRepository.findById(id).get();
        LocalDate localDate = LocalDate.now();
        task.setCompletedDate(localDate.toString());
        task.setStatus(status);
        taskRepository.save(task);
        return "Update Successfully";
    }

    @Override
    public boolean saveStatus(Task task, Long id) {
        String status = task.getStatus();
        Task task2 = taskRepository.findById(id).get();
        if (status.equalsIgnoreCase("inprogress")) {
            task2.setStatus(status);
            taskRepository.save(task2);
            return true;
        } else if (status.equalsIgnoreCase("completed")) {
            task2.setStatus(status);
            LocalDate localDate = LocalDate.now();
            task2.setCompletedDate(localDate.toString());
            taskRepository.save(task2);
            return true;
        }

        return false;
    }

}
