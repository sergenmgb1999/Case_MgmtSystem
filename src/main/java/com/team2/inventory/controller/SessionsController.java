package com.team2.inventory.controller;

import com.team2.inventory.model.LoginModel;
import com.team2.inventory.model.Role;
import com.team2.inventory.model.User;
import com.team2.inventory.service.UserImplementation;
import com.team2.inventory.springSecurity.MainUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("userdetails")
public class SessionsController {
    @Autowired
    private UserImplementation userImplementation;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping("/index")
    public String getIndexPage(Model model, SessionStatus sessionStatus) {
        return "Index";
    }

    @RequestMapping("/login")
    public String login(Model model, SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        model.addAttribute("user", new LoginModel());
        return "Login";
    }

    @RequestMapping("/register")
    public String register(Model model, SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        model.addAttribute("user", new User());
        return "Register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model, SessionStatus sessionStatus) {
        if(userImplementation.getUserByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username already exists");
            return "Register";
        }
        sessionStatus.setComplete();
        String pwd = user.getPassword();
        String encryptPwd = bCryptPasswordEncoder.encode(pwd);
        user.setPassword(encryptPwd);
        user.setRole(Role.EMPLOYEE);
        userImplementation.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "Index";
    }

    @GetMapping("/error")
    public String forbidden() {
        return "forbidden";
    }

    @RequestMapping("/")
    public String getdashboard(@AuthenticationPrincipal MainUser mainUser, HttpServletRequest request, Model model, HttpSession httpSession) {
        if(mainUser != null) {
            User user = userImplementation.getUserByUsername(mainUser.getUsername());

            httpSession.setAttribute("userdetails", user);
            if (user.getRole() == Role.ADMIN || user.getRole() == Role.EMPLOYEE) {
                return "redirect:admin/dashboard";
            } else {
                return "redirect:/index";
            }
        }
        return "redirect:/index";
    }
}

