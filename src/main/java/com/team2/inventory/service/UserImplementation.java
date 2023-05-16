package com.team2.inventory.service;

import com.team2.inventory.interfacemethods.UserInterface;
import com.team2.inventory.model.User;
import com.team2.inventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserImplementation implements UserInterface {

	@Autowired
	private UserRepository userRepository;

	// Create a user
	@Transactional
	public void createUser(User user) {
		userRepository.save(user);
	}

	// Read individual user
	@Transactional
	public User getUser(Integer id) {
		return userRepository.getOne(id);
	}

	// Update User
	@Transactional
	public void updateUser(User user) {
		userRepository.save(user);
	};

	// Delete User
	@Transactional
	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	@Override
	public User findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		return user;
	}

	// Find by UserID
	@Transactional
	public User findById(Integer id) {
		return userRepository.findById(id).get();
	}

	// Find by UserName
	@Transactional
	public User getUserByUsername(String name) {
		User user = userRepository.findByUsername(name);
		return user;
	}

	// Pagination
	public List<User> findAll() {
		return userRepository.findAll();
	}
}
