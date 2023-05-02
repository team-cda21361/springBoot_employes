package fr.spear.employes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.spear.employes.bean.User;
import fr.spear.employes.service.UserService;

@Controller
public class RegisterController {
	
	@Autowired
	private UserService  userService;


	@GetMapping("/register")
	public String register(User user) {
		
		return "user/register";
	}
	
	@PostMapping("/register")
	public String register(@Validated User user, BindingResult bindingResult) {
		userService.ajoutUser(user);
		return "user/register";
	}
}
