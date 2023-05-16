package com.team2.inventory.repository;

import com.team2.inventory.model.Role;
import com.team2.inventory.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Integer> {
    public User findUserById(int id);
    public User findByUsername(String username);
    public List<User> findUsersByRole(Role role);
}
