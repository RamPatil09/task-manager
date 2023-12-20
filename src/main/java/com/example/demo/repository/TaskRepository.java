package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

	Optional<Task> findByTitle(String title);

	@Query(value = "SELECT * FROM task t WHERE t.assigned_to=:userEmail", nativeQuery = true)
	List<Task> findByAssignedTo(String userEmail);
}
