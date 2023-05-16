package com.team2.inventory.interfacemethods;

import com.team2.inventory.model.User;

public interface UserInterface {

    public void createUser(User user);
    public User findById(Integer id);
    public void deleteUser(User user);
    public User findByUsername(String username);
}
