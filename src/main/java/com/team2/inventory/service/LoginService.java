
package com.team2.inventory.service;

import com.team2.inventory.repository.UserRepository;
import com.team2.inventory.springSecurity.MainUser;
import com.team2.inventory.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//Spring Security interface implemented which expects: UserDetails loadUserByUsername(String username) throws UsernameNotFoundException



@Service
public class LoginService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

   @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User does not exist");
            }
        return new MainUser(user);
    }
}

