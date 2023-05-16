package com.team2.inventory.controller;

import com.team2.inventory.model.User;
import com.team2.inventory.service.CaseImplementation;
import com.team2.inventory.service.UserImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//userdetails as session model name
//user as attributename for employee
//Remember to use Controller not RESTController...

@RequestMapping("/admin")
@SessionAttributes("userdetails") // name of the model we need to store in the session
@Controller
public class AdminController {

	@Autowired
	private UserImplementation userImplementation;
	@Autowired
	private CaseImplementation caseImplementation;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// Allow ADMIN to view their personal dashboard using httpSession
	@GetMapping("/dashboard")
	public String getDashboard(Model model) {
		User user = new User();
		int employees = userImplementation.findAll().size();
		int cases = caseImplementation.findAllCases().size();

		model.addAttribute("user", user);
		model.addAttribute("employees", employees);
		model.addAttribute("cases", cases);
		return "AdminDashboard";
	}

	@RequestMapping(value = "/list")
	public String listusers(Model model) {
		model.addAttribute("users", userImplementation.findAll());
		return "ViewAllUsers";
	}

	// View individual user detail based on employee id
	@RequestMapping(value = "/showuser/{id}", method = RequestMethod.GET)
	public String showdetail(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("user", userImplementation.findById(id));

		return "ShowUserDetail";
	}

	@GetMapping(value = "/adduser")
	public String getUserCreationForm(Model model) {
		model.addAttribute("user", new User());
		return "UserCreation";
	}

	@PostMapping(value = "/saveuser")
	public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "Unsuccessful! Please try again.");
			return "UserCreation";
		}

		String pwd = user.getPassword();
		String encryptPwd = bCryptPasswordEncoder.encode(pwd);
		user.setPassword(encryptPwd);
		userImplementation.createUser(user);
		return "forward:/admin/list";
	}

	@PostMapping(value = "/updateuser")
	public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "Unsuccessful! Please try again.");
			return "UserCreation";
		}

		if(user.getPassword() == null || user.getPassword().isEmpty()) {
			user.setPassword(userImplementation.findById(user.getId()).getPassword());
		}else{
			String pwd = user.getPassword();
			String encryptPwd = bCryptPasswordEncoder.encode(pwd);
			user.setPassword(encryptPwd);
		}
		userImplementation.updateUser(user);
		return "forward:/admin/list";
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String editForm(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("user", userImplementation.findById(id));
		return "EditUserDetails";
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteUser(Model model, @PathVariable("id") Integer id) {
		userImplementation.deleteUser(userImplementation.findById(id));
		return "forward:/admin/list";
	}

}
